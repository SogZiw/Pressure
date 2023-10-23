package com.pressure.record.acc;

import com.pressure.record.base.RecordSyncAdapter;

public final class RecordRunner implements Runnable {

    public final RecordSyncAdapter syncAdapter;

    public RecordRunner(RecordSyncAdapter syncAdapter) {
        this.syncAdapter = syncAdapter;
    }

    @Override
    public void run() {
        RecordSyncAdapter.sync(this.syncAdapter);
    }
}