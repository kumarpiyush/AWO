package github.kumarpiyush.awo;

import java.net.URLEncoder;

public class OwaAuth {
    public static String buildAuthenticationUrl() throws Exception {
        String parameters = "response_type=code" +
                "&client_id=" + Constants.Owa.ClientId +
                "&redirect_uri=" + URLEncoder.encode("http://localhost/awo/", "UTF-8") +
                "&scope=" + URLEncoder.encode("offline_access https://outlook.office.com/mail.read", "UTF-8") +
                "&prompt=login";

        return Constants.Owa.AuthorizationUrl + "?" + parameters;
    }
}
