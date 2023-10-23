package com.pressure.record.acc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface QueryInterface extends IInterface {

    String interfaceName = "com.pressure.record.newService";

    abstract class QueryBinder extends Binder implements QueryInterface {

        public QueryBinder() {
            attachInterface(this, interfaceName);
        }

        public IBinder asBinder() {
            return this;
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code == 1598968902) {
                reply.writeString(interfaceName);
                return true;
            } else if (code != 1) {
                return super.onTransact(code, data, reply, flags);
            } else {
                data.enforceInterface(interfaceName);
                onError();
                reply.writeNoException();
                return true;
            }
        }
    }

    void onError() throws RemoteException;
}