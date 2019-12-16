package com.revolut.dto;

public class TransferResponsePOJO {

    private String status;
    private String message;
    private Double balance;

    public TransferResponsePOJO() {}

    public TransferResponsePOJO(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public TransferResponsePOJO(String status, String message, Double balance) {
        this.status = status;
        this.message = message;
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
