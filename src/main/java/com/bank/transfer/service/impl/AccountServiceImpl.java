package com.bank.transfer.service.impl;

import com.bank.transfer.api.model.response.GenericResponse;
import com.bank.transfer.api.model.response.Response;
import com.bank.transfer.exception.AccountAlreadyExistsException;
import com.bank.transfer.exception.AccountNotFoundException;
import com.bank.transfer.exception.InsufficientFundsException;
import com.bank.transfer.models.Account;
import com.bank.transfer.repo.AccountRepository;
import com.bank.transfer.service.AccountService;
import com.google.inject.Inject;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Ehtiram_Abdullayev on 2/9/2020
 * @project bank-transfer
 */
public class AccountServiceImpl implements AccountService<Account> {
    private final AccountRepository accountRepository;

    @Inject
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public GenericResponse<Account> getAccountByAccountNumber(int accountNumber) {
        GenericResponse<Account> genericResponse;
        try {
            Account account = accountRepository.getAccountByNumber(accountNumber);
            genericResponse = new GenericResponse(account);
        } catch (AccountNotFoundException e) {
            genericResponse = new GenericResponse(new Response(404, e.getMessage()));
        }
        return genericResponse;
    }

    @Override
    public GenericResponse<Account> saveAccount(int accountNumber, BigDecimal amount) {
        GenericResponse<Account> genericResponse;
        try {
            accountRepository.saveAccount(accountNumber, amount);
            genericResponse = new GenericResponse<>(new Response(200, "SUCCESS"));
        } catch (AccountAlreadyExistsException | InsufficientFundsException e) {
            genericResponse = new GenericResponse<>(new Response(400, e.getMessage()));
        }
        return genericResponse;
    }

    @Override
    public GenericResponse<List<Account>> getAllAccounts() {
        List<Account> accounts = accountRepository.getAllAccounts();
        return new GenericResponse<>(accounts);
    }

    @Override
    public GenericResponse<Account> deleteAllAccounts() {
        accountRepository.deleteAllAccounts();
        return new GenericResponse<>(new Response(200, "SUCCESS"));
    }
}
