
package model;

public class CurrentAccount extends Account {

    private final double overdraftLimit;

    public CurrentAccount(String ownerName, double openingBalance,
                          double overdraftLimit) {
        super(ownerName, openingBalance);

        if (overdraftLimit < 0 || !Double.isFinite(overdraftLimit)) {
            throw new IllegalArgumentException(
                    "Overdraft limit cannot be negative or invalid.");
        }

        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public double interestRate() {
        return 0.0;
    }

    @Override
    public boolean canWithdraw(double amount) {
        return amount > 0
                && Double.isFinite(amount)
                && balance - amount >= -overdraftLimit;
    }
}
