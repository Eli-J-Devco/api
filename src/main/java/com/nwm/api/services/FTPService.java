/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/


package com.nwm.api.services;

import com.nwm.api.entities.CSVHeaderEntity;

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
	public String getValueFromEntityByHeader(CSVHeaderEntity entity, String header) {
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
		 * Returns a list of all sites with complete FTP data
		 * @return List<FTPEntity> with complete data or empty list if none found
		 */
	public java.util.List<FTPEntity> getAllValidFTPConfigs() {
		java.util.List<FTPEntity> ftpConfigs = new java.util.ArrayList<>();
		SqlSession session = null;
		
		try {
			session = sqlMap.openSession();
			ftpConfigs = session.selectList("FTP.getAllValidFTPConfigs");
			
		} catch (Exception ex) {
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
		Pattern pattern = Pattern.compile("#(SmartLogger|INV\\d+|Sensor) ESN:([A-Z0-9]+)");
		for (String line : csvContent) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				serialNumbers.add(matcher.group(2));
			}
		}
		return serialNumbers;
	}



		/**
		 * Functional interface for FTP actions that can be retried
		 */
	   @FunctionalInterface
	   private interface FTPAction {
		   boolean run() throws Exception;
	   }

		/**
		 * General retry method for FTP actions
		 */
	   private boolean retryFTPAction(int maxRetries, long baseSleepMillis, FTPAction action, String actionName) {
		   for (int attempt = 1; attempt <= maxRetries; attempt++) {
			   try {
				   if (action.run()) {
					   System.out.println(actionName + " successful on attempt " + attempt);
					   return true;
				   }
			   } catch (Exception ex) {
				   //    System.out.println(actionName + " error on attempt " + attempt + ": " + ex.getMessage());
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
		//    System.out.println(actionName + " failed after " + maxRetries + " attempts");
		   return false;
	   }

		/**
		 * Replacement for connectWithRetry: connect to FTP with retry
		 */
	   private boolean connectWithRetry(FTPClient ftpClient, String server, int port) {
		   ftpClient.setConnectTimeout(180000); // 3 minutes
		   ftpClient.setDefaultTimeout(300000); // 5 minutes
		   return retryFTPAction(5, 3000L, () -> {
			   ftpClient.connect(server, port);
			   return true;
		   }, "FTP connect");
	   }

		/**
		 * Replacement for loginWithRetry: login to FTP with retry and auto reconnect if disconnected
		 */
	   private boolean loginWithRetry(FTPClient ftpClient, String user, String pass, String server, int port) {
		   return retryFTPAction(5, 2000L, () -> {
			   if (!ftpClient.isConnected()) {
				   ftpClient.connect(server, port);
			   }
			   ftpClient.setSoTimeout(180000); // 3 minutes
			   return ftpClient.login(user, pass);
		   }, "FTP login");
	   }


		/**
		 * Safely list files in a directory
		 * @param ftpClient FTPClient instance
		 * @param directory Folder path to list files from (if null or empty, current directory is used)
		 * @return Array of FTPFile or null if an error occurs
		 */
		private FTPFile[] listFilesInDirectorySafe(FTPClient ftpClient, String directory) {
			int maxRetries = 3;
			for (int attempt = 1; attempt <= maxRetries; attempt++) {
				try {
					if (directory != null && !directory.isEmpty()) {
						if (!ftpClient.changeWorkingDirectory(directory)) {
							//    System.out.println("Error changing to directory " + directory);
							return null;
						}
					}
					ftpClient.setSoTimeout(120000);
					FTPFile[] files = ftpClient.listFiles();
					return files;
				} catch (Exception ex) {
					//    System.out.println("Error listing files on attempt " + attempt + ": " + ex.getMessage());
					if (attempt < maxRetries) {
						try { Thread.sleep(1000L * attempt); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); return null; }
					}
				}
			}
			return null;
		}



		/**
		 * Find the latest CSV file in the current directory
		 * @param ftpClient Connected FTP client
		 * @return Latest CSV file or null if no CSV files found
		 */
	private FTPFile findLatestCSVInCurrentDirSafe(FTPClient ftpClient) {
		int maxRetries = 3;
		for (int attempt = 1; attempt <= maxRetries; attempt++) {
			try {
				FTPFile[] files = listFilesInDirectorySafe(ftpClient, null);
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
					//    System.out.println("Error finding latest CSV on attempt " + attempt + ": " + ex.getMessage());
				if (attempt < maxRetries) {
					try { Thread.sleep(1000L * attempt); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); return null; }
				}
			}
		}
		return null;
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
			// System.out.println("Error closing FTP connection: " + ex.getMessage());
		}
	}

		/**
		 * Read and display the latest CSV data from FTP - simplified for terminal output
		 * @param ftpEntity FTP configuration from database
		 * @return true if new data found and displayed
		 */
	public boolean readAndDisplayLatestCSVData(FTPEntity ftpEntity) {
		FTPClient ftpClient = new FTPClient();
		boolean foundNewData = false;
		try {
			   System.out.println("🔌 Connecting to FTP Server: " + ftpEntity.getFtpServer() + ":" + ftpEntity.getFtpPort());
			ftpClient.setConnectTimeout(150000);
			ftpClient.setDefaultTimeout(300000);
			int port = Integer.parseInt(ftpEntity.getFtpPort());
			if (!connectWithRetry(ftpClient, ftpEntity.getFtpServer(), port) ||
				!loginWithRetry(ftpClient, ftpEntity.getFtpUser(), ftpEntity.getFtpPass(), ftpEntity.getFtpServer(), port)) {
				//    System.out.println("❌ Unable to connect or login to FTP server");
				return false;
			}
			// System.out.println("✅ FTP connection successful!");
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			String ftpFolder = ftpEntity.getFtpFolder();
			// System.out.println("📁 Accessing folder: " + ftpFolder); 
			FTPFile[] initialFiles = listFilesInDirectorySafe(ftpClient, ftpFolder);
			if (initialFiles == null) {
				System.out.println("❌ Unable to access folder: " + ftpFolder); 
				return false; 
			} 
			   System.out.println("✅ Folder accessed successfully!"); 
			   // Combine root and subfolders into one loop
			   List<String> folders = new ArrayList<>();
			   folders.add("."); // root
			FTPFile[] files = listFilesInDirectorySafe(ftpClient, null);
			if (files != null) {
				for (FTPFile file : files) {
					if (file.isDirectory() && !file.getName().equals(".") && !file.getName().equals("..")) {
						folders.add(file.getName());
					}
				}
			}
			int folderCount = 0;
			for (String folder : folders) {
				if (!folder.equals(".")) {
					if (listFilesInDirectorySafe(ftpClient, folder) == null) continue;
					folderCount++;
					//    System.out.println("\n📁 Subfolder " + folderCount + ": " + folder);
				   } else {
					//    System.out.println("\n🔍 SEARCHING FOR THE LATEST CSV FILE IN ROOT FOLDER...");
				   }
				FTPFile latestCSV = findLatestCSVInCurrentDirSafe(ftpClient);
				   if (latestCSV != null) {
					//    System.out.println("📄 Latest CSV file: " + latestCSV.getName());
					//    System.out.println("📅 Timestamp: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(latestCSV.getTimestamp().getTime()));
					ftpClient.setSoTimeout(120000);
					ftpClient.setDataTimeout(120000);
					foundNewData |= tryReadCSVWithRetry(ftpClient, latestCSV, folder.equals(".") ? "ROOT" : folder, ftpEntity.getId());
				} else {
					//   System.out.println("📂 No CSV file in this folder");
				}
				if (!folder.equals(".")) listFilesInDirectorySafe(ftpClient, "..");
			}
			   if (folderCount > 0) System.out.println("✅ Checked " + folderCount + " subfolders");
		   } catch (Exception ex) {
			//    System.out.println("❌ Error reading FTP data: " + ex.getMessage());
			   ex.printStackTrace();
		} finally {
			closeFTPConnectionSafe(ftpClient);
		}
		return foundNewData;
	}

		// Helper method: retry reading CSV file
	private boolean tryReadCSVWithRetry(FTPClient ftpClient, FTPFile csvFile, String location, int siteId) {
		int maxReadRetries = 5;
		for (int attempt = 1; attempt <= maxReadRetries; attempt++) {
			try {
				   if (readAndDisplayCSVContent(ftpClient, csvFile, location, siteId)) {
					//    System.out.println("✅ Successfully read CSV file on attempt " + attempt);
					   return true;
				   }
				   break;
			   } catch (Exception ex) {
				   String msg = ex.getMessage() != null ? ex.getMessage().toLowerCase() : "";
				   if (msg.contains("timed out") || msg.contains("connect")) {
					//    System.out.println("Timeout or connection error reading CSV file, retrying attempt " + attempt);
					   try { Thread.sleep(3000L * attempt); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); break; }
					   continue;
				   }
				//    System.out.println("Error reading CSV file on attempt " + attempt + ": " + ex.getMessage());
				   break;
			}
		}
		return false;
	}
	
		/**
		 * Read CSV content, display to terminal, and process data for database insertion
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
	final List<String> EXPECTED_HEADER = CSVHeaderEntity.getHeaderList();
		try {
			   ftpClient.setSoTimeout(180000); // 3 minutes
			   ftpClient.setDataTimeout(180000);
			inputStream = ftpClient.retrieveFileStream(csvFile.getName());
			   if (inputStream == null) {
				//    System.out.println("❌ Unable to read file: " + csvFile.getName());
				   return false;
			   }
			reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			List<String> metadataLines = new ArrayList<>();
			List<String> header = EXPECTED_HEADER;
			boolean headerFound = false;
			List<CSVHeaderEntity> dataRecords = new ArrayList<>();
			int lineNumber = 0;
			//    System.out.println("\n📖 READING FILE CONTENT: " + csvFile.getName());
			//    System.out.println("📍 Location: " + location);
			//    System.out.println("🏢 Site ID: " + siteId);
			//    System.out.println("Using delimiter: " + CSV_DELIMITER);

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

			int dataLineNumber = 0;
			while (line != null) {
				dataLineNumber++;
				String[] fields = line.split(CSV_DELIMITER, -1);
				if (fields.length > 0 && fields[fields.length - 1].trim().isEmpty()) {
					fields = java.util.Arrays.copyOf(fields, fields.length - 1);
				}
				CSVHeaderEntity entity = CSVHeaderEntity.fromCsvFields(fields);
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
						for (CSVHeaderEntity record : dataRecords) {
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
					// System.out.println("❌ Error processing serial and insert: " + ex.getMessage());
					ex.printStackTrace();
				} finally {
					if (session != null) session.close();
				}
			}
			return dataLineNumber > 0;
		} catch (Exception ex) {
			// System.out.println("❌ Error reading and processing CSV: " + ex.getMessage());
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
		 * Process CSV data and insert into a specific data table
		 * @param csvContent All CSV content
		 * @param dataTableName Target table name (e.g., data5438_model_imtsolar_tv_class8004)
		 * @param serialNumber Device serial number (e.g., EM02311HJE00G3000008)
		 * @param siteId Site ID (e.g., 593)
		 * @param deviceId Device ID from database (e.g., 5438)
		 */
	private void processAndInsertData(List<String> csvContent, List<String> header, String dataTableName, String serialNumber, int siteId, int deviceId) {
		if (csvContent == null || header == null || header.size() == 0 || csvContent.size() < 2) return;
		List<Map<String, Object>> records = new ArrayList<>();
		for (String row : csvContent) {
			if (row.startsWith("#") || row.toLowerCase().contains("time")) continue;
			String[] cols = row.split(";");
			String[] fullCols = new String[header.size()];
			for (int i = 0; i < header.size(); i++) fullCols[i] = (i < cols.length) ? cols[i] : "0";
			Map<String, Object> record = new HashMap<>();
			record.put("time", parseCSVTimeWithFallback(fullCols[0]));
			for (int i = 1; i < header.size(); i++) {
				record.put(header.get(i), parseDoubleSafe(fullCols[i]));
			}
            Double pacValue = null;
            int pacIdx = header.indexOf("Pac");
            if (pacIdx >= 0) {
                pacValue = parseDoubleSafe(fullCols[pacIdx]);
                record.put("Pac", pacValue);
                record.put("nvmActivePower", pacValue);
            }
				// Ensure Eac = MeasuredProduction
				   Double eacValue = null;
				   int eacIdx = header.indexOf("Eac");
				   if (eacIdx >= 0) {
					   eacValue = parseDoubleSafe(fullCols[eacIdx]);
					   record.put("Eac", eacValue);
					   record.put("MeasuredProduction", eacValue);
				   }
			record.put("id_device", deviceId);
			records.add(record);
		}
		if (records.isEmpty()) return;
		int maxInsertRetries = 3;
		for (int attempt = 1; attempt <= maxInsertRetries; attempt++) {
			SqlSession session = null;
			try {
				session = sqlMap.openSession();
				Map<String, Object> batchParams = new HashMap<>();
				batchParams.put("datatablename", dataTableName);
				batchParams.put("records", records);
				int inserted = session.insert("DynamicData.insertDynamic", batchParams);
				if (inserted > 0) {
					   System.out.println("Inserted/updated " + dataTableName + " for device " + deviceId + " on attempt " + attempt);
					session.commit();
					break;
				}
				session.commit();
			} catch (Exception ex) {
				   System.out.println(" Error inserting batch into " + dataTableName + " on attempt " + attempt + ": " + ex.getMessage());
				   if (attempt == maxInsertRetries) {
					   System.out.println("Insert failed after " + maxInsertRetries + " attempts");
				   }
			} finally {
				if (session != null) session.close();
			}
			try { Thread.sleep(1000L * attempt); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); break; }
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
			if (!loginWithRetry(ftpClient, ftpEntity.getFtpUser(), ftpEntity.getFtpPass(), ftpEntity.getFtpServer(), Integer.parseInt(ftpEntity.getFtpPort()))) return false;
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			FTPFile[] testChangeDir2 = listFilesInDirectorySafe(ftpClient, ftpEntity.getFtpFolder());
			if (testChangeDir2 == null) return false;

			String[] folderNames = new String[] {"."};
			FTPFile[] files = listFilesInDirectorySafe(ftpClient, null);
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
					FTPFile[] testChangeDir3 = listFilesInDirectorySafe(ftpClient, folder);
					if (testChangeDir3 == null) continue;
				}
				FTPFile latestCSV = findLatestCSVInCurrentDirSafe(ftpClient);
				if (latestCSV != null) {
					if (readAndDisplayCSVContent(ftpClient, latestCSV, folder.equals(".") ? "ROOT" : folder, ftpEntity.getId())) {
						foundData = true;
					}
				}
				if (!folder.equals(".")) listFilesInDirectorySafe(ftpClient, "..");
			}
		} catch (Exception ex) {
			//    System.out.println("Error reading latest CSV data: " + ex.getMessage());
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
	private String parseCSVTimeWithFallback(String csvTime) {
		try {
			SimpleDateFormat csvFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			csvFormat.setLenient(false);
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, 2020); 
			csvFormat.set2DigitYearStart(calendar.getTime());
			Date parsedDate = csvFormat.parse(csvTime.trim());
			SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return dbFormat.format(parsedDate);
		} catch (Exception ex) {
			return csvTime.trim();
		}
	}

}