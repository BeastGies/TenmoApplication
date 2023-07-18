package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getBalanceFromUsername(String username) {
        Account account = null;
        String sql = "SELECT balance, account_id, account.user_id FROM account " +
                "JOIN tenmo_user ON tenmo_user.user_id = account.user_id WHERE username ILIKE ?";
        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql,username);
            if (rowSet.next()) {
                account= mapRowToAccount(rowSet);
            }
        } catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database",e);
        }catch(BadSqlGrammarException e){
            throw new DaoException("Sql syntax error", e);
        }
        return account;
    }

    @Override
    public Account updateByUsername(Account account, String username) {
        Account newAccount = null;
        String sql = "UPDATE account SET " +
                "balance = ? WHERE account_id = ?;";
        BigDecimal balance = account.getBalance();
        try {
            int rowsAffected = jdbcTemplate.update(sql, username);
            if (rowsAffected == 0) {
                throw new DaoException("Error updating balance. Balance not updater");
            } else {
                newAccount = getBalanceFromUsername(username);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("Sql syntax error", e);
        }catch(DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e );
        }
        return newAccount;
    }
    @Override
    public boolean createById (Account account){
        String sql = "INSERT INTO account (user_id, balance) VALUES (?, ?) RETURNING account_id";
        Integer newAccountId;
        try {
            newAccountId = jdbcTemplate.queryForObject(sql, Integer.class, account.getUser_id(),
                    account.getBalance());
        }catch(DataAccessException e){
            return false;
        }
        return true;
        }

    @Override
    public Account getAccountFromId(int id){
        Account account = null;
        String sql = "SELECT balance, user_id FROM account WHERE account_id = ? ;";
        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql,id);//issue
            if (rowSet.next()) {
                account= mapRowToAccount(rowSet);
            }
        } catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database",e);
        }catch(BadSqlGrammarException e){
            throw new DaoException("Sql syntax error", e);
        }
        return account;
    }

    private Account mapRowToAccount(SqlRowSet rs){
        Account account = new Account();
        account.setId(rs.getInt("account_id"));
        account.setUser_id((rs.getInt("user_id")));
        account.setBalance((rs.getBigDecimal("balance")));
        return account;
    }
}
