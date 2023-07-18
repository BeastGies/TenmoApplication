package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {
    private int id;
    private int user_id;
    public static BigDecimal balance;

    public Account(int id, int user_id, BigDecimal balance) {
        this.id = id;
        this.user_id = user_id;
        this.balance = new BigDecimal("1000");
    }

    public Account() {

    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;

    }

}
