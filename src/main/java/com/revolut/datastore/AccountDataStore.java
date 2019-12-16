package com.revolut.datastore;

import com.revolut.model.AccountDetails;
import com.revolut.model.TransactionLog;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AccountDataStore implements IdataStore<AccountDetails> {

    public static Map<String, AccountDetails> accounts = new HashMap<>();
    public static List<TransactionLog> transactionLog = new ArrayList<>();
    private static final AtomicInteger count = new AtomicInteger(0);

    public AccountDataStore() {
        init();
    }

    private void init() {
        //Initialize two accounts
        AccountDetails accountDetails1 = new AccountDetails(count.incrementAndGet(), "Kingsley", "1234-567-45", "NGN", 600.0);
        AccountDetails accountDetails2 = new AccountDetails(count.incrementAndGet(), "Shade", "456-346-78", "NGN", 0.0);

        add(accountDetails1);
        add(accountDetails2);
    }

    @Override
    public AccountDetails findById(String id) {
        return null;
    }

    @Override
    public List<AccountDetails> findAll() {
        return new ArrayList<>(accounts.values());
    }

    @Override
    public void add(Object data) {
        AccountDetails accountDetails = (AccountDetails) data;
        accounts.put(accountDetails.getAccountNumber(), accountDetails);
    }

    public void updateAccountBalance(String accountNumber, double newBalance) {
        AccountDetails accountDetails = accounts.get(accountNumber);
        accountDetails.setBalance(newBalance);
        accounts.put(accountNumber, accountDetails);
    }
}
