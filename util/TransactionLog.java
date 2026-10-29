
package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class TransactionLog {

    public static void append(Path path, String message)
            throws IOException {

        // Create the parent folder if it does not exist
        Path parent = path.getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }

        // Append the transaction to the file
        String entry = message + System.lineSeparator();

        Files.writeString(
                path,
                entry,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
    }
}
