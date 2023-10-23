package com.pressure.record.base;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.pressure.record.news.AccountContainer;

public final class RecordBindAdapter extends Service {

    public static volatile RecordSyncAdapter adapter;

    public IBinder onBind(Intent intent) {

        try {
            if (adapter == null) {
                synchronized (RecordBindAdapter.class) {
                    if (adapter == null) {
                        Context applicationContext = getApplicationContext();
                        adapter = new RecordSyncAdapter(applicationContext, AccountContainer.ins.getInfo(applicationContext));
                    }
                }
            }
            RecordSyncAdapter syncAdapter = adapter;
            if (syncAdapter == null) {
                return null;
            }
            return syncAdapter.getSyncAdapterBinder();
        } catch (Throwable th) {
            return null;
        }
    }

    public void onCreate() {
        super.onCreate();
    }
}