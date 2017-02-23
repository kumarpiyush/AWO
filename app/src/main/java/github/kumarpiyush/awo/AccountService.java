package github.kumarpiyush.awo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

// I don't know what this does
public class AccountService extends Service {
    public AccountService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
