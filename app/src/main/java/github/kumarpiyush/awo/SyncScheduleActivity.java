package github.kumarpiyush.awo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

// Schedules recurring sync events using AlarmManager
public class SyncScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, SyncEventReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, (int) (System.currentTimeMillis() & 0xfffffff), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                SystemClock.elapsedRealtime() + Constants.Sync.syncIntervalInMilliseconds,
                Constants.Sync.syncIntervalInMilliseconds,
                alarmIntent);
    }
}
