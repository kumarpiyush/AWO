package github.kumarpiyush.awo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Date;

public class SyncEventReceiver extends BroadcastReceiver {
    public SyncEventReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent errorIntent = new Intent(context, ErrorDisplayActivity.class);
        errorIntent.putExtra(Constants.errorDisplayMessageKey, "intent called " + new Date());
        errorIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(errorIntent);
    }
}
