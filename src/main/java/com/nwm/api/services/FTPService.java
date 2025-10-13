/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

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
import com.nwm.api.entities.ModelIMTSolarTvClass8004Entity;
import java.text.ParseException;
import java.util.Map;
import java.util.HashMap;
import java.util.Calendar;

@Service
public class FTPService extends DB {
	

	
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
		
		// Updated patterns to match your CSV format:
		// #SmartLogger ESN:2102311HJE10G3000008
		// #INV1 ESN:21010730386TG9900170
		Pattern[] patterns = {
			Pattern.compile("#SmartLogger ESN:([A-Z0-9]+)"),
			Pattern.compile("#INV\\d+ ESN:([A-Z0-9]+)"),
			Pattern.compile("#Sensor ESN:([A-Z0-9]+)") // Keep old pattern as fallback
		};
		
		for (String line : csvContent) {
			for (Pattern pattern : patterns) {
				Matcher matcher = pattern.matcher(line);
				if (matcher.find()) {
					String serialNumber = matcher.group(1);
					serialNumbers.add(serialNumber);
					System.out.println("🔍 Found serial number: " + serialNumber + " (from pattern: " + pattern.pattern() + ")");
				}
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
	 * Change directory safely
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
	 * List files in current directory safely
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
	 * Scheduled task - Run every 5 minutes to read LATEST CSV files only
	 * Simplified version for reading and displaying new data
	 */
	@Scheduled(fixedRate = 300000) // 5 minutes = 300,000 milliseconds
	public void readLatestDataScheduled() {
		Date currentTime = new Date();
		System.out.println("\n════════════════════════════════════════════════════");
		System.out.println("� SCHEDULER ĐANG CHẠY! FTPService.readLatestDataScheduled()");
		System.out.println("📅 Thời gian: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTime));
		System.out.println("🎯 Thread: " + Thread.currentThread().getName());
		System.out.println("════════════════════════════════════════════════════");
		
		try {
			// Get all valid FTP configs
			java.util.List<FTPEntity> allValidFtpConfigs = getAllValidFTPConfigs();
			
			if (allValidFtpConfigs != null && !allValidFtpConfigs.isEmpty()) {
				System.out.println("✅ Tìm thấy " + allValidFtpConfigs.size() + " cấu hình FTP");
				
				// Process each FTP connection
				for (FTPEntity ftpConfig : allValidFtpConfigs) {
					System.out.println("\n🏢 ĐANG XỬ LÝ SITE: " + ftpConfig.getId() + " - " + ftpConfig.getName());
					System.out.println("📁 Thư mục FTP: " + ftpConfig.getFtpFolder());
					
					// Read latest CSV data and display to terminal
					boolean hasData = readAndDisplayLatestCSVData(ftpConfig);
					
					if (hasData) {
						System.out.println("✅ Đã đọc dữ liệu cho Site " + ftpConfig.getId());
					} else {
						System.out.println("📊 Không có dữ liệu cho Site " + ftpConfig.getId());
					}
				}
				
			} else {
				System.out.println("⚠️ Không tìm thấy cấu hình FTP hợp lệ");
			}
			
		} catch (Exception ex) {
			System.out.println("❌ Lỗi khi đọc dữ liệu FTP: " + ex.getMessage());
			ex.printStackTrace();
		}
		
		System.out.println("\n════════════════════════════════════════════════════");
		System.out.println("✅ HOÀN THÀNH QUÉT DỮ LIỆU");
		System.out.println("⏰ Lần quét tiếp theo sau: 5 phút");
		System.out.println("════════════════════════════════════════════════════");
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
		
		try {
			System.out.println("\n📖 ĐANG ĐỌC NỘI DUNG FILE: " + csvFile.getName());
			System.out.println("📍 Vị trí: " + location);
			System.out.println("🏢 Site ID: " + siteId);
			
			inputStream = ftpClient.retrieveFileStream(csvFile.getName());
			if (inputStream == null) {
				System.out.println("❌ Không thể đọc file: " + csvFile.getName());
				return false;
			}
			
			reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			int lineNumber = 0;
			List<String> allLines = new ArrayList<>();
			
			System.out.println("┌─────────────────────────────────────────────────────────────────────────────┐");
			System.out.println("│                     TOÀN BỘ DỮ LIỆU CSV MỚI NHẤT                          │");
			System.out.println("├─────────────────────────────────────────────────────────────────────────────┤");
			
			// Read all lines
			while ((line = reader.readLine()) != null) {
				lineNumber++;
				allLines.add(line);
				
				if (lineNumber == 1) {
					// Header line
					System.out.println("│ HEADER: " + String.format("%-64s", line) + "│");
					System.out.println("├─────────────────────────────────────────────────────────────────────────────┤");
				} else {
					// Data lines - hiển thị hết tất cả dữ liệu
					System.out.println("│ " + String.format("%3d", lineNumber - 1) + ": " + String.format("%-67s", line) + "│");
				}
			}
			
			System.out.println("└─────────────────────────────────────────────────────────────────────────────┘");
			System.out.println("📊 Tổng số dòng đã hiển thị: " + (lineNumber - 1) + " dòng dữ liệu (HIỂN THỊ HET)");
			System.out.println("📅 Thời gian đọc: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			
			// Process serial numbers and find corresponding devices
			System.out.println("\n🔍 ĐANG XỬ LÝ SERIAL NUMBERS...");
			List<String> serialNumbers = extractSerialNumbers(allLines);
			
			if (!serialNumbers.isEmpty()) {
				System.out.println("✅ Tìm thấy " + serialNumbers.size() + " serial numbers trong CSV");
				
				// Get all devices for this site first (for better performance)
				SqlSession session = null;
				try {
					session = sqlMap.openSession();
					
					System.out.println("🏢 Đang tìm tất cả devices cho Site ID: " + siteId);
					List<java.util.Map<String, Object>> siteDevices = session.selectList("FTP.getDevicesBySiteId", siteId);
					System.out.println("� Tìm thấy " + siteDevices.size() + " devices trong site");
					
					// Display all devices in site for debugging
					for (java.util.Map<String, Object> device : siteDevices) {
						System.out.println("   📱 Device ID: " + device.get("id") + 
							", Serialnumber: " + device.get("serialnumber") + 
							", Table: " + device.get("datatablename"));
					}
					
					// Process each CSV serial number
					for (String csvSerialNumber : serialNumbers) {
						System.out.println("\n📡 Xử lý CSV Serial: " + csvSerialNumber);
						
						boolean foundDevice = false;
						
						// Find matching device in site devices
						for (java.util.Map<String, Object> device : siteDevices) {
							String dbSerialNumber = (String) device.get("serialnumber");
							
							if (csvSerialNumber.equals(dbSerialNumber)) {
								String dataTableName = (String) device.get("datatablename");
								Integer deviceId = (Integer) device.get("id");
								
								System.out.println("🎉 KHỚP! CSV Serial '" + csvSerialNumber + "' = DB serialnumber '" + dbSerialNumber + "'");
								System.out.println("✅ Device ID: " + deviceId + ", Table: " + dataTableName);
								System.out.println("🎯 Sẽ lưu dữ liệu vào bảng: " + dataTableName);
								
								// Process and insert data
								processAndInsertData(allLines, dataTableName, csvSerialNumber, siteId, deviceId);
								foundDevice = true;
								break;
							}
						}
						
						if (!foundDevice) {
							System.out.println("❌ Không tìm thấy device nào có serial '" + csvSerialNumber + "' trong site " + siteId);
							System.out.println("💡 Kiểm tra lại serial number trong CSV và database");
						}
					}
					
				} catch (Exception ex) {
					System.out.println("❌ Error processing devices: " + ex.getMessage());
					ex.printStackTrace();
				} finally {
					if (session != null) {
						session.close();
					}
				}
			} else {
				System.out.println("⚠️ Không tìm thấy serial number nào trong CSV file");
			}
			
			return lineNumber > 1; // Has data if more than just header
			
		} catch (Exception ex) {
			System.out.println("❌ Lỗi khi đọc nội dung CSV: " + ex.getMessage());
			return false;
		} finally {
			try {
				if (reader != null) reader.close();
				if (inputStream != null) inputStream.close();
				ftpClient.completePendingCommand();
			} catch (Exception cleanupEx) {
				System.out.println("⚠️ Lỗi cleanup: " + cleanupEx.getMessage());
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
	private void processAndInsertData(List<String> csvContent, String dataTableName, String serialNumber, int siteId, int deviceId) {
		System.out.println("\n💾 ═══════════════════════════════════════════════");
		System.out.println("🚀 BẮT ĐẦU XỬ LÝ VÀ LUU DỮ LIỆU");
		System.out.println("💾 ═══════════════════════════════════════════════");
		System.out.println("🏢 Site ID: " + siteId);
		System.out.println("📱 Device ID: " + deviceId);
		System.out.println("📡 Device Serial: " + serialNumber);
		System.out.println("🎯 Target Table: " + dataTableName);
		System.out.println("📊 Total CSV Lines: " + csvContent.size());
		
		// Process CSV data using MyBatis mapper
		SqlSession session = null;
		int processedLines = 0;
		int insertedRecords = 0;
		int skippedLines = 0;
		boolean inTargetDeviceSection = false;
		
		try {
			session = sqlMap.openSession();
			
			System.out.println("\n🔍 ONLY processing data for device serial: " + serialNumber);
			
			// Process each data line - ONLY extract data for target device
			for (int i = 0; i < csvContent.size(); i++) {
				String line = csvContent.get(i);
				processedLines++;
				
				// Skip empty lines
				if (line == null || line.trim().isEmpty()) {
					skippedLines++;
					continue;
				}
				
				// Check if we're entering the section for our target device
				if (line.contains("ESN:" + serialNumber)) {
					inTargetDeviceSection = true;
					System.out.println("🎯 Found target device section: " + line.trim());
					skippedLines++;
					continue;
				}
				
				// Check if we're entering a different device section (reset flag)
				if (line.startsWith("#") && line.contains("ESN:") && !line.contains(serialNumber)) {
					inTargetDeviceSection = false;
					System.out.println("📝 Skip other device section: " + line.substring(0, Math.min(50, line.length())) + "...");
					skippedLines++;
					continue;
				}
				
				// Skip header lines and metadata
				if (line.startsWith("#") || line.startsWith("//")) {
					System.out.println("📝 Skip metadata line " + (i + 1) + ": " + line.substring(0, Math.min(50, line.length())) + "...");
					skippedLines++;
					continue;
				}
				
				// Only process data lines if we're in the target device section
				if (!inTargetDeviceSection) {
					skippedLines++;
					continue;
				}
				
				// Parse CSV line - detect delimiter automatically
				String[] values;
				if (line.contains(";")) {
					values = line.split(";"); // Semicolon delimiter
					System.out.println("📋 Processing line " + i + " with " + values.length + " columns (semicolon-delimited)");
				} else {
					values = line.split("\t"); // Tab delimiter
					System.out.println("📋 Processing line " + i + " with " + values.length + " columns (tab-delimited)");
				}
				
				try {
					// Create parameter map for MyBatis
					Map<String, Object> params = new HashMap<>();
					params.put("datatablename", dataTableName);
					params.put("id_device", deviceId);
					params.put("error", 0.001);
					params.put("low_alarm", 0.001);
					params.put("high_alarm", 0.001);
					
					// CSV Column mapping for semicolon-delimited format:
					// Time;WSP;WD;PV Temp;Amp Temp;Radiation;Status;
					//  0   1   2     3       4        5       6
					// Example: 25-10-09 00:00:00;0.0;0;0.0;0.0;0.0;45056;
					
					// Parse time from CSV (column 0: #Time)
					if (values.length > 0) {
						String csvTime = values[0].trim();
						System.out.println("   🔍 Raw CSV Time: '" + csvTime + "'");
						
						// Use the helper method for better parsing
						String parsedTime = parseCSVTimeWithFallback(csvTime);
						params.put("time", parsedTime);
						
					} else {
						String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
						params.put("time", currentTime);
						System.out.println("   ⚠️ No time column found, using current time: " + currentTime);
					}
					
					// Parse WSP → wspeed (column 1)
					if (values.length > 1) {
						try {
							params.put("wspeed", Double.parseDouble(values[1].trim()));
						} catch (NumberFormatException e) {
							params.put("wspeed", 0.001);
						}
					} else {
						params.put("wspeed", 0.001);
					}
					
					// Parse PV Temp → tcell (column 3)
					if (values.length > 3) {
						try {
							params.put("tcell", Double.parseDouble(values[3].trim()));
						} catch (NumberFormatException e) {
							params.put("tcell", 0.001);
						}
					} else {
						params.put("tcell", 0.001);
					}
					
					// Parse Amp Temp → text (column 4)
					if (values.length > 4) {
						try {
							params.put("text", Double.parseDouble(values[4].trim()));
						} catch (NumberFormatException e) {
							params.put("text", 0.001);
						}
					} else {
						params.put("text", 0.001);
					}
					
					// Parse Radiation → irradiance + nvm_irradiance (column 5)
					if (values.length > 5) {
						try {
							double radiationValue = Double.parseDouble(values[5].trim());
							params.put("irradiance", radiationValue);
							params.put("nvm_irradiance", radiationValue); // Same value for both
						} catch (NumberFormatException e) {
							params.put("irradiance", 0.001);
							params.put("nvm_irradiance", 0.001);
						}
					} else {
						params.put("irradiance", 0.001);
						params.put("nvm_irradiance", 0.001);
					}
					
					// Set default values for other NVM fields
					params.put("nvm_temperature", 0.001);
					params.put("nvm_panel_temperature", 0.001);					// Insert using MyBatis mapper
					int result = session.insert("ModelIMTSolarTvClass8004.insertModelIMTSolarTvClass8004", params);
					
					if (result > 0) {
						insertedRecords++;
						System.out.println("✅ Inserted record " + insertedRecords + " - Line " + i);
						System.out.println("   📅 TIME: " + params.get("time"));
						System.out.println("   📊 DATA: irradiance=" + params.get("irradiance") + 
							", tcell=" + params.get("tcell") + 
							", text=" + params.get("text") + 
							", wspeed=" + params.get("wspeed"));
					}
					
				} catch (Exception parseEx) {
					System.out.println("❌ Error parsing line " + i + ": " + parseEx.getMessage());
					System.out.println("   Raw line: " + line);
					skippedLines++;
				}
			}
			
			// Commit transaction
			session.commit();
			System.out.println("💾 MyBatis transaction committed successfully!");
			
		} catch (Exception ex) {
			System.out.println("❌ Error processing CSV data: " + ex.getMessage());
			ex.printStackTrace();
			if (session != null) {
				session.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		System.out.println("\n✅ ═══════════════════════════════════════════════");
		System.out.println("🎉 HOÀN THÀNH XỬ LÝ DỮ LIỆU VỚI MYBATIS");
		System.out.println("✅ ═══════════════════════════════════════════════");
		System.out.println("📊 Processed Lines: " + processedLines);
		System.out.println("⏭️  Skipped Lines: " + skippedLines);
		System.out.println("💾 Successfully Inserted: " + insertedRecords + " records");
		System.out.println("🎯 Target Table: " + dataTableName);
		System.out.println("📡 Device Serial: " + serialNumber);
		System.out.println("🔧 MyBatis Mapper: ModelIMTSolarTvClass8004.insertModelIMTSolarTvClass8004");
	}

	/**
	 * Read ONLY the latest CSV file in each folder and display all content
	 * Optimized to find and process only the newest CSV file per directory
	 * @param ftpEntity FTP configuration
	 * @return true if data found, false otherwise
	 */
	public boolean readLatestCSVDataOnly(FTPEntity ftpEntity) {
		FTPClient ftpClient = new FTPClient();
		boolean foundData = false;
		
		try {
			// Setup connection timeouts
			ftpClient.setConnectTimeout(15000);
			ftpClient.setDefaultTimeout(30000);
			
			// Connect and login
			int port = Integer.parseInt(ftpEntity.getFtpPort());
			if (!connectWithRetry(ftpClient, ftpEntity.getFtpServer(), port)) {
				return false;
			}
			
			ftpClient.setSoTimeout(60000);
			
			if (!loginWithRetry(ftpClient, ftpEntity.getFtpUser(), ftpEntity.getFtpPass())) {
				return false;
			}
			
			// Configure FTP settings
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			
			// Navigate to FTP folder
			if (!changeDirectorySafe(ftpClient, ftpEntity.getFtpFolder())) {
				return false;
			}
			
			System.out.println("🎯 Strategy: Find and read ONLY the latest CSV file in each directory");
			
			// Check root folder for latest CSV ONLY
			System.out.println("🔍 [ROOT] Searching for latest CSV file...");
			FTPFile rootLatestCSV = findLatestCSVInCurrentDirSafe(ftpClient);
			if (rootLatestCSV != null) {
				System.out.println("📄 [ROOT] Found latest CSV: " + rootLatestCSV.getName() + 
					" (" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rootLatestCSV.getTimestamp().getTime()) + ")");
				
				if (readAndDisplayCSVContent(ftpClient, rootLatestCSV, "ROOT", ftpEntity.getId())) {
					foundData = true;
				}
			} else {
				System.out.println("⚠ [ROOT] No CSV files found");
			}
			
			// Get subfolders only
			FTPFile[] files = listFilesSafe(ftpClient);
			if (files != null) {
				int processedFolders = 0;
				
				// Process each subfolder to find ONLY the latest CSV
				for (FTPFile file : files) {
					if (file.isDirectory() && !file.getName().equals(".") && !file.getName().equals("..")) {
						processedFolders++;
						String folderName = file.getName();
						
						System.out.println("📁 [FOLDER " + processedFolders + "] " + folderName + " - Searching for latest CSV...");
						
						if (changeDirectorySafe(ftpClient, folderName)) {
							// Find ONLY the latest CSV in this folder
							FTPFile folderLatestCSV = findLatestCSVInCurrentDirSafe(ftpClient);
							if (folderLatestCSV != null) {
								System.out.println("📄 [" + folderName + "] Found latest CSV: " + folderLatestCSV.getName() + 
									" (" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(folderLatestCSV.getTimestamp().getTime()) + ")");
								
								if (readAndDisplayCSVContent(ftpClient, folderLatestCSV, folderName, ftpEntity.getId())) {
									foundData = true;
								}
							} else {
								System.out.println("⚠ [" + folderName + "] No CSV files found");
							}
							
							// Return to parent directory
							changeDirectorySafe(ftpClient, "..");
						}
					}
				}
				
				System.out.println("✅ Processed " + (processedFolders + 1) + " directories (root + " + processedFolders + " subfolders)");
			}
			
		} catch (Exception ex) {
			System.out.println("✗ Error reading latest CSV data: " + ex.getMessage());
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
		if (csvTime == null || csvTime.trim().isEmpty()) {
			System.out.println("   ⚠️ Empty CSV time, using current time");
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		}
		
		String cleanTime = csvTime.trim();
		System.out.println("   🔍 Parsing CSV time: '" + cleanTime + "'");
		
		try {
			// Try different primary formats based on detected pattern
			SimpleDateFormat csvFormat = null;
			String detectedPattern = "";
			
			// Detect time format pattern
			if (cleanTime.matches("\\d{2}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
				// Format: 25-10-09 00:00:00 (yy-MM-dd HH:mm:ss)
				// 25 = năm 2025, 10 = tháng 10, 09 = ngày 09
				csvFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
				detectedPattern = "yy-MM-dd HH:mm:ss";
			} else if (cleanTime.matches("\\d{2}/\\d{2}/\\d{2} \\d{2}:\\d{2}")) {
				// Format: 25/10/08 01:25
				csvFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
				detectedPattern = "dd/MM/yy HH:mm";
			} else if (cleanTime.matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}:\\d{2}")) {
				// Format: 25/10/2008 01:25:00
				csvFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				detectedPattern = "dd/MM/yyyy HH:mm:ss";
			}
			
			if (csvFormat != null) {
				csvFormat.setLenient(false); // Strict parsing
				
				// Set the century for 2-digit years (09 = 2025, not 2009)
				// Since we're in 2025, interpret 2-digit years with current century context
				if (detectedPattern.contains("yy") && !detectedPattern.contains("yyyy")) {
					Calendar calendar = Calendar.getInstance();
					calendar.set(Calendar.YEAR, 2020); // Set base year to 2020
					csvFormat.set2DigitYearStart(calendar.getTime()); // 20-99 will be 2020-2099, 00-19 will be 2100-2119
					System.out.println("   🎯 Set 2-digit year start to 2020 for pattern: " + detectedPattern);
					System.out.println("   📅 This means: 09 → 2025, 20 → 2020, 99 → 2099");
				}
				
				Date parsedDate = csvFormat.parse(cleanTime);
				
				// Format for database insertion (yyyy-MM-dd HH:mm:ss)
				SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String formattedTime = dbFormat.format(parsedDate);
				
				System.out.println("   ✅ Time parse SUCCESS: '" + cleanTime + "' → '" + formattedTime + "' (pattern: " + detectedPattern + ")");
				System.out.println("   🔍 Parsed Date object: " + parsedDate);
				return formattedTime;
			} else {
				throw new ParseException("No matching time pattern found for: " + cleanTime, 0);
			}
			
		} catch (ParseException parseEx) {
			System.out.println("   ❌ Time parse FAILED for '" + cleanTime + "': " + parseEx.getMessage());
			System.out.println("   📝 Expected format: dd/MM/yy HH:mm (e.g., 25/10/08 01:25)");
			
			// Try alternative formats as fallback
			try {
				SimpleDateFormat[] fallbackFormats = {
					new SimpleDateFormat("yy-MM-dd HH:mm:ss"), // 25-10-09 00:00:00 (yy-MM-dd)
					new SimpleDateFormat("dd-MM-yy HH:mm:ss"), // 09-10-25 00:00:00 (dd-MM-yy) - old format
					new SimpleDateFormat("dd/MM/yy HH:mm"),    // 25/10/08 01:25
					new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"), // 25/10/2008 01:25:00
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), // Already formatted
					new SimpleDateFormat("MM/dd/yy HH:mm"),    // US format
				};
				
				for (SimpleDateFormat format : fallbackFormats) {
					try {
						format.setLenient(false);
						
						// Set 2-digit year start for fallback formats too (09 = 2025)
						if (format.toPattern().contains("yy") && !format.toPattern().contains("yyyy")) {
							Calendar fallbackCalendar = Calendar.getInstance();
							fallbackCalendar.set(Calendar.YEAR, 2020); // Same logic: 2020 base year
							format.set2DigitYearStart(fallbackCalendar.getTime());
						}
						
						Date parsedDate = format.parse(cleanTime);
						SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String formattedTime = dbFormat.format(parsedDate);
						System.out.println("   🔄 Fallback SUCCESS with format '" + format.toPattern() + "': '" + cleanTime + "' → '" + formattedTime + "'");
						return formattedTime;
					} catch (ParseException e) {
						// Continue to next format
					}
				}
			} catch (Exception fallbackEx) {
				System.out.println("   ❌ All fallback formats failed: " + fallbackEx.getMessage());
			}
			
			// Last resort: use current time
			String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			System.out.println("   🔄 Using current time as last resort: " + currentTime);
			return currentTime;
		}
	}

}