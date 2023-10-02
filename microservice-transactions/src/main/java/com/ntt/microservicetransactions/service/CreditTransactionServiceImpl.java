package com.ntt.microservicetransactions.service;

import com.ntt.microservicetransactions.domain.model.dto.*;
import com.ntt.microservicetransactions.domain.model.entity.CreditTransaction;
import com.ntt.microservicetransactions.domain.model.exception.InsufficientBalanceException;
import com.ntt.microservicetransactions.domain.model.exception.InsufficientParameterException;
import com.ntt.microservicetransactions.domain.model.exception.MethodCallFailureException;
import com.ntt.microservicetransactions.domain.repository.CreditTransactionRepository;
import com.ntt.microservicetransactions.domain.service.CreditTransactionService;
import com.ntt.microservicetransactions.infraestructure.feignclient.CreditClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreditTransactionServiceImpl implements CreditTransactionService {

    @Autowired
    private CreditTransactionRepository creditTransactionRepository;

    @Autowired
    private CreditClient creditClient;

    /**
     * Retrieve a list of credit transaction with a credit id and customer document number
     *
     * @param creditId The ID of credit
     * @param customerDocumentNumber The number of document of costumer
     * @return A list of class CreditTransactionDTO.
     */
    @Override
    public List<CreditTransactionDTO> getFilteredCreditTransactions(String creditId, String customerDocumentNumber) {
        List<CreditTransaction> creditTransactionList = new ArrayList<>();
        if (creditId == null && customerDocumentNumber == null) {
            throw new InsufficientParameterException("Es necesario que se pase al menos alguno de los parametros");
        }

        if (creditId != null && customerDocumentNumber == null) {
            creditTransactionList = creditTransactionRepository.findByCreditId(creditId);
        }

        if (creditId == null && customerDocumentNumber != null) {
            creditTransactionList = creditTransactionRepository.findByCustomerDocumentNumber(customerDocumentNumber);
        }

        if (creditId != null && customerDocumentNumber != null) {
            creditTransactionList = creditTransactionRepository.findByCreditIdAndCustomerDocumentNumber(creditId, customerDocumentNumber);
        }

        return creditTransactionList.stream().map(creditTransaction -> mapDTO(creditTransaction)).collect(Collectors.toList());
    }

    /**
     * Create a credit transaction with an object of type creditTransactionDTO
     *
     * @param creditTransactionDTO The object creditTransactionDTO
     * @return An instance of creditTransactionDTO
     */
    @Override
    public CreditTransactionDTO createCreditTransaction(CreditTransactionDTO creditTransactionDTO) {
        CreditDTO creditDTO = creditClient.getCredit(creditTransactionDTO.getCreditId()).getBody();
        validateAmountCreditPay(creditDTO, creditTransactionDTO);
        CreditTransaction creditTransactionResponse = new CreditTransaction();
        try {
            UpdatedCreditDTO updatedCreditDTO = mapUpdateCredit(creditDTO, creditTransactionDTO);
            updateCreditFeign(creditTransactionDTO.getCreditId(), updatedCreditDTO);
            creditTransactionResponse = creditTransactionRepository.save(mapEntity(creditTransactionDTO));
        } catch (Exception e) {
            StackTraceElement[] stackTrace = e.getStackTrace();
            for (StackTraceElement element : stackTrace) {
                if (element.getMethodName().equals("updateCreditFeign")) {
                    throw new MethodCallFailureException("Fallo en la llamada al metodo updateCreditFeign");
                }
                if (element.getMethodName().equals("save")) {
                    UpdatedCreditDTO updatedExceptionCreditDTO = mapUpdateExceptionCredit(creditDTO);
                    updateCreditFeign(creditTransactionDTO.getCreditId(), updatedExceptionCreditDTO);
                    throw new MethodCallFailureException("Fallo en la llamada al metodo save");
                }
            }
        }
        return mapDTO(creditTransactionResponse);
    }

    /**
     * Map the object to update in microservice credit
     *
     * @param creditDTO The object creditDTO
     * @param creditTransactionDTO The object creditTransactionDTO
     * @return An instance of UpdatedCreditDTO
     */
    private UpdatedCreditDTO mapUpdateCredit(CreditDTO creditDTO, CreditTransactionDTO creditTransactionDTO) {
        UpdatedCreditDTO updatedCreditDTO = new UpdatedCreditDTO();
        updatedCreditDTO.setCustomerId(creditDTO.getCustomerId());
        updatedCreditDTO.setAmountGranted(creditDTO.getAmountGranted());
        updatedCreditDTO.setInterest(creditDTO.getInterest());
        updatedCreditDTO.setAmountInterest(creditDTO.getAmountInterest());
        updatedCreditDTO.setAmountPaid(creditTransactionDTO.getAmount() + creditDTO.getAmountPaid());
        return updatedCreditDTO;
    }

    /**
     * Map the object to update in microservice credit when an error occurs
     *
     * @param creditDTO The object creditDTO
     * @return An instance of UpdatedCreditDTO
     */
    private UpdatedCreditDTO mapUpdateExceptionCredit(CreditDTO creditDTO) {
        UpdatedCreditDTO mapUpdatedCredit = new UpdatedCreditDTO();
        mapUpdatedCredit.setCustomerId(creditDTO.getCustomerId());
        mapUpdatedCredit.setAmountGranted(creditDTO.getAmountGranted());
        mapUpdatedCredit.setInterest(creditDTO.getInterest());
        mapUpdatedCredit.setAmountInterest(creditDTO.getAmountInterest());
        mapUpdatedCredit.setAmountPaid(creditDTO.getAmountPaid());

        return mapUpdatedCredit;

    }

    /**
     * Makes the call to the feign client update method
     *
     * @param idCredit The ID of the credit
     * @param updatedCreditDTO the object to be updated
     */
    private void updateCreditFeign(String idCredit, UpdatedCreditDTO updatedCreditDTO) {
        creditClient.updateCredit(idCredit, updatedCreditDTO);
    }

    /**
     * Check if there is available balance for the transaction type equals PAGO
     *
     * @param creditDTO The object creditDTO
     * @param creditTransactionDTO The object creditTransactionDTO
     */
    private void validateAmountCreditPay(CreditDTO creditDTO, CreditTransactionDTO creditTransactionDTO) {
        if (creditDTO.getAmountPaid() + creditTransactionDTO.getAmount() > creditDTO.getAmountGranted()) {
            throw new InsufficientBalanceException("La deuda es inferior al monto pagado");
        }
    }

    /**
     * Map the data access object to data transfer object of CreditTransaction
     *
     * @param creditTransaction The object creditTransaction
     * @return An instance of CreditTransactionDTO
     */
    private CreditTransactionDTO mapDTO(CreditTransaction creditTransaction) {
        CreditTransactionDTO creditTransactionDTO = new CreditTransactionDTO();
        creditTransactionDTO.setId(creditTransaction.getId());
        creditTransactionDTO.setType(creditTransaction.getType());
        creditTransactionDTO.setDate(creditTransaction.getDate());
        creditTransactionDTO.setAmount(creditTransaction.getAmount());
        creditTransactionDTO.setAtm(creditTransaction.getAtm());
        creditTransactionDTO.setCreditId(creditTransaction.getCreditId());
        creditTransactionDTO.setCustomerDocumentNumber(creditTransaction.getCustomerDocumentNumber());
        return creditTransactionDTO;
    }

    /**
     * Map the data transfer object to data access object of CreditTransaction
     *
     * @param creditTransactionDTO The object creditTransactionDTO
     * @return An instance of CreditTransactionDTO
     */
    private CreditTransaction mapEntity(CreditTransactionDTO creditTransactionDTO) {
        CreditTransaction creditTransaction = new CreditTransaction();
        creditTransaction.setId(creditTransactionDTO.getId());
        creditTransaction.setType(creditTransactionDTO.getType());
        creditTransaction.setDate(creditTransactionDTO.getDate());
        creditTransaction.setAmount(creditTransactionDTO.getAmount());
        creditTransaction.setAtm(creditTransactionDTO.getAtm());
        creditTransaction.setCreditId(creditTransactionDTO.getCreditId());
        creditTransaction.setCustomerDocumentNumber(creditTransactionDTO.getCustomerDocumentNumber());
        return creditTransaction;
    }
}
