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
import com.google.inject.Singleton;

/**
 * @author Ehtiram_Abdullayev on 2/9/2020
 * @project bank-transfer
 */
public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        /* in here i wanted to guarantee Guice will not create a new instance of dependencies for each injection,
           otherwise we might end up with multiple in memory dbs
        */
        bind(AccountRepository.class).to(AccountRepositoryImpl.class).in(Singleton.class);
        bind(AccountService.class).to(AccountServiceImpl.class).in(Singleton.class);
        bind(TransferService.class).to(TransferServiceImpl.class).in(Singleton.class);
        bind(OperationsController.class).to(OperationsControllerImpl.class).in(Singleton.class);
        bind(JsonParser.class).to(JsonParserImpl.class).in(Singleton.class);
    }
}
