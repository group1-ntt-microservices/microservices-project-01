package com.ntt.microservice.customers.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionMessage {
    MESSAGE_KEY("Error"),
    CUSTOMER_NOT_FOUND("Customer not found");

    private final String message;
}
