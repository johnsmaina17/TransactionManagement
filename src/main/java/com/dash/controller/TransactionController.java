package com.dash.controller;

import com.dash.entity.Transaction;
import com.dash.service.TransactionService;
import org.springframework.dao.EmptyResultDataAccessException;
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
    public List<Transaction> getAllTransactions(){
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable Long id){
        return transactionService.getTransactionById(id);
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody  Transaction transaction){
        return transactionService.createTransaction(transaction);
    }

    @PutMapping("/{id}")
    public Transaction updateTransaction(@PathVariable(value = "id")  Long id ,@RequestBody  Transaction transaction){
        return transactionService.updateTransaction(id,transaction);
    }

    @DeleteMapping("/{id}")
    public String updateTransaction(@PathVariable(value = "id")  Long id){
        try {
            transactionService.deleteTransaction(id);
        }catch (EmptyResultDataAccessException e){
            return "Transaction could not be deleted";
        }
        return "Successfully Deleted Transaction with id "+id;
    }

}
