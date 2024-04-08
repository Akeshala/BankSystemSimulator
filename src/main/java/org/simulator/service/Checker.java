package org.simulator.service;

import org.simulator.entity.BankAccount;

import java.math.BigDecimal;

public class Checker implements Runnable {

	private final BankAccount bankAccount;
	private BigDecimal amount;

	public Checker(BankAccount bankAccount) {
		super();
		this.bankAccount = bankAccount;
	}

	@Override
	public void run() {
		try {
			this.amount = bankAccount.getBalance();
		}catch(Exception e) {
			System.out.println("Error in deposit: "+e.getMessage());
		}
	}
}
