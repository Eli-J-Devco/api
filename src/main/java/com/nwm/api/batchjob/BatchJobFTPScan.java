package com.nwm.api.batchjob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.nwm.api.services.FTPService;
import com.nwm.api.entities.FTPEntity;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class BatchJobFTPScan {
    @Autowired
    private FTPService ftpService;

    @Scheduled(fixedRate = 300000) // 5 minutes
    public void readLatestDataScheduled() {
        try {
            List<FTPEntity> allValidFtpConfigs = ftpService.getAllValidFTPConfigs();
            if (allValidFtpConfigs == null || allValidFtpConfigs.isEmpty()) {
                System.out.println("No valid FTP configurations found");
                return;
            }
            System.out.println("Found " + allValidFtpConfigs.size() + " FTP configurations to process.");
            List<CompletableFuture<Void>> tasks = allValidFtpConfigs.stream()
                .map(cfg -> CompletableFuture.runAsync(() -> {
                    try {
                        ftpService.readAndDisplayLatestCSVData(cfg);
                    } catch (Exception e) {
                        System.out.println("Error processing FTP " + cfg.getFtpServer() + ": " + e.getMessage());
                    }
                }))
                .collect(Collectors.toList());
            CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).join();
        } catch (Exception ex) {
            System.out.println("Error reading FTP data: " + ex.getMessage());
            ex.printStackTrace();
        }
        System.out.println("[SCHEDULER] Finished scanning FTP data.");
    }
}
