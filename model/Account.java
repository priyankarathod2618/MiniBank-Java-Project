
package model;
import java.io.Serializable;
import java.util.Objects;
import model.annotation.Id;
import model.annotation.Positive;
import model.annotation.MaxLength;

public abstract class Account

        implements Transactable, InterestBearing, Serializable,
                   Comparable<Account> {

    private static final long serialVersionUID = 1L;

    private static int counter = 1000;
    public static synchronized void updateCounter(String accountNumber) {
    if (accountNumber != null && accountNumber.startsWith("AC")) {
        try {
            int number = Integer.parseInt(accountNumber.substring(2));

            if (number > counter) {
                counter = number;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid account number: " + accountNumber);
        }
    }
}

    @Id
    private final String accountNumber;

    @MaxLength(50)
    private final String ownerName;

    @Positive(message = "Balance must not be negative")
    protected double balance;

    private boolean active;

    public Account(String ownerName, double openingBalance) {
        if (ownerName == null || ownerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Owner name is required.");
        }

        if (openingBalance < 0 || !Double.isFinite(openingBalance)) {
            throw new IllegalArgumentException(
                    "Opening balance cannot be negative or invalid.");
        }

        this.accountNumber = "AC" + (++counter);
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public synchronized double getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    public abstract double interestRate();

    public abstract boolean canWithdraw(double amount);

    @Override
    public synchronized boolean deposit(double amount)
            throws InvalidAmountException {

        if (amount <= 0 || !Double.isFinite(amount)) {
            throw new InvalidAmountException(
                    "Deposit amount must be greater than zero.");
        }

        if (!active) {
            throw new InvalidAmountException("Account is inactive.");
        }

        balance += amount;
        return true;
    }

    @Override
    public synchronized boolean withdraw(double amount)
            throws InsufficientFundsException, InvalidAmountException {

        if (amount <= 0 || !Double.isFinite(amount)) {
            throw new InvalidAmountException(
                    "Withdrawal amount must be greater than zero.");
        }

        if (!active) {
            throw new InvalidAmountException("Account is inactive.");
        }

        if (!canWithdraw(amount)) {
            double shortfall = Math.max(0, amount - balance);
            throw new InsufficientFundsException(shortfall);
        }

        balance -= amount;
        return true;
    }

    public void transfer(Account to, double amount) throws BankException {
        if (to == null) {
            throw new AccountNotFoundException("null");
        }

        try {
            withdraw(amount);
            to.deposit(amount);
            System.out.println("Transfer successful.");
        } catch (BankException e) {
            throw e;
        } finally {
            System.out.println("Transfer operation finished.");
        }
    }
@Override
public int compareTo(Account other) {
    return this.accountNumber.compareTo(other.accountNumber);
}
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Account)) {
            return false;
        }

        Account other = (Account) obj;
        return accountNumber.equals(other.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber
                + ", Owner: " + ownerName
                + ", Balance: Rs. " + getBalance()
                + ", Interest: " + (interestRate() * 100) + "%"
                + ", Active: " + active;
    }
    
public synchronized void restoreBalance(double amount) {
    this.balance = amount;
}

}
