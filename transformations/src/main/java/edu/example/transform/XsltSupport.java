package edu.example.transform;

import java.io.InputStream;
import java.nio.file.Path;
import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/** Creates secure XML sources and bundled stylesheet transformers. */
public final class XsltSupport {
    private XsltSupport() {
    }

    /**
     * Loads a bundled stylesheet with external resource access disabled.
     * @param resource classpath stylesheet location
     * @return configured transformer
     * @throws Exception if the stylesheet is missing or invalid */
    public static Transformer transformer(String resource) throws Exception {
        TransformerFactory factory = TransformerFactory.newDefaultInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        try (InputStream stylesheet = XsltSupport.class.getResourceAsStream(resource)) {
            if (stylesheet == null) {
                throw new IllegalArgumentException("Missing stylesheet: " + resource);
            }
            return factory.newTransformer(new StreamSource(stylesheet));
        }
    }

    /**
     * Creates a secure SAX input source for an XML file.
     * @param xml input XML file
     * @return source rejecting DOCTYPE and external entities
     * @throws Exception if a secure parser cannot be configured */
    public static SAXSource source(Path xml) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newDefaultInstance();
        factory.setNamespaceAware(true);
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        XMLReader reader = factory.newSAXParser().getXMLReader();
        reader.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        reader.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        return new SAXSource(reader, new InputSource(xml.toUri().toString()));
    }
}
