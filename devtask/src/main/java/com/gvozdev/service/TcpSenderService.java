package com.gvozdev.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface TcpSenderService {
    void sendData(HttpServletRequest req, HttpServletResponse resp) throws IOException;
}
