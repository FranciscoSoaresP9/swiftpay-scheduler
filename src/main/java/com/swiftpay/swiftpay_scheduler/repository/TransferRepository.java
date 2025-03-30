package com.swiftpay.swiftpay_scheduler.repository;

import com.swiftpay.swiftpay_scheduler.entity.transfer.Transfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    Page<Transfer> findBySenderAccountId(Long accountId, Pageable pageable);

    Optional<Transfer> findByIdAndSenderAccountId(Long id, Long accountId);
}
