package com.pressure.record.news;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;

import com.pressure.record.R;

public final class SyncOne {

    public static final SyncOne ins = new SyncOne();

    public void requestSync(Context context, Account account) {
        try {
            Bundle bundle = new Bundle();
            bundle.putBoolean("isPro", true);
            bundle.putBoolean("need_judge", true);
            if (account == null) {
                account = new Account(context.getString(R.string.record_label_new), context.getString(R.string.record_acc_type));
            }
            ContentResolver.requestSync(account, context.getString(R.string.record_acc_auth), bundle);
        } catch (Throwable ignored) {
        }
    }
}