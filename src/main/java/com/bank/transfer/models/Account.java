package com.bank.transfer.models;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Ehtiram_Abdullayev on 2/9/2020
 * @project bank-transfer
 */
public class Account {

    private final int number;
    private BigDecimal balance;

    public Account(final int number, final BigDecimal balance) {
        this.number = number;
        this.balance = balance;
    }

    public int getNumber() {
        return number;
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
                "number=" + number +
                ", balance=" + balance +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return number == account.number &&
                balance.equals(account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, balance);
    }
}
