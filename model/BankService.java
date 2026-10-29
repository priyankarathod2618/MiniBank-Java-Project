
package model;

import java.util.List;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class BankService {

    private final Repository<Account> repository;
    private static final Path FILE = Path.of("accounts.dat");

    public BankService() {
        repository = new Repository<>();
        loadAccounts();
    }

    // Open an account
    public String openAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException(
                    "Account cannot be null.");
        }

        repository.save(account);
        return account.getAccountNumber();
    }

    // Find an account
    private Account getAccount(String accountNumber)
            throws AccountNotFoundException {

        Account account = repository.findById(accountNumber);

        if (account == null) {
            throw new AccountNotFoundException(accountNumber);
        }

        return account;
    }

    // Deposit money
    public void deposit(String accountNumber, double amount)
            throws BankException {

        Account account = getAccount(accountNumber);
        account.deposit(amount);
    }

    // Withdraw money
    public void withdraw(String accountNumber, double amount)
            throws BankException {

        Account account = getAccount(accountNumber);
        account.withdraw(amount);
    }

    // Transfer money
    public void transfer(String fromNumber, String toNumber,
                         double amount) throws BankException {

        Account from = getAccount(fromNumber);
        Account to = getAccount(toNumber);

        if (from == to) {
            throw new IllegalArgumentException(
                    "Cannot transfer to the same account.");
        }

        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Transfer amount must be greater than zero.");
        }

        Account first = from.getAccountNumber()
                .compareTo(to.getAccountNumber()) < 0 ? from : to;

        Account second = first == from ? to : from;

        synchronized (first) {
            synchronized (second) {
                double oldFrom = from.getBalance();
                double oldTo = to.getBalance();

                from.withdraw(amount);

                try {
                    to.deposit(amount);
                } catch (BankException | RuntimeException e) {
                    from.restoreBalance(oldFrom);
                    to.restoreBalance(oldTo);
                    throw e;
                }
            }
        }

        System.out.println("Transfer successful.");
    }

    // Account statement
    public String statement(String accountNumber)
            throws AccountNotFoundException {

        Account account = getAccount(accountNumber);

        return "----- ACCOUNT STATEMENT -----\n"
                + account
                + "\nCurrent balance: Rs. "
                + account.getBalance()
                + "\n-----------------------------";
    }

    // List accounts
    public List<Account> listAccounts() {
        return repository.findAll();
    }

    // Count accounts
    public int accountCount() {
        return repository.size();
    }

    // Load accounts when the application starts
    private void loadAccounts() {
        if (!Files.exists(FILE)) {
            return;
        }

        try (ObjectInputStream input =
                     new ObjectInputStream(Files.newInputStream(FILE))) {

            Account[] savedAccounts = (Account[]) input.readObject();

            for (Account account : savedAccounts) {
                 Account.updateCounter(account.getAccountNumber());
    repository.save(account);
            }

            System.out.println("Saved accounts loaded successfully.");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(
                    "Could not load accounts: " + e.getMessage());
        }
    }

    // Save accounts before exiting
    public void saveAccounts() {
        try (ObjectOutputStream output =
                     new ObjectOutputStream(Files.newOutputStream(FILE))) {

            Account[] savedAccounts =
                    repository.findAll().toArray(new Account[0]);

            output.writeObject(savedAccounts);

            System.out.println("Accounts saved successfully.");

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Could not save accounts: " + e.getMessage(), e);
        }
    }
}
