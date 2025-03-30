package com.swiftpay.swiftpay_scheduler.service.validation;

import com.swiftpay.swiftpay_scheduler.entity.transfer.Transfer;
import com.swiftpay.swiftpay_scheduler.entity.transfer.TransferStatus;
import com.swiftpay.swiftpay_scheduler.exception.TransferCancellationNotAllowedException;
import org.springframework.stereotype.Component;

@Component
public class TransferCancellationValidator implements Validator<Transfer> {
    public void validate(Transfer transfer) {
       if (transfer.getStatus() == TransferStatus.CANCELLED || transfer.getStatus() == TransferStatus.COMPLETED) {
           throw new TransferCancellationNotAllowedException("Transfers with status '" + transfer.getStatus() + "' cannot be cancelled.");
       }
    }
}
