package com.bank.transfer.api.model.request;

import java.math.BigDecimal;

/**
 * @author Ehtiram_Abdullayev on 2/11/2020
 * @project bank-transfer
 */
public class AccountRequest {
    private final int number;
    private final BigDecimal balance;

    public AccountRequest(int number, BigDecimal balance) {
        this.number = number;
        this.balance = balance;
    }


    public int getNumber() {
        return number;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
