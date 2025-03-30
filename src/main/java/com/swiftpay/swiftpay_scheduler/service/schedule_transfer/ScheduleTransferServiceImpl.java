package com.swiftpay.swiftpay_scheduler.service.schedule_transfer;

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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleTransferServiceImpl implements ScheduleTransferService {

    private final FeeCalculatorService calculatorService;
    private final BankAccountService bankAccountService;
    private final TransferService transferService;
    private final UserService userService;
    private final FeeService feeService;

    @Override
    public void ScheduleTransfer(WriteTransferDTO write) {
        var currentUserAccount = userService.getCurrentUser().getBankAccount();
        var receiverAccount = bankAccountService.findBankAccountByIban(write.receiverIban());
        var fee = feeService.getFeeRange(write.amount(), write.scheduleDate());

        var appliedTax = fee.map(feeRange -> new TransferFees()
                .withTaxPercentage(feeRange.getTaxPercentage())
                .withFixedFee(feeRange.getFixedFee())).orElse(null);

        var totalAmount = calculatorService.calculateTotalAmountWithFee(write.amount(), write.scheduleDate());

        var transfer = new Transfer()
                .withSenderAccount(currentUserAccount)
                .withReceiverAccount(receiverAccount)
                .withAmount(write.amount())
                .withScheduleDate(write.scheduleDate())
                .withAppliedTax(appliedTax)
                .withTotalAmount(totalAmount)
                .withStatus(TransferStatus.PENDING);

        transferService.create(transfer);

    }

}
