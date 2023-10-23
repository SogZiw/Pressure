package com.pressure.record.acc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface PreIInterface extends IInterface {

    abstract class Stub extends Binder implements PreIInterface {
        public static final String DESCRIPTOR = "android.content.ISyncAdapterUnsyncableAccountCallback";

        public static class Proxy implements PreIInterface {
            public static PreIInterface sDefaultImpl;
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void onUnsyncableAccountDone(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    if (this.mRemote.transact(1, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().onUnsyncableAccountDone(z);
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

        public static PreIInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (!(queryLocalInterface instanceof PreIInterface)) ? new Proxy(iBinder) : (PreIInterface) queryLocalInterface;
        }

        public static PreIInterface getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            } else if (code != 1) {
                return super.onTransact(code, data, reply, flags);
            } else {
                data.enforceInterface(DESCRIPTOR);
                onUnsyncableAccountDone(data.readInt() != 0);
                reply.writeNoException();
                return true;
            }
        }
    }

    void onUnsyncableAccountDone(boolean z) throws RemoteException;
}