package com.ntt.microservice.credits.feign.service;

import com.ntt.microservice.credits.feign.dto.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "customer-service", url = "http://localhost:8081")
public interface CustomerFeignService {
  @GetMapping("/")
  List<CustomerDto> findAllCustomers();

  @GetMapping("/{id}")
  CustomerDto findCustomerById(@PathVariable("id") String id);

  @GetMapping("/documentNumber/{documentNumber}")
  CustomerDto findCustomerByDocumentNumber(@PathVariable("documentNumber") String documentNumber);
}
