package com.bank.transfer.service;

import com.bank.transfer.exception.AccountNotFoundException;
import com.bank.transfer.models.Account;
import com.bank.transfer.repo.AccountRepository;
import com.bank.transfer.service.impl.TransferServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.math.BigDecimal;
import java.util.Random;

/**
 * @author Ehtiram_Abdullayev on 2/12/2020
 * @project bank-transfer
 */
public class TransferServiceConcurrencyTest {
    private TransferService service;
    private AccountRepository repositoryMock;

    private Account accountOne;
    private Account accountTwo;

    private BigDecimal transferAmount = BigDecimal.ONE;
    private BigDecimal sumOfAccountsBalanceBeforeOperations;

    public TransferServiceConcurrencyTest() throws AccountNotFoundException {
        repositoryMock = Mockito.mock(AccountRepository.class);
        service = new TransferServiceImpl(repositoryMock);

        accountOne = new Account(1, new BigDecimal(100_000));
        accountTwo = new Account(2, new BigDecimal(100_000));

        Mockito.when(repositoryMock.getAccountByNumber(1)).thenReturn(accountOne);
        Mockito.when(repositoryMock.getAccountByNumber(2)).thenReturn(accountTwo);

        sumOfAccountsBalanceBeforeOperations = accountOne.getBalance().add(accountTwo.getBalance());
    }



    @Test
    public void test_InMultiOperationsDeadlockShouldNotHappen_success() throws Exception {
        Thread[] operations = new Thread[100];

        for (int i = 0; i < operations.length; i++) {
            operations[i] = new Thread(() -> doTenThousandTransfers());
            operations[i].start();
        }

        Thread.sleep(5 * 1000);

        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        long ids[] = bean.findMonitorDeadlockedThreads();

        if (ids != null) {
            Assert.fail("Your method is not protected from deadlock");
        }
    }


    @Test
    public void test_whenMultiTransfersSumShouldBeSame_success() throws InterruptedException {
        Thread[] operations = new Thread[100];

        for (int i = 0; i < operations.length; i++) {
            operations[i] = new Thread(() -> doTenThousandTransfers());
            operations[i].start();
        }

        for (int i = 0; i < operations.length; i++) {
            operations[i].join();
        }

        BigDecimal sumOfBalancesAfterOperations = accountOne.getBalance().add(accountTwo.getBalance());
        Assert.assertEquals(sumOfBalancesAfterOperations, sumOfAccountsBalanceBeforeOperations);
    }

    private void doTenThousandTransfers() {
        Random random = new Random();
        for (int j = 0; j < 10_000; j++) {
            int randomIndex = random.nextInt(2);

            Integer from = randomIndex == 0 ? accountOne.getAccountNumber() : accountTwo.getAccountNumber();
            Integer to = randomIndex == 0 ? accountTwo.getAccountNumber() : accountOne.getAccountNumber();

            service.doTransfer(from, to, transferAmount);

        }
    }
}
