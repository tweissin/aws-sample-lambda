package com.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.io.*;

public class AppTest {

    @Test
    public void handleRequest_shouldReturnConstantValue() throws IOException {
        APIDemoHandler function = new APIDemoHandler();
        InputStream inputStream = AppTest.class.getResourceAsStream("/request.json");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        function.handleRequest(inputStream, outputStream, null);
        assertEquals("{\"statusCode\":200,\"headers\":{\"x-custom-header\":\"my custom header value\"},\"body\":\"{\\\"message\\\":\\\"New item created\\\"}\"}", outputStream.toString());
    }
}
