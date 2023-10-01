package com.ntt.microservicetransactions.service;

import com.ntt.microservicetransactions.domain.model.dto.BankAccountDTO;
import com.ntt.microservicetransactions.domain.model.dto.BankAccountTransactionDTO;
import com.ntt.microservicetransactions.domain.model.dto.UpdatedBankAccountDTO;
import com.ntt.microservicetransactions.domain.model.entity.BankAccountTransaction;
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

    private BankAccountTransactionDTO processTransactionCurrentAccount(BankAccountDTO bankAccountDTO, BankAccountTransactionDTO bankAccountTransactionDTO){
        verifyTransactionType(bankAccountDTO,bankAccountTransactionDTO);
        updateBankAccountFeign(bankAccountDTO.getAccountNumber(), updateBankAccount(bankAccountDTO, bankAccountTransactionDTO));
        return saveTransaction(bankAccountTransactionDTO);
    }

    private BankAccountTransactionDTO processTransactionSavingAccount(BankAccountDTO bankAccountDTO, BankAccountTransactionDTO bankAccountTransactionDTO){
        verifyAvailableTransactions(bankAccountDTO);
        verifyTransactionType(bankAccountDTO,bankAccountTransactionDTO);
        updateBankAccountFeign(bankAccountDTO.getAccountNumber(),updateBankAccount(bankAccountDTO,bankAccountTransactionDTO));
        return saveTransaction(bankAccountTransactionDTO);
    }

    private BankAccountTransactionDTO processTransactionFixedTermAccount(BankAccountDTO bankAccountDTO, BankAccountTransactionDTO bankAccountTransactionDTO){
        verifyDaySuitableForTransaction(bankAccountDTO);
        verifyAvailableTransactions(bankAccountDTO);
        verifyTransactionType(bankAccountDTO,bankAccountTransactionDTO);
        updateBankAccountFeign(bankAccountDTO.getAccountNumber(),updateBankAccount(bankAccountDTO,bankAccountTransactionDTO));
        return saveTransaction(bankAccountTransactionDTO);
    }

    private void verifyDaySuitableForTransaction(BankAccountDTO bankAccountDTO){
        int dayOfMonth= ZonedDateTime.now().getDayOfMonth();

        if(bankAccountDTO.getWithdrawalDay() != dayOfMonth){
            throw new InvalidDateException("No tienes permitido hacer un movimiento este dia");
        }
    }

    private void verifyAvailableTransactions(BankAccountDTO bankAccountDTO){
        if(bankAccountDTO.getCompletedTransactions() >= bankAccountDTO.getMonthlyTransactionLimit()){
            throw new LimitTransactionsExceededException("Has superado el limite de movimientos permitidos en tu cuenta");
        }
    }

    private void verifyTransactionType(BankAccountDTO bankAccountDTO, BankAccountTransactionDTO bankAccountTransactionDTO){
        if(bankAccountTransactionDTO.getType().equalsIgnoreCase("RETIRO")){
            verifyAvailableBalanceForWithdrawal(bankAccountDTO,bankAccountTransactionDTO);
        }
    }

    private void verifyAvailableBalanceForWithdrawal(BankAccountDTO bankAccountDTO, BankAccountTransactionDTO bankAccountTransactionDTO){
        if(bankAccountDTO.getBalance()<bankAccountTransactionDTO.getAmount()){
            throw new InsufficientBalanceException("No cuentas con el saldo suficiente para la transaccion");
        }
    }

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

    private Float calculateNewBalance(Boolean typeTransactionDeposit, Float currentBalance, Float transactionBalance){
        BinaryOperator<Float> operation = (a,b) -> typeTransactionDeposit ? a+b : a-b;
        return operation.apply(currentBalance,transactionBalance);
    }

    private Integer calculateNewCompletedTransactions(int completedTransactions){
        UnaryOperator<Integer> increase = a -> a+1;
        return increase.apply(completedTransactions);
    }



    private void updateBankAccountFeign(String accountNumber, UpdatedBankAccountDTO updatedBankAccountDTO){
        bankAccountClient.updateBankAccount(accountNumber,updatedBankAccountDTO);
    }

    private BankAccountTransactionDTO saveTransaction(BankAccountTransactionDTO bankAccountTransactionDTO){
        return mapDTO(bankAccountTransactionRepository.save(mapEntity(bankAccountTransactionDTO)));
    }

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
