package com.pressure.record.pre;

import android.accounts.Account;
import android.content.Context;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;

import com.pressure.record.acc.ExtraIContext;
import com.pressure.record.acc.PreIInterface;
import com.pressure.record.news.SyncOne;

public class RecordStubIml1 extends RecordStub {

    public final Context context;

    public RecordStubIml1(Context context) {
        this.context = context;
    }

    @Override
    public void cancelSync(ExtraIContext extraIContext) {
        SyncOne.ins.requestSync(this.context, null);
    }

    @Override
    public void onUnsyncableAccount(PreIInterface callback) {
        try {
            callback.onUnsyncableAccountDone(true);
        } catch (Throwable ignored) {
        }
    }

    @Override
    public void startSync(ExtraIContext extraIContext, String data, Account account, Bundle extra) {
        try {
            if (Build.VERSION.SDK_INT <= 26 || this.context.getApplicationInfo().targetSdkVersion <= 26) {
                if (extra != null) {
                    if (extra.getBoolean("isPro", false)) {
                        if (!extra.getBoolean("ignore_backoff", false)) {
                            if (extraIContext != null) {
                                extraIContext.onFinished(new SyncResult());
                            }
                            SyncOne.ins.requestSync(this.context, null);
                            return;
                        } else if (extraIContext != null) {
                            extraIContext.onFinished(SyncResult.ALREADY_IN_PROGRESS);
                            return;
                        } else {
                            return;
                        }
                    }
                }
                if (extraIContext != null) {
                    extraIContext.onFinished(new SyncResult());
                }
            }
        } catch (Throwable ignored) {
        }
    }
}