package com.gvozdev.service;

import com.gvozdev.jaxb.Envelope;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface XmlParserService {
    Envelope getEnvelopeFromHttpRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, JAXBException, SAXException;
}
