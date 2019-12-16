package com.revolut.service;

import com.revolut.datastore.AccountDataStore;
import com.revolut.dto.TransferRequestPOJO;
import com.revolut.dto.TransferResponsePOJO;
import com.revolut.model.AccountDetails;
import com.revolut.model.TransactionLog;

public class AccountService {

    public AccountDataStore dataStore = new AccountDataStore();

    public TransferResponsePOJO transferMoney(TransferRequestPOJO transferRequestPOJO) {
            return doTransfer(transferRequestPOJO);
    }

    private TransferResponsePOJO doTransfer(TransferRequestPOJO transferRequestPOJO) {

        //check if amount is valid amount
        if(transferRequestPOJO.getAmount() < 0)
            return new TransferResponsePOJO("FAILED", "Invalid amount");

        //check if source and target account details are valid
        if(AccountDataStore.accounts.get(transferRequestPOJO.getSourceAccount()) == null
                || AccountDataStore.accounts.get(transferRequestPOJO.getTargetAccount()) == null) {
            return new TransferResponsePOJO("FAILED", "Incorrect account details found for source or target account");
        }

        AccountDetails sourceAccountDetails = AccountDataStore.accounts.get(transferRequestPOJO.getSourceAccount());
        AccountDetails targetAccountDetails = AccountDataStore.accounts.get(transferRequestPOJO.getTargetAccount());

        //check if account source has sufficient amount to complete the transaction
        if(sourceAccountDetails.getBalance() < transferRequestPOJO.getAmount()) {
            return new TransferResponsePOJO("FAILED", "Insufficient account balance");
        }


        //update target account
        dataStore.updateAccountBalance(targetAccountDetails.getAccountNumber(), targetAccountDetails.getBalance() + transferRequestPOJO.getAmount());

        //update source account
        dataStore.updateAccountBalance(sourceAccountDetails.getAccountNumber(), sourceAccountDetails.getBalance() - transferRequestPOJO.getAmount());

        //log transaction
        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setDescription(transferRequestPOJO.getDescription());
        transactionLog.setAmount(transferRequestPOJO.getAmount());
        transactionLog.setSourceAccount(transferRequestPOJO.getSourceAccount());
        transactionLog.setTargetAccount(transferRequestPOJO.getTargetAccount());

        AccountDataStore.transactionLog.add(transactionLog);

        return new TransferResponsePOJO("SUCCESS", "Transfer successful");
    }

    public TransferResponsePOJO getAccountBalance(String accountNumber) {
        if(AccountDataStore.accounts.get(accountNumber) == null) {
            return new TransferResponsePOJO("FAILED", "The account doesn't exist");
        }

        AccountDetails accountDetails = AccountDataStore.accounts.get(accountNumber);

        return new TransferResponsePOJO("SUCCESS", "Succesfully retrieved account balance", accountDetails.getBalance());
    }
}
