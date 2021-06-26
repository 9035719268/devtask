package com.gvozdev;

import com.gvozdev.jaxb.Envelope;
import com.gvozdev.service.XmlParserService;
import com.gvozdev.serviceimpl.XmlParserServiceImpl;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class XmlParserTests {
    final String XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
        "<Envelope>\n" +
        "    <header>h</header>\n" +
        "    <body>b</body>\n" +
        "</Envelope>";

    final String HEADER = "h";
    final String BODY = "b";

    @Test
    void shouldUnmarshalXml() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Envelope.class);
        Envelope envelope = (Envelope) jaxbContext.createUnmarshaller().unmarshal(new StringReader(XML));

        assertEquals(HEADER, envelope.getHeader());
        assertEquals(BODY, envelope.getBody());
    }

    @Test
    void shouldReturnEnvelope() throws IOException, JAXBException, SAXException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(
            "xml=%3C%3Fxml+version%3D%221.0%22+encoding%3D%22UTF-8%22+%3F%3E%0D%0A%3CEnvelope%3E%0D%0A++++%3Cheader%3Eh%3C%2Fheader%3E%0D%0A++++%3Cbody%3Eb%3C%2Fbody%3E%0D%0A%3C%2FEnvelope%3E"
        )));

        XmlParserService xmlParserService = new XmlParserServiceImpl();
        Envelope envelope = xmlParserService.getEnvelopeFromHttpRequest(request, response);

        assertEquals(HEADER, envelope.getHeader());
        assertEquals(BODY, envelope.getBody());
    }

    @Test
    void shouldThrowValidationError() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(
            "xml=%3C%3Fxml+version%3D%221.0%22+encoding%3D%22UTF-8%22+%3F%3E%0D%0A%3CEnvelope%3E%0D%0A++++%3CHEADER%3Eh%3C%2Fheader%3E%0D%0A++++%3Cbody%3Eb%3C%2Fbody%3E%0D%0A%3C%2FEnvelope%3E"
        )));

        XmlParserService xmlParserService = new XmlParserServiceImpl();

        assertThrows(UnmarshalException.class, () -> xmlParserService.getEnvelopeFromHttpRequest(request, response));
    }
}
