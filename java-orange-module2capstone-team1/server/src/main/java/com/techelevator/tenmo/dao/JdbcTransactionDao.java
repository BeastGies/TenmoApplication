package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcTransactionDao implements TransactionDao{
    private JdbcTemplate jdbcTemplate;
    private JdbcUserDao jdbcUserDao;
    private JdbcAccountDao jdbcAccountDao;
    public JdbcTransactionDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcUserDao = jdbcUserDao;
        this.jdbcAccountDao = jdbcAccountDao;
    }

    @Override
    public boolean transfer(int account_id, BigDecimal transferAmount, Timestamp transactionDate, String transferDestination) {
       String sql = "INSERT INTO money_transaction (account_id, transfer_amount, transaction_date, transferDestination) VALUES " +
               "(?, ? , ?, ?) " +
               "RETURNING transaction_id; ";
       Integer newTransactionId;
       try{
           newTransactionId= jdbcTemplate.queryForObject(sql,Integer.class, account_id, transferAmount,
                   transactionDate, transferDestination);
       }catch (DataAccessException e){
           if(transferAmount.compareTo(BigDecimal.ZERO) <= 0){
               return false;
           }
           if(transferAmount.compareTo(Account.balance) > 0){
               return false;
           }
           if(transferDestination.equals(User.username)){
               return false;
           }
           return false;
       }
        System.out.println("Approved");
       return true;
    }


    @Override
    public List<Transaction> transactionList(String username) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT transaction_id, money_transaction.account_id, transfer_amount, transaction_date "+
                "FROM money_transaction JOIN account ON money_transaction.transaction_id = account.account_id "+
                "JOIN tenmo_user ON account.user_id = tenmo_user.user_id" +
                "WHERE username ILIKE ?; ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,username);
            while (results.next()) {
                Transaction transaction = mapRowToTransaction(results);
                transactions.add(transaction);
            }
        return transactions;
    }


    public Transaction mapRowToTransaction(SqlRowSet rs){
        Transaction transaction = new Transaction();
        transaction.setId(rs.getInt("transaction_id"));
        transaction.setAccountId(rs.getInt("account_id"));
        transaction.setTransferAmount(rs.getBigDecimal("transfer_amount"));
        transaction.setTransactionDate(rs.getTimestamp("transaction_date"));
        transaction.setTransferDestination(rs.getString("transfer_destination"));
        return transaction;
    }
}
