package com.bank.transfer.service;

import com.bank.transfer.api.model.response.GenericResponse;

import java.math.BigDecimal;

/**
 * @author Ehtiram_Abdullayev on 2/9/2020
 * @project bank-transfer
 */
public interface TransferService<T> {
    GenericResponse<T> doTransfer(int fromAccount, int toAccount, BigDecimal transferAmount);
}
