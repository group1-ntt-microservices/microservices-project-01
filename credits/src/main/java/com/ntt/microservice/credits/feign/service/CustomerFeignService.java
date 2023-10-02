package com.ntt.microservice.credits.feign.service;

import com.ntt.microservice.credits.feign.dto.CustomerDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * This interface defines the Feign client for interacting with the Customer Service.
 */
@FeignClient(name = "customers-service",
    url = "http://localhost:8085/customerService/api/customers")
public interface CustomerFeignService {

  /**
   * Retrieve a list of all customers.
   *
   * @return A list of CustomerDto objects representing the customers.
   */
  @GetMapping("/")
  List<CustomerDto> findAllCustomers();

  /**
   * Retrieve a customer by id.
   *
   * @param id The unique identifier of the customer.
   * @return The CustomerDto representing the customer.
   */
  @GetMapping("/{id}")
  CustomerDto findCustomerById(@PathVariable("id") String id);

  /**
   * Retrieve a customer by their document number.
   *
   * @param documentNumber The document number of the customer.
   * @return The CustomerDto representing the customer.
   */
  @GetMapping("/documentNumber/{documentNumber}")
  CustomerDto findCustomerByDocumentNumber(@PathVariable("documentNumber") String documentNumber);
}
