package com.pressure.record.base;

import android.accounts.Account;
import android.content.Context;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;

import com.pressure.record.acc.ExtraIContext;
import com.pressure.record.acc.PreIInterface;
import com.pressure.record.pre.RecordStubIml2;

public final class RecordSync2 extends RecordStubIml2 {
    public final Context mContext;

    public RecordSync2(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public void cancelSync(ExtraIContext extraIContext) {
    }

    @Override
    public void onUnsyncableAccount(PreIInterface callback) {
        if (callback != null) {
            try {
                callback.onUnsyncableAccountDone(false);
            } catch (Throwable ignored) {
            }
        }
    }

    @Override
    public void startSync(ExtraIContext extraIContext, String data, Account account, Bundle extra) {
        try {
            if (Build.VERSION.SDK_INT > 26 && this.mContext.getApplicationInfo().targetSdkVersion > 26) return;
            if (extraIContext != null) {
                extraIContext.onFinished(new SyncResult());
            }
        } catch (Throwable ignored) {
        }
    }
}