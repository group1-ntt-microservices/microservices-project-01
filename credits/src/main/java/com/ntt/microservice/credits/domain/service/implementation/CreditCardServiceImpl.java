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

  /**
   * Retrieve a list of all credit cards.
   *
   * @return A list of CreditCard objects.
   */
  @Override
  public List<CreditCard> findAll() {
    return creditCardRepository.findAll();
  }

  /**
   * Find a credit card by its ID.
   *
   * @param id The ID of the credit card to find.
   * @return An optional CreditCard object if found, empty otherwise.
   */
  @Override
  public Optional<CreditCard> findById(String id) {
    return creditCardRepository.findById(id);
  }

  /**
   * Find a credit card by its customer ID.
   *
   * @param customerId The customer ID to search for.
   * @return An optional CreditCard object if found, empty otherwise.
   */
  @Override
  public Optional<CreditCard> findByCustomerId(String customerId) {
    return creditCardRepository.findByCustomerId(customerId);
  }

  /**
   * Save a new credit card.
   *
   * @param creditCard The CreditCard object to save or update.
   * @return The saved CreditCard object.
   */
  @Override
  public CreditCard save(CreditCard creditCard) {
    return creditCardRepository.save(creditCard);
  }

  /**
   * Delete a credit card by its ID.
   *
   * @param id The ID of the credit card to delete.
   */
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
