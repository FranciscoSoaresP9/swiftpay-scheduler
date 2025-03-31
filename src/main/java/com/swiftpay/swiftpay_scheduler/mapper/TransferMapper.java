package com.swiftpay.swiftpay_scheduler.mapper;

import com.swiftpay.swiftpay_scheduler.dto.transfer.TransferDTO;
import com.swiftpay.swiftpay_scheduler.dto.transfer.TransferSmallDTO;
import com.swiftpay.swiftpay_scheduler.entity.transfer.Transfer;
import org.springframework.stereotype.Component;

@Component
public class TransferMapper {

    public TransferSmallDTO toSmallDTO(Transfer transfer) {
        return new TransferSmallDTO(
                transfer.getId(),
                transfer.getReceiverAccount().getUser().getName(),
                transfer.getAmount(),
                transfer.getScheduleDate(),
                transfer.getAmountIncludingFees(),
                transfer.getStatus()
        );
    }

    public TransferDTO toDTO(Transfer transfer) {
        return new TransferDTO(
                transfer.getId(),
                transfer.getReceiverAccount().getUser().getName(),
                transfer.getReceiverAccount().getIban(),
                transfer.getAmount(),
                transfer.getScheduleDate(),
                transfer.getAppliedFee().getTaxPercentage(),
                transfer.getAppliedFee().getFixedFee(),
                transfer.getAmountIncludingFees(),
                transfer.getStatus()
        );
    }

}
