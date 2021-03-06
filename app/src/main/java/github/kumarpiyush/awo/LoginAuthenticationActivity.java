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

// Trades OWA OAuth authorization token with authentication/refresh token
public class LoginAuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_authentication);

        // TODO : do network call in async mode
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String refreshToken = null;

        try {
            refreshToken = getRefreshToken(getIntent().getData());
        }
        catch (Exception e) {
            Intent errorIntent = new Intent(this, DebugActivity.class);
            errorIntent.putExtra(Constants.errorDisplayMessageKey, e.toString());

            startActivity(errorIntent);
        }

        if (refreshToken != null) {
            saveRefreshToken(refreshToken);
            // Go back to home page
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
        }
    }

    private String getRefreshToken(Uri intentData) throws Exception {
        String authorizationCode = intentData.getQueryParameter("code");

        AuthenticationResponse response = OwaHelpers.getAuthTokensFromGrantToken(
                OwaHelpers.GrantType.authCode,
                authorizationCode,
                new AppCredentials(Constants.Owa.clientId, getString(R.string.app_secret)));

        return response.refreshToken;
    }

    private void saveRefreshToken(String refreshToken) {
        try {
            AccountManager manager = AccountManager.get(getBaseContext());
            final Account account = new Account("AWO user", Constants.accountType);
            manager.addAccountExplicitly(account, refreshToken, null);
        }
        catch (Exception e) {
            Intent errorIntent = new Intent(this, DebugActivity.class);
            errorIntent.putExtra(Constants.errorDisplayMessageKey, e.toString());

            startActivity(errorIntent);
        }
    }
}
