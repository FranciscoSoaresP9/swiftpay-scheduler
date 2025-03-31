package com.swiftpay.swiftpay_scheduler.controller;

import com.swiftpay.swiftpay_scheduler.dto.transfer.TransferDTO;
import com.swiftpay.swiftpay_scheduler.dto.transfer.TransferSmallDTO;
import com.swiftpay.swiftpay_scheduler.dto.transfer.UpdateTransferDTO;
import com.swiftpay.swiftpay_scheduler.dto.transfer.WriteTransferDTO;
import com.swiftpay.swiftpay_scheduler.service.transfer_service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static com.swiftpay.swiftpay_scheduler.constants.ApiPaths.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = API_PATH + TRANSFER)
public class TransferController {

    private final TransferService transferService;

    @GetMapping()
    public Page<TransferSmallDTO> getAll(Pageable pageable) {
        return transferService.getAll(pageable);

    }

    @GetMapping(ID)
    public TransferDTO getById(@PathVariable Long id) {
        return transferService.getDtoById(id);
    }

    @PostMapping()
    public TransferDTO create(@RequestBody WriteTransferDTO write) {
        return transferService.create(write);
    }

    @PutMapping(ID)
    public TransferDTO update(@PathVariable Long id,
                              @RequestBody UpdateTransferDTO write) {
        return transferService.update(id, write);
    }

    @DeleteMapping(ID)
    public void delete(@PathVariable Long id) {
        transferService.delete(id);
    }

    @PatchMapping(ID + CANCEL)
    public void cancel(@PathVariable Long id) {
        transferService.cancelTransfer(id);
    }

}
