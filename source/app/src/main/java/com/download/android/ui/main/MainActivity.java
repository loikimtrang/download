package com.download.android.ui.main;

import android.app.DownloadManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.download.android.BR;
import com.download.android.R;
import com.download.android.data.model.api.response.VersionResponse;
import com.download.android.databinding.ActivityMainBinding;
import com.download.android.di.component.ActivityComponent;
import com.download.android.ui.base.activity.BaseActivity;


public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setA(this);
        viewBinding.setVm(viewModel);
    }
    public void onClickDownload() {
        viewModel.downloadStatus.setValue("Đang tải...");

        VersionResponse versionResponse = new VersionResponse();
        versionResponse.setUrlApk("https://ra-dpc-update-dev.racontrol.de/app.apk");

        PersistableBundle bundle = new PersistableBundle();
        bundle.putString("apk_url", versionResponse.getUrlApk());

        ComponentName componentName = new ComponentName(this, ApkDownloadJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(123, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(false)
                .setOverrideDeadline(0)
                .setExtras(bundle)
                .build();

        JobScheduler scheduler = (JobScheduler) this.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        scheduler.schedule(jobInfo);

        Toast.makeText(this, "Bắt đầu tải APK", Toast.LENGTH_SHORT).show();
    }
    private final BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra(
                    DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            DownloadManager dm =
                    (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(id);

            try (Cursor cursor = dm.query(query)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int status = cursor.getInt(
                            cursor.getColumnIndexOrThrow(
                                    DownloadManager.COLUMN_STATUS));

                    if (status == DownloadManager.STATUS_SUCCESSFUL) {
                        String localUri = cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        DownloadManager.COLUMN_LOCAL_URI));

                        String path = Uri.parse(localUri).getPath();

                        Toast.makeText(
                                MainActivity.this,
                                "APK tải xong: " + path,
                                Toast.LENGTH_LONG
                        ).show();

                        viewModel.downloadStatus.setValue("Tải xong");
                        viewBinding.status.setText("Tải xong");
                    }
                }
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(
                downloadReceiver,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(downloadReceiver);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }
    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }
}
