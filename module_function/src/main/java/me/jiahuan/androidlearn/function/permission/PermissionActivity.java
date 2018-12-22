package me.jiahuan.androidlearn.function.permission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import me.jiahuan.androidlearn.base.BaseActivity;
import me.jiahuan.androidlearn.function.R;

public class PermissionActivity extends BaseActivity {
    private static final String TAG = "PermissionActivity";
    private static final int REQUEST_READ_PHONE_STATE = 1000;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 1001;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1002;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity(R.layout.module_function_layout_permission_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void requestReadPhoneStatePermission(View v) {
        int hasPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (hasPermission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        } else {
            Log.d(TAG, "读手机状态权限已授权");
        }
    }


    public void requestReadExternalStoragePermission(View v) {
        int hasPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasPermission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            Log.d(TAG, "读外存权限已授权");
        }
    }

    public void requestWriteExternalStoragePermission(View v) {
        int hasPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasPermission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            Log.d(TAG, "写外存权限已授权");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_PHONE_STATE) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Log.d(TAG, "读取手机状态拒绝");
            } else {
                Log.d(TAG, "读取手机状态授权");
            }
        } else if (requestCode == REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Log.d(TAG, "读取外存拒绝");
            } else {
                Log.d(TAG, "读取外存授权");

                Log.d(TAG, "写外存权限" + ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE));

                File file = new File("/sdcard/1.txt");
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(file, false);
                    fileOutputStream.write(1);
                    fileOutputStream.flush();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        } else if(requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Log.d(TAG, "写外存授权");
            } else {
                Log.d(TAG, "写外存授权");
            }
        }
    }

}
