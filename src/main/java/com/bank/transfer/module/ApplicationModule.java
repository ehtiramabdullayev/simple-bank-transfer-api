package com.bank.transfer.module;

import com.bank.transfer.controller.OperationsController;
import com.bank.transfer.controller.impl.OperationsControllerImpl;
import com.bank.transfer.repo.AccountRepository;
import com.bank.transfer.repo.impl.AccountRepositoryImpl;
import com.bank.transfer.service.AccountService;
import com.bank.transfer.service.JsonParser;
import com.bank.transfer.service.TransferService;
import com.bank.transfer.service.impl.AccountServiceImpl;
import com.bank.transfer.service.impl.JsonParserImpl;
import com.bank.transfer.service.impl.TransferServiceImpl;
import com.google.inject.AbstractModule;

/**
 * @author Ehtiram_Abdullayev on 2/9/2020
 * @project bank-transfer
 */
public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(AccountRepository.class).to(AccountRepositoryImpl.class);
        bind(AccountService.class).to(AccountServiceImpl.class);
        bind(TransferService.class).to(TransferServiceImpl.class);
        bind(OperationsController.class).to(OperationsControllerImpl.class);
        bind(JsonParser.class).to(JsonParserImpl.class);
    }
}
