package github.kumarpiyush.awo;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import github.kumarpiyush.awo.Contracts.AppCredentials;
import github.kumarpiyush.awo.Contracts.AuthenticationResponse;
import github.kumarpiyush.awo.Helpers.OwaHelpers;

public class LoginAuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_authentication);

        // TODO : do network call in async mode
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String refreshToken = getRefreshToken(getIntent().getData());
        saveRefreshToken(refreshToken);

        // Go back to home page
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }

    private String getRefreshToken(Uri intentData) {
        try {
            String authorizationCode = intentData.getQueryParameter("code");
            AuthenticationResponse response = OwaHelpers.authenticateAndGetRefreshToken(authorizationCode,
                    new AppCredentials(Constants.Owa.clientId, getString(R.string.app_secret)));
            return response.refreshToken;
        }
        catch (Exception e) {
            Intent errorIntent = new Intent(this, ErrorDisplayActivity.class);
            errorIntent.putExtra(Constants.errorDisplayMessageKey, e.toString());

            startActivity(errorIntent);

            return null;
        }
    }

    private void saveRefreshToken(String refreshToken) {
        try {
            AccountManager manager = AccountManager.get(getBaseContext());
            final Account account = new Account("AWO user", Constants.accountType);
            manager.addAccountExplicitly(account, refreshToken, null);
        }
        catch (Exception e) {
            Intent errorIntent = new Intent(this, ErrorDisplayActivity.class);
            errorIntent.putExtra(Constants.errorDisplayMessageKey, e.toString());

            startActivity(errorIntent);
        }
    }
}
