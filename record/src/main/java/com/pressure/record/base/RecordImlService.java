package com.pressure.record.base;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.pressure.record.pre.RecordStubIml1;

public final class RecordImlService extends Service {

    public IBinder onBind(Intent intent) {

        try {
            Context applicationContext = getApplicationContext();
            try {
                return new RecordStubIml1(applicationContext).asBinder();
            } catch (Throwable unused) {
                return null;
            }
        } catch (Throwable th) {
            return null;
        }
    }

    public void onCreate() {
        super.onCreate();
    }
}