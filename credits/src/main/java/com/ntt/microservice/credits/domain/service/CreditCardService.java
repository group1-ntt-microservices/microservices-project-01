package com.ntt.microservice.credits.domain.service;

import com.ntt.microservice.credits.domain.model.CreditCard;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing CreditCard entities.
 */
public interface CreditCardService {
  List<CreditCard> findAll();

  Optional<CreditCard> findById(String id);

  Optional<CreditCard> findByCustomerId(String customerId);

  CreditCard save(CreditCard creditCard);

  void deleteById(String id);

  CreditCard update(String id, CreditCard creditCard);
}
