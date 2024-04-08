package org.simulator.entity;

import org.simulator.constants.AccountType;

import java.math.BigDecimal;
import java.util.List;

public class BankAccount {

    private String accountNumber;
    private List<String> customerNames;
    private BigDecimal balance = new BigDecimal("0.0");
    private final AccountType accountType;
    private BigDecimal interestRate;

    public BankAccount(String accountNumber, List<String> customerNames, AccountType accountType, BigDecimal interestRate) {
        this.accountNumber = accountNumber;
        this.customerNames = customerNames;
        this.accountType = accountType;
        this.interestRate = interestRate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public synchronized BigDecimal getBalance() {
        System.out.println(
                Thread.currentThread().getName() + ": check balance in the account " + this.accountNumber +
                        ". Balance is " + this.balance
        );
        return balance;
    }

    public synchronized void withdraw(BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO) > 0) {
            if (amount.compareTo(this.balance) <= 0) {
                this.balance = this.balance.subtract(amount);
                System.out.println(
                        Thread.currentThread().getName() + ": withdrawal successful in the account " + this.accountNumber
                        + ". Amount withdrawn is " + amount + " balance after withdrawal is " + this.balance
                );
            } else {
                throw new IllegalArgumentException(Thread.currentThread().getName() + ": Insufficient funds");
            }
        } else {
            throw new IllegalArgumentException("Amount you wish to withdraw cannot be 0 or below");
        }
    }

    public synchronized void deposit(BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO) > 0) {
            this.balance = this.balance.add(amount);
            System.out.println(
                    Thread.currentThread().getName() + ": deposit successful in the account " + this.accountNumber +
                    ". Amount deposited is " + amount + " balance after deposit is " + this.balance
            );
        } else {
            throw new IllegalArgumentException("Amount you wish to deposit cannot be 0 or below");
        }
    }
}
