
package util;

import java.nio.file.Path;
import java.io.IOException;

import model.Account;
import model.SavingsAccount;
import model.CurrentAccount;

public class Practical11Demo {

    public static void main(String[] args) {

        Account[] accounts = {
            new SavingsAccount("Alice", 5000, 500),
            new CurrentAccount("Bob", 8000, 1000)
        };

        Path path = Path.of("accounts.dat");

        try {
            StatePersister.save(accounts, path);

            Account[] loadedAccounts =
                    StatePersister.load(path);

            System.out.println("\nLoaded Account Details:");

            for (Account account : loadedAccounts) {
                System.out.println(account);
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("File operation failed: "
                    + e.getMessage());
        }
    }
}
