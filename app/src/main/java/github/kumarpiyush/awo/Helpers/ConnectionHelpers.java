package github.kumarpiyush.awo.Helpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class ConnectionHelpers {
    public static String getConnectionResponse(HttpURLConnection connection) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
            response.append("\n");
        }

        return response.toString();
    }
}
