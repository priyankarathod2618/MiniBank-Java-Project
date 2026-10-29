
package util;

import model.BankSession;

public class Practical8Demo {

    public static void main(String[] args) {

        try (BankSession session = new BankSession()) {
            System.out.println("Performing bank operations...");
        }

        System.out.println("Program finished.");
    }
}
