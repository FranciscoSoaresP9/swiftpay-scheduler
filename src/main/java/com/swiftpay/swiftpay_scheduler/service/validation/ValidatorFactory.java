package com.swiftpay.swiftpay_scheduler.service.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidatorFactory {

    private final ScheduleTransferValidator scheduleTransferValidator;
    private final TransferBalanceValidator transferBalanceValidator;
    private final TransferCancellationValidator transferCancellationValidator;
    private final TransferDateValidator transferDateValidator;
    private final TransferDeletionService transferDeletionService;
    private final UpdateTransferValidator updateTransferValidator;

    public <T> Validator<T> getValidator(ValidatorType type) {
        return switch (type) {
            case TRANSFER_BALANCE_VALIDATOR -> (Validator<T>) transferBalanceValidator;
            case SCHEDULE_TRANSFER_VALIDATOR -> (Validator<T>) scheduleTransferValidator;
            case TRANSFER_CANCELLATION_VALIDATOR -> (Validator<T>) transferCancellationValidator;
            case TRANSFER_DATE_VALIDATOR -> (Validator<T>) transferDateValidator;
            case TRANSFER_DELETION_SERVICE -> (Validator<T>) transferDeletionService;
            case UPDATE_TRANSFER_VALIDATOR -> (Validator<T>) updateTransferValidator;
        };
    }

}
