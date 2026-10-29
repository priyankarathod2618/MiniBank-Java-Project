
package model;

public class AccountWorker implements Runnable {

    private final Account account;
    private final int times;
    private final double amount;

    public AccountWorker(Account account, int times, double amount) {
        this.account = account;
        this.times = times;
        this.amount = amount;
    }

    @Override
    public void run() {
        for (int i = 0; i < times; i++) {
            try {
                account.deposit(amount);
            } catch (InvalidAmountException e) {
                System.out.println(
                        Thread.currentThread().getName()
                        + ": " + e.getMessage());
            }
        }

        System.out.println(
                Thread.currentThread().getName()
                + " finished.");
    }
}
