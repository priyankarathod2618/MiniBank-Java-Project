
package util;

import model.Account;
import model.SavingsAccount;
import model.BankException;
import model.InvalidAmountException;

public class Practical10Demo {

    public static void main(String[] args) {

        Account account = new SavingsAccount(
                "Priyanka", 1000, 500);

        TransactionProcessor processor =
                new TransactionProcessor();

        System.out.println("Initial balance: "
                + account.getBalance());

        for (int i = 1; i <= 10; i++) {
            processor.submit(() -> {
                try {
                    account.deposit(100);
                    System.out.println(
                            Thread.currentThread().getName()
                            + " deposited Rs. 100");
                } catch (InvalidAmountException e) {
                    System.out.println(e.getMessage());
                }
            });
        }

        processor.stop();

        System.out.println("Final balance: "
                + account.getBalance());
        System.out.println("Expected balance: 2000.0");
    }
}
