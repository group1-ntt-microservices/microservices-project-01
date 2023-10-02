package com.ntt.microserviceaccounts.domain.repository;

import com.ntt.microserviceaccounts.domain.model.enity.SavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.rmi.server.UID;
import java.util.UUID;

/**
 * Repository interface for accessing SavingAccount entities in the database.
 */
@Repository
public interface SavingAccountRepository extends JpaRepository<SavingAccount, String> {
}
