package com.bank.transfer.service;

import com.bank.transfer.api.model.response.GenericResponse;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Ehtiram_Abdullayev on 2/9/2020
 * @project bank-transfer
 */
public interface AccountService<T> {
    GenericResponse<T> getAccountByAccountNumber(int accountNumber);

    GenericResponse<T> saveAccount(int accountNumber, BigDecimal amount);

    GenericResponse<List<T>> getAllAccounts();

    GenericResponse<T> deleteAllAccounts();
}
