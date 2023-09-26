package com.ntt.microservice.customers.configuration;

import com.ntt.microservice.customers.service.exception.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ExceptionAdvisor {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCustomerNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(
                        ExceptionMessage.MESSAGE_KEY.getMessage(),
                        ExceptionMessage.CUSTOMER_NOT_FOUND.getMessage()
                ));
    }
}
