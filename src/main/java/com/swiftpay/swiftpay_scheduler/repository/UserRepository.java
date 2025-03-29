package com.swiftpay.swiftpay_scheduler.repository;

import com.swiftpay.swiftpay_scheduler.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
