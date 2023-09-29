package com.ntt.microservice.credits.api.controller;

import com.ntt.microservice.credits.domain.model.CreditCard;
import com.ntt.microservice.credits.domain.service.CreditCardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/creditCard")
public class CreditCardController {
  private CreditCardService creditCardService;

  @GetMapping("/")
  public ResponseEntity<List<CreditCard>> findAll(){
    return ResponseEntity.ok(creditCardService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<CreditCard> findById(@PathVariable String id){
    return creditCardService.findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/customerId/{customerId}")
  public ResponseEntity<CreditCard> findByCustomerId(@PathVariable String customerId){
    return creditCardService.findByCustomerId(customerId)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/")
  public ResponseEntity<CreditCard> save(@RequestBody CreditCard credit){
    return ResponseEntity.ok(creditCardService.save(credit));
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable String id){
    creditCardService.deleteById(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CreditCard> update(
      @PathVariable String id,
      @RequestBody CreditCard credit
  ) {
    return ResponseEntity.ok(creditCardService.update(id, credit));
  }

}
