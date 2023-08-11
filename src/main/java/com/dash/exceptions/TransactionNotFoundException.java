package com.dash.exceptions;

import lombok.Data;

@Data
public class TransactionNotFoundException extends Exception {
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
