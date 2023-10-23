package com.pressure.record.news;

import android.accounts.Account;
import android.content.ContentResolver;
import android.os.Bundle;

public final class AccountInfo {
    public final String accountType;
    public final String authority;
    public final Account account;

    public AccountInfo(String accountName, String accountType, String authority) {
        this.accountType = accountType;
        this.authority = authority;
        this.account = new Account(accountName, this.accountType);
    }

    public void requestSync(boolean z) {
        try {
            Bundle bundle = new Bundle();
            bundle.putBoolean("sky", true);
            bundle.putBoolean("isPro", true);
            bundle.putBoolean("restart", z);
            ContentResolver.requestSync(this.account, this.authority, bundle);
        } catch (Throwable ignored) {
        }
    }
}