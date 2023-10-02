package com.ntt.microservicetransactions.service;

import com.ntt.microservicetransactions.domain.model.dto.BankAccountDTO;
import com.ntt.microservicetransactions.domain.model.dto.BankAccountTransactionDTO;
import com.ntt.microservicetransactions.domain.model.dto.UpdatedBankAccountDTO;
import com.ntt.microservicetransactions.domain.model.dto.UpdatedCreditDTO;
import com.ntt.microservicetransactions.domain.model.entity.BankAccountTransaction;
import com.ntt.microservicetransactions.domain.model.entity.CreditTransaction;
import com.ntt.microservicetransactions.domain.model.exception.*;
import com.ntt.microservicetransactions.domain.repository.BankAccountTransactionRepository;
import com.ntt.microservicetransactions.domain.service.BankAccountTransactionService;
import com.ntt.microservicetransactions.infraestructure.feignclient.BankAccountClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.management.AttributeNotFoundException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Service
public class BankAccountTransactionServiceImpl implements BankAccountTransactionService {

    @Autowired
    private BankAccountTransactionRepository bankAccountTransactionRepository;

    @Autowired
    private BankAccountClient bankAccountClient;

    /**
     * Retrieve a list of bank account transaction with a bank account number and customer document number
     *
     * @param bankAccountNumber The number of bank account
     * @param customerDocumentNumber The number of bank account
     * @return A list of class BankAccountTransactionDTO.
     */
    @Override
    public List<BankAccountTransactionDTO> getFilteredBankAccountTransactions(String bankAccountNumber, String customerDocumentNumber) {
        List<BankAccountTransaction> bankAccountTransactionList = new ArrayList<>();
        if (bankAccountNumber == null && customerDocumentNumber == null){
            throw new InsufficientParameterException("Es necesario que se pase al menos alguno de los parametros");
        }

        if(bankAccountNumber != null && customerDocumentNumber == null){
            bankAccountTransactionList = bankAccountTransactionRepository.findByBankAccountNumber(bankAccountNumber);
        }

        if(bankAccountNumber == null && customerDocumentNumber != null){
            bankAccountTransactionList = bankAccountTransactionRepository.findByCustomerDocumentNumber(customerDocumentNumber);
        }

        if (bankAccountNumber != null && customerDocumentNumber != null){
            bankAccountTransactionList = bankAccountTransactionRepository.findByBankAccountNumberAndCustomerDocumentNumber(bankAccountNumber,customerDocumentNumber);
        }

        //List<BankAccountTransaction> bankAccountTransactionList = bankAccountTransactionRepository.findByBankAccountNumberAndCustomerDocumentNumber(bankAccountNumber, customerDocumentNumber);
        return bankAccountTransactionList.stream().map(bankAccountTransaction -> mapDTO(bankAccountTransaction)).collect(Collectors.toList());
    }

    /**
     * Create a bank account transaction with an object of type bankAccountTransactionDTO
     *
     * @param bankAccountTransactionDTO The object bankAccountTransactionDTO
     * @return An instance of bankAccountTransactionDTO
     */
    @Override
    public BankAccountTransactionDTO createBankAccountTransaction(BankAccountTransactionDTO bankAccountTransactionDTO) {
        ResponseEntity<BankAccountDTO> bankAccountDTOResponse = bankAccountClient.getBankAccount(bankAccountTransactionDTO.getBankAccountNumber());
        switch(bankAccountDTOResponse.getBody().getTypeAccount()){

            case "currentAccount":
                return processTransactionCurrentAccount(bankAccountDTOResponse.getBody(),bankAccountTransactionDTO);

            case "savingAccount":
                return processTransactionSavingAccount(bankAccountDTOResponse.getBody(),bankAccountTransactionDTO);


            case "fixedTermAccount":
                return processTransactionFixedTermAccount(bankAccountDTOResponse.getBody(),bankAccountTransactionDTO);


            default:
                throw new InvalidTypeAccountException("Tipo de cuenta no valido");

        }
    }

    /**
     * Processes the transaction for a bank account of the current account type
     *
     * @param bankAccountDTO The object bankAccountDTO
     * @param bankAccountTransactionDTO The object bankAccountTransactionDTO
     * @return An instance of bankAccountTransactionDTO
     */
    private BankAccountTransactionDTO processTransactionCurrentAccount(BankAccountDTO bankAccountDTO, BankAccountTransactionDTO bankAccountTransactionDTO){
        verifyTransactionType(bankAccountDTO,bankAccountTransactionDTO);
        return processUpdateAndSaveTransaction(bankAccountDTO,bankAccountTransactionDTO);
    }

    /**
     * Processes the transaction for a bank account of the saving account type
     *
     * @param bankAccountDTO The object bankAccountDTO
     * @param bankAccountTransactionDTO The object bankAccountTransactionDTO
     * @return An instance of bankAccountTransactionDTO
     */
    private BankAccountTransactionDTO processTransactionSavingAccount(BankAccountDTO bankAccountDTO, BankAccountTransactionDTO bankAccountTransactionDTO){
        verifyAvailableTransactions(bankAccountDTO);
        verifyTransactionType(bankAccountDTO,bankAccountTransactionDTO);
        return processUpdateAndSaveTransaction(bankAccountDTO,bankAccountTransactionDTO);
    }

    /**
     * Processes the transaction for a bank account of the fixed term account type
     *
     * @param bankAccountDTO The object bankAccountDTO
     * @param bankAccountTransactionDTO The object bankAccountTransactionDTO
     * @return An instance of bankAccountTransactionDTO
     */
    private BankAccountTransactionDTO processTransactionFixedTermAccount(BankAccountDTO bankAccountDTO, BankAccountTransactionDTO bankAccountTransactionDTO){
        verifyDaySuitableForTransaction(bankAccountDTO);
        verifyAvailableTransactions(bankAccountDTO);
        verifyTransactionType(bankAccountDTO,bankAccountTransactionDTO);
        return processUpdateAndSaveTransaction(bankAccountDTO,bankAccountTransactionDTO);
    }

    /**
     * Processes the transaction, update in microservice Bank Account and save the transaction.
     *
     * @param bankAccountDTO The object bankAccountDTO
     * @param bankAccountTransactionDTO The object bankAccountTransactionDTO
     * @return An instance of bankAccountTransactionDTO
     */

    private BankAccountTransactionDTO processUpdateAndSaveTransaction(BankAccountDTO bankAccountDTO, BankAccountTransactionDTO bankAccountTransactionDTO){
        BankAccountTransactionDTO bankAccountTransactionResponse = new BankAccountTransactionDTO();
        try {
            updateBankAccountFeign(bankAccountDTO.getAccountNumber(), updateBankAccount(bankAccountDTO, bankAccountTransactionDTO));
            bankAccountTransactionResponse = saveTransaction(bankAccountTransactionDTO);
        } catch (Exception e) {
            StackTraceElement[] stackTrace = e.getStackTrace();
            for (StackTraceElement element : stackTrace) {
                if (element.getMethodName().equals("updateBankAccountFeign")) {
                    throw new MethodCallFailureException("Fallo en la llamada al metodo updateBankAccountFeign");
                }
                if (element.getMethodName().equals("saveTransaction")) {
                    UpdatedBankAccountDTO updatedExceptionBankAccountDTO = mapUpdateExceptionBankAccount(bankAccountDTO,bankAccountTransactionDTO);
                    updateBankAccountFeign(bankAccountDTO.getAccountNumber(), updatedExceptionBankAccountDTO);
                    throw new MethodCallFailureException("Fallo en la llamada al metodo saveTransaction");
                }
            }
        }
        return bankAccountTransactionResponse;
    }

    /**
     * Check if it is an available day to do a transaction
     *
     * @param bankAccountDTO The object bankAccountDTO
     */
    private void verifyDaySuitableForTransaction(BankAccountDTO bankAccountDTO){
        int dayOfMonth= ZonedDateTime.now().getDayOfMonth();

        if(bankAccountDTO.getWithdrawalDay() != dayOfMonth){
            throw new InvalidDateException("No tienes permitido hacer un movimiento este dia");
        }
    }

    /**
     * Check if there are transactions available to make a transaction
     *
     * @param bankAccountDTO The object bankAccountDTO
     */
    private void verifyAvailableTransactions(BankAccountDTO bankAccountDTO){
        if(bankAccountDTO.getCompletedTransactions() >= bankAccountDTO.getMonthlyTransactionLimit()){
            throw new LimitTransactionsExceededException("Has superado el limite de movimientos permitidos en tu cuenta");
        }
    }

    /**
     * Check if the transaction type equals RETIRO
     *
     * @param bankAccountDTO The object bankAccountDTO
     * @param bankAccountTransactionDTO The object bankAccountTransactionDTO
     */
    private void verifyTransactionType(BankAccountDTO bankAccountDTO, BankAccountTransactionDTO bankAccountTransactionDTO){
        if(bankAccountTransactionDTO.getType().equalsIgnoreCase("RETIRO")){
            verifyAvailableBalanceForWithdrawal(bankAccountDTO,bankAccountTransactionDTO);
        }
    }

    /**
     * Check if there is available balance for the transaction type equals RETIRO
     *
     * @param bankAccountDTO The object bankAccountDTO
     * @param bankAccountTransactionDTO The object bankAccountTransactionDTO
     */
    private void verifyAvailableBalanceForWithdrawal(BankAccountDTO bankAccountDTO, BankAccountTransactionDTO bankAccountTransactionDTO){
        if(bankAccountDTO.getBalance()<bankAccountTransactionDTO.getAmount()){
            throw new InsufficientBalanceException("No cuentas con el saldo suficiente para la transaccion");
        }
    }

    /**
     * Map the object to update in microservice bank account
     *
     * @param bankAccountDTO The object bankAccountDTO
     * @param bankAccountTransactionDTO The object bankAccountTransactionDTO
     * @return An instance of UpdatedBankAccountDTO
     */
    private UpdatedBankAccountDTO updateBankAccount(BankAccountDTO bankAccountDTO, BankAccountTransactionDTO bankAccountTransactionDTO){
        UpdatedBankAccountDTO mapUpdatedBankAccount=new UpdatedBankAccountDTO();
        mapUpdatedBankAccount.setAccountNumber(bankAccountDTO.getAccountNumber());
        mapUpdatedBankAccount.setAmount(bankAccountTransactionDTO.getAmount());
        mapUpdatedBankAccount.setTypeTransaction(bankAccountTransactionDTO.getType());
        mapUpdatedBankAccount.setCompletedTransaction(true);

        Boolean typeTransactionDeposit = bankAccountTransactionDTO.getType().equalsIgnoreCase("DEPOSITO");
        Float currentBalance = bankAccountDTO.getBalance();
        Float transactionBalance = bankAccountTransactionDTO.getAmount();
        Float newBalance = calculateNewBalance(typeTransactionDeposit,currentBalance,transactionBalance);

        mapUpdatedBankAccount.setBalance(newBalance);
        mapUpdatedBankAccount.setCompletedTransactions(calculateNewCompletedTransactions(bankAccountDTO.getCompletedTransactions()));

        return mapUpdatedBankAccount;
    }

    /**
     * Calculate new balance in the bank account
     *
     * @param typeTransactionDeposit A boolean true if is equals DEPOSITO
     * @param currentBalance The current balance in the bank account
     * @param transactionBalance The balance of the transaction
     * @return The new balance
     */
    private Float calculateNewBalance(Boolean typeTransactionDeposit, Float currentBalance, Float transactionBalance){
        BinaryOperator<Float> operation = (a,b) -> typeTransactionDeposit ? a+b : a-b;
        return operation.apply(currentBalance,transactionBalance);
    }

    /**
     * Calculate new completedTransactions in the bank account
     *
     * @param completedTransactions The current completedTransactions in the bank account
     * @return The new completedTransactions, add 1 completedTransactions
     */
    private Integer calculateNewCompletedTransactions(int completedTransactions){
        UnaryOperator<Integer> increase = a -> a+1;
        return increase.apply(completedTransactions);
    }

    /**
     * Makes the call to the feign client update method
     *
     * @param accountNumber The account number of the bank account
     * @param updatedBankAccountDTO the object to be updated
     */
    private void updateBankAccountFeign(String accountNumber, UpdatedBankAccountDTO updatedBankAccountDTO){
        bankAccountClient.updateBankAccount(accountNumber,updatedBankAccountDTO);
    }

    /**
     * Save the transaction in the db
     *
     * @param bankAccountTransactionDTO The account number of the bank account
     * @return An instance of BankAccountTransactionDTO
     */
    private BankAccountTransactionDTO saveTransaction(BankAccountTransactionDTO bankAccountTransactionDTO){
        return mapDTO(bankAccountTransactionRepository.save(mapEntity(bankAccountTransactionDTO)));
    }

    /**
     * Map the object to update in microservice bank account when an error occurs
     *
     * @param bankAccountDTO The object bankAccountDTO
     * @param bankAccountTransactionDTO The object bankAccountTransactionDTO
     * @return An instance of UpdatedBankAccountDTO
     */
    private UpdatedBankAccountDTO mapUpdateExceptionBankAccount(BankAccountDTO bankAccountDTO, BankAccountTransactionDTO bankAccountTransactionDTO){
        UpdatedBankAccountDTO mapUpdatedBankAccount=new UpdatedBankAccountDTO();
        mapUpdatedBankAccount.setAccountNumber(bankAccountDTO.getAccountNumber());
        mapUpdatedBankAccount.setAmount(bankAccountTransactionDTO.getAmount());
        mapUpdatedBankAccount.setTypeTransaction(bankAccountTransactionDTO.getType());
        mapUpdatedBankAccount.setCompletedTransaction(false);
        mapUpdatedBankAccount.setBalance(bankAccountDTO.getBalance());
        mapUpdatedBankAccount.setCompletedTransactions(bankAccountDTO.getCompletedTransactions());
        return mapUpdatedBankAccount;
    }

    /**
     * Map the data access object to data transfer object of BankAccountTransaction
     *
     * @param bankAccountTransaction The object bankAccountTransaction
     * @return An instance of BankAccountTransactionDTO
     */
    private BankAccountTransactionDTO mapDTO(BankAccountTransaction bankAccountTransaction){
        BankAccountTransactionDTO bankAccountTransactionDTO = new BankAccountTransactionDTO();
        bankAccountTransactionDTO.setId(bankAccountTransaction.getId());
        bankAccountTransactionDTO.setType(bankAccountTransaction.getType());
        bankAccountTransactionDTO.setDate(bankAccountTransaction.getDate());
        bankAccountTransactionDTO.setAmount(bankAccountTransaction.getAmount());
        bankAccountTransactionDTO.setAtm(bankAccountTransaction.getAtm());
        bankAccountTransactionDTO.setBankAccountNumber(bankAccountTransaction.getBankAccountNumber());
        bankAccountTransactionDTO.setCustomerDocumentNumber(bankAccountTransaction.getCustomerDocumentNumber());
        return bankAccountTransactionDTO;
    }

    /**
     * Map the data transfer object to data access object of BankAccountTransaction
     *
     * @param bankAccountTransactionDTO The object bankAccountTransactionDTO
     * @return An instance of BankAccountTransactionDTO
     */
    private BankAccountTransaction mapEntity(BankAccountTransactionDTO bankAccountTransactionDTO){
        BankAccountTransaction bankAccountTransaction = new BankAccountTransaction();
        bankAccountTransaction.setId(bankAccountTransactionDTO.getId());
        bankAccountTransaction.setType(bankAccountTransactionDTO.getType());
        bankAccountTransaction.setDate(bankAccountTransactionDTO.getDate());
        bankAccountTransaction.setAmount(bankAccountTransactionDTO.getAmount());
        bankAccountTransaction.setAtm(bankAccountTransactionDTO.getAtm());
        bankAccountTransaction.setBankAccountNumber(bankAccountTransactionDTO.getBankAccountNumber());
        bankAccountTransaction.setCustomerDocumentNumber(bankAccountTransactionDTO.getCustomerDocumentNumber());
        return bankAccountTransaction;
    }
}
