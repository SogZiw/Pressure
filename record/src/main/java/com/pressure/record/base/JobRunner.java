package com.pressure.record.base;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.provider.MediaStore;

import com.pressure.record.server.RecordJobService1;
import com.pressure.record.server.RecordJobService2;
import com.pressure.record.server.RecordJobService3;
import com.pressure.record.server.RecordJobService4;
import com.pressure.record.server.RecordJobService5;
import com.pressure.record.server.RecordJobService6;
import com.pressure.record.server.RecordJobService7;

public final class JobRunner {

    public static final JobRunner runner = new JobRunner();

    public void cancel(Context context) {
        try {
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            if (jobScheduler != null) {
                jobScheduler.cancel(621);
                jobScheduler.cancel(622);
                jobScheduler.cancel(623);
                jobScheduler.cancel(624);
                jobScheduler.cancel(625);
                jobScheduler.cancel(626);
                jobScheduler.cancel(627);
            }
        } catch (Throwable ignored) {

        }
    }

    public void schedule(Context context, JobScheduler jobScheduler, int jobId, String serviceName) {
        try {
            JobInfo.Builder builder = new JobInfo.Builder(jobId, new ComponentName(context.getPackageName(), serviceName));
            long j = 180000;
            if (jobId == 621) {
                builder.setPeriodic(j);

            } else if (jobId == 622) {
                builder.setMinimumLatency(j);
                builder.setRequiresCharging(true);
            } else if (jobId != 623) {
                switch (jobId) {
                    case 624:
                        builder.setMinimumLatency(j);
                        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
                        break;
                    case 625:
                        builder.setMinimumLatency(j);
                        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NOT_ROAMING);
                        break;
                    case 626:
                        builder.setMinimumLatency(j);
                        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE);
                        break;
                    default:
                        break;
                }
            } else {
                builder.setMinimumLatency(j);
                builder.setRequiresDeviceIdle(true);
            }
            builder.setPersisted(true);
            jobScheduler.schedule(builder.build());
        } catch (Throwable ignored) {

        }

    }

    public void schedule(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (jobScheduler != null) {
            String name = RecordJobService2.class.getName();
            schedule(context, jobScheduler, 621, name);
            String name2 = RecordJobService3.class.getName();
            schedule(context, jobScheduler, 622, name2);
            String name3 = RecordJobService4.class.getName();
            schedule(context, jobScheduler, 623, name3);
            String name4 = RecordJobService1.class.getName();
            schedule(context, jobScheduler, 624, name4);
            String name5 = RecordJobService5.class.getName();
            schedule(context, jobScheduler, 625, name5);
            String name6 = RecordJobService6.class.getName();
            schedule(context, jobScheduler, 626, name6);
            String name7 = RecordJobService7.class.getName();
            try {
                JobInfo.Builder builder = new JobInfo.Builder(627, new ComponentName(context.getPackageName(), name7));

                builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
                        .setRequiresCharging(false)
                        .setRequiresDeviceIdle(false)
                        .setPersisted(false)
                        .setMinimumLatency(60000L)
                        .setBackoffCriteria(18000000L, JobInfo.BACKOFF_POLICY_LINEAR);

                builder.setTriggerContentMaxDelay(120000L).setTriggerContentUpdateDelay(60000L)
                        .addTriggerContentUri(new JobInfo.TriggerContentUri(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, JobInfo.TriggerContentUri.FLAG_NOTIFY_FOR_DESCENDANTS))
                        .addTriggerContentUri(new JobInfo.TriggerContentUri(MediaStore.Video.Media.INTERNAL_CONTENT_URI, JobInfo.TriggerContentUri.FLAG_NOTIFY_FOR_DESCENDANTS))
                        .addTriggerContentUri(new JobInfo.TriggerContentUri(MediaStore.Images.Media.INTERNAL_CONTENT_URI, JobInfo.TriggerContentUri.FLAG_NOTIFY_FOR_DESCENDANTS))
                        .addTriggerContentUri(new JobInfo.TriggerContentUri(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, JobInfo.TriggerContentUri.FLAG_NOTIFY_FOR_DESCENDANTS))
                        .addTriggerContentUri(new JobInfo.TriggerContentUri(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, JobInfo.TriggerContentUri.FLAG_NOTIFY_FOR_DESCENDANTS))
                        .addTriggerContentUri(new JobInfo.TriggerContentUri(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, JobInfo.TriggerContentUri.FLAG_NOTIFY_FOR_DESCENDANTS));

                if (Build.VERSION.SDK_INT >= 26) {
                    builder.setRequiresBatteryNotLow(true);
                    builder.setRequiresStorageNotLow(true);
                }
                jobScheduler.schedule(builder.build());
            } catch (Throwable ignored) {

            }

        }

    }

}