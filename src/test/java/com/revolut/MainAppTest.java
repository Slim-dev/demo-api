package com.revolut;

import com.revolut.dto.TransferRequestPOJO;
import com.revolut.dto.TransferResponsePOJO;
import com.revolut.service.AccountService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

public class MainAppTest {


    public static AccountService accountService;
    public static TransferRequestPOJO requestObj;

    @BeforeClass
    public static void init() {
        accountService = new AccountService();

        /**
         * For the purpose of this demo test, two accounts have been created on initialization. The details can be found in the AccountDataStore class
         */

        //Create request object
        requestObj = new TransferRequestPOJO();
        requestObj.setSourceAccount("1234-567-45");
        requestObj.setTargetAccount("456-346-78");
        requestObj.setAmount(500);
        requestObj.setCurrency("NGN");
        requestObj.setDescription("");

    }

    @Test
    public void shouldTransferMoneyBetweenTwoAccounts() {

        //get account balance of source account before the transaction
        TransferResponsePOJO prevSourceAccountBalObj = accountService.getAccountBalance("1234-567-45");

        //get account balance of target account before the transaction
        TransferResponsePOJO prevTargetAccountBalObj = accountService.getAccountBalance("456-346-78");

        TransferResponsePOJO responsePOJO = accountService.transferMoney(requestObj);
        Assert.assertEquals(responsePOJO.getStatus(), "SUCCESS");

        //get account balance of source account after the transaction
        TransferResponsePOJO currentSourceAccountBalObj = accountService.getAccountBalance("1234-567-45");

        //get account balance of target account after the transaction
        TransferResponsePOJO currentTargetAccountBalObj = accountService.getAccountBalance("456-346-78");

        //check that account balance was deducted from source account
        Assert.assertEquals(Double.valueOf(prevSourceAccountBalObj.getBalance() - requestObj.getAmount()), currentSourceAccountBalObj.getBalance());

        //check that target account balance was credited
        Assert.assertEquals(Double.valueOf(prevTargetAccountBalObj.getBalance() + requestObj.getAmount()), currentTargetAccountBalObj.getBalance());

    }

    @Test
    public void shouldFailToTransferMoneyBetweenTwoAccountsBecauseInsufficientFundsInSourceAccount() {
        requestObj.setAmount(1000);

        TransferResponsePOJO responsePOJO = accountService.transferMoney(requestObj);
        Assert.assertEquals(responsePOJO.getStatus(), "FAILED");
        Assert.assertEquals(responsePOJO.getMessage(), "Insufficient account balance");
    }

    @Test
    public void shouldFailToTransferMoneyBetweenAccountNumberIsInCorrect() {
        requestObj.setSourceAccount("123111111111");

        TransferResponsePOJO responsePOJO = accountService.transferMoney(requestObj);
        Assert.assertEquals(responsePOJO.getStatus(), "FAILED");
        Assert.assertEquals(responsePOJO.getMessage(), "Incorrect account details found for source or target account");
    }

}
