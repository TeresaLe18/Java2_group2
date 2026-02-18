package com.mycompany.projectjava2.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GeminiChatService implements ChatService {

    private final String apiKey;
    private final HttpClient httpClient;
    private final Gson gson;
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=";

    public GeminiChatService(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    @Override
    public String getResponse(String input) {
        try {
            // Create JSON body
            JsonObject textPart = new JsonObject();
            textPart.addProperty("text", input);

            JsonArray parts = new JsonArray();
            parts.add(textPart);

            JsonObject content = new JsonObject();
            content.add("parts", parts);

            JsonArray contents = new JsonArray();
            contents.add(content);

            JsonObject requestBody = new JsonObject();
            requestBody.add("contents", contents);

            String jsonInputString = gson.toJson(requestBody);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + apiKey))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonInputString))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return parseResponse(response.body());
            } else {
                return "Error: " + response.statusCode() + " - " + response.body();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error communicating with Gemini: " + e.getMessage();
        }
    }

    private String parseResponse(String jsonResponse) {
        try {
            JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
            JsonArray candidates = jsonObject.getAsJsonArray("candidates");
            if (candidates != null && candidates.size() > 0) {
                JsonObject candidate = candidates.get(0).getAsJsonObject();
                JsonObject content = candidate.getAsJsonObject("content");
                JsonArray parts = content.getAsJsonArray("parts");
                if (parts != null && parts.size() > 0) {
                    return parts.get(0).getAsJsonObject().get("text").getAsString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error parsing response.";
        }
        return "No response text found.";
    }
}
