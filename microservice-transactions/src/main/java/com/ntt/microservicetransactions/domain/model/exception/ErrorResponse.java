package com.ntt.microservicetransactions.domain.model.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents an error response
 */
@Getter
@Setter
public class ErrorResponse {
    private String message;
    private int status;

    /**
     * Constructor to create an instance of Person
     * @param message Message of the error
     * @param status Code status of the error
     */
    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
