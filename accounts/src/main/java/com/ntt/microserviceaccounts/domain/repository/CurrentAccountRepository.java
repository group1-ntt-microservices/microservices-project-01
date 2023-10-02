package com.ntt.microserviceaccounts.domain.repository;

import com.ntt.microserviceaccounts.domain.model.entity.CurrentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing CurrentAccount entities in the database.
 */
@Repository
public interface CurrentAccountRepository extends JpaRepository<CurrentAccount, String> {
    /**
     * Retrieves a current account by its unique account number.
     *
     * @param accountNumber The account number of the current account.
     * @return The current account with the given account number, if it exists.
     */
    CurrentAccount findByAccountNumber(String accountNumber);
}
