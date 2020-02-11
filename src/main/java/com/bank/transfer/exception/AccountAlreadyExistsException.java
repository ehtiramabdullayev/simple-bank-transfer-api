package com.bank.transfer.exception;

/**
 * @author Ehtiram_Abdullayev on 2/11/2020
 * @project bank-transfer
 */
public class AccountAlreadyExistsException extends Exception {
    public AccountAlreadyExistsException() {
    }

    public AccountAlreadyExistsException(String message) {
        super(message);
    }
}
