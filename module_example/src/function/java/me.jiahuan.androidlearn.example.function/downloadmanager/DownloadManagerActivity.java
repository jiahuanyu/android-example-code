package me.jiahuan.androidlearn.example.function.downloadmanager;

import android.Manifest;
import android.app.DownloadManager;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;

import me.jiahuan.androidlearn.base.BaseActivity;
import me.jiahuan.androidlearn.example.R;

public class DownloadManagerActivity extends BaseActivity {
    private long id;
    private DownloadManager mDownloadManager;
    private Button mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivityWithToolbar(R.layout.module_example_layout_download_manager, true);
        mButton = findViewById(R.id.id_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(DownloadManagerActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(DownloadManagerActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                    return;
                }
                download();
            }
        });
    }


    private void download() {
        mDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse("https://github.com/eddywm/KTFLITE/raw/master/releases/KTFLITE-0.0.1.apk"));
        // 设置notification 标题
        request.setTitle("我是Notification标题");
        request.setDescription("我是Notification描述");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        // 设置下载时允许网络
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "download.apk");
        request.setVisibleInDownloadsUi(true);
        // 开始下载
        id = mDownloadManager.enqueue(request);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDownloadManager.remove(id);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100 && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                download();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
