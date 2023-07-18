package com.techelevator.tenmo.dao;

import com.techelevator.dao.BaseDaoTests;
import com.techelevator.tenmo.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class JdbcAccountDaoTest extends BaseDaoTests {
    private static final Account ACCOUNT_1 =
            new Account (2001, 1001, new BigDecimal("1000"));
    private static final Account ACCOUNT_2 =
            new Account (2002, 1002, new BigDecimal("1000"));
    private static final Account ACCOUNT_3 =
            new Account (2003, 1003, new BigDecimal("1000"));
    private Account testAccount;

    private JdbcAccountDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao (jdbcTemplate);
        testAccount = new Account(0, 0,new BigDecimal("0"));
    }
    @Test
    public void getBalanceFromUsername_returns_correct_balance() {
        Account account = sut.getBalanceFromUsername("user");
        assertAccountsMatch(ACCOUNT_1, account);

        account = sut.getBalanceFromUsername("user1");
        assertAccountsMatch(ACCOUNT_2, account);
    }
    @Test
    public void getAccountFromId_returns_correct_balance() {
        Account account = sut.getAccountFromId(ACCOUNT_1.getId());
        assertAccountsMatch(ACCOUNT_1, account);

        account = sut.getAccountFromId(ACCOUNT_2.getId());
        assertAccountsMatch(ACCOUNT_2, account);
    }

    @Test
    public void getAccountFromUsername_returns_null_when_id_not_found() {
        Account account = sut.getBalanceFromUsername("no");
        Assert.assertNull(account);
    }
    @Test
    public void getAccountById_returns_null_when_id_not_found() {
        Account account = sut.getAccountFromId(0);
        Assert.assertNull(account);
    }

    @Test
    public void createById_returns_Account_with_id_and_expected_values() {
           boolean createdAccount = sut.createById(testAccount);
            Account account = sut.getAccountFromId(testAccount.getId());
            int newId = account.getId();
            assertTrue(newId > 0);
            assertAccountsMatch(account, testAccount);
    }


    private void assertAccountsMatch(Account expected, Account actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getBalance(), actual.getBalance());
        Assert.assertEquals(expected.getUser_id(), actual.getUser_id());
    }
}