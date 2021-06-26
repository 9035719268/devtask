package com.gvozdev.jaxb;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Envelope")
@XmlType(propOrder = {"header", "body"})
@JsonPropertyOrder({"header", "body"})
public class Envelope {
    private String header;
    private String body;

    public Envelope() {
    }

    public Envelope(String header, String body) {
        this.header = header;
        this.body = body;
    }

    public String getHeader() {
        return header;
    }

    @XmlElement(name = "header")
    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    @XmlElement(name = "body")
    public void setBody(String body) {
        this.body = body;
    }
}
