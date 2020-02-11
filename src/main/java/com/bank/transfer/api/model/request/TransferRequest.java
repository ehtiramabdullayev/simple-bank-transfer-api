package com.bank.transfer.api.model.request;

import java.math.BigDecimal;

/**
 * @author Ehtiram_Abdullayev on 2/12/2020
 * @project bank-transfer
 */
public class TransferRequest {
    private final int fromAccountNumber;
    private final int toAccountNumber;
    private final BigDecimal amount;

    public TransferRequest(int fromAccountNumber, int toAccountNumber, BigDecimal amount) {
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.amount = amount;
    }

    public int getFromAccountNumber() {
        return fromAccountNumber;
    }

    public int getToAccountNumber() {
        return toAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
