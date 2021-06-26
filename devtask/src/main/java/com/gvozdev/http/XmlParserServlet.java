package com.gvozdev.http;

import com.gvozdev.jaxb.Envelope;
import com.gvozdev.service.XmlParserService;
import com.gvozdev.serviceimpl.XmlParserServiceImpl;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_ACCEPTED;

public class XmlParserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setStatus(SC_ACCEPTED);
        XmlParserService xmlParserService = new XmlParserServiceImpl();
        try {
            Envelope envelope = xmlParserService.getEnvelopeFromHttpRequest(req, resp);
        } catch (JAXBException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }
}
