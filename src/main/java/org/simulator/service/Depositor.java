package org.simulator.service;

import org.simulator.entity.BankAccount;

import java.math.BigDecimal;

public class Depositor implements Runnable {
	
	private final BankAccount bankAccount;
	private BigDecimal amount;

	public Depositor(BankAccount bankAccount, BigDecimal amount) {
		super();
		this.bankAccount = bankAccount;
		this.amount = amount;
	}

	@Override
	public void run() {
		try {
			bankAccount.deposit(amount);
		}catch(Exception e) {
			System.out.println("Error in deposit: " + e.getMessage());
		}
	}
}
