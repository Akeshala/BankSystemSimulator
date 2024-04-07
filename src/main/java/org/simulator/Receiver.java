package org.simulator;

import java.math.BigDecimal;

public class Receiver implements Runnable {
	
	private final BankAccount bankAccount;
	private BigDecimal amount;

	public Receiver(BankAccount bankAccount, BigDecimal amount) {
		super();
		this.bankAccount = bankAccount;
		this.amount = amount;
	}

	@Override
	public void run() {
		try {
			bankAccount.withdraw(amount);
		}catch(Exception e) {
			System.out.println("Error in withdrawal "+e.getMessage());
		}
	}

}
