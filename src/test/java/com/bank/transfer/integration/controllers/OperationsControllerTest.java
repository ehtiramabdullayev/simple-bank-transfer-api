package com.bank.transfer.integration.controllers;

import com.bank.transfer.Main;
import com.bank.transfer.api.model.request.AccountRequest;
import com.bank.transfer.api.model.request.TransferRequest;
import com.bank.transfer.service.AccountService;
import com.bank.transfer.service.JsonParsingService;
import com.bank.transfer.service.impl.JsonParsingServiceImpl;
import com.google.inject.Inject;
import org.junit.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * @author Ehtiram_Abdullayev on 2/12/2020
 * @project bank-transfer
 */
public class OperationsControllerTest {
    JsonParsingService jsonParsingService = new JsonParsingServiceImpl();

    @BeforeClass
    public static void setup() {
        Main.main(new String[1]);
    }


    @Test
    public void test_whenRequestBodyIsEmptyCreateAccountShouldFail() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonParsingService.toJsonPOJO((new Object()))))
                .uri(URI.create("http://localhost:5555/accounts"))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals(400, response.statusCode());
    }

    @Test
    public void test_whenCreateAccountWithInvalidAmountShouldFail() throws IOException, InterruptedException {
        AccountRequest accountRequest = new AccountRequest(1,new BigDecimal(-1));
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonParsingService.toJsonPOJO((new Object()))))
                .uri(URI.create("http://localhost:5555/accounts"))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals(400, response.statusCode());
    }

    @Test
    public void test_whenCreateAlreadyExistingAccountWithShouldFail() throws IOException, InterruptedException {
        AccountRequest accountRequest = new AccountRequest(1,new BigDecimal(1));
        HttpRequest request1 = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonParsingService.toJsonPOJO((new Object()))))
                .uri(URI.create("http://localhost:5555/accounts"))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response1 = HttpClient.newBuilder().build().send(request1, HttpResponse.BodyHandlers.ofString());

        AccountRequest accountRequest2 = new AccountRequest(1,new BigDecimal(3));
        HttpRequest request2 = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonParsingService.toJsonPOJO((new Object()))))
                .uri(URI.create("http://localhost:5555/accounts"))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response2 = HttpClient.newBuilder().build().send(request2, HttpResponse.BodyHandlers.ofString());


        Assert.assertEquals(400, response2.statusCode());
    }

    @Test
    public void test_whenRequestIsValidThenNewAccountShouldBeCreatedSuccessfully() throws IOException, InterruptedException {
        AccountRequest accountRequest = new AccountRequest(6,new BigDecimal(5));
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonParsingService.toJsonPOJO((accountRequest))))
                .uri(URI.create("http://localhost:5555/accounts"))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals(200, response.statusCode());
    }

    @Test
    public void test_whenAccountIdDoesNotExistThenStatusShouldBeAccountNotFoundFail() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:5555/accounts/6"))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals(404, response.statusCode());
    }

    @Test
    public void test_whenAccountIdExistThenAccountShouldBeReturnSuccessful() throws IOException, InterruptedException {
        AccountRequest accountRequest = new AccountRequest(1,new BigDecimal(1));
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonParsingService.toJsonPOJO(accountRequest)))
                .uri(URI.create("http://localhost:5555/accounts"))
                .header("Content-Type", "application/json")
                .build();

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());

        request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:5555/accounts/"+accountRequest.getNumber()))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals(200, response.statusCode());
    }

    @Test
    public void test_whenRequestBodyIsEmptyThenTransferStatusShouldBeBadRequestFail() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonParsingService.toJsonPOJO(new Object())))
                .uri(URI.create("http://localhost:5555/accounts/transfer"))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals(400, response.statusCode());
    }

    @Test
    public void test_whenAccountsDoNotExistThenTransferStatusShouldBeBadRequestFail() throws IOException, InterruptedException {
        TransferRequest transferRequest = new TransferRequest(1,2,new BigDecimal(100));

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonParsingService.toJsonPOJO(transferRequest)))
                .uri(URI.create("http://localhost:5555/accounts/transfer"))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals(400, response.statusCode());
    }
    @Test
    public void test_whenAccountsExistThenTransferStatusShouldBeSuccessful() throws IOException, InterruptedException {
        AccountRequest accountRequest1 = new AccountRequest(1,new BigDecimal(10));
        HttpRequest request1 = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonParsingService.toJsonPOJO(accountRequest1)))
                .uri(URI.create("http://localhost:5555/accounts"))
                .header("Content-Type", "application/json")
                .build();

        HttpClient.newBuilder().build().send(request1, HttpResponse.BodyHandlers.ofString());

        AccountRequest accountRequest2 = new AccountRequest(2,new BigDecimal(20));
        HttpRequest request2 = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonParsingService.toJsonPOJO(accountRequest2)))
                .uri(URI.create("http://localhost:5555/accounts"))
                .header("Content-Type", "application/json")
                .build();

        HttpClient.newBuilder().build().send(request2, HttpResponse.BodyHandlers.ofString());


        TransferRequest transferRequest = new TransferRequest(1,2,new BigDecimal(5));

        HttpRequest request3 = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonParsingService.toJsonPOJO(transferRequest)))
                .uri(URI.create("http://localhost:5555/accounts/transfer"))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = HttpClient.newBuilder().build().send(request3, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals(200, response.statusCode());
    }


    @Test
    public void test_whenAccountsDoesntHaveEnoughFoundThenTransferStatusShouldBeBadRequestFail() throws IOException, InterruptedException {
        AccountRequest accountRequest1 = new AccountRequest(1,new BigDecimal(10));
        HttpRequest request1 = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonParsingService.toJsonPOJO(accountRequest1)))
                .uri(URI.create("http://localhost:5555/accounts"))
                .header("Content-Type", "application/json")
                .build();

        HttpClient.newBuilder().build().send(request1, HttpResponse.BodyHandlers.ofString());

        AccountRequest accountRequest2 = new AccountRequest(2,new BigDecimal(20));
        HttpRequest request2 = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonParsingService.toJsonPOJO(accountRequest2)))
                .uri(URI.create("http://localhost:5555/accounts"))
                .header("Content-Type", "application/json")
                .build();

        HttpClient.newBuilder().build().send(request2, HttpResponse.BodyHandlers.ofString());


        TransferRequest transferRequest = new TransferRequest(1,2,new BigDecimal(15));

        HttpRequest request3 = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonParsingService.toJsonPOJO(transferRequest)))
                .uri(URI.create("http://localhost:5555/accounts/transfer"))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = HttpClient.newBuilder().build().send(request3, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals(400, response.statusCode());
    }
}
