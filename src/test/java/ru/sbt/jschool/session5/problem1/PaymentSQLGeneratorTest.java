package ru.sbt.jschool.session5.problem1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 */
public class PaymentSQLGeneratorTest {
    SQLGenerator gen = new SQLGenerator();

    @Test public void testSelect() {
        assertEquals("SELECT payerid, payeraccountid, recipientID, recipientaccountid " +
            "FROM PAYMENTS WHERE operationid = ?", gen.select(Payment.class));
    }

    @Test public void testInsert() {
        assertEquals("INSERT INTO PAYMENTS(operationid, payerid, payeraccountid, recipientID, recipientaccountid)" +
            "VALUES (?, ?, ?, ?, ?)", gen.insert(Payment.class));
    }

    @Test public void testUpdate() {
        assertEquals("UPDATE PAYMENTS SET payerid = ?, payeraccountid = ?, recipientID = ?, recipientaccountid = ?" +
            "WHERE operationid = ?", gen.update(Payment.class));
    }

    @Test public void testDelete() {
        assertEquals("DELETE FROM PAYMENTS WHERE operationid = ?", gen.delete(Payment.class));
    }
}
