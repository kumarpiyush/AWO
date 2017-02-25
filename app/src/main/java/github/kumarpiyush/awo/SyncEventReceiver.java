package github.kumarpiyush.awo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import github.kumarpiyush.awo.Sync.SyncComponentFactory;
import github.kumarpiyush.awo.Sync.ISyncComponent;

public class SyncEventReceiver extends BroadcastReceiver {
    public SyncEventReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ISyncComponent[] activeComponents = SyncComponentFactory.getActiveComponents(context);
        for (ISyncComponent comp : activeComponents) {
            comp.updateState();
            comp.notifyChanges();
        }
    }
}
