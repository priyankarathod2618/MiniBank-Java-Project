
package util;

import model.Account;
import model.AccountStore;
import model.SavingsAccount;
import model.CurrentAccount;

public class Practical12Demo {

    public static void main(String[] args) {

        AccountStore store = new AccountStore();

        Account a1 = new SavingsAccount("Alice", 5000, 500);
        Account a2 = new CurrentAccount("Bob", 8000, 1000);
        Account a3 = new SavingsAccount("Charlie", 3000, 500);

        store.add(a1);
        store.add(a2);
        store.add(a3);

        store.recordTransaction("Alice deposited Rs. 500");
        store.recordTransaction("Bob withdrew Rs. 200");

        System.out.println("Total accounts: " + store.size());

        System.out.println("\nSearch by account number:");
        System.out.println(store.get(a2.getAccountNumber()));

        System.out.println("\nAccounts sorted by balance (highest first):");

        for (Account account : store.sortedByBalance()) {
            System.out.println(account);
        }

        System.out.println("\nSorted by account number using Comparable:");

        java.util.List<Account> accounts =
                new java.util.ArrayList<>(store.getAll());

        java.util.Collections.sort(accounts);

        for (Account account : accounts) {
            System.out.println(account.getAccountNumber());
        }

        System.out.println("\nTransaction history:");

        for (String transaction : store.getTransactionHistory()) {
            System.out.println(transaction);
        }
    }
}
