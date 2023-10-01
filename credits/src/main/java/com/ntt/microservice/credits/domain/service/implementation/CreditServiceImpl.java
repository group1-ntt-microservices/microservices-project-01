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

  @Override
  public List<Credit> findAll() {
    return creditRepository.findAll();
  }

  @Override
  public Optional<Credit> findById(String id) {
    return creditRepository.findById(id);
  }

  @Override
  public boolean existsCustomerId(String customerId) {
    return creditRepository.existsByCustomerId(customerId);
  }

  @Override
  public Credit save(Credit credit) {
    return creditRepository.save(credit);
  }

  @Override
  public void deleteById(String id) {
    creditRepository.deleteById(id);
  }

}
