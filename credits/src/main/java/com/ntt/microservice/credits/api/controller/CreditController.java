package com.ntt.microservice.credits.api.controller;

import com.ntt.microservice.credits.api.dto.request.CreditRequestDto;
import com.ntt.microservice.credits.domain.model.Credit;
import com.ntt.microservice.credits.domain.service.CreditService;
import com.ntt.microservice.credits.feign.dto.CustomerDto;
import com.ntt.microservice.credits.feign.service.CustomerFeignService;
import com.ntt.microservice.credits.service.CreditMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.ntt.microservice.credits.configuration.Constants.*;

@AllArgsConstructor
@RestController
@RequestMapping("/credit")
public class CreditController {

  private CreditService creditService;
  private CustomerFeignService customerFeignService;
  private CreditMapper creditMapper;

  @GetMapping("/")
  public ResponseEntity<List<Credit>> findAll() {
    return ResponseEntity.ok(creditService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Credit> findById(@PathVariable String id) {
   Optional<Credit> creditOptional = creditService.findById(id);
    return creditOptional.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/")
  public ResponseEntity<Object> save(@RequestBody CreditRequestDto creditRequestDto) {
    try {
      CustomerDto customer = customerFeignService.findCustomerById(creditRequestDto.getCustomerId());
      if (customer == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found: NULL");
      }
      if (customer.getCustomerType().equals(TYPE_PERSONAL_CUSTOMER)
          && creditService.existsCustomerId(creditRequestDto.getCustomerId())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Credit already exists for the same personal customer");
      }
      Credit credit = creditMapper.creditRequestDtoToCredit(creditRequestDto);
      credit.setId(UUID.randomUUID().toString());
      credit.setCustomerDocumentNumber(customer.getDocumentNumber());
      credit.setCustomerType(customer.getCustomerType());
      credit.setActive(true);
      credit.setCreatedAt(new Date());
      credit.setStatus(STATUS_CREDIT_PENDING);
      return ResponseEntity.ok(creditService.save(credit));
    } catch (feign.FeignException.NotFound ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found: ERROR");
    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Credit Error");
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteById(@PathVariable String id) {
    Optional<Credit> credit = creditService.findById(id);
    if (!credit.isPresent()){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Credit not found");
    }
    creditService.deleteById(id);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> update(
      @PathVariable String id,
      @RequestBody CreditRequestDto creditRequestDto
  ) {
    Optional<Credit> optionalCredit = creditService.findById(id);
    if (!optionalCredit.isPresent()){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Credit not found");
    }
    if (creditRequestDto.getAmountInterest() < creditRequestDto.getAmountPaid()){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Monto con Interes menor al Pagado");
    }
    if (creditRequestDto.getAmountGranted() != optionalCredit.get().getAmountGranted()
    || creditRequestDto.getAmountInterest() != optionalCredit.get().getAmountInterest()){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Montos No Coinciden");
    }
    Credit credit = optionalCredit.get();
    if (credit.getStatus() == STATUS_CREDIT_PAID){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Credit already paid");
    }
    credit.setAmountPaid(creditRequestDto.getAmountPaid());
    credit.setId(id);
    if (credit.getAmountPaid() == credit.getAmountInterest()){
      credit.setStatus(STATUS_CREDIT_PAID);
    }
    return ResponseEntity.ok(creditService.save(credit));
  }

  @PatchMapping("/pay/{id}")
  public ResponseEntity<Credit> pay(@PathVariable String id, @RequestBody float amountPaid) {
    Optional<Credit> optionalCredit = creditService.findById(id);
    if (!optionalCredit.isPresent()){
      return ResponseEntity.badRequest().build();
    }
    Credit credit = optionalCredit.get();
    if (credit.getStatus() == STATUS_CREDIT_PAID){
      return ResponseEntity.badRequest().build();
    }
    float montoRestante = credit.getAmountInterest() - credit.getAmountPaid();
    if (amountPaid > montoRestante){
      return ResponseEntity.badRequest().build();
    }
    credit.setAmountPaid(credit.getAmountPaid() + amountPaid);
    if (credit.getAmountPaid() == credit.getAmountInterest()){
      credit.setStatus(STATUS_CREDIT_PAID);
    }
    return ResponseEntity.ok(creditService.save(credit));
  }

}
