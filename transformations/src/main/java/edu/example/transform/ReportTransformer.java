package edu.example.transform;

import java.io.OutputStream;
import java.nio.file.Path;

/** Converts XML input into an end-user report without owning the destination stream. */
public interface ReportTransformer {
    /**
     * Transforms XML into an end-user report.
     * @param xml input XML file
     * @param output caller-owned report stream
     * @throws Exception when parsing, transformation, or rendering fails */
    void transform(Path xml, OutputStream output) throws Exception;
}
