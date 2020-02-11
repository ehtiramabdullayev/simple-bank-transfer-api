package com.bank.transfer.controller;

import spark.Request;
import spark.Response;

/**
 * @author Ehtiram_Abdullayev on 2/11/2020
 * @project bank-transfer
 */
public interface OperationsController {
    String createAccount(Request request, Response response);

    String getAllAccounts(Request request, Response response);

    String getAccountById(Request request, Response response);

    String deleteAllAccounts(Request request, Response response);

    String makeTransfer(Request request, Response response) throws Exception;
//
}
