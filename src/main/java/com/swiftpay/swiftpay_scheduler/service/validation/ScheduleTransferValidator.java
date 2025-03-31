package com.swiftpay.swiftpay_scheduler.service.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTransferValidator implements Validator<CreateTransferValidationParams> {

    @Lazy
    @Autowired
    private ValidatorFactory validatorFactory;

    public void validate(CreateTransferValidationParams params) {
        validatorFactory
                .getValidator(ValidatorType.TRANSFER_BALANCE_VALIDATOR)
                .validate(new TransferBalanceValidationParams(params.transfer().getAmountIncludingFees(), params.currentBalance()));
        validatorFactory
                .getValidator(ValidatorType.TRANSFER_DATE_VALIDATOR)
                .validate(params.transfer().getScheduleDate());
    }

}
