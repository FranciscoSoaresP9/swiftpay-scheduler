package com.swiftpay.swiftpay_scheduler.controller;

import com.swiftpay.swiftpay_scheduler.dto.transfer.TransferDTO;
import com.swiftpay.swiftpay_scheduler.dto.transfer.WriteTransferDTO;
import com.swiftpay.swiftpay_scheduler.service.schedule_transfer.ScheduleTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.swiftpay.swiftpay_scheduler.constants.ApiPaths.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = API_PATH + TRANSFER)
public class TransferController {

    private final ScheduleTransferService service;

    @PostMapping(SCHEDULE_TRANSFER)
    public TransferDTO register(@RequestBody WriteTransferDTO write) {
        return service.schedule(write);
    }

}
