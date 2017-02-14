package github.kumarpiyush.awo.Helpers;

import android.net.Uri;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import github.kumarpiyush.awo.Constants;
import github.kumarpiyush.awo.Contracts.AppCredentials;
import github.kumarpiyush.awo.Contracts.AuthenticationResponse;

public class OwaHelpers {
    public static String buildAuthorizationUrl() throws Exception {
        String parameters = "response_type=code" +
                "&client_id=" + Constants.Owa.clientId +
                "&redirect_uri=" + URLEncoder.encode(Constants.Owa.redirectUrl, "UTF-8") +
                "&scope=" + URLEncoder.encode(Constants.Owa.scope, "UTF-8") +
                "&prompt=login";

        return Constants.Owa.authorizationUrl + "?" + parameters;
    }

    public static AuthenticationResponse authenticateAndGetRefreshToken(String authorizationCode, AppCredentials appCredentials) throws Exception {
        URL tokenEndpoint = new URL(Constants.Owa.tokenUrl);
        HttpURLConnection connection = (HttpURLConnection) tokenEndpoint.openConnection();

        connection.setRequestMethod("POST");
        addAuthenticationParametersToUrlConnection(connection, authorizationCode, appCredentials);

        connection.connect();

        String response = getAuthenticationResponseFromUrlConnection(connection);

        JSONObject parsedResponse = new JSONObject(response);
        String refreshToken = parsedResponse.getString("refresh_token");

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.refreshToken = refreshToken;
        return authenticationResponse;
    }

    private static void addAuthenticationParametersToUrlConnection(HttpURLConnection connection, String authorizationCode, AppCredentials appCredentials) throws Exception {
        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("grant_type", "authorization_code")
                .appendQueryParameter("code", authorizationCode)
                .appendQueryParameter("scope", Constants.Owa.scope)
                .appendQueryParameter("redirect_uri", Constants.Owa.redirectUrl)
                .appendQueryParameter("client_id", appCredentials.AppId)
                .appendQueryParameter("client_secret", appCredentials.AppSecret);

        String query = builder.build().getEncodedQuery();

        OutputStream os = connection.getOutputStream();
        BufferedWriter osWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        osWriter.write(query);
        osWriter.flush();
        osWriter.close();
        os.close();
    }

    private static String getAuthenticationResponseFromUrlConnection(HttpURLConnection connection) throws Exception {
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
