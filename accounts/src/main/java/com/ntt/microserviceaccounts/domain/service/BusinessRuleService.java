package com.ntt.microserviceaccounts.domain.service;

public interface BusinessRuleService {
    boolean validateSavingsAndFixedAccount(String documentNumber);
    boolean validateCurrentAccount(String documentNumber);


}
