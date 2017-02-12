package github.kumarpiyush.awo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoginAuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_authentication);

        Intent callingIntent = getIntent();

        String authorizationCode = GetAuthorizationCode(callingIntent.getData());
    }

    public String GetAuthorizationCode(Uri intentData) {
        return intentData.getQueryParameter("code");
    }
}
