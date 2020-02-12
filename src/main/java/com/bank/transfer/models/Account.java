package com.bank.transfer.models;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Ehtiram_Abdullayev on 2/9/2020
 * @project bank-transfer
 */
public class Account {

    private final int accountNumber;
    private BigDecimal balance;

    public Account(final int accountNumber, final BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }


    public void withdraw(final BigDecimal amount) {
        balance = balance.subtract(amount);
    }

    public void addFunds(final BigDecimal amount) {
        balance = balance.add(amount);
    }

    @Override
    public String toString() {
        return "Account{" +
                "number=" + accountNumber +
                ", balance=" + balance +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountNumber == account.accountNumber &&
                balance.equals(account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, balance);
    }
}
