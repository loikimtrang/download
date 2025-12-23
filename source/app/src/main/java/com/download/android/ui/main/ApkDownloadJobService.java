package com.download.android.ui.main;

import android.app.DownloadManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.net.Uri;
import android.os.Environment;

public class ApkDownloadJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        String apkUrl = params.getExtras().getString("apk_url");

        if (apkUrl == null || apkUrl.isEmpty()) {
            jobFinished(params, false);
            return false;
        }

        DownloadApkUtils.downloadApk(this, apkUrl, "update_app.apk");
        jobFinished(params, false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
