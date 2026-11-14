package edu.example.transform;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

class ReportTransformersTest {
    @TempDir
    Path directory;

    private Path sample() throws Exception {
        return Path.of(getClass().getResource("/data/students.xml").toURI());
    }

    @Test
    void htmlIncludesNestedDataAndBothEnrollmentStates() throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        new HtmlReportTransformer().transform(sample(), output);
        String html = output.toString(StandardCharsets.UTF_8);
        assertAll(() -> assertTrue(html.contains("Alice Morgan")),
                () -> assertTrue(html.contains("Daniel Reed")),
                () -> assertTrue(html.contains("Web Services")),
                () -> assertTrue(html.contains("94.5")),
                () -> assertTrue(html.contains("Inactive")),
                () -> assertTrue(html.contains("Active")));
    }

    @Test
    void pdfIsReadableAndContainsStudentAndSubjectData() throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        new PdfReportTransformer().transform(sample(), output);
        try (var pdf = Loader.loadPDF(output.toByteArray())) {
            String text = new PDFTextStripper().getText(pdf);
            assertEquals(1, pdf.getNumberOfPages());
            assertTrue(text.contains("Alice Morgan"));
            assertTrue(text.contains("Daniel Reed"));
            assertTrue(text.contains("XML Technologies"));
            assertTrue(text.contains("94.5"));
        }
    }

    @Test
    void htmlEscapesInputText() throws Exception {
        Path input = directory.resolve("escaped.xml");
        Files.writeString(input, "<students><student><name>&lt;script&gt; &amp; test</name>"
                + "<subjects/></student></students>");
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        new HtmlReportTransformer().transform(input, output);
        assertTrue(output.toString(StandardCharsets.UTF_8).contains("&lt;script&gt; &amp; test"));
    }

    @Test
    void rejectsExternalEntitiesAndMissingStylesheets() throws Exception {
        Path input = directory.resolve("unsafe.xml");
        Files.writeString(input, "<!DOCTYPE students [<!ENTITY xxe SYSTEM 'file:///secret'>]>"
                + "<students>&xxe;</students>");
        assertThrows(Exception.class,
                () -> new HtmlReportTransformer().transform(input, new ByteArrayOutputStream()));
        assertThrows(IllegalArgumentException.class, () -> XsltSupport.transformer("/missing.xsl"));
    }

    @Test
    void appWritesBothReportsAndRejectsMissingArguments() throws Exception {
        TransformationApp.main(new String[]{sample().toString(), directory.toString()});
        assertTrue(Files.size(directory.resolve("students.html")) > 100);
        assertTrue(Files.size(directory.resolve("students.pdf")) > 100);
        assertThrows(IllegalArgumentException.class, () -> TransformationApp.main(new String[0]));
    }
}
