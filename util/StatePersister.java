
package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import model.Account;

public class StatePersister {

    public static void save(Account[] accounts, Path path)
            throws IOException {

        try (ObjectOutputStream out =
                new ObjectOutputStream(
                        new FileOutputStream(path.toFile()))) {

            out.writeObject(accounts);
            System.out.println("Accounts saved successfully.");
        }
    }

    public static Account[] load(Path path)
            throws IOException, ClassNotFoundException {

        try (ObjectInputStream in =
                new ObjectInputStream(
                        new FileInputStream(path.toFile()))) {

            Account[] accounts = (Account[]) in.readObject();
            System.out.println("Accounts loaded successfully.");

            return accounts;
        }
    }
}
