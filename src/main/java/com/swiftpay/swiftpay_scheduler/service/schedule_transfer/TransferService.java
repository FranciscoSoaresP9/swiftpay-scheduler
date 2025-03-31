package com.swiftpay.swiftpay_scheduler.service.schedule_transfer;


import com.swiftpay.swiftpay_scheduler.dto.transfer.TransferDTO;
import com.swiftpay.swiftpay_scheduler.dto.transfer.TransferSmallDTO;
import com.swiftpay.swiftpay_scheduler.dto.transfer.UpdateTransferDTO;
import com.swiftpay.swiftpay_scheduler.dto.transfer.WriteTransferDTO;
import com.swiftpay.swiftpay_scheduler.entity.transfer.Transfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransferService {

    Page<TransferSmallDTO> getAll(Pageable pageable);

    TransferDTO getDtoById(Long id);

    Transfer getById(Long id);

    TransferDTO update(Long id, UpdateTransferDTO write);

    void cancelTransfer(Long id);

    void delete(Long id);

    TransferDTO create(WriteTransferDTO write);

}
