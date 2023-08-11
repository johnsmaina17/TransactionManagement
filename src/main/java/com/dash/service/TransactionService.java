package com.dash.service;

import com.dash.entity.Transaction;
import com.dash.exceptions.TransactionNotFoundException;
import com.dash.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions(){
        return (List<Transaction>) transactionRepository.findAll();
    }

    public Transaction getTransactionById(Long id){
        return transactionRepository.findById(id).orElse(null);
    }

    public Transaction createTransaction(Transaction transaction){
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Long id, Transaction transaction){
        Optional<Transaction> dbTransaction = transactionRepository.findById(id);
        if(dbTransaction.isPresent()){
            transaction.setId(id);
           return  transactionRepository.save(transaction);
        }
        return null;
    }

    public  void deleteTransaction(Long id){
        transactionRepository.deleteById(id);
    }

}
