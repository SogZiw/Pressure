package com.pressure.record.base;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class RecordAuthService extends Service {

    public RecordAuthNew auth;

    public static final class RecordAuthNew extends AbstractAccountAuthenticator {
        public RecordAuthNew(Context context) {
            super(context);
        }

        @Override
        public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) {
            return null;
        }

        @Override
        public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) {
            return null;
        }

        @Override
        public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
            return null;
        }

        @Override
        public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) {
            return null;
        }

        @Override
        public String getAuthTokenLabel(String str) {
            return null;
        }

        @Override
        public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) {
            return null;
        }

        @Override
        public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) {
            return null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.auth.getIBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.auth = new RecordAuthNew(getApplicationContext());
    }
}
