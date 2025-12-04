package com.nwm.api.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nwm.api.utils.FLLogger;

@Service
public class S3AutoUploadLogService {
	
	@Autowired
	private S3BasicService s3BasicService;
	
	@Value("${uploadpath.root}")
	private String uploadPath;
	
	protected final FLLogger logger = FLLogger.getLogger("S3AutoUploadLogService");
	
	// Pattern để parse tên file: bm-{serialnumber}-{modbusport}-{modbusdevice}-{unique}.{timestamp}.log hoặc .log.gz
	private static final Pattern FILE_NAME_PATTERN = Pattern.compile(
		"bm-([^-]+)-([^-]+)-([^-]+)-([a-f0-9\\-]+)\\.(\\d{14})\\.(log(\\.gz)?)"
	);
	
	/**
	 * @description Tìm và upload tất cả file log trong thư mục uploads lên S3
	 * @author duc.van
	 * @since 2025-12-04
	 */
	public int uploadAllLogFiles() {
		int successCount = 0;
		
		try {
			logger.info("Starting auto upload log files to S3...");
			
			// Lấy danh sách tất cả file log trong thư mục uploads
			List<File> logFiles = findAllLogFiles();
			
			if (logFiles.isEmpty()) {
				logger.info("No log files found in uploads directory");
				return 0;
			}
			
			logger.info("Found " + logFiles.size() + " log files to upload");
			
			for (File logFile : logFiles) {
				try {
					boolean uploaded = uploadSingleLogFile(logFile);
					if (uploaded) {
						successCount++;
					}
				} catch (Exception e) {
					logger.error("Error uploading file: " + logFile.getName() + " - " + e.getMessage());
				}
			}
			
			logger.info("Upload completed. Success: " + successCount + "/" + logFiles.size());
			
		} catch (Exception e) {
			logger.error("Error in uploadAllLogFiles: " + e.getMessage());
		}
		
		return successCount;
	}
	
	/**
	 * @description Upload một file log lên S3 và xóa file nếu thành công
	 * @author duc.van
	 * @since 2025-12-04
	 */
	public boolean uploadSingleLogFile(File logFile) {
		try {
			String fileName = logFile.getName();
			
			// Parse tên file để lấy thông tin
			LogFileInfo fileInfo = parseFileName(fileName);
			if (fileInfo == null) {
				logger.warn("Cannot parse file name: " + fileName);
				return false;
			}
			
			// Parse timestamp để lấy year, month, day
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
			LocalDateTime dateTime = LocalDateTime.parse(fileInfo.timestamp, formatter);
			
			String year = dateTime.format(DateTimeFormatter.ofPattern("yyyy"));
			String month = dateTime.format(DateTimeFormatter.ofPattern("MM"));
			String day = dateTime.format(DateTimeFormatter.ofPattern("dd"));
			
			// Tạo S3 path theo format: dataloggers/{serialnumber}/{modbusport}/{year}/{month}/{day}/{filename}
			String s3Path = "dataloggers/" + fileInfo.serialnumber + "/" + fileInfo.modbusport + "/" 
							+ year + "/" + month + "/" + day + "/" + fileName;
			
			logger.info("Uploading file: " + fileName + " to S3 path: " + s3Path);
			
			// Upload file lên S3
			s3BasicService.uploadFile(logFile.getAbsolutePath(), s3Path);
			
			// Xóa file sau khi upload thành công
			boolean deleted = logFile.delete();
			if (deleted) {
				logger.info("File deleted successfully: " + fileName);
			} else {
				logger.warn("Failed to delete file: " + fileName);
			}
			
			return true;
			
		} catch (Exception e) {
			logger.error("Failed to upload file: " + logFile.getName() + " - " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * @description Tìm tất cả file log trong thư mục uploads và các thư mục con
	 * @author duc.van
	 * @since 2025-12-04
	 */
	private List<File> findAllLogFiles() throws IOException {
		List<File> logFiles = new ArrayList<>();
		Path uploadsDir = Paths.get(uploadPath);
		
		if (!Files.exists(uploadsDir)) {
			logger.warn("Uploads directory does not exist: " + uploadPath);
			return logFiles;
		}
		
		// Tìm file log trong tất cả các thư mục con
		scanDirectory(uploadsDir.toFile(), logFiles);
		
		return logFiles;
	}
	
	/**
	 * @description Quét thư mục đệ quy để tìm file log
	 * @author duc.van
	 * @since 2025-12-04
	 */
	private void scanDirectory(File directory, List<File> logFiles) {
		if (!directory.isDirectory()) {
			return;
		}
		
		File[] files = directory.listFiles();
		if (files == null) {
			return;
		}
		
		for (File file : files) {
			if (file.isDirectory()) {
				// Quét thư mục con
				scanDirectory(file, logFiles);
			} else if (file.isFile() && (file.getName().endsWith(".log") || file.getName().endsWith(".log.gz"))) {
				// Kiểm tra xem file có đúng format không
				if (FILE_NAME_PATTERN.matcher(file.getName()).matches()) {
					logFiles.add(file);
				}
			}
		}
	}
	
	/**
	 * @description Parse tên file để lấy thông tin
	 * Format: bm-{serialnumber}-{modbusport}-{modbusdevice}-{unique}.{timestamp}.log
	 * @author duc.van
	 * @since 2025-12-04
	 */
	private LogFileInfo parseFileName(String fileName) {
		Matcher matcher = FILE_NAME_PATTERN.matcher(fileName);
		
		if (!matcher.matches()) {
			return null;
		}
		
		LogFileInfo info = new LogFileInfo();
		info.serialnumber = matcher.group(1);
		info.modbusport = matcher.group(2);
		info.modbusdevice = matcher.group(3);
		info.unique = matcher.group(4);
		info.timestamp = matcher.group(5);
		
		return info;
	}
	
	/**
	 * Inner class để lưu thông tin file log
	 */
	private static class LogFileInfo {
		String serialnumber;
		String modbusport;
		String modbusdevice;
		String unique;
		String timestamp;
	}
}
