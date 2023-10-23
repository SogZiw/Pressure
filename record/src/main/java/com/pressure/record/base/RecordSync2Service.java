package com.pressure.record.base;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.pressure.record.pre.RecordStub2;

public final class RecordSync2Service extends Service {

    public static volatile RecordStub2 stub2;

    public IBinder onBind(Intent intent) {
        try {
            if (stub2 == null) {
                synchronized (RecordSync2Service.class) {
                    if (stub2 == null) {
                        Context applicationContext = getApplicationContext();
                        stub2 = new RecordSync2(applicationContext);
                    }
                }
            }
            RecordStub2 stub = stub2;
            if (stub != null) {
                try {
                    return stub.asBinder();
                } catch (Throwable ignored) {
                }
            }
        } catch (Throwable ignored) {
        }
        return null;
    }

    public void onCreate() {
        super.onCreate();
    }
}