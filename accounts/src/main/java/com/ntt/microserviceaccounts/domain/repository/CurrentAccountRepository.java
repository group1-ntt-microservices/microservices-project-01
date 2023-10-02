package com.ntt.microserviceaccounts.domain.repository;

import com.ntt.microserviceaccounts.domain.model.enity.CurrentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CurrentAccountRepository extends JpaRepository<CurrentAccount, Long> {
    CurrentAccount findByAccountNumber(String accountNumber);
}
