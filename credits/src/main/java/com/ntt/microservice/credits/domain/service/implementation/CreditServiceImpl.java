package com.ntt.microservice.credits.domain.service.implementation;

import com.ntt.microservice.credits.domain.model.Credit;
import com.ntt.microservice.credits.domain.repository.CreditRepository;
import com.ntt.microservice.credits.domain.service.CreditService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the CreditService interface.
 */
@AllArgsConstructor
@Service
public class CreditServiceImpl implements CreditService {

  private CreditRepository creditRepository;

  /**
   * Retrieve a list of all credits.
   *
   * @return A list of Credit objects.
   */
  @Override
  public List<Credit> findAll() {
    return creditRepository.findAll();
  }

  /**
   * Find a credit by ID.
   *
   * @param id The ID of the credit to find.
   * @return An optional Credit object if found, empty otherwise.
   */
  @Override
  public Optional<Credit> findById(String id) {
    return creditRepository.findById(id);
  }

  /**
   * Check if a credit exists for a given customer ID.
   *
   * @param id The customer ID to check.
   * @return True if a credit exists, false otherwise.
   */
  @Override
  public boolean existsCustomerId(String id) {
    return creditRepository.existsByCustomerId(id);
  }

  /**
   * Save a new credit.
   *
   * @param credit The Credit object to save or update.
   * @return The saved Credit object.
   */
  @Override
  public Credit save(Credit credit) {
    return creditRepository.save(credit);
  }

  /**
   * Delete a credit by its ID.
   *
   * @param id The ID of the credit to delete.
   */
  @Override
  public void deleteById(String id) {
    creditRepository.deleteById(id);
  }

}
