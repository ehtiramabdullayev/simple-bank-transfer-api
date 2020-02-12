package com.bank.transfer.repo;

import com.bank.transfer.exception.AccountAlreadyExistsException;
import com.bank.transfer.exception.AccountNotFoundException;
import com.bank.transfer.exception.InsufficientFundsException;
import com.bank.transfer.models.Account;
import com.bank.transfer.repo.impl.AccountRepositoryImpl;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ehtiram_Abdullayev on 2/12/2020
 * @project bank-transfer
 */
public class AccountRepositoryImplTest {

    @Test(expected = AccountNotFoundException.class)
    public void test_whenTryToGetAccountNumberGenericResponseIsFail() throws AccountNotFoundException {
        new AccountRepositoryImpl().getAccountByNumber(1);
    }

    @Test(expected = AccountAlreadyExistsException.class)
    public void test_whenTryToSaveAccountWhereAccountNumberIsNotPositiveFail() throws AccountAlreadyExistsException, InsufficientFundsException, AccountNotFoundException {
        new AccountRepositoryImpl().saveAccount(-1, BigDecimal.ONE);
    }

    @Test(expected = InsufficientFundsException.class)
    public void test_whenTryToSaveAccountWhereAmountIsNotPositiveFail() throws AccountAlreadyExistsException, InsufficientFundsException, AccountNotFoundException {
        new AccountRepositoryImpl().saveAccount(1, new BigDecimal(-1));
    }

    @Test
    public void test_whenTryToGetAllAccountsSuccessful() throws AccountAlreadyExistsException, InsufficientFundsException, AccountNotFoundException {
        AccountRepository accountRepository = new AccountRepositoryImpl();
        accountRepository.saveAccount(1,BigDecimal.ONE);
        accountRepository.saveAccount(2,BigDecimal.TEN);
        List<Account> list = new ArrayList<>(accountRepository.getAllAccounts());
        Assert.assertEquals(2, list.size());
    }


    @Test
    public void test_whenTryToDeleteAllAccountsSuccessful() throws AccountAlreadyExistsException, InsufficientFundsException, AccountNotFoundException {
        AccountRepository accountRepository = new AccountRepositoryImpl();
        accountRepository.saveAccount(1,BigDecimal.ONE);
        accountRepository.saveAccount(2,BigDecimal.TEN);
        accountRepository.deleteAllAccounts();
        List<Account> list = new ArrayList<>(accountRepository.getAllAccounts());
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void test_whenTryToSaveAccountAllIsValidAndSuccessful() throws AccountAlreadyExistsException, InsufficientFundsException, AccountNotFoundException {
        AccountRepository accountRepository = new AccountRepositoryImpl();
        accountRepository.saveAccount(1,BigDecimal.ONE);
        Assert.assertEquals(new Account(1,BigDecimal.ONE), accountRepository.getAccountByNumber(1));

    }


}
