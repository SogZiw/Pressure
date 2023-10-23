package com.pressure.record.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.pressure.record.acc.QueryInterface;

public abstract class BaseRecordService extends Service {

    public final QueryBinderIml iBinder = new QueryBinderIml(this);

    public static final class QueryBinderIml extends QueryInterface.QueryBinder {

        public final BaseRecordService mService;

        public QueryBinderIml(BaseRecordService service) {
            this.mService = service;
        }

        @Override
        public void onError() {
        }
    }

    public BaseRecordService() {
    }

    public IBinder onBind(Intent intent) {
        return this.iBinder;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }
}