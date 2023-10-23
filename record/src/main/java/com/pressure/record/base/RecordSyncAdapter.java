package com.pressure.record.base;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.content.SyncStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.pressure.record.acc.RecordRunner;
import com.pressure.record.news.AccountInfo;

public final class RecordSyncAdapter extends AbstractThreadedSyncAdapter {
    public static final Handler mHandler = new Handler(Looper.getMainLooper());

    public final AccountInfo info;

    public RecordSyncAdapter(Context context, AccountInfo info) {
        super(context, true);
        this.info = info;
    }

    public static void sync(RecordSyncAdapter syncAdapter) {
        syncAdapter.info.requestSync(true);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult result) {
        boolean isSync = true;
        if (extras != null) {
            try {
                if (extras.getBoolean("restart")) {
                    SyncStats syncStats = null;
                    if (!isSync) {
                        if (result != null) {
                            syncStats = result.stats;
                        }
                        if (syncStats != null) {
                            syncStats.numIoExceptions = 0;
                        }
                        this.info.requestSync(false);
                        return;
                    }
                    SyncStats syncStats2 = result == null ? null : result.stats;
                    if (syncStats2 != null) {
                        syncStats2.numIoExceptions = 1;
                    }
                    mHandler.removeCallbacksAndMessages(null);
                    mHandler.postDelayed(new RecordRunner(this), 30000);
                }
            } catch (Throwable ignored) {
            }
        }
    }
}