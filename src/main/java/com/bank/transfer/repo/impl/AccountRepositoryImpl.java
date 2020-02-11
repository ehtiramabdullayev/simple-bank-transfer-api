package com.bank.transfer.repo.impl;

import com.bank.transfer.exception.AccountAlreadyExistsException;
import com.bank.transfer.exception.AccountNotFoundException;
import com.bank.transfer.exception.InsufficientFundsException;
import com.bank.transfer.models.Account;
import com.bank.transfer.repo.AccountRepository;
import com.google.inject.Inject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Ehtiram_Abdullayev on 2/9/2020
 * @project bank-transfer
 */
public class AccountRepositoryImpl implements AccountRepository {
    private Map<Integer, Account> accounts;

    @Inject
    public AccountRepositoryImpl() {
        accounts = new ConcurrentHashMap<>();
    }

    @Override
    public Account getAccountByNumber(int accountNumber) throws AccountNotFoundException {
        if(!accounts.containsKey(accountNumber)){
            throw new AccountNotFoundException("Account is not found");
        }
        return accounts.get(accountNumber);
    }

    @Override
    public boolean saveAccount(int accountNumber, BigDecimal amount) throws AccountAlreadyExistsException,InsufficientFundsException {
        validateId(accountNumber);
        if (amount.compareTo(BigDecimal.ZERO) < 0){
            throw new InsufficientFundsException("You can't create this account with this fund!");
        }
        accounts.put(accountNumber, new Account(accountNumber,amount));
        return true;
    }

    @Override
    public void deleteAllAccounts() {
        accounts.clear();
    }

    @Override
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

    private void validateId(int accountNumber) throws AccountAlreadyExistsException {
        if (accountNumber <= 0 || accounts.containsKey(accountNumber)) {
            throw new AccountAlreadyExistsException("Account already exists or it is not correct!");
        }
    }
}
