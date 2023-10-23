package com.pressure.record.news;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;

import com.pressure.record.R;

public final class AccountSync {

    public static final AccountSync ins = new AccountSync();

    public void requestSync(Context context, Account account, boolean z) {
        try {
            Bundle bundle = new Bundle();
            bundle.putBoolean("isPro", true);
            if (z) {
                bundle.putBoolean("need_judge", true);
            }
            if (account == null) {
                account = new Account(context.getString(R.string.record_label_pre), context.getString(R.string.record_acc_type_pre));
            }
            ContentResolver.requestSync(account, context.getString(R.string.record_acc_auth_pre_one), bundle);
        } catch (Throwable ignored) {
        }
    }
}