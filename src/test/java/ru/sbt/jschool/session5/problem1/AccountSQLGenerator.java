package ru.sbt.jschool.session5.problem1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 */
public class AccountSQLGenerator {
    SQLGenerator gen = new SQLGenerator();

    @Test public void testSelect() {
        assertEquals("SELECT clientid, curr, balance " +
            "FROM BANK_ACCOUNTS WHERE accountid = ? AND bankid = ?", gen.select(Account.class));
    }

    @Test public void testInsert() {
        assertEquals("INSERT INTO BANK_ACCOUNTS(clientid, accountid, bankid, curr, balance)" +
            "VALUES (?, ?, ?, ?, ?)", gen.select(Account.class));
    }

    @Test public void testUpdate() {
        assertEquals("UPDATE BANK_ACCOUNTS SET clientid = ?, curr = ?, balance = ?" +
            "WHERE accountid = ? AND bankid = ?", gen.select(Account.class));
    }

    @Test public void testDelete() {
        assertEquals("DELETE FROM BANK_ACCOUNTS WHERE accountid = ? AND bankid = ?", gen.select(Account.class));
    }
}
