package com.swiftpay.swiftpay_scheduler.service.schedule_transfer;


import com.swiftpay.swiftpay_scheduler.dto.transfer.WriteTransferDTO;

public interface ScheduleTransferService {
    void ScheduleTransfer(WriteTransferDTO write);
}
