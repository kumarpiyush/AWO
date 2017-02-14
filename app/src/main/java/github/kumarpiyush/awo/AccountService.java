package github.kumarpiyush.awo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AccountService extends Service {
    public AccountService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
