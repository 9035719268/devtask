package com.gvozdev.serviceimpl;

import com.gvozdev.config.ApplicationConfig;
import com.gvozdev.service.TcpSenderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.valueOf;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.asList;
import static org.apache.commons.codec.binary.Hex.encodeHex;

public class TcpSenderServiceImpl implements TcpSenderService {
    private static final Logger LOGGER = Logger.getLogger(TcpSenderServiceImpl.class.getName());

    private List<String> strings = asList(
        "Item Description Format and Value",
        "Header fixed magic FFBBCCDD",
        "Length variable json length 4 bytes little endian integer",
        "Json byte[] of json string utf16-le charset"
    );

    @Override
    public void sendData() {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        ApplicationConfig applicationConfig = context.getBean("applicationConfig", ApplicationConfig.class);

        try (Socket socket = new Socket(applicationConfig.getDestAddr(), applicationConfig.getDestPort())) {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            bufferedWriter.write(strings.get(0));
            bufferedWriter.newLine();
            LOGGER.info(valueOf(encodeHex(strings.get(0).getBytes(UTF_8))));

            bufferedWriter.write(strings.get(1));
            bufferedWriter.newLine();
            LOGGER.info(valueOf(encodeHex(strings.get(1).getBytes(UTF_8))));

            bufferedWriter.write(strings.get(2));
            bufferedWriter.newLine();
            LOGGER.info(valueOf(encodeHex(strings.get(2).getBytes(UTF_8))));

            bufferedWriter.write(strings.get(3));
            LOGGER.info(valueOf(encodeHex(strings.get(3).getBytes(UTF_8))));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
