package com.ntt.microserviceaccounts.domain.repository;

import com.ntt.microserviceaccounts.domain.model.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Repository interface for accessing BankAccount entities in the database.
 */
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

    /**
     * Retrieves a list of bank accounts associated with a specific document number.
     *
     * @param documentNumber The document number of the account holder.
     * @return A list of bank accounts associated with the given document number.
     */
    List<BankAccount> findByDocumentNumber(String documentNumber);

    /**
     * Retrieves a bank account by its unique account number.
     *
     * @param accountNumber The account number of the bank account.
     * @return An Optional containing the bank account with the given account number, if it exists.
     */
    Optional<BankAccount> findByAccountNumber(String accountNumber);

    /**
     * Checks if a bank account with the given account number exists in the database.
     *
     * @param accountNumber The account number to check.
     * @return true if a bank account with the given account number exists; false otherwise.
     */
    boolean existsByAccountNumber(String accountNumber);
}