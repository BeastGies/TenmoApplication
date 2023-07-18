package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransactionDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
@PreAuthorize("isAuthenticated()")
@RestController
public class TenmoController {
    private AccountDao accountDao;
    private TransactionDao transactionDao;
    private UserDao userDao;
    public TenmoController(AccountDao accountDao, TransactionDao transactionDao, UserDao userDao){
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "tenmo/users", method = RequestMethod.GET)
    public List<User> findAll(){
        return userDao.findAll();
    }

    @RequestMapping(path ="tenmo/username",method = RequestMethod.GET )
    public User findByUsername(Principal principal){
        return userDao.findByUsername(principal.getName());
    }

    @RequestMapping(path ="tenmo/username/id", method = RequestMethod.GET)
    public int findIdByUsername(Principal principal){
      return userDao.findIdByUsername(principal.getName());
    }

    @RequestMapping(path = "tenmo/user/make", method = RequestMethod.POST)
    public boolean create(@Valid @RequestBody User newUser) {
     userDao.create(newUser.getPassword(), newUser.getUsername());
     return true;
    }
    @PreAuthorize("hasRole('USER')")
    @RequestMapping(path = "/tenmo/balance", method = RequestMethod.GET)
    public Account getBalanceFromUsername(Principal principal) {
        return accountDao.getBalanceFromUsername(principal.getName());
    }
    @PreAuthorize("hasRole('USER')")
    @RequestMapping(path = "/tenmo/transactions", method = RequestMethod.GET)
    public List<Transaction> transactionList(Principal principal, String username){
       return transactionDao.transactionList(principal.getName());
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping (path = "tenmo/transfer", method = RequestMethod.POST)
    public boolean transfer(@Valid @RequestBody Transaction newTransaction) {
    transactionDao.transfer(newTransaction.getAccount_id(), newTransaction.getTransferAmount(),
            newTransaction.getTransactionDate(), newTransaction.getTransferDestination());
    return true;
}

}
