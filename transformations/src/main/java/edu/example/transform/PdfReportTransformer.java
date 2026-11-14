package edu.example.transform;

import java.io.OutputStream;
import java.nio.file.Path;
import javax.xml.transform.sax.SAXResult;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

/** Transforms XML to XSL-FO, then renders the FO events to PDF using Apache FOP. */
public final class PdfReportTransformer implements ReportTransformer {
    private final FopFactory factory;

    /** Creates a reusable FOP factory with the current directory as its base URI. */
    public PdfReportTransformer() {
        factory = FopFactory.newInstance(Path.of(".").toAbsolutePath().toUri());
    }

    @Override
    public void transform(Path xml, OutputStream output) throws Exception {
        Fop fop = factory.newFop(MimeConstants.MIME_PDF, factory.newFOUserAgent(), output);
        XsltSupport.transformer("/xsl/students-fo.xsl")
                .transform(XsltSupport.source(xml), new SAXResult(fop.getDefaultHandler()));
    }
}
