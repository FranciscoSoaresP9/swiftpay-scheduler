package com.swiftpay.swiftpay_scheduler.service.schedule_transfer;

import com.swiftpay.swiftpay_scheduler.dto.transfer.TransferDTO;
import com.swiftpay.swiftpay_scheduler.dto.transfer.WriteTransferDTO;
import com.swiftpay.swiftpay_scheduler.entity.transfer.Transfer;
import com.swiftpay.swiftpay_scheduler.entity.transfer.TransferFee;
import com.swiftpay.swiftpay_scheduler.entity.transfer.TransferStatus;
import com.swiftpay.swiftpay_scheduler.entity.user.bank_account.BankAccount;
import com.swiftpay.swiftpay_scheduler.service.bank_account.BankAccountService;
import com.swiftpay.swiftpay_scheduler.service.fee.FeeCalculatorService;
import com.swiftpay.swiftpay_scheduler.service.fee.FeeService;
import com.swiftpay.swiftpay_scheduler.service.transfer_service.TransferService;
import com.swiftpay.swiftpay_scheduler.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    @Transactional
    @Override
    public TransferDTO schedule(WriteTransferDTO write) {
        log.info("Scheduling transfer for amount: {} to receiver IBAN: {}", write.amount(), write.receiverIban());

        var currentUserAccount = userService.getCurrentUser().getBankAccount();
        var receiverAccount = bankAccountService.findBankAccountByIban(write.receiverIban());
        var transferFee = getTransferFee(write.amount(), write.scheduleDate());

        var amountIncludingFees = calculateAmountIncludingFees(write.amount(), write.scheduleDate());

        var transfer = createTransfer(currentUserAccount, receiverAccount, write, transferFee, amountIncludingFees);

        validationService.validate(transfer, currentUserAccount.getBalance());

        transferService.create(transfer);
        bankAccountService.debitAccountBalance(currentUserAccount.getId(), transfer.getAmountIncludingFees());

        log.info("Transfer scheduled successfully for transfer ID: {}", transfer.getId());

        return mapToTransferDTO(transfer);
    }

    private TransferFee getTransferFee(BigDecimal amount, LocalDate scheduleDate) {
        return feeService.getFeeRange(amount, scheduleDate)
                .map(feeRange -> new TransferFee()
                        .withTaxPercentage(feeRange.getTaxPercentage())
                        .withFixedFee(feeRange.getFixedFee()))
                .orElse(new TransferFee()
                        .withTaxPercentage(BigDecimal.ZERO)
                        .withFixedFee(BigDecimal.ZERO));
    }

    private BigDecimal calculateAmountIncludingFees(BigDecimal amount, LocalDate scheduleDate) {
        var amountIncludingFees = calculatorService.calculateTotalAmountWithFee(amount, scheduleDate);
        log.info("Amount after applying fees: {}", amountIncludingFees);
        return amountIncludingFees;
    }

    private Transfer createTransfer(BankAccount senderAccount,
                                    BankAccount receiverAccount,
                                    WriteTransferDTO write,
                                    TransferFee transferFee,
                                    BigDecimal amountIncludingFees) {
        return new Transfer()
                .withSenderAccount(senderAccount)
                .withReceiverAccount(receiverAccount)
                .withAmount(write.amount())
                .withScheduleDate(write.scheduleDate())
                .withAppliedFee(transferFee)
                .withAmountIncludingFees(amountIncludingFees)
                .withStatus(TransferStatus.PENDING);
    }

    private TransferDTO mapToTransferDTO(Transfer transfer) {
        return new TransferDTO(
                transfer.getReceiverAccount().getIban(),
                transfer.getAmount(),
                transfer.getScheduleDate(),
                transfer.getAppliedFee().getTaxPercentage(),
                transfer.getAppliedFee().getFixedFee(),
                transfer.getAmountIncludingFees()
        );
    }
}
