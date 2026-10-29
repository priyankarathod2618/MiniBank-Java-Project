
package model;

public interface InterestBearing {

    double interestRate();

    default double yearlyInterest(double balance) {
        return balance * interestRate();
    }
}
