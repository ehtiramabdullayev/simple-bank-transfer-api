package com.bank.transfer.controller.impl;

import com.bank.transfer.api.model.request.AccountRequest;
import com.bank.transfer.api.model.response.GenericResponse;
import com.bank.transfer.controller.OperationsController;
import com.bank.transfer.exception.AccountNotFoundException;
import com.bank.transfer.exception.InsufficientFundsException;
import com.bank.transfer.models.Account;
import com.bank.transfer.service.AccountService;
import com.bank.transfer.service.JsonParser;
import com.bank.transfer.service.TransferService;
import com.google.gson.Gson;
import com.google.inject.Inject;
import spark.Request;
import spark.Response;

import java.util.List;

/**
 * @author Ehtiram_Abdullayev on 2/11/2020
 * @project bank-transfer
 */
public class OperationsControllerImpl implements OperationsController {

    private final AccountService<Account> accountService;
    private final TransferService transferService;
    private final JsonParser jsonParser;

    @Inject
    public OperationsControllerImpl(AccountService accountService, TransferService transferService, JsonParser jsonParser) {
        this.accountService = accountService;
        this.transferService = transferService;
        this.jsonParser = jsonParser;
    }

    @Override
    public String createAccount(Request request, Response response) {
        AccountRequest accountRequest = jsonParser.fromJSonToPOJO(request.body(), AccountRequest.class);
        GenericResponse<Account> genericResponse = accountService.saveAccount(accountRequest.getNumber(), accountRequest.getBalance());
        // setting here the response status code
        response.status(genericResponse.getResponse().getStatus());
        return jsonParser.toJsonPOJO(genericResponse);
    }

    @Override
    public String getAllAccounts(Request request, Response response) {
        GenericResponse<List<Account>> genericResponse = accountService.getAllAccounts();
        // setting here the response status code
        response.status(genericResponse.getResponse().getStatus());
        return jsonParser.toJsonPOJO(genericResponse);
    }

    @Override
    public String getAccountById(Request request, Response response) {
        int accountNumber = Integer.parseInt(request.params("number"));
        GenericResponse<Account> genericResponse = accountService.getAccountByAccountNumber(accountNumber);
        response.status(genericResponse.getResponse().getStatus());
        return jsonParser.toJsonPOJO(genericResponse);
    }

    @Override
    public String deleteAllAccounts(Request request, Response response) {
        GenericResponse<Account> genericResponse = accountService.deleteAllAccounts();
        response.status(genericResponse.getResponse().getStatus());
        return jsonParser.toJsonPOJO(genericResponse);
    }

    @Override
    public String makeTransfer(Request request, Response response) throws Exception {
        return null;
    }
}
