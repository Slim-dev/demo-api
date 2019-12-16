package com.revolut.model;

public class AccountDetails {

    private int id;
    private String name;
    private String accountNumber;
    private String currency;
    private double balance;

    public AccountDetails() {}

    public AccountDetails(int id, String name, String accountNumber, String currency, double balance) {
        this.id = id;
        this.name = name;
        this.accountNumber = accountNumber;
        this.currency = currency;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
