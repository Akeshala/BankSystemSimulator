package org.simulator.service;

import org.simulator.entity.Bank;

public class InterestCalculationManager implements Runnable {
	
	private final Bank bank;

	public InterestCalculationManager(Bank bank) {
		super();
		this.bank = bank;
	}

	@Override
	public void run() {
		try {
			for (int i = 0; i < 5; i++) {
				bank.addInterest();
				Thread.sleep(1000);
			}
		}catch(Exception e) {
			System.out.println("Error in interest calculation: "+e.getMessage());
		}
	}
}
