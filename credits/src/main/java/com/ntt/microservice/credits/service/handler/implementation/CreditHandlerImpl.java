package com.ntt.microservice.credits.service.handler.implementation;

import static com.ntt.microservice.credits.configuration.Constants.STATUS_CREDIT_PAID;
import static com.ntt.microservice.credits.configuration.Constants.STATUS_CREDIT_PENDING;
import static com.ntt.microservice.credits.configuration.Constants.TYPE_PERSONAL_CUSTOMER;

import com.ntt.microservice.credits.api.dto.request.CreditRequestDto;
import com.ntt.microservice.credits.api.dto.response.CreditResponseDto;
import com.ntt.microservice.credits.domain.model.Credit;
import com.ntt.microservice.credits.domain.service.CreditService;
import com.ntt.microservice.credits.feign.dto.CustomerDto;
import com.ntt.microservice.credits.feign.service.CustomerFeignService;
import com.ntt.microservice.credits.service.exception.CreditAlreadyPaidException;
import com.ntt.microservice.credits.service.exception.CreditNotFoundException;
import com.ntt.microservice.credits.service.exception.CustomerAlreadyAssignedException;
import com.ntt.microservice.credits.service.exception.CustomerFoundIsNullException;
import com.ntt.microservice.credits.service.exception.CustomerNotFoundException;
import com.ntt.microservice.credits.service.exception.SomeAmountIsIncorrectException;
import com.ntt.microservice.credits.service.handler.CreditHandler;
import com.ntt.microservice.credits.service.mapper.CreditMapper;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the CreditHandler interface.
 */
@AllArgsConstructor
@Service
public class CreditHandlerImpl implements CreditHandler {

  private CreditService creditService;
  private CreditMapper creditMapper;
  private CustomerFeignService customerFeignService;

  /**
   * Retrieves a list of all credits.
   *
   * @return A list of CreditResponseDto objects.
   */
  @Override
  public List<CreditResponseDto> findAll() {
    return creditService.findAll()
        .stream()
        .map(creditMapper::creditToCreditResponseDto)
        .collect(Collectors.toList());
  }

  /**
   * Retrieves a credit by its ID.
   *
   * @param id The unique identifier of the credit.
   * @return The CreditResponseDto object if found, or null otherwise.
   */
  @Override
  public CreditResponseDto findById(String id) {
    return creditService.findById(id)
        .map(creditMapper::creditToCreditResponseDto)
        .orElseThrow(CreditNotFoundException::new);
  }

  /**
   * Saves a new credit based on the provided CreditRequestDto.
   *
   * @param creditRequestDto The CreditRequestDto containing credit details.
   * @return The created CreditResponseDto.
   */
  @Override
  public CreditResponseDto save(CreditRequestDto creditRequestDto) {
    try {
      CustomerDto customer = customerFeignService
          .findCustomerById(creditRequestDto.getCustomerId());
      if (customer == null) {
        throw new CustomerFoundIsNullException();
      }
      if (customer.getCustomerType().equals(TYPE_PERSONAL_CUSTOMER)
          && creditService.existsCustomerId(creditRequestDto.getCustomerId())) {
        throw new CustomerAlreadyAssignedException();
      }
      Credit credit = creditMapper.creditRequestDtoToCredit(creditRequestDto);
      credit.setId(UUID.randomUUID().toString());
      credit.setCustomerDocumentNumber(customer.getDocumentNumber());
      credit.setCustomerType(customer.getCustomerType());
      credit.setActive(true);
      credit.setCreatedAt(new Date());
      credit.setStatus(STATUS_CREDIT_PENDING);
      return creditMapper.creditToCreditResponseDto(creditService.save(credit));
    } catch (feign.FeignException.NotFound ex) {
      throw new CustomerNotFoundException();
    }
  }

  /**
   * Delete a credit by its ID.
   *
   * @param id The unique identifier of the credit to delete.
   */
  @Override
  public void deleteById(String id) {
    creditService.findById(id).ifPresentOrElse(credit -> creditService.deleteById(id),
        () -> {
          throw new CreditNotFoundException();
        });
  }

  /**
   * Updates an existing credit with the provided CreditRequestDto.
   *
   * @param id               The unique identifier of the credit to update.
   * @param creditRequestDto The CreditRequestDto containing updated credit details.
   * @return The updated CreditResponseDto.
   */
  @Override
  public CreditResponseDto update(String id, CreditRequestDto creditRequestDto) {
    if (creditRequestDto.getAmountInterest() < creditRequestDto.getAmountPaid()) {
      throw new SomeAmountIsIncorrectException();
    }

    Credit credit = creditService.findById(id).orElseThrow(CreditNotFoundException::new);

    if (creditRequestDto.getAmountGranted() != credit.getAmountGranted()
        || creditRequestDto.getAmountInterest() != credit.getAmountInterest()) {
      throw new SomeAmountIsIncorrectException();
    }

    if (credit.getStatus() == STATUS_CREDIT_PAID) {
      throw new CreditAlreadyPaidException();
    }

    credit.setAmountPaid(creditRequestDto.getAmountPaid());
    credit.setId(id);

    if (credit.getAmountPaid() == credit.getAmountInterest()) {
      credit.setStatus(STATUS_CREDIT_PAID);
    }

    return creditMapper.creditToCreditResponseDto(creditService.save(credit));
  }
}
