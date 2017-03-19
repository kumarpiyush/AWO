package github.kumarpiyush.awo.Sync;

import android.content.Context;

public class SyncComponentFactory {
    public static ISyncComponent[] getActiveComponents(Context context) {
        return new ISyncComponent[]{
                new MailComponent(context)
        };
    }
}
