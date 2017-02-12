package github.kumarpiyush.awo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class LoginAuthorizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_authorization);

        startAuthorization(null);
    }

    public void startAuthorization(View view) {
        try {
            String url = OwaAuth.buildAuthorizationUrl();

            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            Log.e("LoginAuthorization", e.toString());
        }
    }
}