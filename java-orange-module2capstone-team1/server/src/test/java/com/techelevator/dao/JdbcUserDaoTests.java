package com.techelevator.dao;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class JdbcUserDaoTests extends BaseDaoTests{
    private static final User USER_1 =
            new User (1,"user1", "password","isAuthorized");
    private static final User USER_2 =
            new User (2,"user2", "password","isAuthorized");
    private static final User USER_3 =
            new User (3,"user3", "password","isAuthorized");
    private JdbcUserDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        AccountDao jdbcAccountDao = new JdbcAccountDao(jdbcTemplate);
        sut = new JdbcUserDao(jdbcTemplate,jdbcAccountDao);
    }

    @Test
    public void createNewUser() {
        boolean userCreated = sut.create("TEST_USER","test_password");
        Assert.assertTrue(userCreated);
        User user = sut.findByUsername("TEST_USER");
        Assert.assertEquals("TEST_USER", user.getUsername());
    }
    @Test
    public void findByUsername_returns_correct_user(){
        User user = sut.findByUsername("user1");
        assertUsersMatch(USER_1, user);

        user = sut.findByUsername("user2");
        assertUsersMatch(USER_2, user);

    }

    @Test
    public void findByUsername_returns_null_when_username_not_found() {
        User user = sut.findByUsername("asf");
        Assert.assertNull(user);
    }
    @Test
    public void findIdByUsername_returns_correct_user() {
        int user = sut.findIdByUsername("user1");
        assertUsersMatch(USER_1, user);

        user = sut.findIdByUsername("user2");
        assertUsersMatch(USER_2, user);
    }
    @Test
    public void findIdByUsername_returns_null_when_username_not_found() {
        int user = sut.findIdByUsername("asdh");
        Assert.assertNull(user);
    }

    @Test
    public void findAll_returns_all_values(){
        List<User> users = sut.findAll();

        Assert.assertEquals(2, users.size());

        assertUsersMatch(USER_1, users.get(0));
        assertUsersMatch(USER_2, users.get(1));
        assertUsersMatch(USER_3, users.get(2));

    }

    private void assertUsersMatch(User expected, User actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getUsername(), actual.getUsername());
        Assert.assertEquals(expected.getPassword(), actual.getPassword());
        Assert.assertEquals(expected.getAuthorities(), actual.getAuthorities());
        Assert.assertEquals(expected.isActivated(), actual.isActivated());
    }


}
