package com.gvozdev.serviceimpl;

import com.gvozdev.jaxb.Envelope;
import com.gvozdev.service.XmlParserService;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import static java.net.URLDecoder.decode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

public class XmlParserServiceImpl implements XmlParserService {
    private static final int XML_START_INDEX = 4;

    @Override
    public Envelope getEnvelopeFromHttpRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, JAXBException, SAXException {
        String decodedXml = req.getReader().readLine().substring(XML_START_INDEX);
        String encodedXml = decode(decodedXml, UTF_8.name());

        JAXBContext jaxbContext = JAXBContext.newInstance(Envelope.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        SchemaFactory schemaFactory = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File("src/main/resources/static/schema.xsd"));
        unmarshaller.setSchema(schema);

        return (Envelope) unmarshaller.unmarshal(new StringReader(encodedXml));
    }
}
