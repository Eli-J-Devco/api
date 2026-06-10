package com.nwm.api.batchjob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nwm.api.services.S3AutoUploadLogService;
import com.nwm.api.utils.FLLogger;

/**
 * @description Batch job tự động upload các file log từ thư mục uploads lên S3
 * @author duc.van
 * @since 2025-12-04
 */
@Component
public class BatchJobS3AutoUploadLog {
	
	@Autowired
	private S3AutoUploadLogService s3AutoUploadLogService;
	
	protected final FLLogger logger = FLLogger.getLogger("BatchJobS3AutoUploadLog");
	
	/**
	 * @description Chạy mỗi 30 giây để upload file log lên S3
	 * @author duc.van
	 * @since 2025-12-04
	 */
	@Scheduled(fixedRate = 30000)
	public void autoUploadLogFiles() {
		try {
			logger.info("[SCHEDULER] Starting auto upload log files to S3...");
			int uploadedCount = s3AutoUploadLogService.uploadAllLogFiles();
			logger.info("[SCHEDULER] Finished uploading log files. Total uploaded: " + uploadedCount);
		} catch (Exception e) {
			logger.error("[SCHEDULER] Error during auto upload: " + e.getMessage(), e);
		}
	}
}
