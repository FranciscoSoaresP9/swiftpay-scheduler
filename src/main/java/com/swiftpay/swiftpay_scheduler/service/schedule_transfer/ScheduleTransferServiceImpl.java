package com.swiftpay.swiftpay_scheduler.service.schedule_transfer;

import com.swiftpay.swiftpay_scheduler.dto.transfer.TransferDTO;
import com.swiftpay.swiftpay_scheduler.dto.transfer.WriteTransferDTO;
import com.swiftpay.swiftpay_scheduler.entity.transfer.Transfer;
import com.swiftpay.swiftpay_scheduler.entity.transfer.TransferFees;
import com.swiftpay.swiftpay_scheduler.entity.transfer.TransferStatus;
import com.swiftpay.swiftpay_scheduler.service.bank_account.BankAccountService;
import com.swiftpay.swiftpay_scheduler.service.fee.FeeCalculatorService;
import com.swiftpay.swiftpay_scheduler.service.fee.FeeService;
import com.swiftpay.swiftpay_scheduler.service.transfer_service.TransferService;
import com.swiftpay.swiftpay_scheduler.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleTransferServiceImpl implements ScheduleTransferService {

    private final FeeCalculatorService calculatorService;
    private final ScheduleTransferValidationService validationService;
    private final BankAccountService bankAccountService;
    private final TransferService transferService;
    private final UserService userService;
    private final FeeService feeService;

    @Override
    public TransferDTO schedule(WriteTransferDTO write) {
        log.info("Scheduling transfer process started for amount: {} and receiver IBAN: {}", write.amount(), write.receiverIban());

        validationService.validate(write);
        log.info("Validation of transfer data completed successfully for amount: {} and receiver IBAN: {}", write.amount(), write.receiverIban());

        var currentUserAccount = userService.getCurrentUser().getBankAccount();
        var receiverAccount = bankAccountService.findBankAccountByIban(write.receiverIban());
        var fee = feeService.getFeeRange(write.amount(), write.scheduleDate());

        var appliedTax = fee.map(feeRange -> new TransferFees()
                .withTaxPercentage(feeRange.getTaxPercentage())
                .withFixedFee(feeRange.getFixedFee())).orElse(null);

        var amountIncludingFees = calculatorService.calculateTotalAmountWithFee(write.amount(), write.scheduleDate());
        log.info("Total amount after applying fee: {}", amountIncludingFees);

        var transfer = new Transfer()
                .withSenderAccount(currentUserAccount)
                .withReceiverAccount(receiverAccount)
                .withAmount(write.amount())
                .withScheduleDate(write.scheduleDate())
                .withAppliedTax(appliedTax)
                .withAmountIncludingFees(amountIncludingFees)
                .withStatus(TransferStatus.PENDING);

        transferService.create(transfer);
        log.info("Transfer created: sender ID: {}, receiver ID: {}, amount: {}, total amount: {}",
                currentUserAccount.getId(), receiverAccount.getId(), write.amount(), amountIncludingFees);

        log.info("Transfer scheduling process completed successfully for transfer ID: {}", transfer.getId());

        return new TransferDTO(
                transfer.getReceiverAccount().getIban(),
                transfer.getAmount(),
                transfer.getScheduleDate(),
                transfer.getAppliedTax().getTaxPercentage(),
                transfer.getAppliedTax().getFixedFee(),
                transfer.getAmountIncludingFees()
        );
    }

}
