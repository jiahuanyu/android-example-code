package com.github.jiahuanyu.example.servicedialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.github.jiahuanyu.example.R;

/**
 * Created by doom on 16/3/30.
 */
public class ServiceDialogActivity extends AppCompatActivity
{
    public static final String SHOW_COMMON_DIALOG_ACTION = "com.github.jiahuanyu.example.servicedialog.SHOW_COON_DIALOG_ACTION";
    public static final String SHOW_V7_DIALOG_ACTION = "com.github.jiahuanyu.example.servicedialog.SHOW_V7_DIALOG_ACTION";

    private Intent mServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_service_dialog);
        mServiceIntent = new Intent(this, MyService.class);
        startService(mServiceIntent);
    }


    public void showCommonDialog(View v)
    {
        Intent intent = new Intent(SHOW_COMMON_DIALOG_ACTION);
        sendBroadcast(intent);
    }

    public void showV7Dialog(View v)
    {
        Intent intent = new Intent(SHOW_V7_DIALOG_ACTION);
        sendBroadcast(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        stopService(mServiceIntent);
    }
}
