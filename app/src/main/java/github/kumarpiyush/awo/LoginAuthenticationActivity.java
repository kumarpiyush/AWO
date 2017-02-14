package github.kumarpiyush.awo;

import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import github.kumarpiyush.awo.Contracts.AppCredentials;
import github.kumarpiyush.awo.Contracts.AuthenticationResponse;

public class LoginAuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_authentication);

        // TODO : do network call in parallel thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent callingIntent = getIntent();

        String authorizationCode = GetAuthorizationCode(callingIntent.getData());

        try {
            AuthenticationResponse response = OwaAuth.authenticateAndGetRefreshToken(authorizationCode, new AppCredentials(Constants.Owa.ClientId, getString(R.string.app_secret)));

            // Save the refresh token
        } catch (Exception e) {
            Log.e("LoginAuthentication", e.toString());
        }
    }

    public String GetAuthorizationCode(Uri intentData) {
        return intentData.getQueryParameter("code");
    }
}
