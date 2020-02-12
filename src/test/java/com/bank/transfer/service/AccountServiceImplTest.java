package com.bank.transfer.service;

import com.bank.transfer.api.model.response.GenericResponse;
import com.bank.transfer.exception.AccountAlreadyExistsException;
import com.bank.transfer.exception.AccountNotFoundException;
import com.bank.transfer.exception.InsufficientFundsException;
import com.bank.transfer.models.Account;
import com.bank.transfer.repo.AccountRepository;
import com.bank.transfer.service.impl.AccountServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ehtiram_Abdullayev on 2/12/2020
 * @project bank-transfer
 */
public class AccountServiceImplTest {
    private AccountService accountService;
    private AccountRepository repositoryMock;

    public AccountServiceImplTest() {
        repositoryMock = Mockito.mock(AccountRepository.class);
        accountService = new AccountServiceImpl(repositoryMock);
    }

    @Test
    public void test_whenTryToSaveWrongAccountNumberGenericResponseIsFail() throws AccountAlreadyExistsException, InsufficientFundsException {
        Mockito.when(repositoryMock.saveAccount(-1, BigDecimal.ONE)).thenThrow(AccountAlreadyExistsException.class);
        GenericResponse genericResponse = accountService.saveAccount(-1, BigDecimal.ONE);
        Assert.assertEquals(400, genericResponse.getResponse().getStatus());
    }

    @Test
    public void test_whenTryToSaveWrongAmountNumberGenericResponseIsFail() throws AccountAlreadyExistsException, InsufficientFundsException {
        Mockito.when(repositoryMock.saveAccount(1, new BigDecimal(-1))).thenThrow(InsufficientFundsException.class);
        GenericResponse genericResponse = accountService.saveAccount(1, new BigDecimal(-1));
        Assert.assertEquals(400, genericResponse.getResponse().getStatus());
    }

    @Test
    public void test_whenTryToSaveCorrectAccountStatusIsSuccessFul() throws AccountAlreadyExistsException, InsufficientFundsException {
        Mockito.when(repositoryMock.saveAccount(1, BigDecimal.ONE)).thenReturn(true);
        GenericResponse genericResponse = accountService.saveAccount(1, BigDecimal.ONE);
        Assert.assertEquals(200, genericResponse.getResponse().getStatus());
    }

    @Test
    public void test_whenTryToGetAccountByNumberThatDoesNotExistFail() throws AccountNotFoundException {
        Mockito.when(repositoryMock.getAccountByNumber(1)).thenThrow(AccountNotFoundException.class);
        GenericResponse genericResponse = accountService.getAccountByAccountNumber(1);
        Assert.assertEquals(404, genericResponse.getResponse().getStatus());
    }

    @Test
    public void test_whenTryToGetAccountByNumberThatExistsSuccessful() throws AccountNotFoundException {
        Mockito.when(repositoryMock.getAccountByNumber(1)).thenReturn(new Account(1, BigDecimal.ONE));
        GenericResponse genericResponse = accountService.getAccountByAccountNumber(1);
        Assert.assertEquals(200, genericResponse.getResponse().getStatus());
    }

    @Test
    public void test_whenTryToGetAllAccountsSuccessful() throws AccountNotFoundException {
        Mockito.when(repositoryMock.getAllAccounts()).thenReturn(Arrays.asList(new Account(1, BigDecimal.ONE),
                                                                               new Account(2, BigDecimal.TEN)));
        GenericResponse<List<Account>>  genericResponse = accountService.getAllAccounts();
        Assert.assertEquals(2, ((List<Account>) new ArrayList<>(genericResponse.getT())).size());
    }

    @Test
    public void test_whenTryToDeleteEveryThingIsSuccessful() throws AccountNotFoundException {
        Mockito.doNothing().when((repositoryMock)).deleteAllAccounts();
        GenericResponse genericResponse = accountService.deleteAllAccounts();
        Assert.assertEquals(200, genericResponse.getResponse().getStatus());
    }

}
