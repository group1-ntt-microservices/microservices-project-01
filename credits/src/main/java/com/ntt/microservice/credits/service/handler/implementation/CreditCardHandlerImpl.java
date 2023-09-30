package com.ntt.microservice.credits.service.handler.implementation;

import com.ntt.microservice.credits.api.dto.request.CreditCardRequestDto;
import com.ntt.microservice.credits.api.dto.response.CreditCardResponseDto;
import com.ntt.microservice.credits.domain.model.CreditCard;
import com.ntt.microservice.credits.domain.service.CreditCardService;
import com.ntt.microservice.credits.feign.dto.CustomerDto;
import com.ntt.microservice.credits.feign.service.CustomerFeignService;
import com.ntt.microservice.credits.service.exception.CardNumberAlreadyExistsException;
import com.ntt.microservice.credits.service.exception.CreditNotFoundException;
import com.ntt.microservice.credits.service.exception.CreditServiceInternalErrorException;
import com.ntt.microservice.credits.service.exception.CustomerAlreadyAssignedException;
import com.ntt.microservice.credits.service.exception.CustomerFoundIsNullException;
import com.ntt.microservice.credits.service.exception.CustomerNotFoundException;
import com.ntt.microservice.credits.service.exception.ImpossibleChangeValueException;
import com.ntt.microservice.credits.service.exception.SomeAmountIsIncorrectException;
import com.ntt.microservice.credits.service.handler.CreditCardHandler;
import com.ntt.microservice.credits.service.mapper.CreditCardMapper;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the CreditCardHandler interface.
 */
@AllArgsConstructor
@Service
public class CreditCardHandlerImpl implements CreditCardHandler {

  private CreditCardService creditCardService;
  private CreditCardMapper creditCardMapper;
  private CustomerFeignService customerFeignService;

  @Override
  public List<CreditCardResponseDto> findAll() {
    return creditCardService.findAll()
        .stream()
        .map(creditCardMapper::creditCardToCreditCardResponseDto)
        .collect(Collectors.toList());
  }

  @Override
  public CreditCardResponseDto findById(String id) {
    return creditCardService.findById(id)
        .map(creditCardMapper::creditCardToCreditCardResponseDto)
        .orElseThrow(CreditNotFoundException::new);
  }

  @Override
  public CreditCardResponseDto findByCustomerId(String customerId) {
    try {
      CustomerDto customer = customerFeignService.findCustomerById(customerId);
      if (customer == null) {
        throw new CustomerFoundIsNullException();
      }
      return creditCardService.findByCustomerId(customerId)
          .map(creditCardMapper::creditCardToCreditCardResponseDto)
          .orElseThrow(CreditNotFoundException::new);

    } catch (feign.FeignException.NotFound ex) {
      throw new CustomerNotFoundException();

    } catch (Exception ex) {
      throw new CreditServiceInternalErrorException();

    }
  }

  @Override
  public CreditCardResponseDto save(CreditCardRequestDto creditCardRequestDto) {
    if (creditCardService.existsByCardNumber(creditCardRequestDto.getCardNumber())) {
      throw new CardNumberAlreadyExistsException();
    }
    if (creditCardService.existsByCustomerId(creditCardRequestDto.getCustomerId())) {
      throw new CustomerAlreadyAssignedException();
    }
    if (creditCardRequestDto.getBalanceAvailable()
        + creditCardRequestDto.getBalanceDue() > creditCardRequestDto.getLimitAmount()
        || creditCardRequestDto.getBalanceDue() > creditCardRequestDto.getLimitAmount()
        || creditCardRequestDto.getBalanceAvailable() > creditCardRequestDto.getLimitAmount()) {
      throw new SomeAmountIsIncorrectException();
    }
    try {
      CustomerDto customer = customerFeignService
          .findCustomerById(creditCardRequestDto.getCustomerId());
      if (customer == null) {
        throw new CustomerFoundIsNullException();
      }

      CreditCard creditCard = creditCardMapper
          .creditCardRequestDtoToCreditCard(creditCardRequestDto);

      creditCard.setId(UUID.randomUUID().toString());
      creditCard.setCustomerDocumentNumber(customer.getDocumentNumber());
      creditCard.setCustomerType(customer.getCustomerType());
      creditCard.setActive(true);
      creditCard.setCreatedAt(new Date());

      return creditCardMapper.creditCardToCreditCardResponseDto(creditCardService.save(creditCard));

    } catch (feign.FeignException.NotFound ex) {
      throw new CustomerNotFoundException();

    } catch (Exception ex) {
      throw new CreditServiceInternalErrorException();

    }
  }

  @Override
  public void deleteById(String id) {
    creditCardService.findById(id).ifPresentOrElse(creditCard -> creditCardService.deleteById(id),
        () -> {
          throw new CreditNotFoundException();
        });
  }

  @Override
  public CreditCardResponseDto update(String id, CreditCardRequestDto creditCardRequestDto) {
    Optional<CreditCard> optionalCreditCard = creditCardService.findById(id);

    if (!optionalCreditCard.isPresent()) {
      throw new CreditNotFoundException();
    }

    CreditCard creditCard = optionalCreditCard.get();
    if (!Objects.equals(creditCard.getCustomerId(), creditCardRequestDto.getCustomerId())
        || !Objects.equals(creditCard.getCardNumber(), creditCardRequestDto.getCardNumber())
        || !Objects.equals(creditCard.getLimitAmount(), creditCardRequestDto.getLimitAmount())) {
      throw new ImpossibleChangeValueException();
    }

    if (creditCardRequestDto.getBalanceAvailable()
        + creditCardRequestDto.getBalanceDue() > creditCardRequestDto.getLimitAmount()
        || creditCardRequestDto.getBalanceDue() > creditCardRequestDto.getLimitAmount()
        || creditCardRequestDto.getBalanceAvailable() > creditCardRequestDto.getLimitAmount()) {
      throw new SomeAmountIsIncorrectException();
    }

    creditCard.setBalanceDue(creditCardRequestDto.getBalanceDue());
    creditCard.setBalanceAvailable(creditCardRequestDto.getBalanceAvailable());
    creditCard.setLimitAmount(creditCardRequestDto.getLimitAmount());

    return creditCardMapper.creditCardToCreditCardResponseDto(creditCardService.save(creditCard));
  }
}
