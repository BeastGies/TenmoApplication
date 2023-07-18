package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;
import java.security.Principal;

public interface AccountDao {


    Account getBalanceFromUsername(String username);

    Account updateByUsername(Account account, String username);

    boolean createById(Account account);

    Account getAccountFromId(int id);
}
