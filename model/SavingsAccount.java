
package model;

public class SavingsAccount extends Account {

    private final double minBalance;

    public SavingsAccount(String ownerName, double openingBalance,
                          double minBalance) {
        super(ownerName, openingBalance);

        if (minBalance < 0 || openingBalance < minBalance) {
            throw new IllegalArgumentException(
                    "Opening balance must meet the minimum balance.");
        }

        this.minBalance = minBalance;
    }

    @Override
    public double interestRate() {
        return 0.04;
    }

    @Override
    public boolean canWithdraw(double amount) {
        return amount > 0
                && Double.isFinite(amount)
                && balance - amount >= minBalance;
    }
}
