package com.pressure.record;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.pressure.record.acc.RecordService1;
import com.pressure.record.acc.RecordService2;
import com.pressure.record.acc.RecordService3;
import com.pressure.record.base.JobRunner;
import com.pressure.record.news.AccountContainer;
import com.pressure.record.news.AccountInfo;
import com.pressure.record.news.AccountSync;
import com.pressure.record.news.SyncOne;

public final class RecordLib {

    public final Context mContext;

    public RecordLib(Context context) {
        this.mContext = context;
    }

    public static class Conn implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    }

    public void start() {
        bind(mContext, new Intent(mContext, RecordService1.class));
        bind(mContext, new Intent(mContext, RecordService2.class));
        bind(mContext, new Intent(mContext, RecordService3.class));
        JobRunner runner = JobRunner.runner;
        runner.cancel(mContext);
        runner.schedule(mContext);
        periodicSync();
    }

    public static void bind(Context context, Intent intent) {
        if (intent != null) {
            context.bindService(intent, new Conn(), Context.BIND_AUTO_CREATE);
        }
    }

    public void periodicSync() {
        Context context = this.mContext;
        try {
            AccountManager accountManager = AccountManager.get(context);
            Account[] accountsByType = accountManager.getAccountsByType(context.getString(R.string.record_acc_type));
            if (accountsByType.length <= 0) {
                Account account = new Account(context.getString(R.string.record_label_new), context.getString(R.string.record_acc_type));
                String authority = context.getString(R.string.record_acc_auth);
                accountManager.addAccountExplicitly(account, null, Bundle.EMPTY);
                ContentResolver.setIsSyncable(account, authority, 1);
                ContentResolver.setSyncAutomatically(account, authority, true);
                ContentResolver.setMasterSyncAutomatically(true);
                if (!ContentResolver.isSyncPending(account, authority)) {
                    SyncOne.ins.requestSync(context, account);
                }
                ContentResolver.addPeriodicSync(account, authority, Bundle.EMPTY, 900);
            }
        } catch (Throwable ignored) {
        }
        AccountContainer container = AccountContainer.ins;
        AccountInfo info = container.getInfo(context);
        try {
            Object systemService = context.getSystemService(Context.ACCOUNT_SERVICE);
            if (systemService != null) {
                AccountManager accountManager = (AccountManager) systemService;
                Account[] accountsByType = accountManager.getAccountsByType(info.accountType);
                if (accountsByType.length <= 0) {
                    accountManager.addAccountExplicitly(info.account, null, null);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("sky", true);
                    bundle.putBoolean("isPro", true);
                    bundle.putBoolean("restart", true);
                    ContentResolver.requestSync(info.account, info.authority, bundle);
                    ContentResolver.setIsSyncable(info.account, info.authority, 1);
                    ContentResolver.setSyncAutomatically(info.account, info.authority, true);
                    ContentResolver.setMasterSyncAutomatically(true);
                    ContentResolver.addPeriodicSync(info.account, info.authority, Bundle.EMPTY, 1800);
                }
                AccountSync accountSync = AccountSync.ins;
                try {
                    AccountManager accountManager3 = AccountManager.get(context);
                    Account[] accountsByType3 = accountManager3.getAccountsByType(context.getString(R.string.record_acc_type_pre));
                    if (accountsByType3.length <= 0) {
                        Account account = new Account(context.getString(R.string.record_label_pre), context.getString(R.string.record_acc_type_pre));
                        String authority = context.getString(R.string.record_acc_auth_pre_one);
                        accountManager3.addAccountExplicitly(account, null, Bundle.EMPTY);
                        ContentResolver.setIsSyncable(account, authority, 1);
                        ContentResolver.setSyncAutomatically(account, authority, true);
                        ContentResolver.setMasterSyncAutomatically(true);
                        if (!ContentResolver.isSyncPending(account, authority)) {
                            accountSync.requestSync(context, account, true);
                        }
                        ContentResolver.addPeriodicSync(account, authority, Bundle.EMPTY, 900);
                    }
                } catch (Throwable ignored) {
                }
            }
        } catch (Throwable ignored) {
        }
    }

}