package com.ntt.microserviceaccounts.domain.service;

import com.ntt.microserviceaccounts.domain.model.enity.BankAccount;
import com.ntt.microserviceaccounts.domain.model.enity.CurrentAccount;

import java.util.function.Predicate;

public interface BusinessRuleService {
    boolean validateAccountsCustomer(String documentNumber, String typeAccount);

    boolean validateAccountDuplicate(String acocuntNumber);
    Predicate<BankAccount> isValidAccount();
}
