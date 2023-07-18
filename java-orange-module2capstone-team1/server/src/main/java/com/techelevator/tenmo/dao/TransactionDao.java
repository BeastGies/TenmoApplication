package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public interface TransactionDao {

 boolean transfer(int account_id, BigDecimal transferAmount, Timestamp transactionDate, String transferDestination);

    List<Transaction> transactionList(String username);


}
