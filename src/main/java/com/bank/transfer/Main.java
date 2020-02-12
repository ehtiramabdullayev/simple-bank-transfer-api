package com.bank.transfer;

import com.bank.transfer.controller.OperationsController;
import com.bank.transfer.module.ApplicationModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ExceptionHandler;

import static spark.Spark.*;

/**
 * @author Ehtiram_Abdullayev on 2/9/2020
 * @project bank-transfer
 */

public class Main {
    public static final int MAIN_PORT = 5555;
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args) {
        port(MAIN_PORT);
        before((request, response) -> response.type("application/json"));
//        exceptionHandler();
        initRoutes();
    }


    public static void initRoutes() {
        Injector injector = Guice.createInjector(new ApplicationModule());
        OperationsController operationsController = injector.getInstance(OperationsController.class);
        get("/hello", (req, res) -> "Hello World");
        post("/accounts", operationsController::createAccount);
        delete("/accounts", operationsController::deleteAllAccounts);
        get("/accounts", operationsController::getAllAccounts);
        get("/accounts/", operationsController::getAllAccounts);
        get("/accounts/:number", operationsController::getAccountById);
        post("/accounts/transfer", operationsController::makeTransfer);
    }
}
