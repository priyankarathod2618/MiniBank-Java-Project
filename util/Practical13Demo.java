
package util;

import model.Account;
import model.Repository;
import model.SavingsAccount;
import model.CurrentAccount;

public class Practical13Demo {

    public static void main(String[] args) {

        Repository<Account> repository = new Repository<>();

        Account a1 = new SavingsAccount("Alice", 5000, 500);
        Account a2 = new CurrentAccount("Bob", 8000, 1000);

        repository.saveAll(a1, a2);

        System.out.println("\nFind account by ID:");

        Account found =
                repository.findById(a1.getAccountNumber());

        System.out.println(found);

        System.out.println("\nAll saved accounts:");

        for (Account account : repository.findAll()) {
            System.out.println(account);
        }

        System.out.println("\nTotal saved accounts: "
                + repository.size());

        System.out.println("\nDeleting Bob's account:");

        boolean deleted =
                repository.delete(a2.getAccountNumber());

        System.out.println("Deleted: " + deleted);
        System.out.println("Remaining accounts: "
                + repository.size());
                System.out.println("\nGeneric Average Method:");

Integer[] numbers = {10, 20, 30, 40};
System.out.println("Average of integers: "
        + Repository.average(numbers));

Double[] marks = {75.5, 80.0, 90.5};
System.out.println("Average of marks: "
        + Repository.average(marks));
    }
    
}
