
package util;

import model.Account;
import model.SavingsAccount;
import model.AccountWorker;

public class Practical9Demo {

    public static void main(String[] args)
            throws InterruptedException {

        Account account = new SavingsAccount(
                "Priyanka", 1000, 500);

        System.out.println("Initial balance: "
                + account.getBalance());

        Thread t1 = new Thread(
                new AccountWorker(account, 1000, 10), "Worker-1");

        Thread t2 = new Thread(
                new AccountWorker(account, 1000, 10), "Worker-2");

        Thread t3 = new Thread(
                new AccountWorker(account, 1000, 10), "Worker-3");

        Thread t4 = new Thread(
                new AccountWorker(account, 1000, 10), "Worker-4");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        System.out.println("Final balance: "
                + account.getBalance());

        System.out.println("Expected balance: 41000.0");
    }
}
