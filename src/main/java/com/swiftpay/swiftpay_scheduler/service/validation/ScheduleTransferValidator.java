package com.swiftpay.swiftpay_scheduler.service.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduleTransferValidator implements Validator<ScheduleTransferValidationParams> {

    private final ValidatorFactory validatorFactory;

    public void validate(ScheduleTransferValidationParams params) {
        validatorFactory
                .getValidator(ValidatorType.TRANSFER_BALANCE_VALIDATOR)
                .validate(new TransferBalanceValidationParams(params.transfer().getAmountIncludingFees(), params.currentBalance()));
        validatorFactory
                .getValidator(ValidatorType.TRANSFER_DATE_VALIDATOR)
                .validate(params.transfer().getScheduleDate());
    }

}
