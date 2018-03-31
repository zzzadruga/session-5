package ru.sbt.jschool.session5.problem1;

import java.util.Objects;

/**
 */
@Table(name = "BANK_ACCOUNTS")
public class Account {
    @Column
    private long clientID;

    @PrimaryKey
    private long accountID;

    @PrimaryKey
    private long bankID;

    @Column(name = "CURR")
    private Currency currency;

    @Column
    private float balance;

    public Account(long clientID, long accountID, long bankID, Currency currency, float balance) {
        this.clientID = clientID;
        this.accountID = accountID;
        this.bankID = bankID;
        this.currency = currency;
        this.balance = balance;
    }

    public long getClientID() {
        return clientID;
    }

    public void setClientID(long clientID) {
        this.clientID = clientID;
    }

    public long getAccountID() {
        return accountID;
    }

    public void setAccountID(long accountID) {
        this.accountID = accountID;
    }

    public long getBankID() {
        return bankID;
    }

    public void setBankID(long bankID) {
        this.bankID = bankID;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Account account = (Account)o;
        return clientID == account.clientID &&
            accountID == account.accountID &&
            bankID == account.bankID &&
            Float.compare(account.balance, balance) == 0 &&
            currency == account.currency;
    }

    @Override public int hashCode() {
        return Objects.hash(clientID, accountID, bankID, currency, balance);
    }
}

