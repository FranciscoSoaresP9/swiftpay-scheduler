package com.swiftpay.swiftpay_scheduler.repository;

import com.swiftpay.swiftpay_scheduler.entity.transfer.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
