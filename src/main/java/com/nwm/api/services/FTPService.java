/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/

package com.nwm.api.services;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.FTPEntity;
import java.text.ParseException;
import java.util.Map;
import java.util.HashMap;
import java.util.Calendar;

@Service
public class FTPService extends DB {
	/**
	 * Helper: get value from entity by header name
	 */
	public String getValueFromEntityByHeader(com.nwm.api.entities.CSVHeaderEntity entity, String header) {
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
			case "CycleTime": return entity.getCycleTime();
			default: return "0";
		}
	}
	/**
	 * Safely convert String value to Double, return null if error
	 */
	private Double parseDoubleSafe(String value) {
		try {
			if (value == null || value.trim().isEmpty()) return null;
			return Double.parseDouble(value.trim());
		} catch (NumberFormatException e) {
			return null;
		}
	}
	

	
	/**
	 * Get all valid FTP configurations using optimized SQL query
	 * Returns list of all sites with complete FTP data
	 * @return List<FTPEntity> with complete data or empty list if none found
	 */
	public java.util.List<FTPEntity> getAllValidFTPConfigs() {
		java.util.List<FTPEntity> ftpConfigs = new java.util.ArrayList<>();
		SqlSession session = null;
		
		try {
			session = sqlMap.openSession();
			
			// Use optimized SQL query to get all valid FTP configs directly
			ftpConfigs = session.selectList("FTP.getAllValidFTPConfigs");
			
		} catch (Exception ex) {
			System.out.println("✗ Error occurred while getting all valid FTP configurations: " + ex.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		return ftpConfigs;
	}



	/**
	 * Extract serial numbers from CSV content
	 * @param csvContent List of CSV lines
	 * @return List of serial numbers found
	 */
	private List<String> extractSerialNumbers(List<String> csvContent) {
		List<String> serialNumbers = new ArrayList<>();
		// Tổng hợp pattern cho các loại thiết bị
		Pattern pattern = Pattern.compile("#(SmartLogger|INV\\d+|Sensor) ESN:([A-Z0-9]+)");
		for (String line : csvContent) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				serialNumbers.add(matcher.group(2));
				// Nếu cần debug thì mở dòng dưới
				// System.out.println("🔍 Found serial number: " + matcher.group(2));
			}
		}
		return serialNumbers;
	}



	/**
	 * Connect to FTP server with retry mechanism
	 */
	private boolean connectWithRetry(FTPClient ftpClient, String server, int port) {
		int maxRetries = 3;
		
		for (int attempt = 1; attempt <= maxRetries; attempt++) {
			try {
				ftpClient.connect(server, port);
				return true;
			} catch (Exception ex) {
				if (attempt < maxRetries) {
					try {
						Thread.sleep(2000L * attempt);
					} catch (InterruptedException ie) {
						Thread.currentThread().interrupt();
						return false;
					}
				}
			}
		}
		
		return false;
	}

	/**
	 * Login to FTP server with retry mechanism
	 */
	private boolean loginWithRetry(FTPClient ftpClient, String user, String pass) {
		int maxRetries = 3;
		
		for (int attempt = 1; attempt <= maxRetries; attempt++) {
			try {
				boolean loginSuccess = ftpClient.login(user, pass);
				if (loginSuccess) {
					return true;
				}
			} catch (Exception ex) {
				if (attempt < maxRetries) {
					try {
						Thread.sleep(1000L * attempt);
					} catch (InterruptedException ie) {
						Thread.currentThread().interrupt();
						return false;
					}
				}
			}
		}
		
		return false;
	}

	/**
	 * Safely change directory
	 */
	private boolean changeDirectorySafe(FTPClient ftpClient, String directory) {
		try {
			return ftpClient.changeWorkingDirectory(directory);
		} catch (Exception ex) {
			System.out.println("Error changing to directory " + directory + ": " + ex.getMessage());
			return false;
		}
	}

	/**
	 * Safely list files in current directory
	 */
	private FTPFile[] listFilesSafe(FTPClient ftpClient) {
		try {
			return ftpClient.listFiles();
		} catch (Exception ex) {
			System.out.println("Error listing directory: " + ex.getMessage());
			return null;
		}
	}



	/**
	 * Find the latest CSV file in current directory
	 * @param ftpClient Connected FTP client
	 * @return Latest CSV file or null if no CSV files found
	 */
	private FTPFile findLatestCSVInCurrentDirSafe(FTPClient ftpClient) {
		try {
			FTPFile[] files = listFilesSafe(ftpClient);
			if (files == null) {
				return null;
			}
			
			FTPFile latestCSV = null;
			
			for (FTPFile file : files) {
				if (!file.isDirectory() && file.getName().toLowerCase().endsWith(".csv")) {
					if (latestCSV == null || file.getTimestamp().getTime().after(latestCSV.getTimestamp().getTime())) {
						latestCSV = file;
					}
				}
			}
			return latestCSV;
		} catch (Exception ex) {
			System.out.println("Error finding latest CSV: " + ex.getMessage());
			return null;
		}
	}



	/**
	 * Safely close FTP connection
	 */
	private void closeFTPConnectionSafe(FTPClient ftpClient) {
		try {
			if (ftpClient.isConnected()) {
				ftpClient.logout();
				ftpClient.disconnect();
			}
		} catch (IOException ex) {
			System.out.println("Error closing FTP connection: " + ex.getMessage());
		}
	}



	/**
	 * Scheduled task - Run every 5 minutes to read ONLY the latest CSV files
	 * Simplified version for reading and displaying new data
	 */
	@Scheduled(fixedRate = 300000) // 5 minutes = 300,000 milliseconds
	public void readLatestDataScheduled() {
		System.out.println("[SCHEDULER] Đang quét dữ liệu FTP...");

		try {
			java.util.List<FTPEntity> allValidFtpConfigs = getAllValidFTPConfigs();
			if (allValidFtpConfigs != null && !allValidFtpConfigs.isEmpty()) {
				System.out.println("Tìm thấy " + allValidFtpConfigs.size() + " cấu hình FTP");

				// Use thread pool to process FTPs in parallel
								int threadCount = Math.min(10, allValidFtpConfigs.size()); // Maximum 10 threads or number of configs
				ExecutorService executor = Executors.newFixedThreadPool(threadCount);

				List<Future<?>> futures = new ArrayList<>();
				for (FTPEntity ftpConfig : allValidFtpConfigs) {
					futures.add(executor.submit(() -> {
						readAndDisplayLatestCSVData(ftpConfig);
					}));
				}

				// Wait for all tasks to complete
				for (java.util.concurrent.Future<?> future : futures) {
					try {
						future.get();
					} catch (Exception e) {
						System.out.println("❌ Lỗi khi xử lý FTP: " + e.getMessage());
					}
				}

				executor.shutdown();
			} else {
				System.out.println("⚠️ Không tìm thấy cấu hình FTP hợp lệ");
			}
		} catch (Exception ex) {
			System.out.println("❌ Lỗi khi đọc dữ liệu FTP: " + ex.getMessage());
			ex.printStackTrace();
		}

		System.out.println("[SCHEDULER] Hoàn thành quét dữ liệu FTP.");
	}
	
	/**
	 * Read and display latest CSV data from FTP - Simplified for terminal output
	 * @param ftpEntity FTP configuration from database
	 * @return true if new data found and displayed
	 */
	public boolean readAndDisplayLatestCSVData(FTPEntity ftpEntity) {
		FTPClient ftpClient = new FTPClient();
		boolean foundNewData = false;
		
		try {
			System.out.println("🔌 Đang kết nối tới FTP Server: " + ftpEntity.getFtpServer() + ":" + ftpEntity.getFtpPort());
			
			// Setup connection
			ftpClient.setConnectTimeout(15000);
			ftpClient.setDefaultTimeout(30000);
			
			// Connect and login
			int port = Integer.parseInt(ftpEntity.getFtpPort());
			if (!connectWithRetry(ftpClient, ftpEntity.getFtpServer(), port)) {
				System.out.println("❌ Không thể kết nối tới FTP server");
				return false;
			}
			
			ftpClient.setSoTimeout(60000);
			
			if (!loginWithRetry(ftpClient, ftpEntity.getFtpUser(), ftpEntity.getFtpPass())) {
				System.out.println("❌ Đăng nhập FTP thất bại");
				return false;
			}
			
			System.out.println("✅ Kết nối FTP thành công!");
			
			// Configure FTP
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			
			// Navigate to FTP folder from database
			String ftpFolder = ftpEntity.getFtpFolder();
			System.out.println("📁 Đang truy cập thư mục: " + ftpFolder);
			
			if (!changeDirectorySafe(ftpClient, ftpFolder)) {
				System.out.println("❌ Không thể truy cập thư mục: " + ftpFolder);
				return false;
			}
			
			System.out.println("✅ Đã truy cập thư mục thành công!");
			
			// Check root folder for latest CSV
			System.out.println("\n🔍 ĐANG TÌM FILE CSV MỚI NHẤT TRONG THU MỤC GỐC...");
			FTPFile latestRootCSV = findLatestCSVInCurrentDirSafe(ftpClient);
			if (latestRootCSV != null) {
				System.out.println("📄 Tìm thấy file CSV: " + latestRootCSV.getName());
				System.out.println("📅 Thời gian file: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(latestRootCSV.getTimestamp().getTime()));
				System.out.println("📊 Kích thước: " + latestRootCSV.getSize() + " bytes");
				
				// Read and display data
				if (readAndDisplayCSVContent(ftpClient, latestRootCSV, "ROOT", ftpEntity.getId())) {
					foundNewData = true;
				}
			} else {
				System.out.println("📂 Không tìm thấy file CSV trong thư mục gốc");
			}
			
			// Check subfolders
			System.out.println("\n🔍 ĐANG TÌM TRONG CÁC THƯ MỤC CON...");
			FTPFile[] files = listFilesSafe(ftpClient);
			if (files != null) {
				int folderCount = 0;
				
				for (FTPFile file : files) {
					if (file.isDirectory() && !file.getName().equals(".") && !file.getName().equals("..")) {
						folderCount++;
						String folderName = file.getName();
						System.out.println("\n📁 Thư mục " + folderCount + ": " + folderName);
						
						if (changeDirectorySafe(ftpClient, folderName)) {
							FTPFile latestCSV = findLatestCSVInCurrentDirSafe(ftpClient);
							if (latestCSV != null) {
								System.out.println("📄 File CSV mới nhất: " + latestCSV.getName());
								System.out.println("📅 Thời gian: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(latestCSV.getTimestamp().getTime()));
								
								// Read and display data
								if (readAndDisplayCSVContent(ftpClient, latestCSV, folderName, ftpEntity.getId())) {
									foundNewData = true;
								}
							} else {
								System.out.println("📂 Không có file CSV trong thư mục này");
							}
							
							// Return to parent directory
							changeDirectorySafe(ftpClient, "..");
						}
					}
				}
				
				System.out.println("✅ Đã kiểm tra " + folderCount + " thư mục con");
			}
			
		} catch (Exception ex) {
			System.out.println("❌ Lỗi khi đọc dữ liệu FTP: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			closeFTPConnectionSafe(ftpClient);
		}
		
		return foundNewData;
	}
	
	/**
	 * Read CSV content, display to terminal and process data for database insertion
	 * @param ftpClient Connected FTP client
	 * @param csvFile CSV file to read
	 * @param location Location description (ROOT or folder name)
	 * @param siteId Site ID for device lookup
	 * @return true if data was displayed and processed
	 */
	private boolean readAndDisplayCSVContent(FTPClient ftpClient, FTPFile csvFile, String location, int siteId) {
		InputStream inputStream = null;
		BufferedReader reader = null;
		final String CSV_DELIMITER = ";";
		final List<String> EXPECTED_HEADER = com.nwm.api.entities.CSVHeaderEntity.getHeaderList();
		try {
			inputStream = ftpClient.retrieveFileStream(csvFile.getName());
			if (inputStream == null) {
				System.out.println("❌ Không thể đọc file: " + csvFile.getName());
				return false;
			}
			reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			List<String> metadataLines = new ArrayList<>();
			List<String> header = EXPECTED_HEADER;
			boolean headerFound = false;
			List<com.nwm.api.entities.CSVHeaderEntity> dataRecords = new ArrayList<>();
			int lineNumber = 0;
			System.out.println("\n📖 ĐANG ĐỌC NỘI DUNG FILE: " + csvFile.getName());
			System.out.println("📍 Vị trí: " + location);
			System.out.println("🏢 Site ID: " + siteId);
			System.out.println("Sử dụng delimiter: " + CSV_DELIMITER);

			// Read metadata and header
			while ((line = reader.readLine()) != null) {
				lineNumber++;
				if (line.startsWith("#")) {
					metadataLines.add(line);
					continue;
				}
				if (!headerFound) {
					String[] parsedHeaderArr = line.split(CSV_DELIMITER, -1);
					if (parsedHeaderArr.length > 0 && parsedHeaderArr[parsedHeaderArr.length - 1].trim().isEmpty()) {
						parsedHeaderArr = java.util.Arrays.copyOf(parsedHeaderArr, parsedHeaderArr.length - 1);
					}
					if (parsedHeaderArr.length == EXPECTED_HEADER.size()) {
						boolean headerValid = true;
						for (int i = 0; i < parsedHeaderArr.length; i++) {
							if (!parsedHeaderArr[i].trim().equals(EXPECTED_HEADER.get(i))) {
								headerValid = false;
							}
						}
						if (headerValid) {
							header = java.util.Arrays.asList(parsedHeaderArr);
						}
					}
					headerFound = true;
					continue;
				}
				break;
			}

			// Process data lines
			int dataLineNumber = 0;
			while (line != null) {
				dataLineNumber++;
				String[] fields = line.split(CSV_DELIMITER, -1);
				if (fields.length > 0 && fields[fields.length - 1].trim().isEmpty()) {
					fields = java.util.Arrays.copyOf(fields, fields.length - 1);
				}
				com.nwm.api.entities.CSVHeaderEntity entity = com.nwm.api.entities.CSVHeaderEntity.fromCsvFields(fields);
				dataRecords.add(entity);
				line = reader.readLine();
			}

			// Extract serial numbers
			List<String> serialNumbers = extractSerialNumbers(metadataLines);

			// Process serial numbers and insert data
			if (!serialNumbers.isEmpty() && !dataRecords.isEmpty()) {
				SqlSession session = null;
				try {
					session = sqlMap.openSession();
					List<java.util.Map<String, Object>> siteDevices = session.selectList("FTP.getDevicesBySiteId", siteId);
					Map<String, Map<String, Object>> serialToDevice = new HashMap<>();
					for (Map<String, Object> device : siteDevices) {
						String dbSerialNumber = (String) device.get("serialnumber");
						if (dbSerialNumber != null) {
							serialToDevice.put(dbSerialNumber, device);
						}
					}
					for (String csvSerialNumber : serialNumbers) {
						Map<String, Object> device = serialToDevice.get(csvSerialNumber);
						if (device == null) continue;
						String dataTableName = (String) device.get("datatablename");
						Integer deviceId = (Integer) device.get("id");
						List<String> rowsToInsert = new ArrayList<>();
						for (com.nwm.api.entities.CSVHeaderEntity record : dataRecords) {
							StringBuilder sb = new StringBuilder();
							List<String> headerList = com.nwm.api.entities.CSVHeaderEntity.getHeaderList();
							for (String col : headerList) {
								String value = this.getValueFromEntityByHeader(record, col);
								sb.append(value != null ? value : "0").append(";");
							}
							rowsToInsert.add(sb.toString());
						}
						processAndInsertData(rowsToInsert, header, dataTableName, csvSerialNumber, siteId, deviceId);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					if (session != null) session.close();
				}
			}
			return dataLineNumber > 0;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			try {
				if (reader != null) reader.close();
				if (inputStream != null) inputStream.close();
				ftpClient.completePendingCommand();
			} catch (Exception cleanupEx) {
			}
		}
	}

	/**
	 * Process CSV data and insert into specific data table
	 * @param csvContent All CSV content
	 * @param dataTableName Target table name (e.g., data5438_model_imtsolar_tv_class8004)
	 * @param serialNumber Device serial number (e.g., EM02311HJE00G3000008)
	 * @param siteId Site ID (e.g., 593)
	 * @param deviceId Device ID from database (e.g., 5438)
	 */
	private void processAndInsertData(List<String> csvContent, List<String> header, String dataTableName, String serialNumber, int siteId, int deviceId) {
		if (csvContent == null || header == null || header.size() == 0 || csvContent.size() < 2) return;
		ExecutorService executor = Executors.newFixedThreadPool(Math.min(10, csvContent.size()));
		List<Future<?>> futures = new ArrayList<>();
		try {
			for (String row : csvContent) {
				if (row.startsWith("#") || row.toLowerCase().contains("time")) continue;
				futures.add(executor.submit(() -> {
					SqlSession session = null;
					try {
						session = sqlMap.openSession();
						String[] cols = row.split(";");
						String[] fullCols = new String[header.size()];
						for (int i = 0; i < header.size(); i++) fullCols[i] = (i < cols.length) ? cols[i] : "0";
						Map<String, Object> params = new HashMap<>();
						params.put("datatablename", dataTableName);
						params.put("time", parseCSVTimeWithFallback(fullCols[0]));
						for (int i = 1; i < header.size(); i++) {
							params.put(header.get(i), parseDoubleSafe(fullCols[i]));
						}
						params.put("id_device", deviceId);
						int inserted = session.insert("DynamicData.insertDynamic", params);
						if (inserted > 0) {
							System.out.println("✅ Đã insert thành công vào bảng " + dataTableName + " cho device " + deviceId);
						}
						session.commit();
					} catch (Exception ex) {
						System.out.println("❌ Lỗi khi insert vào bảng " + dataTableName + ": " + ex.getMessage());
					} finally {
						if (session != null) session.close();
					}
				}));
			}
			// Wait for all inserts to complete
			for (Future<?> future : futures) {
				try {
					future.get();
				} catch (Exception e) {
					System.out.println("❌ Lỗi khi insert song song: " + e.getMessage());
				}
			}
		} catch (Exception ex) {
			System.out.println("❌ Lỗi khi xử lý và insert dữ liệu: " + ex.getMessage());
		} finally {
			executor.shutdown();
		}
	}

	/**
	 * Read ONLY the latest CSV file in each folder and display all content (short version)
	 * @param ftpEntity FTP configuration
	 * @return true if data found, false otherwise
	 */
	public boolean readLatestCSVDataOnly(FTPEntity ftpEntity) {
		FTPClient ftpClient = new FTPClient();
		boolean foundData = false;
		try {
			ftpClient.setConnectTimeout(15000);
			ftpClient.setDefaultTimeout(30000);
			int port = Integer.parseInt(ftpEntity.getFtpPort());
			if (!connectWithRetry(ftpClient, ftpEntity.getFtpServer(), port)) return false;
			ftpClient.setSoTimeout(60000);
			if (!loginWithRetry(ftpClient, ftpEntity.getFtpUser(), ftpEntity.getFtpPass())) return false;
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			if (!changeDirectorySafe(ftpClient, ftpEntity.getFtpFolder())) return false;

			// Process root and subfolders in a single loop
			String[] folderNames = new String[] {"."};
			FTPFile[] files = listFilesSafe(ftpClient);
			if (files != null) {
				List<String> subFolders = new ArrayList<>();
				for (FTPFile file : files) {
					if (file.isDirectory() && !file.getName().equals(".") && !file.getName().equals("..")) {
						subFolders.add(file.getName());
					}
				}
				folderNames = new String[subFolders.size() + 1];
				folderNames[0] = ".";
				for (int i = 0; i < subFolders.size(); i++) folderNames[i+1] = subFolders.get(i);
			}

			for (String folder : folderNames) {
				if (!folder.equals(".")) {
					if (!changeDirectorySafe(ftpClient, folder)) continue;
				}
				FTPFile latestCSV = findLatestCSVInCurrentDirSafe(ftpClient);
				if (latestCSV != null) {
					if (readAndDisplayCSVContent(ftpClient, latestCSV, folder.equals(".") ? "ROOT" : folder, ftpEntity.getId())) {
						foundData = true;
					}
				}
				if (!folder.equals(".")) changeDirectorySafe(ftpClient, "..");
			}
		} catch (Exception ex) {
			// Only log general errors
			System.out.println("Error reading latest CSV data: " + ex.getMessage());
		} finally {
			closeFTPConnectionSafe(ftpClient);
		}
		return foundData;
	}

	/**
	 * Helper method to parse CSV time with detailed debugging and validation
	 * @param csvTime Time string from CSV (e.g., "25/10/08 01:25")
	 * @return Formatted time string for database
	 */
	// Only parse format 'yy-MM-dd HH:mm:ss' and convert to 'yyyy-MM-dd HH:mm:ss'
	private String parseCSVTimeWithFallback(String csvTime) {
		try {
			SimpleDateFormat csvFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			csvFormat.setLenient(false);
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, 2020); // 2-digit year start: 2020
			csvFormat.set2DigitYearStart(calendar.getTime());
			Date parsedDate = csvFormat.parse(csvTime.trim());
			SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return dbFormat.format(parsedDate);
		} catch (Exception ex) {
			// If error, return original string (or optionally throw exception)
			return csvTime.trim();
		}
	}

}