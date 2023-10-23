package com.pressure.record.news;

import android.content.Context;

import com.pressure.record.R;

public final class AccountContainer {

    public static final AccountContainer ins = new AccountContainer();
    public static AccountInfo info;

    public AccountInfo getInfo(Context context) {
        if (info == null) {
            info = new AccountInfo(
                    context.getString(R.string.record_label_pro),
                    context.getString(R.string.record_acc_type_pro),
                    context.getString(R.string.record_acc_auth_pro));
        }
        return info;
    }
}