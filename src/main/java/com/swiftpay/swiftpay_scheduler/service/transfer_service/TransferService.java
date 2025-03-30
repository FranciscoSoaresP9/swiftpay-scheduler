package com.swiftpay.swiftpay_scheduler.service.transfer_service;

import com.swiftpay.swiftpay_scheduler.dto.transfer.TransferDTO;
import com.swiftpay.swiftpay_scheduler.dto.transfer.TransferSmallDTO;
import com.swiftpay.swiftpay_scheduler.dto.transfer.UpdateTransferDTO;
import com.swiftpay.swiftpay_scheduler.entity.transfer.Transfer;
import com.swiftpay.swiftpay_scheduler.entity.transfer.TransferStatus;
import com.swiftpay.swiftpay_scheduler.exception.TransferNotFoundException;
import com.swiftpay.swiftpay_scheduler.mapper.TransferMapper;
import com.swiftpay.swiftpay_scheduler.repository.TransferRepository;
import com.swiftpay.swiftpay_scheduler.service.user.UserService;
import com.swiftpay.swiftpay_scheduler.service.validation.UpdateTransferValidationParams;
import com.swiftpay.swiftpay_scheduler.service.validation.ValidatorFactory;
import com.swiftpay.swiftpay_scheduler.service.validation.ValidatorType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;
    private final ValidatorFactory validatorFactory;
    private final TransferMapper transferMapper;
    private final UserService userService;

    public void create(Transfer entity) {
        transferRepository.save(entity);
    }

    public Page<TransferSmallDTO> getAll(Pageable pageable) {
        var current = userService.getCurrentUser();
        return transferRepository.findBySenderAccountId(current.getBankAccount().getId(), pageable)
                .map(transferMapper::toSmallDTO);
    }

    public TransferDTO getDtoById(Long id) {
        return transferMapper.toDTO(getById(id));
    }

    public Transfer getById(Long id) {
        var current = userService.getCurrentUser();
        return transferRepository.findByIdAndSenderAccountId(id, current.getBankAccount().getId())
                .orElseThrow(() -> new TransferNotFoundException("Transfer with id " + id + " not found"));
    }

    @Transactional
    public TransferDTO update(Long id, UpdateTransferDTO write) {
        var transfer = getById(id);
        var account = transfer.getSenderAccount();
        validatorFactory
                .getValidator(ValidatorType.UPDATE_TRANSFER_VALIDATOR)
                .validate(new UpdateTransferValidationParams(transfer, write.amount(), account.getBalance(), write.scheduleDate()));

        transfer.setAmount(write.amount());
        transfer.setScheduleDate(write.scheduleDate());
        transferRepository.save(transfer);
        return transferMapper.toDTO(transfer);
    }

    @Transactional
    public void cancel(Long id) {
        var transfer = getById(id);
        validatorFactory.getValidator(ValidatorType.TRANSFER_CANCELLATION_VALIDATOR).validate(transfer);
        transfer.setStatus(TransferStatus.CANCELLED);
        transferRepository.save(transfer);
    }

    @Transactional
    public void delete(Long id) {
        var transfer = getById(id);
        validatorFactory.getValidator(ValidatorType.TRANSFER_DELETION_SERVICE).validate(transfer);
        transferRepository.delete(transfer);
    }
}
