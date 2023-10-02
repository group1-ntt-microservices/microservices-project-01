package com.ntt.microserviceaccounts.domain.repository;

import com.ntt.microserviceaccounts.domain.model.entity.SavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing SavingAccount entities in the database.
 */
@Repository
public interface SavingAccountRepository extends JpaRepository<SavingAccount, String> {
}
