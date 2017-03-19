package github.kumarpiyush.awo.Sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.NotificationManager;
import android.content.Context;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;

import github.kumarpiyush.awo.Constants;
import github.kumarpiyush.awo.Contracts.AppCredentials;
import github.kumarpiyush.awo.Helpers.OwaHelpers;
import github.kumarpiyush.awo.R;

class MailComponent implements ISyncComponent {
    private Context context;
    private String accessToken;

    MailComponent(Context context) {
        this.context = context;
    }

    @Override
    public boolean updateState() {
        AccountManager manager = AccountManager.get(context);
        final Account account = new Account("AWO user", Constants.accountType);
        String refreshToken = manager.getPassword(account);

        try {
            // TODO : do network call in async mode
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            accessToken = OwaHelpers.getAuthTokensFromGrantToken(
                    OwaHelpers.GrantType.refreshToken,
                    refreshToken,
                    new AppCredentials(Constants.Owa.clientId, this.context.getString(R.string.app_secret))).accessToken;
        }
        catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean notifyChanges() {
        NotificationCompat.Builder nBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Access token")
                        .setContentText(accessToken);

        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify((int) (System.currentTimeMillis() & 0xfffffff), nBuilder.build());

        return true;
    }
}
