package com.revolut;

import com.google.gson.Gson;
import com.revolut.dto.TransferRequestPOJO;
import com.revolut.dto.TransferResponsePOJO;
import com.revolut.service.AccountService;

import static spark.Spark.*;

public class Main {

    private static AccountService accountService = new AccountService();

    public static void main(String[] args) {
        port(8080);

        post("/transfer", (request, response) -> {

            TransferRequestPOJO requestPOJO = new Gson().fromJson(request.body(), TransferRequestPOJO.class);
            TransferResponsePOJO responsePOJO = accountService.transferMoney(requestPOJO);

            response.body(new Gson().toJson(responsePOJO));
            response.type("application/json");
            return response.body();
        });

        get("/balance", (request, response) -> {

            TransferResponsePOJO responsePOJO = accountService.getAccountBalance(request.queryParams("accountNo"));

            response.body(new Gson().toJson(responsePOJO));
            response.type("application/json");
            return response.body();
        });
    }

}
