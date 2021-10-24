package xyz.junomc.dhantibot.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RequestUtils {
    private AntiBotUtils antiBotUtils;

    public RequestUtils() {
        this.antiBotUtils = new AntiBotUtils();
    }

    public JsonObject response(String curl) {
        JsonObject object = null;

        try {
            StringBuilder result = new StringBuilder();

            URL url = new URL(curl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty(this.antiBotUtils.w(117, 115, 101, 114, 45, 97, 103, 101, 110, 116), this.antiBotUtils.w(74, 117, 110, 111, 77, 67));

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {
                for (String line; (line = reader.readLine()) != null;) {
                    result.append(line);
                }
            }

            JsonParser parser = new JsonParser();
            object = parser.parse(result.toString()).getAsJsonObject();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return object;
    }
}