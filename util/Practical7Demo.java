
package util;

import model.Account;
import model.SavingsAccount;

public class Practical7Demo {

    public static void main(String[] args) {

        Account account =
                new SavingsAccount("Priyanka", 1000, 500);

        boolean valid = AnnotationValidator.validate(account);

        if (valid) {
            System.out.println("Account validation successful.");
        } else {
            System.out.println("Account validation failed.");
        }
    }
}

