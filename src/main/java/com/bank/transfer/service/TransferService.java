package com.bank.transfer.service;

import com.bank.transfer.exception.AccountNotFoundException;
import com.bank.transfer.exception.InsufficientFundsException;

import java.math.BigDecimal;

/**
 * @author Ehtiram_Abdullayev on 2/9/2020
 * @project bank-transfer
 */
public interface TransferService {
    void doTransfer(int fromAccount, int toAccount, BigDecimal transferAmount) throws AccountNotFoundException, InsufficientFundsException;
}
