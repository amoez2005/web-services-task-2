package edu.example.transform;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/** Standalone report generation application. */
public final class TransformationApp {
    private TransformationApp() {
    }

    /**
     * Generates both HTML and PDF reports from an XML file.
     * @param args input XML path and output directory
     * @throws Exception when arguments or report generation fail */
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            throw new IllegalArgumentException("Usage: TransformationApp <students.xml> <output-directory>");
        }
        Path xml = Path.of(args[0]);
        Path directory = Files.createDirectories(Path.of(args[1]));
        write(new HtmlReportTransformer(), xml, directory.resolve("students.html"));
        write(new PdfReportTransformer(), xml, directory.resolve("students.pdf"));
        System.out.println("Reports written to " + directory.toAbsolutePath());
    }

    private static void write(ReportTransformer transformer, Path xml, Path destination) throws Exception {
        try (OutputStream output = Files.newOutputStream(destination)) {
            transformer.transform(xml, output);
        }
    }
}
