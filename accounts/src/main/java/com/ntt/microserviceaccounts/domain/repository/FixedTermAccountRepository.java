package com.ntt.microserviceaccounts.domain.repository;


import com.ntt.microserviceaccounts.domain.model.enity.FixedTermAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.rmi.server.UID;
import java.util.UUID;

@Repository
public interface FixedTermAccountRepository extends JpaRepository<FixedTermAccount, Long> {

}
