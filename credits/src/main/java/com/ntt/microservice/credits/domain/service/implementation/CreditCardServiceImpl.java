package com.ntt.microservice.credits.domain.service.implementation;

import com.ntt.microservice.credits.domain.model.CreditCard;
import com.ntt.microservice.credits.domain.repository.CreditCardRepository;
import com.ntt.microservice.credits.domain.service.CreditCardService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the CreditCardService interface.
 */
@AllArgsConstructor
@Service
public class CreditCardServiceImpl implements CreditCardService {

  private CreditCardRepository creditCardRepository;

  @Override
  public List<CreditCard> findAll() {
    return creditCardRepository.findAll();
  }

  @Override
  public Optional<CreditCard> findById(String id) {
    return creditCardRepository.findById(id);
  }

  @Override
  public Optional<CreditCard> findByCustomerId(String customerId) {
    return creditCardRepository.findByCustomerId(customerId);
  }

  @Override
  public CreditCard save(CreditCard creditCard) {
    return creditCardRepository.save(creditCard);
  }

  @Override
  public void deleteById(String id) {
    creditCardRepository.deleteById(id);
  }

  @Override
  public boolean existsByCustomerId(String customerId) {
    return creditCardRepository.existsByCustomerId(customerId);
  }

  @Override
  public boolean existsByCardNumber(String cardNumber) {
    return creditCardRepository.existsByCardNumber(cardNumber);
  }
}
