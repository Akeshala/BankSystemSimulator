package org.simulator.entity;

import org.simulator.constants.AccountType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Bank {

    private List<BankAccount> bankAccounts = new ArrayList<>();

    public BankAccount createAccount(String accountNumber, List<String> customers, AccountType accountType, BigDecimal bigDecimal) {
        BankAccount bankAccount = new BankAccount(accountNumber, customers, accountType, bigDecimal);
        this.bankAccounts.add(bankAccount);
        return bankAccount;
    }

    public synchronized void addInterest() {
        for (BankAccount account : bankAccounts) {
            if (account.getBalance().compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal interest = account.getBalance().multiply(account.getInterestRate()).divide(
                        BigDecimal.valueOf(1200),
                        2,
                        RoundingMode.HALF_UP
                );
                account.deposit(interest);
            }
        }
    }
}
