package com.bank.transfer.exception;

/**
 * @author Ehtiram_Abdullayev on 2/9/2020
 * @project bank-transfer
 */
public class InsufficientFundsException extends Exception {
    public InsufficientFundsException() {
        super();
    }

    public InsufficientFundsException(String message) {
        super(message);
    }
}
