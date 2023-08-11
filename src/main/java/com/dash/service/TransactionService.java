package com.dash.service;

import com.dash.entity.Transaction;
import com.dash.exceptions.TransactionNotFoundException;
import com.dash.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions(){
        log.info("fetching all transactions");
        return (List<Transaction>) transactionRepository.findAll();
    }

    public Transaction getTransactionById(Long id) throws TransactionNotFoundException {
        log.info("fetching transaction for id {} ",id);
        return transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException("Transaction not found with Id : "+ id));
    }

    public Transaction createTransaction(Transaction transaction){
        log.info("creating  transaction  {} ",transaction);
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Long id, Transaction transaction) throws TransactionNotFoundException {
        log.info("updating  transaction with id  {}, and transaction {} ",id ,transaction);
        Optional<Transaction> dbTransaction = transactionRepository.findById(id);
        if(dbTransaction.isPresent()){
            transaction.setId(id);
           return  transactionRepository.save(transaction);
        }
        log.error("transaction not found");
        throw new TransactionNotFoundException("Transaction not found with Id : "+ id);

    }

    public  void deleteTransaction(Long id){
        log.info("deleting transaction for id {} ",id);
        transactionRepository.deleteById(id);
    }

}
