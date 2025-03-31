package com.swiftpay.swiftpay_scheduler.service.validation;

import com.swiftpay.swiftpay_scheduler.entity.transfer.Transfer;
import com.swiftpay.swiftpay_scheduler.entity.transfer.TransferStatus;
import com.swiftpay.swiftpay_scheduler.exception.TransferModificationNotAllowedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class UpdateTransferValidator implements Validator<UpdateTransferValidationParams> {

    @Lazy
    @Autowired
    private ValidatorFactory validatorFactory;

    public void validate(UpdateTransferValidationParams params) {
        validatorFactory
                .getValidator(ValidatorType.TRANSFER_BALANCE_VALIDATOR)
                .validate(new TransferBalanceValidationParams(params.transfer().getAmountIncludingFees(), params.currentBalance()));
        validatorFactory
                .getValidator(ValidatorType.TRANSFER_DATE_VALIDATOR)
                .validate(params.date());
        validateStatus(params.transfer());
    }

    private void validateStatus(Transfer transfer) {
        if (transfer.getStatus().equals(TransferStatus.CANCELLED) || transfer.getStatus().equals(TransferStatus.COMPLETED)) {
            throw new TransferModificationNotAllowedException("Updates are not allowed for transfers with status: " + transfer.getStatus());
        }
    }

}
