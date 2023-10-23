package com.pressure.record.server;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class RecordJobService1 extends JobService {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

}
