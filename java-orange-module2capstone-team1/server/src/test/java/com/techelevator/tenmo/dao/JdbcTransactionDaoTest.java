package com.techelevator.tenmo.dao;

import com.techelevator.dao.BaseDaoTests;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.*;

public class JdbcTransactionDaoTest extends BaseDaoTests {
    private static final Transaction TRANSACTION_1 =
            new Transaction (3001,2001,new BigDecimal("100"),new BigDecimal("100"),new Timestamp(2020-10-10),"coolkid1");
    private static final Transaction TRANSACTION_2 =
            new Transaction (3002,2002,new BigDecimal("200"),new BigDecimal("200"),new Timestamp(2020-11-11),"coolkid2");
    private static final Transaction TRANSACTION_3 =
            new Transaction (3003,2003,new BigDecimal("300"),new BigDecimal("300"),new Timestamp(2020-12-12),"coolkid3");
    private Transaction testTransaction;

    private JdbcTransactionDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransactionDao (jdbcTemplate);
        testTransaction= new Transaction(0,0,new BigDecimal("0"),new BigDecimal("0"),new Timestamp(0-0-0),"test");
    }


    @Test
    public void updated_transfer_has_expected_values_when_retrieved() {


        Transaction transactionToUpdate = sut.getTransactionById();

        transactionToUpdate.setTransactionDate(new Timestamp(1234-01-01));
        transactionToUpdate.setId(99);
        transactionToUpdate.setAccountId(99);
        transactionToUpdate.setTransferAmount(new BigDecimal("456"));

        sut.transfer(transactionToUpdate);

        Account retrievedTransaction = sut.getTransactionById(3001);
        assert(transactionToUpdate, retrievedTransaction);
    }

    @Test
    public void transactionList_returns_all_values_for_transaction() {
        List<Transaction> transactions = sut.transactionList();

        Assert.assertEquals(0, transactions.size());

        assertTransactionsMatch(TRANSACTION_1, transactions.get(0));
        assertTransactionsMatch(TRANSACTION_2, transactions.get(1));
        assertTransactionsMatch(TRANSACTION_3, transactions.get(2));

    }

    private void assertTransactionsMatch(Transaction expected, Transaction actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getAccount_id(), actual.getAccount_id());
        Assert.assertEquals(expected.getTransferAmount(), actual.getTransferAmount());
        Assert.assertEquals(expected.getTransactionDate(), actual.getTransactionDate());
    }
}