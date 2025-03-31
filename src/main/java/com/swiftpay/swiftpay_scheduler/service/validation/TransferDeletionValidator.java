package com.swiftpay.swiftpay_scheduler.service.validation;

import com.swiftpay.swiftpay_scheduler.entity.transfer.Transfer;
import com.swiftpay.swiftpay_scheduler.entity.transfer.TransferStatus;
import com.swiftpay.swiftpay_scheduler.exception.TransferDeletionNotAllowedException;
import org.springframework.stereotype.Component;

@Component
public class TransferDeletionValidator implements Validator<Transfer>{
    public void validate(Transfer transfer) {
        if(transfer.getStatus() == TransferStatus.COMPLETED) {
            throw new TransferDeletionNotAllowedException("Transfers with status '" + transfer.getStatus() + "' cannot be deleted.");
        }

        if (transfer.getStatus() != TransferStatus.CANCELLED) {
            throw new TransferDeletionNotAllowedException("Transfers must be cancelled before they can be deleted.");
        }
    }
}
