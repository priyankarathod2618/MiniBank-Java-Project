
package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class ReportGenerator {

    public static void generate(Path logDirectory, Path reportFile)
            throws IOException {

        double totalDeposits = 0;
        double totalWithdrawals = 0;
        int transactionCount = 0;

        if (!Files.exists(logDirectory)) {
            System.out.println("Log directory does not exist.");
            return;
        }

        try (DirectoryStream<Path> files =
                Files.newDirectoryStream(logDirectory, "*.txt")) {

            for (Path file : files) {

                try (BufferedReader reader =
                        Files.newBufferedReader(file)) {

                    String line;

                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.trim().split("\\s+");

                        if (parts.length < 2) {
                            continue;
                        }

                        try {
                            double amount =
                                    Double.parseDouble(parts[1]);

                            if (parts[0].equalsIgnoreCase("DEPOSIT")) {
                                totalDeposits += amount;
                                transactionCount++;
                            } else if (parts[0]
                                    .equalsIgnoreCase("WITHDRAW")) {
                                totalWithdrawals += amount;
                                transactionCount++;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println(
                                    "Skipping invalid log entry: " + line);
                        }
                    }
                }
            }
        }

        String report =
                "BANK TRANSACTION REPORT\n"
                + "=======================\n"
                + "Transaction count: " + transactionCount + "\n"
                + "Total deposits: Rs. " + totalDeposits + "\n"
                + "Total withdrawals: Rs. " + totalWithdrawals + "\n"
                + "Net amount: Rs. "
                + (totalDeposits - totalWithdrawals) + "\n";

        Path parent = reportFile.getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }

        Files.writeString(reportFile, report);

        BasicFileAttributes attributes =
                Files.readAttributes(
                        reportFile, BasicFileAttributes.class);

        System.out.println(report);
        System.out.println("Report saved to: "
                + reportFile.toAbsolutePath());
        System.out.println("Report file size: "
                + attributes.size() + " bytes");
        System.out.println("Last modified: "
                + attributes.lastModifiedTime());
    }
}
