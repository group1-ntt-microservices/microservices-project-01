package com.ntt.microservicetransactions.service;

import com.ntt.microservicetransactions.domain.model.dto.*;
import com.ntt.microservicetransactions.domain.model.entity.CreditCardTransaction;
import com.ntt.microservicetransactions.domain.model.exception.InsufficientBalanceException;
import com.ntt.microservicetransactions.domain.model.exception.InsufficientParameterException;
import com.ntt.microservicetransactions.domain.model.exception.MethodCallFailureException;
import com.ntt.microservicetransactions.domain.repository.CreditCardTransactionRepository;
import com.ntt.microservicetransactions.domain.service.CreditCardTransactionService;
import com.ntt.microservicetransactions.infraestructure.feignclient.CreditCardClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

@Service
public class CreditCardTransactionServiceImpl implements CreditCardTransactionService {

    @Autowired
    private CreditCardTransactionRepository creditCardTransactionRepository;

    @Autowired
    private CreditCardClient creditCardClient;

    /**
     * Retrieve a list of credit card transaction with a credit card ID and customer document number
     *
     * @param creditCardId The number of credit card ID
     * @param customerDocumentNumber The number of credit card
     * @return A list of class CreditCardTransactionDTO.
     */
    @Override
    public List<CreditCardTransactionDTO> getFilteredCreditCardTransactions(String creditCardId, String customerDocumentNumber) {
        List<CreditCardTransaction> creditCardTransactionList = new ArrayList<>();
        if (creditCardId == null && customerDocumentNumber == null){
            throw new InsufficientParameterException("Es necesario que se pase al menos alguno de los parametros");
        }

        if(creditCardId != null && customerDocumentNumber == null){
            creditCardTransactionList = creditCardTransactionRepository.findByCreditCardId(creditCardId);
        }

        if(creditCardId == null && customerDocumentNumber != null){
            creditCardTransactionList = creditCardTransactionRepository.findByCustomerDocumentNumber(customerDocumentNumber);
        }

        if (creditCardId != null && customerDocumentNumber != null){
            creditCardTransactionList = creditCardTransactionRepository.findByCreditCardIdAndCustomerDocumentNumber(creditCardId,customerDocumentNumber);
        }

        return creditCardTransactionList.stream().map(creditCardTransaction -> mapDTO(creditCardTransaction)).collect(Collectors.toList());

   }

    /**
     * Create a credit card transaction with an object of type creditCardTransactionDTO
     *
     * @param creditCardTransactionDTO The object creditCardTransactionDTO
     * @return An instance of creditCardTransactionDTO
     */
    @Override
    public CreditCardTransactionDTO createCreditCardTransaction(CreditCardTransactionDTO creditCardTransactionDTO) {
        CreditCardDTO creditCardDTO = creditCardClient.getCreditCard(creditCardTransactionDTO.getCreditCardId()).getBody();
        verifyTransactionType(creditCardDTO,creditCardTransactionDTO);
        CreditCardTransaction creditCardTransactionResponse = new CreditCardTransaction();

        try{
            UpdatedCreditCardDTO updatedCreditCardDTO = mapUpdateCreditCard(creditCardDTO,creditCardTransactionDTO);
            updateCreditCardFeign(creditCardTransactionDTO.getCreditCardId(),updatedCreditCardDTO);
            creditCardTransactionResponse = creditCardTransactionRepository.save(mapEntity(creditCardTransactionDTO));
        }
        catch (Exception e){
            StackTraceElement[] stackTrace = e.getStackTrace();
            for (StackTraceElement element : stackTrace) {
                if (element.getMethodName().equals("updateCreditCardFeign")){
                    throw new MethodCallFailureException("Fallo en la llamada al metodo updateCreditCardFeign");
                }
                if (element.getMethodName().equals("save")){
                    UpdatedCreditCardDTO updatedExceptionCreditCardDTO = mapUpdateExceptionCreditCard(creditCardDTO);
                    updateCreditCardFeign(creditCardTransactionDTO.getCreditCardId(),updatedExceptionCreditCardDTO);
                    throw new MethodCallFailureException("Fallo en la llamada al metodo save");
                }
            }
        }

        return mapDTO(creditCardTransactionResponse);
    }

    /**
     * Map the object to update in microservice credit card
     *
     * @param creditCardDTO The object creditCardDTO
     * @param creditCardTransactionDTO The object creditCardTransactionDTO
     * @return An instance of UpdatedCreditCardDTO
     */
    private UpdatedCreditCardDTO mapUpdateCreditCard(CreditCardDTO creditCardDTO, CreditCardTransactionDTO creditCardTransactionDTO){
        UpdatedCreditCardDTO mapUpdatedCreditCard=new UpdatedCreditCardDTO();
        mapUpdatedCreditCard.setCustomerId(creditCardDTO.getCustomerId());
        mapUpdatedCreditCard.setCardNumber(creditCardDTO.getCardNumber());
        mapUpdatedCreditCard.setLimitAmount(creditCardDTO.getLimitAmount());

        Boolean typeTransactionPay = creditCardTransactionDTO.getType().equalsIgnoreCase("PAGO");
        Float currentBalanceAvailable = creditCardDTO.getBalanceAvailable();
        Float currentBalanceDue = creditCardDTO.getBalanceDue();
        Float transactionBalance = creditCardTransactionDTO.getAmount();
        Float newBalanceAvailable = calculateNewBalanceAvailable(typeTransactionPay,currentBalanceAvailable,transactionBalance);
        Float newBalanceDue = calculateNewBalanceDue(typeTransactionPay,currentBalanceDue,transactionBalance);

        mapUpdatedCreditCard.setBalanceAvailable(newBalanceAvailable);
        mapUpdatedCreditCard.setBalanceDue(newBalanceDue);

        return mapUpdatedCreditCard;
    }

    /**
     * Map the object to update in microservice credit card when an error occurs
     *
     * @param creditCardDTO The object creditCardDTO
     * @return An instance of UpdatedCreditCardDTO
     */
    private UpdatedCreditCardDTO mapUpdateExceptionCreditCard(CreditCardDTO creditCardDTO){
        UpdatedCreditCardDTO mapUpdatedCreditCard=new UpdatedCreditCardDTO();
        mapUpdatedCreditCard.setCustomerId(creditCardDTO.getCustomerId());
        mapUpdatedCreditCard.setCardNumber(creditCardDTO.getCardNumber());
        mapUpdatedCreditCard.setLimitAmount(creditCardDTO.getLimitAmount());
        mapUpdatedCreditCard.setBalanceAvailable(creditCardDTO.getBalanceAvailable());
        mapUpdatedCreditCard.setBalanceDue(creditCardDTO.getBalanceDue());

        return mapUpdatedCreditCard;

    }

    /**
     * Check if there is a transaction of type CONSUMO o PAGO
     *
     * @param creditCardDTO The object creditCardDTO
     * @param creditCardTransactionDTO The object creditCardTransactionDTO
     */
    private void verifyTransactionType(CreditCardDTO creditCardDTO, CreditCardTransactionDTO creditCardTransactionDTO){
        if(creditCardTransactionDTO.getType().equalsIgnoreCase("CONSUMO")){
            verifyAvailableBalanceForWithdrawal(creditCardDTO,creditCardTransactionDTO);
        }
        if(creditCardTransactionDTO.getType().equalsIgnoreCase("PAGO")){
            verifyAvailableBalanceForPay(creditCardDTO,creditCardTransactionDTO);
        }
    }

    /**
     * Check if there is available balance for the transaction type equals CONSUMO
     *
     * @param creditCardDTO The object creditCardDTO
     * @param creditCardTransactionDTO The object creditCardTransactionDTO
     */
    private void verifyAvailableBalanceForWithdrawal(CreditCardDTO creditCardDTO, CreditCardTransactionDTO creditCardTransactionDTO){
        if(creditCardDTO.getBalanceAvailable()<creditCardTransactionDTO.getAmount()){
            throw new InsufficientBalanceException("No cuentas con el saldo suficiente para la transaccion");
        }
    }

    /**
     * Check if there is available balance for the transaction type equals PAGO
     *
     * @param creditCardDTO The object creditCardDTO
     * @param creditCardTransactionDTO The object creditCardTransactionDTO
     */
    private void verifyAvailableBalanceForPay(CreditCardDTO creditCardDTO, CreditCardTransactionDTO creditCardTransactionDTO){
        if(creditCardDTO.getBalanceDue()<creditCardTransactionDTO.getAmount()){
            throw new InsufficientBalanceException("La deuda es inferior al monto pagado");
        }
    }

    /**
     * Calculate new balance in the credit card
     *
     * @param typeTransactionPay A boolean true if is equals PAGO
     * @param currentBalanceAvailable The current balance available in the credit card
     * @param amountTransaction The balance of the transaction
     * @return The new balance
     */
    private Float calculateNewBalanceAvailable(Boolean typeTransactionPay, Float currentBalanceAvailable, Float amountTransaction){
        BinaryOperator<Float> calculateNewBalanceAvailable = (a, b) -> typeTransactionPay ? a+b : a-b;
        return calculateNewBalanceAvailable.apply(currentBalanceAvailable,amountTransaction);
    }

    /**
     * Calculate new balance in the credit card
     *
     * @param typeTransactionPay A boolean true if is equals PAGO
     * @param currentBalanceDue The current balance due in the credit card
     * @param amountTransaction The balance of the transaction
     * @return The new balance
     */
    private Float calculateNewBalanceDue(Boolean typeTransactionPay, Float currentBalanceDue, Float amountTransaction){
        BinaryOperator<Float> calculateNewBalanceDue = (a, b) -> typeTransactionPay ? a-b : a+b;
        return calculateNewBalanceDue.apply(currentBalanceDue,amountTransaction);
    }

    /**
     * Makes the call to the feign client update method
     *
     * @param idCreditCard The ID of the credit card
     * @param updatedCreditCardDTO the object to be updated
     */
    private void updateCreditCardFeign(String idCreditCard, UpdatedCreditCardDTO updatedCreditCardDTO){
        creditCardClient.updateCreditCard(idCreditCard,updatedCreditCardDTO);
    }

    /**
     * Map the data access object to data transfer object of CreditCardTransaction
     *
     * @param creditCardTransaction The object creditCardTransaction
     * @return An instance of CreditCardTransactionDTO
     */
    private CreditCardTransactionDTO mapDTO(CreditCardTransaction creditCardTransaction){
        CreditCardTransactionDTO creditCardTransactionDTO = new CreditCardTransactionDTO();
        creditCardTransactionDTO.setId(creditCardTransaction.getId());
        creditCardTransactionDTO.setType(creditCardTransaction.getType());
        creditCardTransactionDTO.setDate(creditCardTransaction.getDate());
        creditCardTransactionDTO.setAmount(creditCardTransaction.getAmount());
        creditCardTransactionDTO.setAtm(creditCardTransaction.getAtm());
        creditCardTransactionDTO.setCreditCardId(creditCardTransaction.getCreditCardId());
        creditCardTransactionDTO.setCustomerDocumentNumber(creditCardTransaction.getCustomerDocumentNumber());
        return creditCardTransactionDTO;
    }

    /**
     * Map the data transfer object to data access object of CreditCardTransaction
     *
     * @param creditCardTransactionDTO The object creditCardTransactionDTO
     * @return An instance of CreditCardTransactionDTO
     */
    private CreditCardTransaction mapEntity(CreditCardTransactionDTO creditCardTransactionDTO){
        CreditCardTransaction creditCardTransaction = new CreditCardTransaction();
        creditCardTransaction.setId(creditCardTransactionDTO.getId());
        creditCardTransaction.setType(creditCardTransactionDTO.getType());
        creditCardTransaction.setDate(creditCardTransactionDTO.getDate());
        creditCardTransaction.setAmount(creditCardTransactionDTO.getAmount());
        creditCardTransaction.setAtm(creditCardTransactionDTO.getAtm());
        creditCardTransaction.setCreditCardId(creditCardTransactionDTO.getCreditCardId());
        creditCardTransaction.setCustomerDocumentNumber(creditCardTransactionDTO.getCustomerDocumentNumber());
        return creditCardTransaction;
    }
}
