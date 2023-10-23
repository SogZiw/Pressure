package com.pressure.record.acc;

import android.content.SyncResult;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ExtraIContext extends IInterface {

    abstract class Stub extends Binder implements ExtraIContext {
        public static final String DESCRIPTOR = "android.content.ISyncContext";

        public static class Proxy implements ExtraIContext {
            public static ExtraIContext sDefaultImpl;
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void onFinished(SyncResult syncResult) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (syncResult != null) {
                        obtain.writeInt(1);
                        syncResult.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (this.mRemote.transact(2, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().onFinished(syncResult);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override
            public void sendHeartbeat() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (this.mRemote.transact(1, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().sendHeartbeat();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ExtraIContext asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (!(queryLocalInterface instanceof ExtraIContext)) ? new Proxy(iBinder) : (ExtraIContext) queryLocalInterface;
        }

        public static ExtraIContext getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code != 1598968902) {
                if (code == 1) {
                    data.enforceInterface(DESCRIPTOR);
                    sendHeartbeat();
                } else if (code != 2) {
                    return super.onTransact(code, data, reply, flags);
                } else {
                    data.enforceInterface(DESCRIPTOR);
                    onFinished(data.readInt() != 0 ? SyncResult.CREATOR.createFromParcel(data) : null);
                }
                reply.writeNoException();
                return true;
            }
            reply.writeString(DESCRIPTOR);
            return true;
        }
    }

    void onFinished(SyncResult syncResult) throws RemoteException;

    void sendHeartbeat() throws RemoteException;
}