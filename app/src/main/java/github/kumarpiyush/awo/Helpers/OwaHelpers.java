package github.kumarpiyush.awo.Helpers;

import android.net.Uri;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import github.kumarpiyush.awo.Constants;
import github.kumarpiyush.awo.Contracts.AppCredentials;
import github.kumarpiyush.awo.Contracts.AuthenticationResponse;

public class OwaHelpers {
    public enum GrantType {
        authCode,
        refreshToken
    }

    public static String buildAuthorizationUrl() throws Exception {
        String parameters = "response_type=code" +
                "&client_id=" + Constants.Owa.clientId +
                "&redirect_uri=" + URLEncoder.encode(Constants.Owa.redirectUrl, "UTF-8") +
                "&scope=" + URLEncoder.encode(Constants.Owa.scope, "UTF-8") +
                "&prompt=login";

        return Constants.Owa.authorizationUrl + "?" + parameters;
    }

    public static AuthenticationResponse getAuthTokensFromGrantToken(
            GrantType grantType,
            String grantToken,
            AppCredentials appCredentials) throws Exception {
        URL tokenEndpoint = new URL(Constants.Owa.tokenUrl);
        HttpURLConnection connection = (HttpURLConnection) tokenEndpoint.openConnection();

        connection.setRequestMethod("POST");
        addAuthenticationParametersToUrlConnection(grantType, connection, grantToken, appCredentials);

        connection.connect();

        String response = ConnectionHelpers.getConnectionResponse(connection);

        JSONObject parsedResponse = new JSONObject(response);
        String accessToken = parsedResponse.getString("access_token");
        String refreshToken = parsedResponse.getString("refresh_token");

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.refreshToken = refreshToken;
        authenticationResponse.accessToken = accessToken;

        return authenticationResponse;
    }

    private static void addAuthenticationParametersToUrlConnection(
            GrantType grantType,
            HttpURLConnection connection,
            String grantToken,
            AppCredentials appCredentials) throws Exception {
        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("scope", Constants.Owa.scope)
                .appendQueryParameter("redirect_uri", Constants.Owa.redirectUrl)
                .appendQueryParameter("client_id", appCredentials.AppId)
                .appendQueryParameter("client_secret", appCredentials.AppSecret);

        if (grantType == GrantType.authCode) {
            builder.appendQueryParameter("grant_type", "authorization_code");
            builder.appendQueryParameter("code", grantToken);
        }
        else if (grantType == GrantType.refreshToken) {
            builder.appendQueryParameter("grant_type", "refresh_token");
            builder.appendQueryParameter("refresh_token", grantToken);
        }

        String query = builder.build().getEncodedQuery();

        OutputStream os = connection.getOutputStream();
        BufferedWriter osWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        osWriter.write(query);
        osWriter.flush();
        osWriter.close();
        os.close();
    }
}
