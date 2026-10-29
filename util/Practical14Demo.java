
package util;

import model.BankService;
import model.Account;
import model.SavingsAccount;
import model.CurrentAccount;
import model.BankException;

public class Practical14Demo {

    public static void main(String[] args) {

        BankService service = new BankService();

        try {
            // Open two accounts
            Account a1 = new SavingsAccount("Alice", 5000, 500);
            Account a2 = new CurrentAccount("Bob", 3000, 1000);

            String id1 = service.openAccount(a1);
            String id2 = service.openAccount(a2);

            System.out.println("Account opened: " + id1);
            System.out.println("Account opened: " + id2);

            // Deposit money
            service.deposit(id1, 1000);
            System.out.println("\nAfter deposit:");
            System.out.println(service.statement(id1));

            // Withdraw money
            service.withdraw(id1, 500);
            System.out.println("\nAfter withdrawal:");
            System.out.println(service.statement(id1));

            // Transfer money
            service.transfer(id1, id2, 1000);
            System.out.println("\nAfter transfer:");
            System.out.println(service.statement(id1));
            System.out.println(service.statement(id2));

            // List all accounts
            System.out.println("\nAll accounts:");
            for (Account account : service.listAccounts()) {
                System.out.println(account);
            }

            System.out.println(
                    "\nTotal accounts: " + service.accountCount());

        } catch (BankException e) {
            System.out.println("Bank error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Input error: " + e.getMessage());
        }
    }
}
