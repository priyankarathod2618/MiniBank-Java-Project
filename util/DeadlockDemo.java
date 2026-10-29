
package util;

import model.Account;
import model.SavingsAccount;

public class DeadlockDemo {

    public static void safeTransfer(
            Account first, Account second, double amount) {

        Account lock1 = first.getAccountNumber()
                .compareTo(second.getAccountNumber()) < 0
                ? first : second;

        Account lock2 = (lock1 == first) ? second : first;

        synchronized (lock1) {
            synchronized (lock2) {
                try {
                    first.transfer(second, amount);
                } catch (Exception e) {
                    System.out.println(
                            "Transfer failed: " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args)
            throws InterruptedException {

        Account a = new SavingsAccount("Alice", 5000, 500);
        Account b = new SavingsAccount("Bob", 5000, 500);

        Thread t1 = new Thread(
                () -> safeTransfer(a, b, 100), "Transfer-1");

        Thread t2 = new Thread(
                () -> safeTransfer(b, a, 200), "Transfer-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Account A balance: " + a.getBalance());
        System.out.println("Account B balance: " + b.getBalance());
        System.out.println("Deadlock prevention demo completed.");
    }
}

