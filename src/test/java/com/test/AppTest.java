package com.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.LambdaRuntime;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class AppTest {

    @Test
    public void handleRequest_shouldReturnConstantValue() throws IOException {
        InputStream is = AppTest.class.getResourceAsStream("/request-body.json");
        String body = inputStreamToString(is);
        LambdaFunctionRequestHandler function = new LambdaFunctionRequestHandler();
        Context mockContext = mock(Context.class, withSettings().serializable());
        LambdaLogger mockLogger = LambdaRuntime.getLogger();
        when(mockContext.getLogger()).thenReturn(mockLogger);
        APIGatewayV2HTTPEvent event = APIGatewayV2HTTPEvent.builder()
                .withBody(body)
                .build();
        APIGatewayV2HTTPResponse response = function.handleRequest(event, mockContext);
        assertEquals("{\"message\":\"New item created 3\"}",response.getBody());
    }

    private String inputStreamToString(InputStream inputStream) throws IOException {

        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
                (inputStream, StandardCharsets.UTF_8))) {
            int c;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        }
        return textBuilder.toString();
    }
}
