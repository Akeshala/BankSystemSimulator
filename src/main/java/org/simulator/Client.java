package org.simulator;

import org.simulator.entity.Bank;
import org.simulator.entity.BankAccount;
import org.simulator.service.Checker;
import org.simulator.service.Depositor;
import org.simulator.service.InterestCalculationManager;
import org.simulator.service.Receiver;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.simulator.constants.AccountType.REGULAR;
import static org.simulator.constants.AccountType.VIP;

public class Client {
    public static void main(String[] args) {

        Bank bank = new Bank();
        List<Thread> threads = new ArrayList<>();

        List<String> customers1 = new ArrayList<>(List.of("User1", "User2"));
        BankAccount bankAccount1 = bank.createAccount("2024001", customers1, VIP , new BigDecimal("5"));

        List<String> customers2 = new ArrayList<>(List.of("User3", "User4"));
        BankAccount bankAccount2 = bank.createAccount("2024002", customers2, REGULAR, new BigDecimal("4"));

        ThreadGroup vipCustomer = new ThreadGroup("VIP Customer");
        vipCustomer.setMaxPriority(10);
        ThreadGroup regularCustomer = new ThreadGroup("Regular Customer");
        regularCustomer.setMaxPriority(5);
        ThreadGroup backgroundTask = new ThreadGroup("Background task");
        backgroundTask.setMaxPriority(3);

        // deposit threads
        Thread depositorThread1 = new Thread(
                regularCustomer,
                new Depositor(bankAccount2, new BigDecimal("1000")),
                "Depositor1 Regular Customer"
        );
        Thread depositorThread2 = new Thread(
                regularCustomer,
                new Depositor(bankAccount2, new BigDecimal("900")),
                "Depositor2 Regular Customer"
        );
        Thread depositorThread3 = new Thread(
                vipCustomer,
                new Depositor(bankAccount1, new BigDecimal("700")),
                "Depositor3 VIP Customer"
        );

        threads.add(depositorThread1);
        threads.add(depositorThread2);
        threads.add(depositorThread3);

        // receiver threads
        Thread receiverThread1 = new Thread(
                vipCustomer,
                new Receiver(bankAccount1, new BigDecimal("600")),
                "Receiver1 VIP Customer"
        );
        Thread receiverThread2 = new Thread(
                regularCustomer,
                new Receiver(bankAccount2, new BigDecimal("400")),
                "Receiver2 Regular Customer"
        );
        Thread receiverThread3 = new Thread(
                vipCustomer,
                new Receiver(bankAccount1, new BigDecimal("800")),
                "Receiver1 VIP Customer"
        );

        threads.add(receiverThread1);
        threads.add(receiverThread2);
        threads.add(receiverThread3);

        // checker threads
        Thread checkerThread1 = new Thread(vipCustomer, new Checker(bankAccount1), "Checker1 VIP Customer");
        Thread checkerThread2 = new Thread(regularCustomer, new Checker(bankAccount2), "Checker2 Regular Customer");
        Thread checkerThread3 = new Thread(regularCustomer, new Checker(bankAccount2), "Checker3 Regular Customer");
        Thread checkerThread4 = new Thread(regularCustomer, new Checker(bankAccount2), "Checker4 Regular Customer");

        threads.add(checkerThread1);
        threads.add(checkerThread2);
        threads.add(checkerThread3);
        threads.add(checkerThread4);

        // interest rate calculator
        Thread interestCalculationManagerThread = new Thread(
                backgroundTask,
                new InterestCalculationManager(bank),
                "Interest Calculation Manager"
        );

        threads.add(interestCalculationManagerThread);

        for (Thread thread: threads) {
            thread.start();
        }

        // Wait for all threads to terminate
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Error in withdrawal " + e.getMessage());
            }
        }

        System.out.println("All threads have terminated.");
    }
}