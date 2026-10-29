
import java.util.Scanner;

import model.Customer;
import model.Account;
import model.SavingsAccount;
import model.CurrentAccount;
import model.FixedDepositAccount;
import model.BankService;
import model.BankException;

record BankInfo(String name, String branch) {}

enum MenuOption {
    OPEN_ACCOUNT, DEPOSIT, WITHDRAW, TRANSFER,
    VIEW_ACCOUNTS, STATEMENT, EXIT
}

public class MiniBank {

    static Scanner sc = new Scanner(System.in);
    static BankService service = new BankService();

    public static void main(String[] args) {

        BankInfo bank = new BankInfo("MiniBank", "Ahmedabad");

        System.out.println("Welcome to " + bank.name());
        System.out.println("Branch: " + bank.branch());

        boolean running = true;

        while (running) {
            System.out.println("\n===== MINIBANK MENU =====");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. View Accounts");
            System.out.println("6. Account Statement");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            try {
                int choice = Integer.parseInt(sc.nextLine());

                MenuOption option = switch (choice) {
                    case 1 -> MenuOption.OPEN_ACCOUNT;
                    case 2 -> MenuOption.DEPOSIT;
                    case 3 -> MenuOption.WITHDRAW;
                    case 4 -> MenuOption.TRANSFER;
                    case 5 -> MenuOption.VIEW_ACCOUNTS;
                    case 6 -> MenuOption.STATEMENT;
                    case 7 -> MenuOption.EXIT;
                    default -> null;
                };

                if (option == null) {
                    System.out.println("Invalid choice. Try again.");
                    continue;
                }

                switch (option) {
                    case OPEN_ACCOUNT -> openAccount();
                    case DEPOSIT -> depositMoney();
                    case WITHDRAW -> withdrawMoney();
                    case TRANSFER -> transferMoney();
                    case VIEW_ACCOUNTS -> viewAccounts();
                    case STATEMENT -> printStatement();
                    case EXIT -> {
                        service.saveAccounts();
                        running = false;
                        System.out.println("Thank you for using MiniBank!");
                    }
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (BankException e) {
                System.out.println("Bank error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        sc.close();
    }

    static void openAccount() {

        System.out.print("Customer name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        System.out.print("Email: ");
        String email = sc.nextLine().trim();

        System.out.print("Mobile: ");
        String mobile = sc.nextLine().trim();

        Customer customer = new Customer(name, email, mobile);

        System.out.println("\nChoose Account Type:");
        System.out.println("1. Savings Account");
        System.out.println("2. Current Account");
        System.out.println("3. Fixed Deposit Account");
        System.out.print("Enter type: ");

        int type = Integer.parseInt(sc.nextLine());

        System.out.print("Opening balance: ");
        double balance = Double.parseDouble(sc.nextLine());

        if (balance < 0) {
            System.out.println("Opening balance cannot be negative.");
            return;
        }

        Account account;

        switch (type) {
            case 1 -> account =
                    new SavingsAccount(name, balance, 500);
            case 2 -> account =
                    new CurrentAccount(name, balance, 1000);
            case 3 -> account =
                    new FixedDepositAccount(name, balance);
            default -> {
                System.out.println("Invalid account type.");
                return;
            }
        }

        String accountNumber = service.openAccount(account);

        System.out.println("\nCustomer created:");
        System.out.println(customer);

        System.out.println("Account created successfully!");
        System.out.println("Account number: " + accountNumber);
        System.out.println(account);
    }

    static void depositMoney() throws BankException {

        System.out.print("Account number: ");
        String number = sc.nextLine().trim();

        System.out.print("Amount to deposit: ");
        double amount = Double.parseDouble(sc.nextLine());

        service.deposit(number, amount);

        System.out.println("Deposit successful.");
        System.out.println(service.statement(number));
    }

    static void withdrawMoney() throws BankException {

        System.out.print("Account number: ");
        String number = sc.nextLine().trim();

        System.out.print("Amount to withdraw: ");
        double amount = Double.parseDouble(sc.nextLine());

        service.withdraw(number, amount);

        System.out.println("Withdrawal successful.");
        System.out.println(service.statement(number));
    }

    static void transferMoney() throws BankException {

        System.out.print("Sender account number: ");
        String sender = sc.nextLine().trim();

        System.out.print("Receiver account number: ");
        String receiver = sc.nextLine().trim();

        System.out.print("Transfer amount: ");
        double amount = Double.parseDouble(sc.nextLine());

        service.transfer(sender, receiver, amount);

        System.out.println("\nSender statement:");
        System.out.println(service.statement(sender));

        System.out.println("\nReceiver statement:");
        System.out.println(service.statement(receiver));
    }

    static void viewAccounts() {

        if (service.accountCount() == 0) {
            System.out.println("No accounts created yet.");
            return;
        }

        System.out.println("\n===== ALL ACCOUNTS =====");

        for (Account account : service.listAccounts()) {
            System.out.println(account);
        }

        System.out.println("Total accounts: " + service.accountCount());
    }

    static void printStatement() throws BankException {

        System.out.print("Enter account number: ");
        String number = sc.nextLine().trim();

        System.out.println(service.statement(number));
    }
}
