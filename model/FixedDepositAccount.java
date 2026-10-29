
package model;

public class FixedDepositAccount extends Account {

    public FixedDepositAccount(String ownerName, double openingBalance) {
        super(ownerName, openingBalance);
    }

    @Override
    public double interestRate() {
        return 0.07;
    }

    @Override
    public boolean canWithdraw(double amount) {
        return false;
    }
}
