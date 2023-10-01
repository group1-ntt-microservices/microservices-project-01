package com.ntt.microservicetransactions.service;

import com.ntt.microservicetransactions.domain.model.dto.*;
import com.ntt.microservicetransactions.domain.model.entity.CreditTransaction;
import com.ntt.microservicetransactions.domain.model.exception.InsufficientParameterException;
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

    @Override
    public List<CreditTransactionDTO> getFilteredCreditTransactions(String creditId, String customerDocumentNumber, Date startDate, Date endDate) {
        List<CreditTransaction> creditTransactionList = new ArrayList<>();
        if (creditId == null && customerDocumentNumber == null){
            throw new InsufficientParameterException("Es necesario que se pase al menos alguno de los parametros");
        }

        if(creditId != null && customerDocumentNumber == null){
            creditTransactionList = creditTransactionRepository.findByCreditId(creditId);
        }

        if(creditId == null && customerDocumentNumber != null){
            creditTransactionList = creditTransactionRepository.findByCustomerDocumentNumber(customerDocumentNumber);
        }

        if (creditId != null && customerDocumentNumber != null){
            creditTransactionList = creditTransactionRepository.findByCreditIdAndCustomerDocumentNumber(creditId,customerDocumentNumber);
        }

        return creditTransactionList.stream().map(creditTransaction -> mapDTO(creditTransaction)).collect(Collectors.toList());
    }

    @Override
    public CreditTransactionDTO createCreditTransaction(CreditTransactionDTO creditTransactionDTO) {
        CreditDTO creditDTO = creditClient.getCredit(creditTransactionDTO.getCreditId()).getBody();
        UpdatedCreditDTO updatedCreditDTO = mapUpdateCredit(creditDTO,creditTransactionDTO);
        updateCreditFeign(creditTransactionDTO.getCreditId(),updatedCreditDTO);
        return mapDTO(creditTransactionRepository.save(mapEntity(creditTransactionDTO)));
    }

    private UpdatedCreditDTO mapUpdateCredit(CreditDTO creditDTO, CreditTransactionDTO creditTransactionDTO){
        UpdatedCreditDTO updatedCreditDTO = new UpdatedCreditDTO();
        updatedCreditDTO.setCustomerId(creditDTO.getCustomerId());
        updatedCreditDTO.setAmountGranted(creditDTO.getAmountGranted());
        updatedCreditDTO.setInterest(creditDTO.getInterest());
        updatedCreditDTO.setAmountInterest(creditDTO.getAmountInterest());
        updatedCreditDTO.setAmountPaid(creditDTO.getAmountPaid());
        return updatedCreditDTO;
    }

    private void updateCreditFeign(String idCredit, UpdatedCreditDTO updatedCreditDTO){
        creditClient.updateCredit(idCredit,updatedCreditDTO);
    }

    private CreditTransactionDTO mapDTO(CreditTransaction creditTransaction){
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

    private CreditTransaction mapEntity(CreditTransactionDTO creditTransactionDTO){
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
