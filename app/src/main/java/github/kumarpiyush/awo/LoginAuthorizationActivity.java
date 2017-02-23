package github.kumarpiyush.awo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import github.kumarpiyush.awo.Helpers.OwaHelpers;

// Authorizes app using OWA OAuth
public class LoginAuthorizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_authorization);

        startAuthorization(null);
    }

    public void startAuthorization(View view) {
        try {
            String url = OwaHelpers.buildAuthorizationUrl();

            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
            startActivity(intent);
        }
        catch (Exception e) {
            Intent errorIntent = new Intent(this, ErrorDisplayActivity.class);
            errorIntent.putExtra(Constants.errorDisplayMessageKey, e.toString());

            startActivity(errorIntent);
        }
    }
}