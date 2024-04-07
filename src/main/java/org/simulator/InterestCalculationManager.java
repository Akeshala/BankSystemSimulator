package org.simulator;

public class InterestCalculationManager implements Runnable {
	
	private final Bank bank;

	public InterestCalculationManager(Bank bank) {
		super();
		this.bank = bank;
	}

	@Override
	public void run() {
		try {
			bank.addInterest();
		}catch(Exception e) {
			System.out.println("Error in interest calculation: "+e.getMessage());
		}
	}

}
