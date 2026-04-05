package com.roadmap.app;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.OutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ApiClient {

    private static final String BASE_URL = "http://127.0.0.1:8000";

    public static JSONObject post(String endpoint, JSONObject payload) {
        try {
            URL url = new URL(BASE_URL + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            if (payload != null) {
                try(OutputStream os = conn.getOutputStream()) {
                    byte[] input = payload.toString().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }

            int code = conn.getResponseCode();
            InputStream is = (code >= 200 && code < 300) ? conn.getInputStream() : conn.getErrorStream();

            try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name())) {
                String responseBody = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                
                JSONObject result = new JSONObject();
                result.put("statusCode", code);
                
                if (!responseBody.isEmpty()) {
                    if (responseBody.trim().startsWith("[")) {
                        result.put("data_array", new JSONArray(responseBody));
                    } else {
                        result.put("data", new JSONObject(responseBody));
                    }
                }
                return result;
            }

        } catch (Exception e) {
            e.printStackTrace();
            JSONObject err = new JSONObject();
            err.put("statusCode", 500);
            err.put("error", e.getMessage());
            return err;
        }
    }

    public static JSONObject get(String endpoint) {
        try {
            URL url = new URL(BASE_URL + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int code = conn.getResponseCode();
            InputStream is = (code >= 200 && code < 300) ? conn.getInputStream() : conn.getErrorStream();

            try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name())) {
                String responseBody = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";

                JSONObject result = new JSONObject();
                result.put("statusCode", code);
                
                if (!responseBody.isEmpty()) {
                    if (responseBody.trim().startsWith("[")) {
                        result.put("data_array", new JSONArray(responseBody));
                    } else {
                        result.put("data", new JSONObject(responseBody));
                    }
                }
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject err = new JSONObject();
            err.put("statusCode", 500);
            err.put("error", e.getMessage());
            return err;
        }
    }
}
