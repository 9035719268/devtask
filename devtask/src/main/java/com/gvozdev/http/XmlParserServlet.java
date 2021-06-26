package com.gvozdev.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gvozdev.jaxb.Envelope;
import com.gvozdev.service.TcpSenderService;
import com.gvozdev.service.XmlParserService;
import com.gvozdev.serviceimpl.TcpSenderServiceImpl;
import com.gvozdev.serviceimpl.XmlParserServiceImpl;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import static javax.servlet.http.HttpServletResponse.SC_ACCEPTED;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

public class XmlParserServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(XmlParserServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        XmlParserService xmlParserService = new XmlParserServiceImpl();
        try {
            PrintWriter writer = resp.getWriter();
            Envelope envelope = xmlParserService.getEnvelopeFromHttpRequest(req, resp);
            LOGGER.info(new ObjectMapper().writeValueAsString(envelope));
            resp.setStatus(SC_ACCEPTED);
            writer.println(new ObjectMapper().writeValueAsString(envelope));
            writer.println("XML object has been handled successfully");

            TcpSenderService tcpSenderService = new TcpSenderServiceImpl();
            tcpSenderService.sendData();

            writer.println("All data was successfully sent to tcp socket");
        } catch (JAXBException | SAXException e) {
            resp.setStatus(SC_BAD_REQUEST);
            e.printStackTrace();
        }
    }
}
