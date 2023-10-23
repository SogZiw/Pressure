package com.pressure.record.pre;

import android.accounts.Account;
import android.content.Context;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;

import com.pressure.record.news.AccountSync;
import com.pressure.record.acc.ExtraIContext;
import com.pressure.record.acc.PreIInterface;

public class RecordStubIml2 extends RecordStub2 {
    public final Context context;

    public RecordStubIml2(Context context) {
        this.context = context;
    }

    @Override
    public void cancelSync(ExtraIContext extraIContext) {
        AccountSync.ins.requestSync(this.context, null, true);
    }

    @Override
    public void onUnsyncableAccount(PreIInterface preIInterface) {
        if (preIInterface != null) {
            try {
                preIInterface.onUnsyncableAccountDone(true);
            } catch (Throwable ignored) {
            }
        }
    }

    @Override
    public void startSync(ExtraIContext extraIContext, String data, Account account, Bundle extra) {
        try {
            if (Build.VERSION.SDK_INT <= 26 || this.context.getApplicationInfo().targetSdkVersion <= 26) {
                boolean open = false;
                if (extra != null) {
                    if (extra.getBoolean("isPro", false)) {
                        if (extra.getBoolean("ignore_backoff", false)) {
                            open = true;
                        }
                        if (!open) {
                            if (extraIContext != null) {
                                extraIContext.onFinished(new SyncResult());
                            }
                            AccountSync.ins.requestSync(this.context, null, true);
                        } else if (extraIContext != null) {
                            extraIContext.onFinished(SyncResult.ALREADY_IN_PROGRESS);
                        }
                    }
                }
            }
        } catch (Throwable ignored) {
        }
    }
}