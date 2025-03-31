package com.swiftpay.swiftpay_scheduler.service.transfer_service;

import com.swiftpay.swiftpay_scheduler.dto.transfer.TransferDTO;
import com.swiftpay.swiftpay_scheduler.dto.transfer.TransferSmallDTO;
import com.swiftpay.swiftpay_scheduler.dto.transfer.UpdateTransferDTO;
import com.swiftpay.swiftpay_scheduler.dto.transfer.WriteTransferDTO;
import com.swiftpay.swiftpay_scheduler.entity.transfer.Transfer;
import com.swiftpay.swiftpay_scheduler.entity.transfer.TransferFee;
import com.swiftpay.swiftpay_scheduler.entity.transfer.TransferStatus;
import com.swiftpay.swiftpay_scheduler.entity.user.bank_account.BankAccount;
import com.swiftpay.swiftpay_scheduler.exception.TransferNotFoundException;
import com.swiftpay.swiftpay_scheduler.mapper.TransferMapper;
import com.swiftpay.swiftpay_scheduler.repository.TransferRepository;
import com.swiftpay.swiftpay_scheduler.service.bank_account.BankAccountService;
import com.swiftpay.swiftpay_scheduler.service.fee.FeeCalculatorService;
import com.swiftpay.swiftpay_scheduler.service.fee.FeeService;
import com.swiftpay.swiftpay_scheduler.service.user.UserService;
import com.swiftpay.swiftpay_scheduler.service.validation.CreateTransferValidationParams;
import com.swiftpay.swiftpay_scheduler.service.validation.UpdateTransferValidationParams;
import com.swiftpay.swiftpay_scheduler.service.validation.ValidatorFactory;
import com.swiftpay.swiftpay_scheduler.service.validation.ValidatorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferServiceImpl implements TransferService {

    private final FeeCalculatorService calculatorService;
    private final TransferRepository repository;
    private final BankAccountService bankAccountService;
    private final ValidatorFactory validatorFactory;
    private final TransferMapper transferMapper;
    private final UserService userService;
    private final FeeService feeService;

    @Override
    public Page<TransferSmallDTO> getAll(Pageable pageable) {
        var current = userService.getCurrentUser();
        return repository.findBySenderAccountId(current.getBankAccount().getId(), pageable)
                .map(transferMapper::toSmallDTO);
    }

    @Override
    public TransferDTO getDtoById(Long id) {
        return transferMapper.toDTO(getById(id));
    }

    @Override
    public Transfer getById(Long id) {
        var current = userService.getCurrentUser();
        return repository.findByIdAndSenderAccountId(id, current.getBankAccount().getId())
                .orElseThrow(() -> new TransferNotFoundException("Transfer with id " + id + " not found"));
    }

    @Transactional
    @Override
    public TransferDTO create(WriteTransferDTO write) {
        log.info("Scheduling transfer for amount: {} to receiver IBAN: {}", write.amount(), write.receiverIban());

        var currentUserAccount = userService.getCurrentUser().getBankAccount();
        var receiverAccount = bankAccountService.findBankAccountByIban(write.receiverIban());
        var transferFee = getTransferFee(write.amount(), write.scheduleDate());
        var amountIncludingFees = calculateAmountIncludingFees(write.amount(), write.scheduleDate());
        var transfer = createTransfer(currentUserAccount, receiverAccount, write, transferFee, amountIncludingFees);

        validatorFactory
                .getValidator(ValidatorType.CREATE_TRANSFER_VALIDATOR)
                .validate(new CreateTransferValidationParams(transfer, currentUserAccount.getBalance()));

        bankAccountService.debit(currentUserAccount.getId(), transfer.getAmountIncludingFees());

        repository.save(transfer);

        log.info("Transfer scheduled successfully ID: {}", transfer.getId());

        return transferMapper.toDTO(transfer);
    }

    @Transactional
    @Override
    public TransferDTO update(Long id, UpdateTransferDTO write) {
        var transfer = getById(id);
        var account = transfer.getSenderAccount();
        var transferFee = getTransferFee(write.amount(), write.scheduleDate());
        var amountIncludingFees = calculateAmountIncludingFees(write.amount(), write.scheduleDate());

        validatorFactory
                .getValidator(ValidatorType.UPDATE_TRANSFER_VALIDATOR)
                .validate(new UpdateTransferValidationParams(transfer, write.amount(), account.getBalance(), write.scheduleDate()));

        adjustUserAccountBalance(transfer, amountIncludingFees);
        transfer.setAmount(write.amount());
        transfer.setScheduleDate(write.scheduleDate());
        transfer.setAmountIncludingFees(amountIncludingFees);
        transfer.setAppliedFee(transferFee);
        repository.save(transfer);
        return transferMapper.toDTO(transfer);
    }

    @Transactional
    @Override
    public void processTransfer(Transfer transfer) {
        var receiverAccount = transfer.getReceiverAccount();
        bankAccountService.deposit(receiverAccount.getId(), transfer.getAmount());
        transfer.setStatus(TransferStatus.COMPLETED);
        repository.save(transfer);
        log.info("Successfully processed transfer ID: {}", transfer.getId());
    }

    @Transactional
    @Override
    public void cancelTransfer(Long id) {
        var transfer = getById(id);
        var current = userService.getCurrentUser();
        validatorFactory.getValidator(ValidatorType.TRANSFER_CANCELLATION_VALIDATOR).validate(transfer);
        transfer.setStatus(TransferStatus.CANCELLED);
        bankAccountService.deposit(current.getBankAccount().getId(), transfer.getAmount());
        repository.save(transfer);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        var transfer = getById(id);
        validatorFactory.getValidator(ValidatorType.TRANSFER_DELETION_SERVICE).validate(transfer);
        repository.delete(transfer);
    }

    private void adjustUserAccountBalance(Transfer transfer, BigDecimal updatedAmount) {
        var currentUser = userService.getCurrentUser();
        var transferAmount = transfer.getAmount();
        var diffAmount = updatedAmount.subtract(transferAmount);

        if (diffAmount.compareTo(BigDecimal.ZERO) > 0) {
            bankAccountService.debit(currentUser.getBankAccount().getId(), diffAmount);
        } else if (diffAmount.compareTo(BigDecimal.ZERO) < 0) {
            bankAccountService.deposit(currentUser.getBankAccount().getId(), diffAmount.abs());
        }
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

}
