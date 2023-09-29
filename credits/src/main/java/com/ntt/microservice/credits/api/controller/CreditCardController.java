package com.ntt.microservice.credits.api.controller;

import com.ntt.microservice.credits.api.dto.request.CreditCardRequestDto;
import com.ntt.microservice.credits.domain.model.CreditCard;
import com.ntt.microservice.credits.domain.service.CreditCardService;
import com.ntt.microservice.credits.feign.dto.CustomerDto;
import com.ntt.microservice.credits.feign.service.CustomerFeignService;
import com.ntt.microservice.credits.service.CreditCardMapper;
import java.util.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/creditCard")
public class CreditCardController {
  private CreditCardService creditCardService;
  private CreditCardMapper creditCardMapper;
  private CustomerFeignService customerFeignService;

  @GetMapping("/")
  public ResponseEntity<List<CreditCard>> findAll() {
    return ResponseEntity.ok(creditCardService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<CreditCard> findById(@PathVariable String id) {
    return creditCardService.findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/customerId/{customerId}")
  public ResponseEntity<CreditCard> findByCustomerId(@PathVariable String customerId) {
    return creditCardService.findByCustomerId(customerId)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/")
  public ResponseEntity<Object> save(@RequestBody CreditCardRequestDto creditCardRequestDto) {
    try {
      CustomerDto customer = customerFeignService.findCustomerById(creditCardRequestDto.getCustomerId());
      if (customer == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found: NULL");
      }
      if (creditCardService.existsByCustomerId(creditCardRequestDto.getCustomerId())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Credit card already exists for the same customer");
      }
      if (creditCardService.existsByCardNumber(creditCardRequestDto.getCardNumber())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Card number already exists");
      }
      if (creditCardRequestDto.getBalanceAvailable()
          + creditCardRequestDto.getBalanceDue() > creditCardRequestDto.getLimitAmount()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Credit card limit exceeded");
      }
      CreditCard creditCard = creditCardMapper.creditCardRequestDtoToCreditCard(creditCardRequestDto);
      creditCard.setId(UUID.randomUUID().toString());
      creditCard.setCustomerDocumentNumber(customer.getDocumentNumber());
      creditCard.setCustomerType(customer.getCustomerType());
      creditCard.setActive(true);
      creditCard.setCreatedAt(new Date());
      return ResponseEntity.ok(creditCardService.save(creditCard));
    } catch (feign.FeignException.NotFound ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found: ERROR");
    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Credit Error");
    }
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable String id) {
    creditCardService.deleteById(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> update(
      @PathVariable String id,
      @RequestBody CreditCardRequestDto creditCardRequestDto
  ) {
    Optional<CreditCard> optionalCreditCard = creditCardService.findById(id);
    if (!optionalCreditCard.isPresent()){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Credit card not found");
    }
    CreditCard creditCard = optionalCreditCard.get();
    if (!Objects.equals(creditCard.getCustomerId(), creditCardRequestDto.getCustomerId())){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Se cambió el customer");
    }
    if (!Objects.equals(creditCard.getCardNumber(), creditCardRequestDto.getCardNumber())){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("se cambió el card number");
    }
    if(creditCardRequestDto.getBalanceAvailable() + creditCardRequestDto.getBalanceDue()
        > creditCardRequestDto.getLimitAmount()){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Exceso Limite");
    }
    creditCard.setBalanceDue(creditCardRequestDto.getBalanceDue());
    creditCard.setBalanceAvailable(creditCardRequestDto.getBalanceAvailable());
    creditCard.setLimitAmount(creditCardRequestDto.getLimitAmount());
    return ResponseEntity.ok(creditCardService.save(creditCard));
  }
}