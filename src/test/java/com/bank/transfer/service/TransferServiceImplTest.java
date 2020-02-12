package com.bank.transfer.service;

import com.bank.transfer.api.model.response.GenericResponse;
import com.bank.transfer.exception.AccountNotFoundException;
import com.bank.transfer.exception.InsufficientFundsException;
import com.bank.transfer.models.Account;
import com.bank.transfer.repo.AccountRepository;
import com.bank.transfer.service.impl.TransferServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

/**
 * @author Ehtiram_Abdullayev on 2/12/2020
 * @project bank-transfer
 */
public class TransferServiceImplTest {
    private TransferService transferService;
    private AccountRepository repositoryMock;

    public TransferServiceImplTest() {
        repositoryMock = Mockito.mock(AccountRepository.class);
        transferService = new TransferServiceImpl(repositoryMock);
    }


    @Test
    public void test_whenTryToTransferFromAccountWhichIsNotValidFail() throws AccountNotFoundException, InsufficientFundsException {
        Mockito.when(repositoryMock.getAccountByNumber(-1)).thenThrow(AccountNotFoundException.class);
        GenericResponse genericResponse = transferService.doTransfer(-1,2, BigDecimal.ONE);
        Assert.assertEquals(400, genericResponse.getResponse().getStatus());
    }
    @Test
    public void test_whenTryToTransferFromAccountWhenDoesNotHaveEnoughFondFail() throws AccountNotFoundException, InsufficientFundsException {
        Mockito.when(repositoryMock.getAccountByNumber(1)).thenReturn(new Account(1,BigDecimal.ONE));
        Mockito.when(repositoryMock.getAccountByNumber(2)).thenReturn(new Account(2,BigDecimal.ZERO));
        GenericResponse genericResponse = transferService.doTransfer(1,2, BigDecimal.TEN);
        Assert.assertEquals(400, genericResponse.getResponse().getStatus());
    }

    @Test
    public void test_whenTryToTransferFromAccountWhenDestinationAccountDoesNotExistsFail() throws AccountNotFoundException, InsufficientFundsException {
        Mockito.when(repositoryMock.getAccountByNumber(1)).thenReturn(new Account(1,BigDecimal.ONE));
        GenericResponse genericResponse = transferService.doTransfer(1,2, BigDecimal.TEN);
        Assert.assertEquals(400, genericResponse.getResponse().getStatus());
    }

    @Test
    public void test_whenTryToTransferFromAccountWhenHaveHaveEnoughFondSuccessful() throws AccountNotFoundException, InsufficientFundsException {
        Mockito.when(repositoryMock.getAccountByNumber(1)).thenReturn(new Account(1,BigDecimal.TEN));
        Mockito.when(repositoryMock.getAccountByNumber(2)).thenReturn(new Account(2,BigDecimal.ZERO));
        GenericResponse genericResponse = transferService.doTransfer(1,2, BigDecimal.ONE);
        Assert.assertEquals(200, genericResponse.getResponse().getStatus());
    }
}
