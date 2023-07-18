package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction {
    private int id;
    private int accountId;
    private BigDecimal transferAmount;
    private Timestamp transactionDate;
    private String  transferDestination;


    public Transaction(int id, int account_id, BigDecimal withdrawAmount, BigDecimal depositAmount, Timestamp transactionDate, String transferDestination) {
        this.id = id;
        this.accountId = accountId;
        this.transferAmount = depositAmount;
        this.transactionDate = transactionDate;
        this.transferDestination = transferDestination;
    }

    public Transaction() {

    }

    public int getId() {
        return id;
    }

    public int getAccount_id() {
        return accountId;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setTransferAmount(BigDecimal depositAmount) {
        this.transferAmount = depositAmount;
    }

    public void setTransactionDate(Timestamp transaction_date) {
        this.transactionDate = transaction_date;
    }

    public String getTransferDestination() {
        return transferDestination;
    }

    public void setTransferDestination(String transferDestination) {
        this.transferDestination = transferDestination;
    }
}


