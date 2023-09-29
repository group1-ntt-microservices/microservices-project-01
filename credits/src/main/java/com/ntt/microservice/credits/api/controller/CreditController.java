package com.ntt.microservice.credits.api.controller;

import com.ntt.microservice.credits.api.dto.request.CreditRequestDto;
import com.ntt.microservice.credits.domain.model.Credit;
import com.ntt.microservice.credits.domain.service.CreditService;
import com.ntt.microservice.credits.feign.dto.CustomerDto;
import com.ntt.microservice.credits.feign.service.CustomerFeignService;
import com.ntt.microservice.credits.service.CreditMapper;
import lombok.AllArgsConstructor;
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
  public ResponseEntity<Credit> findById(String id) {
    return creditService.findById(id).map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/")
  public ResponseEntity<Credit> save(@RequestBody CreditRequestDto creditRequestDto) {
    CustomerDto customer = customerFeignService.findCustomerById(creditRequestDto.getCustomerId());
    // ERROR SI NO ENCUENTRA CLIENTE CON ESE ID
    if (customer == null) {
      return ResponseEntity.badRequest().build();
    }
    // IMPOSIBLE CREAR MÁS DE UN CRÉDITO PARA CLIENTE PERSONAL, POR ESO
    // VERIFICA SI EL CLIENTE ES PERSONAL Y SI EXISTEN CRÉDITOS CON ESE ID
    if (customer.getCustomerType().equals(TYPE_PERSONAL_CUSTOMER)
        && creditService.existsCustomerId(creditRequestDto.getCustomerId())) {
      return ResponseEntity.badRequest().build();
    }
    Credit credit = creditMapper.creditRequestDtoToCredit(creditRequestDto);
    credit.setId(UUID.randomUUID().toString());
    credit.setCustomerDocumentNumber(customer.getDocumentNumber());
    credit.setCustomerType(customer.getCustomerType());
    credit.setActive(true);
    credit.setCreatedAt(new Date());
    credit.setStatus(STATUS_CREDIT_PENDING);
    return ResponseEntity.ok(creditService.save(credit));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteById(@PathVariable String id) {
    // ERROR SI EL CREDITO NO EXISTE
    Optional<Credit> credit = creditService.findById(id);
    if (!credit.isPresent()){
      return ResponseEntity.badRequest().build();
    }
    creditService.deleteById(id);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Credit> update(
      @PathVariable String id,
      @RequestBody CreditRequestDto creditRequestDto
  ) {
    Optional<Credit> optionalCredit = creditService.findById(id);
    // ERROR SI EL CREDITO NO EXISTE
    if (!optionalCredit.isPresent()){
      return ResponseEntity.badRequest().build();
    }
    // ERROR SI MONTO INTERES ES IGUAL AL PAGADO
    if (creditRequestDto.getAmountInterest() == creditRequestDto.getAmountPaid()){
      return ResponseEntity.badRequest().build();
    }
    // ERROR SI LOS MONTOS DEL CREDITO NO COINCIDEN
    if (creditRequestDto.getAmountGranted() != optionalCredit.get().getAmountGranted()
    || creditRequestDto.getAmountInterest() != optionalCredit.get().getAmountInterest()){
      return ResponseEntity.badRequest().build();
    }
    Credit credit = optionalCredit.get();
    credit.setAmountPaid(creditRequestDto.getAmountPaid());
    credit.setId(id);
    return ResponseEntity.ok(creditService.update(id, credit));
  }

  @PatchMapping("/pay/{id}")
  public ResponseEntity<Credit> pay(@PathVariable String id, @RequestBody float amountPaid) {
    Optional<Credit> optionalCredit = creditService.findById(id);
    // ERROR SI EL CREDITO NO EXISTE
    if (!optionalCredit.isPresent()){
      return ResponseEntity.badRequest().build();
    }
    Credit credit = optionalCredit.get();
    // ERROR SI YA ESTÁ PAGADO COMPLETAMENTE
    if (credit.getStatus() == STATUS_CREDIT_PAID){
      return ResponseEntity.badRequest().build();
    }
    float montoRestante = credit.getAmountInterest() - credit.getAmountPaid();
    // ERROR SI EL MONTO A PAGAR ES MAYOR AL MONTO RESTANTE
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
