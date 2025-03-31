package com.swiftpay.swiftpay_scheduler.service.scheduler;

import com.swiftpay.swiftpay_scheduler.entity.transfer.TransferStatus;
import com.swiftpay.swiftpay_scheduler.repository.TransferRepository;
import com.swiftpay.swiftpay_scheduler.service.transfer_service.TransferService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class TransferScheduler {

    private final TransferRepository transferRepository;
    private final TransferService transferService;

    @Scheduled(cron = "0 */30 * * * ?")
    public void processPendingTransfers() {
        log.info("Starting to process pending transfers...");

        var pendingTransfers = transferRepository.findByStatusAndCurrentDate(TransferStatus.PENDING);

        log.info("Found {} pending transfers to process.", pendingTransfers.size());

        pendingTransfers.forEach(transfer -> {
            try {
                transferService.processTransfer(transfer);
            }catch (Exception e) {
                transfer.setStatus(TransferStatus.FAILED);
                transferRepository.save(transfer);
                log.error("Failed to process transfer ID: {} - Error: ", transfer.getId(), e);
            }
        });

        log.info("Finished processing pending transfers.");
    }

    @Scheduled(cron = "0 0 */2 * * ?")
    public void processFailedTransfers() {

        log.info("Starting to retry failed transfers...");

        var failedTransfers = transferRepository.findByStatusAndCurrentDate(TransferStatus.FAILED);
        log.info("Found {} failed transfers to retry.", failedTransfers.size());

        failedTransfers.forEach(transfer -> {
            try {
                transferService.processTransfer(transfer);
            }catch (Exception e) {
                log.error("Retry failed for transfer ID: {} - Error: ", transfer.getId(), e);
            }
        });

        log.info("Finished retrying failed transfers.");
    }

}
