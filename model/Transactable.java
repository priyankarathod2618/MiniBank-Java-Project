
package model;

public interface Transactable {
    boolean deposit(double amount) throws InvalidAmountException;

    boolean withdraw(double amount)
            throws InsufficientFundsException, InvalidAmountException;
}
