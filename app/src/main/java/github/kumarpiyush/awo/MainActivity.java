package github.kumarpiyush.awo;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isUserLoggedIn()) {
            // TODO : navigate to email display activity

            // Hijack error intent to display success
            Intent errorIntent = new Intent(this, ErrorDisplayActivity.class);
            errorIntent.putExtra(Constants.errorDisplayMessageKey, "Refresh token exists");
            startActivity(errorIntent);
        }
        else {
            Intent intent = new Intent(this, LoginAuthorizationActivity.class);
            startActivity(intent);
        }
    }

    private boolean isUserLoggedIn() {
        try {
            AccountManager manager = AccountManager.get(getBaseContext());
            final Account account = new Account("AWO user", Constants.accountType);
            return manager.getPassword(account) != null;
        }
        catch (Exception e) {
            return false;
        }
    }
}
