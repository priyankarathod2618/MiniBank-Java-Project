
package util;

import model.Account;
import model.SavingsAccount;
import model.WithdrawRule;

public class Practical6Demo {

    public static void main(String[] args) {

        Account account =
                new SavingsAccount("Priyanka", 10000, 500);

        System.out.println("Account Details:");
        System.out.println(account);

        // Default method from InterestBearing
        System.out.println("Yearly Interest: Rs. "
                + account.yearlyInterest(account.getBalance()));

        // Anonymous class
        WithdrawRule rule1 = new WithdrawRule() {
            @Override
            public boolean allow(Account acc, double amount) {
                return acc.getBalance() - amount >= 500;
            }
        };

        System.out.println("Anonymous class allows withdrawal of 2000: "
                + rule1.allow(account, 2000));

        // Lambda expression
        WithdrawRule rule2 =
                (acc, amount) -> amount > 0
                        && acc.getBalance() - amount >= 500;

        System.out.println("Lambda allows withdrawal of 2000: "
                + rule2.allow(account, 2000));
    }
}
