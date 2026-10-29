
package util;

import java.nio.file.Path;
import java.io.IOException;

public class Practical11ReportDemo {

    public static void main(String[] args) {

        try {
            ReportGenerator.generate(
                    Path.of("logs"),
                    Path.of("reports", "bank-report.txt"));

        } catch (IOException e) {
            System.out.println(
                    "Report generation failed: " + e.getMessage());
        }
    }
}
