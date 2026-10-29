
package model;

public class InsufficientFundsException extends BankException {

    private final double shortfall;

    public InsufficientFundsException(double shortfall) {
        super("Insufficient funds. Shortfall: Rs. " + shortfall);
        this.shortfall = shortfall;
    }

    public double getShortfall() {
        return shortfall;
    }
}
