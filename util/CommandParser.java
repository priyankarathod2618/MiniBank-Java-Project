
package util;

import model.Command;
import model.TransactionType;

public class CommandParser {

    public static Command parse(String input) {
        String[] parts = input.trim().split("\\s+");

        if (parts.length != 3) {
            throw new IllegalArgumentException(
                    "Use: TYPE ACCOUNT_NUMBER AMOUNT");
        }

        TransactionType type;

        try {
            type = TransactionType.valueOf(
                    parts[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Type must be DEPOSIT, WITHDRAW or TRANSFER.");
        }

        long amount;

        try {
            amount = Long.parseLong(parts[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Amount must be a whole number.");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero.");
        }

        return new Command(type, parts[1], amount);
    }
}

