package com.bank.transfer.repo;

import com.bank.transfer.exception.AccountAlreadyExistsException;
import com.bank.transfer.exception.AccountNotFoundException;
import com.bank.transfer.exception.InsufficientFundsException;
import com.bank.transfer.models.Account;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Ehtiram_Abdullayev on 2/9/2020
 * @project bank-transfer
 */
public interface AccountRepository {
    Account getAccountByNumber(int accountNumber) throws AccountNotFoundException;
    boolean saveAccount(int accountNumber, BigDecimal amount) throws AccountAlreadyExistsException, InsufficientFundsException;
    List<Account> getAllAccounts();
    void deleteAllAccounts();


}
