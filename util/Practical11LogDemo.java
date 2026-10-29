
package util;

import java.nio.file.Path;
import java.io.IOException;

public class Practical11LogDemo {

    public static void main(String[] args) {

        Path logFile = Path.of("logs", "transactions.txt");

        try {
            TransactionLog.append(
                    logFile, "DEPOSIT 1000");

            TransactionLog.append(
                    logFile, "WITHDRAW 200");

            TransactionLog.append(
                    logFile, "DEPOSIT 500");

            System.out.println(
                    "Transaction logs written successfully.");

            System.out.println(
                    "Log location: " + logFile.toAbsolutePath());

        } catch (IOException e) {
            System.out.println(
                    "Unable to write log: " + e.getMessage());
        }
    }
}
