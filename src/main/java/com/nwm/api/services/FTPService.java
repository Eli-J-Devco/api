/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *********************************************************/

package com.nwm.api.services;

import com.nwm.api.entities.CSVHeaderEntity;
import com.nwm.api.entities.FTPEntity;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nwm.api.DBManagers.DB;

@Service
public class FTPService extends DB {

    private static final String CSV_DELIMITER = ";";
    private static final int MAX_RETRIES = 5;
    private static final int MAX_READ_RETRIES = 5;
    private static final int MAX_INSERT_RETRIES = 3;
    private static final long BASE_SLEEP_MILLIS = 2000L;
    private static final int CONNECT_TIMEOUT = 180000;
    private static final int DEFAULT_TIMEOUT = 300000;
    private static final int SOCKET_TIMEOUT = 180000;
    private static final int DATA_TIMEOUT = 180000;


    /**
     * @description Get all valid FTP configurations from database
     * @author duc.pham
     * @since 2025-11-01
     * @return List of FTPEntity
     */
    public List<FTPEntity> getAllValidFTPConfigs() {
        List<FTPEntity> list = new ArrayList<>();
        try {
            list = queryForList("FTP.getAllValidFTPConfigs", new FTPEntity());
            if (list == null) return new ArrayList<>();
        } catch (Exception ex) {
            log.error("FTPService.getAllValidFTPConfigs", ex);
            return new ArrayList<>();
        }
        return list;
    }

    /**
     * @description Get list of FTP configurations with pagination
     * @author duc.pham
     * @since 2025-11-01
     * @param obj FTP entity with pagination parameters
     * @return List of FTPEntity
     */
    public List<FTPEntity> getList(FTPEntity obj) {
        List<FTPEntity> dataList = new ArrayList<>();
        try {
            dataList = queryForList("FTP.getList", obj);
            if (dataList == null) return new ArrayList<>();
        } catch (Exception ex) {
            log.error("FTPService.getList", ex);
            return new ArrayList<>();
        }
        return dataList;
    }

    /**
     * @description Get total record count for pagination
     * @author duc.pham
     * @since 2025-01-01
     * @param obj FTP entity with filter parameters
     * @return int total record count
     */
    public int getTotalRecord(FTPEntity obj) {
        try {
            return (int) queryForObject("FTP.getListCount", obj);
        } catch (Exception ex) {
            log.error("FTPService.getTotalRecord", ex);
            return 0;
        }
    }

    /**
     * @description Get FTP configuration by ID
     * @author duc.pham
     * @since 2025-11-01
     * @param obj FTP entity with ID
     * @return FTPEntity configuration details
     */
    public FTPEntity getDetailById(FTPEntity obj) {
        FTPEntity dataObj = new FTPEntity();
        try {
            dataObj = (FTPEntity) queryForObject("FTP.getDetailById", obj);
            if (dataObj == null) return new FTPEntity();
        } catch (Exception ex) {
            log.error("FTPService.getDetailById", ex);
            return new FTPEntity();
        }
        return dataObj;
    }

    /**
     * @description Insert new FTP configuration
     * @author duc.pham
     * @since 2025-11-01
     * @param obj FTP entity to insert
     * @return FTPEntity inserted entity with ID
     */
    public FTPEntity insertFTPConfig(FTPEntity obj) {
        SqlSession session = this.beginTransaction();
        try {
            session.insert("FTP.insertFTPConfig", obj);
            int insertId = obj.getId();
            if (insertId == 0) return null;
            
            session.commit();
            return obj;
        } catch (Exception ex) {
            session.rollback();
            log.error("FTPService.insertFTPConfig", ex);
            return null;
        } finally {
            session.close();
        }
    }

    /**
     * @description Update FTP configuration
     * @author duc.pham
     * @since 2025-11-01
     * @param obj FTP entity to update
     * @return boolean success status
     */
    public boolean updateFTPConfig(FTPEntity obj) {
        try {
            return update("FTP.updateFTPConfig", obj) > 0;
        } catch (Exception ex) {
            log.error("FTPService.updateFTPConfig", ex);
            return false;
        }
    }

    /**
     * @description Update FTP configuration status
     * @author duc.pham
     * @since 2025-11-01
     * @param obj FTP entity with status
     * @return boolean success status
     */
    public boolean updateStatus(FTPEntity obj) {
        try {
            return update("FTP.updateStatus", obj) > 0;
        } catch (Exception ex) {
            log.error("FTPService.updateStatus", ex);
            return false;
        }
    }

    /**
     * @description Delete FTP configuration
     * @author long.pham
     * @since 2024-01-01
     * @param obj FTP entity with ID to delete
     * @return boolean success status
     */
    public boolean delete(FTPEntity obj) {
        try {
            return delete("FTP.delete", obj) > 0;
        } catch (Exception ex) {
            log.error("FTPService.delete", ex);
            return false;
        }
    }

    /**
     * @description Test FTP connection
     * @author duc.pham
     * @since 2025-11-01
     * @param ftpEntity FTP configuration to test
     * @return boolean connection success status
     */
    public boolean testFTPConnection(FTPEntity ftpEntity) {
        FTPClient ftpClient = new FTPClient();
        try {
            int port = Integer.parseInt(ftpEntity.getFtpPort());
            
            if (_connectWithRetry(ftpClient, ftpEntity.getFtpServer(), port) &&
                _loginWithRetry(ftpClient, ftpEntity.getFtpUser(), ftpEntity.getFtpPass(), ftpEntity.getFtpServer(), port)) {
                
                String baseFolder = ftpEntity.getFtpFolder() != null ? ftpEntity.getFtpFolder().trim() : "";
                if (!baseFolder.isEmpty()) {
                    return ftpClient.changeWorkingDirectory(baseFolder);
                }
                return true;
            }
        } catch (Exception ex) {
            log.error("FTPService.testFTPConnection", ex);
        } finally {
            _closeFTPConnectionSafe(ftpClient);
        }
        return false;
    }

    /**
     * @description Read and process the latest CSV data from FTP server
     * @author duc.pham
     * @since 2025-11-01
     * @param ftpEntity FTP configuration entity
     * @return boolean success status
     */
    public boolean readLatestCSVDataOnly(FTPEntity ftpEntity) {
        return readAndDisplayLatestCSVData(ftpEntity);
    }

    /**
     * @description Main method to read and display latest CSV data from FTP server
     * @author duc.pham
     * @since 2025-11-01
     * @param ftpEntity FTP configuration entity
     * @return boolean success status
     */
    public boolean readAndDisplayLatestCSVData(FTPEntity ftpEntity) {
        FTPClient ftpClient = new FTPClient();
        boolean foundNewData = false;

        try {
            System.out.println("Connecting to FTP Server: " + ftpEntity.getFtpServer() + ":" + ftpEntity.getFtpPort());
            int port = Integer.parseInt(ftpEntity.getFtpPort());

            if (!_connectWithRetry(ftpClient, ftpEntity.getFtpServer(), port) ||
                !_loginWithRetry(ftpClient, ftpEntity.getFtpUser(), ftpEntity.getFtpPass(), ftpEntity.getFtpServer(), port)) {
                return false;
            }

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();

            String baseFolder = ftpEntity.getFtpFolder() != null ? ftpEntity.getFtpFolder().trim() : "";
            if (!baseFolder.isEmpty() && !ftpClient.changeWorkingDirectory(baseFolder)) {
                return false;
            }

            System.out.println("Folder accessed successfully: " + ftpClient.printWorkingDirectory());
                
            // Get a list of all subdirectories (level 1)
            List<String> allFolders = new ArrayList<>();
            FTPFile[] rootFiles = ftpClient.listFiles();
            if (rootFiles != null) {
                for (FTPFile f : rootFiles) {
                    if (f.isDirectory() && !f.getName().equals(".") && !f.getName().equals("..")) {
                        String fullPath = baseFolder.isEmpty() ? f.getName() : baseFolder + "/" + f.getName();
                        allFolders.add(fullPath);
                    }
                }
            }
            allFolders.add(0, baseFolder.isEmpty() ? "." : baseFolder); // thêm root

            for (String folderPath : allFolders) {
                if (!ftpClient.changeWorkingDirectory(folderPath)) {
                    continue;
                }

                FTPFile latestCSV = _findLatestCSVInCurrentDirSafe(ftpClient);
                if (latestCSV != null) {
                    String location = folderPath.equals(baseFolder) || baseFolder.isEmpty() ? "ROOT" : folderPath.substring(baseFolder.length() + 1);
                    foundNewData |= _tryReadCSVWithRetry(ftpClient, latestCSV, location, ftpEntity.getId());
                }
            }

        } catch (Exception ex) {
            log.error("FTPService.readAndDisplayLatestCSVData", ex);
        } finally {
            _closeFTPConnectionSafe(ftpClient);
        }
        return foundNewData;
    }

    /**
     * @description Get value from CSV entity by header name
     * @author duc.pham
     * @since 2025-11-01
     * @param entity CSV header entity
     * @param header Column header name
     * @return String value
     */
    private String _getValueFromEntityByHeader(CSVHeaderEntity entity, String header) {
        if (entity == null || header == null) return "0";
        switch (header) {
            case "Time": return entity.getTime();
            case "Error": return entity.getError();
            case "Low_Alarm": return entity.getLowAlarm();
            case "High_Alarm": return entity.getHighAlarm();
            case "Upv1": return entity.getUpv1();
            case "Upv2": return entity.getUpv2();
            case "Upv3": return entity.getUpv3();
            case "Upv4": return entity.getUpv4();
            case "Upv5": return entity.getUpv5();
            case "Upv6": return entity.getUpv6();
            case "Ipv1": return entity.getIpv1();
            case "Ipv2": return entity.getIpv2();
            case "Ipv3": return entity.getIpv3();
            case "Ipv4": return entity.getIpv4();
            case "Ipv5": return entity.getIpv5();
            case "Ipv6": return entity.getIpv6();
            case "Uac1": return entity.getUac1();
            case "Uac2": return entity.getUac2();
            case "Uac3": return entity.getUac3();
            case "Iac1": return entity.getIac1();
            case "Iac2": return entity.getIac2();
            case "Iac3": return entity.getIac3();
            case "Status": return entity.getStatus();
            case "Error2": return entity.getError2();
            case "Temp": return entity.getTemp();
            case "cos": return entity.getCos();
            case "fac": return entity.getFac();
            case "Pac": return entity.getPac();
            case "Qac": return entity.getQac();
            case "Eac": return entity.getEac();
            default: return "0";
        }
    }

    /**
     * @description Parse double value safely with null check
     * @author duc.pham
     * @since 2025-11-01
     * @param value String value to parse
     * @return Double parsed value or null
     */
    private Double _parseDoubleSafe(String value) {
        if (value == null || value.trim().isEmpty()) return null;
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * @description Round double value to 4 decimal places
     * @author duc.pham
     * @since 2025-11-01
     * @param value Double value to round
     * @return double rounded value
     */
    private double _roundTo4Decimals(double value) {
        return Math.round(value * 10000.0) / 10000.0;
    }

    /**
     * @description Parse CSV time format with fallback
     * @author duc.pham
     * @since 2025-11-01
     * @param csvTime CSV time string
     * @return String formatted time
     */
    private String _parseCSVTimeWithFallback(String csvTime) {
        if (csvTime == null || csvTime.trim().isEmpty()) return csvTime != null ? csvTime.trim() : "";
        try {
            SimpleDateFormat csvFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            csvFormat.setLenient(false);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 2000);
            csvFormat.set2DigitYearStart(calendar.getTime());
            Date parsedDate = csvFormat.parse(csvTime.trim());
            SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dbFormat.format(parsedDate);
        } catch (Exception ex) {
            return csvTime.trim();
        }
    }

    /**
     * @description Parse CSV time format to Timestamp
     * @author duc.pham  
     * @since 2025-11-03
     * @param csvTime CSV time string
     * @return java.sql.Timestamp parsed timestamp
     */
    private java.sql.Timestamp _parseCSVTimeToTimestamp(String csvTime) {
        if (csvTime == null || csvTime.trim().isEmpty()) return new java.sql.Timestamp(System.currentTimeMillis());
        try {
            SimpleDateFormat csvFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            csvFormat.setLenient(false);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 2000);
            csvFormat.set2DigitYearStart(calendar.getTime());
            Date parsedDate = csvFormat.parse(csvTime.trim());
            return new java.sql.Timestamp(parsedDate.getTime());
        } catch (Exception ex) {
            log.error("Error parsing CSV time to timestamp: " + csvTime, ex);
            return new java.sql.Timestamp(System.currentTimeMillis());
        }
    }

    /**
     * @description Functional interface for FTP retry actions
     */
    @FunctionalInterface
    private interface FTPAction {
        boolean run() throws Exception;
    }

    /**
     * @description Retry FTP action with exponential backoff
     * @author duc.pham
     * @since 2025-11-01
     * @param maxRetries Maximum number of retries
     * @param baseSleepMillis Base sleep time in milliseconds
     * @param action FTP action to retry
     * @param actionName Action name for logging
     * @return boolean success status
     */
    private boolean _retryFTPAction(int maxRetries, long baseSleepMillis, FTPAction action, String actionName) {
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                if (action.run()) {
                    return true;
                }
            } catch (Exception ex) {
                log.error(actionName + " failed (attempt " + attempt + "): " + ex.getMessage());
            }
            if (attempt < maxRetries) {
                try {
                    Thread.sleep(baseSleepMillis * attempt);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * @description Connect to FTP server with retry logic
     * @author duc.pham
     * @since 2025-11-01
     * @param ftpClient FTP client instance
     * @param server FTP server address
     * @param port FTP server port
     * @return boolean success status
     */
    private boolean _connectWithRetry(FTPClient ftpClient, String server, int port) {
        ftpClient.setConnectTimeout(CONNECT_TIMEOUT);
        ftpClient.setDefaultTimeout(DEFAULT_TIMEOUT);
        return _retryFTPAction(MAX_RETRIES, BASE_SLEEP_MILLIS, () -> {
            ftpClient.connect(server, port);
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new IOException("FTP server refused connection: " + reply);
            }
            return true;
        }, "FTP connect");
    }

    /**
     * @description Login to FTP server with retry logic
     * @author duc.pham
     * @since 2025-11-01
     * @param ftpClient FTP client instance
     * @param user FTP username
     * @param pass FTP password
     * @param server FTP server address
     * @param port FTP server port
     * @return boolean success status
     */
    private boolean _loginWithRetry(FTPClient ftpClient, String user, String pass, String server, int port) {
        return _retryFTPAction(MAX_RETRIES, BASE_SLEEP_MILLIS, () -> {
            if (!ftpClient.isConnected()) {
                ftpClient.connect(server, port);
            }
            ftpClient.setSoTimeout(SOCKET_TIMEOUT);
            return ftpClient.login(user, pass);
        }, "FTP login");
    }



    /**
     * @description Find the latest CSV file in current directory
     * @author duc.pham
     * @since 2025-11-01
     * @param ftpClient FTP client instance
     * @return FTPFile latest CSV file
     */
    private FTPFile _findLatestCSVInCurrentDirSafe(FTPClient ftpClient) {
        int maxRetries = 3;
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                FTPFile[] files = ftpClient.listFiles(); 
                if (files == null || files.length == 0) {
                    if (attempt == maxRetries) return null;
                    Thread.sleep(1000L * attempt);
                    continue;
                }

                FTPFile latestCSV = null;
                for (FTPFile file : files) {
                    if (file.isFile() && file.getName().toLowerCase().endsWith(".csv")) {
                        Date ts = file.getTimestamp().getTime();
                        if (latestCSV == null || ts.after(latestCSV.getTimestamp().getTime())) {
                            latestCSV = file;
                        }
                    }
                }

                if (latestCSV != null) {
                    return latestCSV;
                } else {
                    if (attempt == maxRetries) return null;
                    Thread.sleep(1000L * attempt);
                }

            } catch (Exception ex) {
                log.error("_findLatestCSVInCurrentDirSafe failed (attempt " + attempt + "): " + ex.getMessage());
                if (attempt == maxRetries) return null;
                try { 
                    Thread.sleep(1000L * attempt); 
                } catch (InterruptedException ie) { 
                    Thread.currentThread().interrupt(); 
                    return null; 
                }
            }
        }
        return null;
    }

    /**
     * @description Safely close FTP connection
     * @author duc.pham
     * @since 2025-11-01
     * @param ftpClient FTP client instance
     */
    private void _closeFTPConnectionSafe(FTPClient ftpClient) {
        try {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (IOException ex) {
            // Ignore connection close errors
        }
    }

    /**
     * @description Try to read CSV with retry logic
     * @author duc.pham
     * @since 2025-11-01
     * @param ftpClient FTP client instance
     * @param csvFile CSV file to read
     * @param location File location
     * @param siteId Site ID
     * @return boolean success status
     */
    private boolean _tryReadCSVWithRetry(FTPClient ftpClient, FTPFile csvFile, String location, int siteId) {
        for (int attempt = 1; attempt <= MAX_READ_RETRIES; attempt++) {
            try {
                if (_readAndDisplayCSVContent(ftpClient, csvFile, location, siteId)) {
                    return true;
                }
                break;
            } catch (Exception ex) {
                String msg = ex.getMessage() != null ? ex.getMessage().toLowerCase() : "";
                if (msg.contains("timed out") || msg.contains("connect")) {
                    try { 
                        Thread.sleep(3000L * attempt); 
                    } catch (InterruptedException ie) { 
                        Thread.currentThread().interrupt(); 
                        break; 
                    }
                    continue;
                }
                break;
            }
        }
        return false;
    }

    /**
     * @description Read and display CSV content
     * @author duc.pham
     * @since 2025-11-01
     * @param ftpClient FTP client instance
     * @param csvFile CSV file to read
     * @param location File location
     * @param siteId Site ID
     * @return boolean success status
     */
    private boolean _readAndDisplayCSVContent(FTPClient ftpClient, FTPFile csvFile, String location, int siteId) {
        InputStream inputStream = null;
        BufferedReader reader = null;

        try {
            ftpClient.setSoTimeout(SOCKET_TIMEOUT);
            ftpClient.setDataTimeout(DATA_TIMEOUT);
            inputStream = ftpClient.retrieveFileStream(csvFile.getName());
            if (inputStream == null) return false;

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            boolean headerFound = false;
            Map<String, List<CSVHeaderEntity>> serialDataMap = new LinkedHashMap<>();
            String currentSerial = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#")) {
                    Matcher matcher = Pattern.compile("#(SmartLogger|INV\\d+|Sensor) ESN:([A-Z0-9]+)").matcher(line);
                    if (matcher.find()) {
                        currentSerial = matcher.group(2);
                        serialDataMap.putIfAbsent(currentSerial, new ArrayList<>());
                    }
                    continue;
                }
                if (!headerFound) {
                    String[] parsedHeaderArr = line.split(CSV_DELIMITER, -1);
                    if (parsedHeaderArr.length > 0 && parsedHeaderArr[parsedHeaderArr.length - 1].trim().isEmpty()) {
                        parsedHeaderArr = Arrays.copyOf(parsedHeaderArr, parsedHeaderArr.length - 1);
                    }
                    headerFound = true;
                    continue;
                }
                if (currentSerial != null) {
                    String[] fields = line.split(CSV_DELIMITER, -1);
                    if (fields.length > 0 && fields[fields.length - 1].trim().isEmpty()) {
                        fields = Arrays.copyOf(fields, fields.length - 1);
                    }
                    CSVHeaderEntity entity = CSVHeaderEntity.fromCsvFields(fields);
                    serialDataMap.get(currentSerial).add(entity);
                }
            }

            if (!serialDataMap.isEmpty()) {
                _processSerialDataMap(serialDataMap, siteId);
            }
            return true;
        } catch (Exception ex) {
            log.error("FTPService._readAndDisplayCSVContent", ex);
            return false;
        } finally {
            try { if (reader != null) reader.close(); } catch (Exception ignored) {}
            try { if (inputStream != null) inputStream.close(); } catch (Exception ignored) {}
            try { ftpClient.completePendingCommand(); } catch (Exception ignored) {}
        }
    }

    /**
     * @description Process serial data map and insert into database
     * @author duc.pham
     * @since 2025-11-01
     * @param serialDataMap Map of serial data
     * @param siteId Site ID
     */
    private void _processSerialDataMap(Map<String, List<CSVHeaderEntity>> serialDataMap, int siteId) {
        SqlSession session = null;
        try {
            session = sqlMap.openSession();
            List<Map<String, Object>> siteDevices = session.selectList("FTP.getDevicesBySiteId", siteId);
            Map<String, Map<String, Object>> serialToDevice = new HashMap<>();
            for (Map<String, Object> device : siteDevices) {
                String sn = (String) device.get("serialnumber");
                if (sn != null) serialToDevice.put(sn, device);
            }
            for (Map.Entry<String, List<CSVHeaderEntity>> entry : serialDataMap.entrySet()) {
                String serial = entry.getKey();
                List<CSVHeaderEntity> records = entry.getValue();
                Map<String, Object> device = serialToDevice.get(serial);
                if (device == null || records.isEmpty()) continue;
                String table = (String) device.get("datatablename");
                int deviceId = (Integer) device.get("id");
                List<String> rows = new ArrayList<>();
                for (CSVHeaderEntity r : records) {
                    StringBuilder sb = new StringBuilder();
                    for (String col : CSVHeaderEntity.getHeaderList()) {
                        sb.append(_getValueFromEntityByHeader(r, col) != null ? _getValueFromEntityByHeader(r, col) : "0").append(CSV_DELIMITER);
                    }
                    rows.add(sb.toString());
                }
                _processAndInsertData(rows, CSVHeaderEntity.getHeaderList(), table, serial, siteId, deviceId);
            }
        } catch (Exception ex) {
            log.error("FTPService._processSerialDataMap", ex);
        } finally {
            if (session != null) session.close();
        }
    }

    /**
     * @description Get latest timestamp for a device in specific table
     * @author duc.pham
     * @since 2025-11-03
     * @param dataTableName Database table name
     * @param deviceId Device ID
     * @return java.sql.Timestamp latest timestamp
     */
    private java.sql.Timestamp _getLatestTimestamp(String dataTableName, int deviceId) {
        SqlSession session = null;
        try {
            session = sqlMap.openSession();
            Map<String, Object> params = new HashMap<>();
            params.put("datatablename", dataTableName);
            params.put("deviceId", deviceId);
            return session.selectOne("DynamicData.getLatestTimestamp", params);
        } catch (Exception ex) {
            log.error("FTPService._getLatestTimestamp", ex);
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    /**
     * @description Check if data exists for specific timestamp and device
     * @author duc.pham
     * @since 2025-11-03
     * @param dataTableName Database table name
     * @param deviceId Device ID
     * @param timestamp Timestamp to check
     * @return boolean true if data exists
     */
    private boolean _checkDataExists(String dataTableName, int deviceId, java.sql.Timestamp timestamp) {
        SqlSession session = null;
        try {
            session = sqlMap.openSession();
            Map<String, Object> params = new HashMap<>();
            params.put("datatablename", dataTableName);
            params.put("deviceId", deviceId);
            params.put("timestamp", timestamp);
            Integer count = session.selectOne("DynamicData.checkDataExists", params);
            return count != null && count > 0;
        } catch (Exception ex) {
            log.error("FTPService._checkDataExists", ex);
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    /**
     * @description Process and insert CSV data into database
     * @author duc.pham
     * @since 2025-11-01
     * @param csvContent CSV content lines
     * @param header CSV header
     * @param dataTableName Database table name
     * @param serialNumber Device serial number
     * @param siteId Site ID
     * @param deviceId Device ID
     */
    private void _processAndInsertData(List<String> csvContent, List<String> header, String dataTableName, String serialNumber, int siteId, int deviceId) {
        if (csvContent == null || csvContent.size() < 2) {
            return;
        }
        if (deviceId == 0) {
            return;
        }

        // Get latest timestamp from database
        java.sql.Timestamp latestTimestamp = _getLatestTimestamp(dataTableName, deviceId);
        System.out.println("[LOG][CHECK] Latest timestamp in DB for device " + deviceId + " (table: " + dataTableName + "): " + latestTimestamp);
        
        List<Map<String, Object>> records = new ArrayList<>();
        int totalRows = 0;
        int newRows = 0;
        int skippedRows = 0;

        for (String row : csvContent) {
            if (row.startsWith("#") || row.toLowerCase().contains("time")) continue;
            totalRows++;
            
            String[] cols = row.split(CSV_DELIMITER);
            String[] fullCols = new String[header.size()];
            for (int i = 0; i < header.size(); i++) {
                fullCols[i] = i < cols.length ? cols[i].trim() : "0";
            }

            // Parse timestamp for comparison
            java.sql.Timestamp recordTimestamp = _parseCSVTimeToTimestamp(fullCols[0]);
            
            // Check if this record is newer than latest in DB
            boolean shouldProcess = false;
            if (latestTimestamp == null) {
                // No data in DB, process all records
                shouldProcess = true;
            } else if (recordTimestamp.after(latestTimestamp)) {
                // Record is newer than latest in DB
                shouldProcess = true;
            } else if (recordTimestamp.equals(latestTimestamp)) {
                // Same timestamp - check if exact record exists
                shouldProcess = !_checkDataExists(dataTableName, deviceId, recordTimestamp);
            }

            if (shouldProcess) {
                Map<String, Object> record = new HashMap<>();
                record.put("time", _parseCSVTimeWithFallback(fullCols[0])); // Keep string format for DB
                for (int i = 1; i < header.size(); i++) {
                    record.put(header.get(i), _parseDoubleSafe(fullCols[i]));
                }
                Double pac = _parseDoubleSafe(fullCols[header.indexOf("Pac")]);
                Double eac = _parseDoubleSafe(fullCols[header.indexOf("Eac")]);
                record.put("Pac", pac);
                record.put("nvmActivePower", pac);
                record.put("Eac", eac);
                record.put("MeasuredProduction", eac);
                record.put("id_device", deviceId);
                records.add(record);
                newRows++;
            } else {
                skippedRows++;
            }
        }
        
        System.out.println("[LOG][DATA_COMPARISON] Serial: " + serialNumber + ", Table: " + dataTableName);
        System.out.println("[LOG][DATA_COMPARISON] Total rows processed: " + totalRows);
        System.out.println("[LOG][DATA_COMPARISON] New rows to insert: " + newRows);
        System.out.println("[LOG][DATA_COMPARISON] Skipped existing rows: " + skippedRows);
        
        if (records.isEmpty()) {
            System.out.println("[LOG][INSERT] Không có bản ghi mới để insert cho serial: " + serialNumber + ", bảng: " + dataTableName);
            return;
        }

        double cumulativeEnergy = 0.0;
        for (Map<String, Object> r : records) {
            Double mp = (Double) r.get("MeasuredProduction");
            if (mp != null) cumulativeEnergy += mp;
            r.put("nvmActiveEnergy", _roundTo4Decimals(cumulativeEnergy));
        }

        System.out.println("[LOG][INSERT] Inserting " + records.size() + " new records for serial: " + serialNumber);
        _insertDataWithRetry(records, dataTableName, serialNumber);
    }

    /**
     * @description Insert data into database with retry logic
     * @author duc.pham
     * @since 2025-11-01
     * @param records List of records to insert
     * @param dataTableName Database table name
     * @param serialNumber Device serial number
     */
    private void _insertDataWithRetry(List<Map<String, Object>> records, String dataTableName, String serialNumber) {
        for (int attempt = 1; attempt <= MAX_INSERT_RETRIES; attempt++) {
            SqlSession session = null;
            try {
                session = sqlMap.openSession();
                Map<String, Object> params = new HashMap<>();
                params.put("datatablename", dataTableName);
                params.put("records", records);
                int inserted = session.insert("DynamicData.insertDynamic", params);
                if (inserted >= 0) {
                    session.commit();
                    break;
                } else {
                    log.warn("No rows inserted for serial: " + serialNumber + ", table: " + dataTableName);
                }
            } catch (Exception ex) {
                log.error("Insert failed (attempt " + attempt + ") for table " + dataTableName + ": " + ex.getMessage());
                if (session != null) {
                    try {
                        session.rollback();
                    } catch (Exception rollbackEx) {
                        log.error("Rollback failed: " + rollbackEx.getMessage());
                    }
                }
            } finally {
                if (session != null) session.close();
            }
            try { 
                Thread.sleep(1000L * attempt); 
            } catch (InterruptedException ignored) {}
        }
    }
}