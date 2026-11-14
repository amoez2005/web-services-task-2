package edu.example.transform;

import java.io.OutputStream;
import java.nio.file.Path;
import javax.xml.transform.stream.StreamResult;

/** Renders an HTML report using XSLT. */
public final class HtmlReportTransformer implements ReportTransformer {
    /** Creates an HTML renderer. */
    public HtmlReportTransformer() {
    }

    @Override
    public void transform(Path xml, OutputStream output) throws Exception {
        XsltSupport.transformer("/xsl/students-html.xsl")
                .transform(XsltSupport.source(xml), new StreamResult(output));
    }
}
