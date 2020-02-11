package com.bank.transfer.service.impl;

import com.bank.transfer.exception.AccountNotFoundException;
import com.bank.transfer.exception.InsufficientFundsException;
import com.bank.transfer.models.Account;
import com.bank.transfer.repo.AccountRepository;
import com.bank.transfer.service.TransferService;
import com.google.inject.Inject;

import java.math.BigDecimal;

/**
 * @author Ehtiram_Abdullayev on 2/10/2020
 * @project bank-transfer
 */
public class TransferServiceImpl implements TransferService {
    private final AccountRepository accountRepository;

    @Inject
    public TransferServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void doTransfer(int fromAccountNumber, int toAccountNumber, BigDecimal transferAmount) throws AccountNotFoundException, InsufficientFundsException {
        Account fromAccount = accountRepository.getAccountByNumber(fromAccountNumber);
        Account toAccount = accountRepository.getAccountByNumber(toAccountNumber);

        if (transferAmount.compareTo(fromAccount.getBalance()) > 0)
            throw new InsufficientFundsException("Money in your account is not enough for this transaction!");
        transfer(fromAccount, toAccount, transferAmount);

    }


    private void transfer(Account fromAccount, Account toAccount, BigDecimal transferAmount) {
        int fromHash = System.identityHashCode(fromAccount);
        int toHash = System.identityHashCode(toAccount);

        Integer lowId = (fromHash < toHash ? fromHash : toHash);
        Integer highId = (fromHash < toHash ? toHash : fromHash);
        synchronized (lowId) {
            synchronized (highId) {
                fromAccount.withdraw(transferAmount);
                toAccount.addFunds(transferAmount);
            }
        }
    }

}
