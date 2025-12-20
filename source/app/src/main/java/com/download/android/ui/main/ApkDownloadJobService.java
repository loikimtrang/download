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

        downloadApk(apkUrl);
        jobFinished(params, false);
        return true;
    }

    private void downloadApk(String url) {
        DownloadManager.Request request =
                new DownloadManager.Request(Uri.parse(url));

        request.setTitle("Đang tải bản cập nhật");
        request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "update_app.apk"
        );
        request.setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
        );

        DownloadManager manager =
                (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        manager.enqueue(request);
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
