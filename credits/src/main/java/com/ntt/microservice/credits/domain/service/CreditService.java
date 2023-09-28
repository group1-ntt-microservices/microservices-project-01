package com.ntt.microservice.credits.domain.service;

import com.ntt.microservice.credits.domain.model.Credit;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing Credit entities.
 */
public interface CreditService {
  List<Credit> findAll();

  Optional<Credit> findById(String id);

  boolean existsCustomerId(String id);

  Credit save(Credit credit);

  void deleteById(String id);

  Credit update(String id, Credit credit);
}
