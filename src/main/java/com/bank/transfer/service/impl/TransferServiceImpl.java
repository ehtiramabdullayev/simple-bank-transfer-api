package com.bank.transfer.service.impl;

import com.bank.transfer.api.model.response.GenericResponse;
import com.bank.transfer.api.model.response.Response;
import com.bank.transfer.exception.AccountNotFoundException;
import com.bank.transfer.exception.InsufficientFundsException;
import com.bank.transfer.models.Account;
import com.bank.transfer.repo.AccountRepository;
import com.bank.transfer.service.TransferService;
import com.google.inject.Inject;

import java.math.BigDecimal;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Ehtiram_Abdullayev on 2/10/2020
 * @project bank-transfer
 */
public class TransferServiceImpl implements TransferService {
    private final AccountRepository accountRepository;
    ReentrantLock lock = new ReentrantLock();
    Executor executor = Executors.newSingleThreadExecutor();

    @Inject
    public TransferServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public GenericResponse doTransfer(int fromAccountNumber, int toAccountNumber, BigDecimal transferAmount) {
        GenericResponse<Account> genericResponse;
        try {
            Account fromAccount = accountRepository.getAccountByNumber(fromAccountNumber);
            Account toAccount = accountRepository.getAccountByNumber(toAccountNumber);

            if (transferAmount.compareTo(fromAccount.getBalance()) > 0)
                throw new InsufficientFundsException("Money in your account is not enough for this transaction!");

            Runnable runnable = transfer(fromAccount, toAccount, transferAmount);
            executor.execute(runnable);

            genericResponse = new GenericResponse<>(new Response(200, "SUCCESS"));

        } catch (AccountNotFoundException | InsufficientFundsException e) {
            genericResponse = new GenericResponse<>(new Response(400, e.getMessage()));
        }
        return genericResponse;
    }


    private Runnable transfer(Account fromAccount, Account toAccount, BigDecimal transferAmount) {
        return () -> {
            try {
                lock.lock();
                fromAccount.withdraw(transferAmount);
                toAccount.addFunds(transferAmount);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        };
    }


}
