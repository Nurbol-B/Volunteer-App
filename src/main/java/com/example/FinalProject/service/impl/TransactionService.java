package com.example.FinalProject.service.impl;

import com.example.FinalProject.entity.Transaction;
import com.example.FinalProject.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createTransaction(String fromAddress, String toAddress, BigDecimal amount, String currency, String status) {
        Transaction transaction = new Transaction();
        transaction.setFromAddress(fromAddress);
        transaction.setToAddress(toAddress);
        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setStatus(status);

        return transactionRepository.save(transaction);
    }
}

