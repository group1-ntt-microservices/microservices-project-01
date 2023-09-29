package com.ntt.microserviceaccounts.domain.repository;

import com.ntt.microserviceaccounts.domain.model.enity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    List<BankAccount> findByDocumentNumber(String documentNumber);
    BankAccount findByAccountNumber(String accountNumber);

    boolean existsByAccountNumber(String accountNumber);
}
