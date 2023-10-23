package com.pressure.record.acc;

import android.accounts.Account;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface RecordIInterface extends IInterface {

    abstract class Stub extends Binder implements RecordIInterface {
        public static final String DESCRIPTOR = "android.content.ISyncAdapter";

        public static class Proxy implements RecordIInterface {
            public static RecordIInterface sDefaultImpl;
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void cancelSync(ExtraIContext extraIContext) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongBinder(extraIContext != null ? extraIContext.asBinder() : null);
                    if (this.mRemote.transact(3, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().cancelSync(extraIContext);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override
            public void onUnsyncableAccount(PreIInterface preIInterface) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongBinder(preIInterface != null ? preIInterface.asBinder() : null);
                    if (this.mRemote.transact(1, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().onUnsyncableAccount(preIInterface);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override
            public void startSync(ExtraIContext extraIContext, String data, Account account, Bundle extra) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongBinder(extraIContext != null ? extraIContext.asBinder() : null);
                    obtain.writeString(data);
                    if (account != null) {
                        obtain.writeInt(1);
                        account.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (extra != null) {
                        obtain.writeInt(1);
                        extra.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (this.mRemote.transact(2, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().startSync(extraIContext, data, account, extra);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "android.content.ISyncAdapter");
        }

        public static RecordIInterface getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String str = DESCRIPTOR;
            if (code != 1598968902) {
                if (code == 1) {
                    data.enforceInterface(str);
                    onUnsyncableAccount(PreIInterface.Stub.asInterface(data.readStrongBinder()));
                } else if (code == 2) {
                    data.enforceInterface(str);
                    ExtraIContext asInterface = ExtraIContext.Stub.asInterface(data.readStrongBinder());
                    String readString = data.readString();
                    Bundle bundle = null;
                    Account account = data.readInt() != 0 ? Account.CREATOR.createFromParcel(data) : null;
                    if (data.readInt() != 0) {
                        bundle = Bundle.CREATOR.createFromParcel(data);
                    }
                    startSync(asInterface, readString, account, bundle);
                } else if (code != 3) {
                    return super.onTransact(code, data, reply, flags);
                } else {
                    data.enforceInterface(str);
                    cancelSync(ExtraIContext.Stub.asInterface(data.readStrongBinder()));
                }
                reply.writeNoException();
                return true;
            }
            reply.writeString(str);
            return true;
        }
    }

    void cancelSync(ExtraIContext extraIContext) throws RemoteException;

    void onUnsyncableAccount(PreIInterface preIInterface) throws RemoteException;

    void startSync(ExtraIContext extraIContext, String data, Account account, Bundle extra) throws RemoteException;
}