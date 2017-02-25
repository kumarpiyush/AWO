package github.kumarpiyush.awo.Sync;

import android.app.NotificationManager;
import android.content.Context;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import github.kumarpiyush.awo.Helpers.ConnectionHelpers;
import github.kumarpiyush.awo.R;

class TestComponent implements ISyncComponent {
    private Context context;
    private String data;

    TestComponent(Context context) {
        this.context = context;
    }

    @Override
    public boolean updateState() {
        try {
            // TODO : do network call in async mode
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL timeTeller = new URL("http://192.168.1.33:8080");
            HttpURLConnection con = (HttpURLConnection) timeTeller.openConnection();
            con.connect();
            data = ConnectionHelpers.getConnectionResponse(con);

            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean notifyChanges() {
        NotificationCompat.Builder nBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Time update")
                        .setContentText(data);

        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify((int) (System.currentTimeMillis() & 0xfffffff), nBuilder.build());

        return true;
    }
}
