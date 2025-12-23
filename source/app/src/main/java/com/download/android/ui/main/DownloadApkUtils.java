package com.download.android.ui.main;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

public class DownloadApkUtils {

    public static long downloadApk(Context context, String apkUrl, String fileName) {
        if (apkUrl == null || apkUrl.isEmpty()) return -1;

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));
        request.setTitle("Đang tải bản cập nhật");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        return manager.enqueue(request);
    }
}

