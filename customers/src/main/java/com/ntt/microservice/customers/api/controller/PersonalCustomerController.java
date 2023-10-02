package com.ntt.microservice.customers.api.controller;

import com.ntt.microservice.customers.domain.model.BusinessCustomer;
import com.ntt.microservice.customers.domain.model.PersonalCustomer;
import com.ntt.microservice.customers.domain.service.PersonalCustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/personal")
public class PersonalCustomerController {

    private PersonalCustomerService personalCustomerService;

    @GetMapping("/")
    public ResponseEntity<List<PersonalCustomer>> findAll(){
        return ResponseEntity.ok(personalCustomerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonalCustomer> findById(@PathVariable String id){
        Optional<PersonalCustomer> optional = personalCustomerService.findById(id);
        return optional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/documentNumber/{documentNumber}")
    public ResponseEntity<PersonalCustomer> findByDocumentNumber(@PathVariable String documentNumber){
        Optional<PersonalCustomer> optional = personalCustomerService.findByDocumentNumber(documentNumber);
        return optional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<PersonalCustomer> save(@RequestBody PersonalCustomer personalCustomer){
        return ResponseEntity.ok(personalCustomerService.save(personalCustomer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonalCustomer> update(@RequestBody BusinessCustomer personalCustomer){
        Optional<PersonalCustomer> optional = personalCustomerService.findById(personalCustomer.getId());
        return optional.map(
                personal -> {
                    PersonalCustomer customer = new PersonalCustomer();
                    customer.setId(personal.getId());
                    return ResponseEntity.ok(personalCustomerService.save(customer));
                }
        ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
