package com.dash.controller;

import com.dash.entity.Transaction;
import com.dash.exceptions.TransactionNotFoundException;
import com.dash.service.TransactionService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions(){
        List<Transaction> transactions= transactionService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) throws TransactionNotFoundException {
       Transaction transaction= transactionService.getTransactionById(id);
       return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody  Transaction transaction){
        Transaction createdTransaction = transactionService.createTransaction(transaction);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable(value = "id")  Long id ,@RequestBody  Transaction transaction) throws TransactionNotFoundException {
        Transaction updatedTransaction = transactionService.updateTransaction(id,transaction);
        return new ResponseEntity<>(updatedTransaction, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> updateTransaction(@PathVariable(value = "id")  Long id){
        try {
            transactionService.deleteTransaction(id);
        }catch (EmptyResultDataAccessException e){
            return new ResponseEntity<>("Transaction could not be deleted", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Successfully Deleted Transaction with id "+id, HttpStatus.OK);
    }

}
