package com.test;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.test.model.Person;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Slf4j
public class APIDemoHandler implements RequestStreamHandler {
    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        JsonObject responseJson = new JsonObject();

        try {
            JsonElement event = JsonParser.parseReader(reader);

            if (event.getAsJsonObject().get("body") != null) {
                Person person = new Person(event.getAsJsonObject().get("body").getAsString());
                log.info("Got person {}", person);
            }

            JsonObject responseBody = new JsonObject();
            responseBody.addProperty("message", "New item created");

            JsonObject headerJson = new JsonObject();
            headerJson.addProperty("x-custom-header", "my custom header value");

            responseJson.addProperty("statusCode", 200);
            responseJson.add("headers", headerJson);
            responseJson.addProperty("body", responseBody.toString());

        } catch (JsonParseException pex) {
            responseJson.addProperty("statusCode", 400);
            responseJson.addProperty("exception", pex.toString());
        }

        OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        writer.write(responseJson.toString());
        writer.close();
    }
}
