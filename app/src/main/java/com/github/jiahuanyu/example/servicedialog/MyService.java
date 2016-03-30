package com.github.jiahuanyu.example.servicedialog;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.github.jiahuanyu.example.R;

/**
 * Created by doom on 16/3/30.
 */
public class MyService extends Service
{
    private android.app.AlertDialog mCommonAlertDialog;
    private android.support.v7.app.AlertDialog mV7Dialog;
    private BroadcastReceiver mReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            if (ServiceDialogActivity.SHOW_COMMON_DIALOG_ACTION.equals(action))
            {
                showCommonDialog();
            }
            else if (ServiceDialogActivity.SHOW_V7_DIALOG_ACTION.equals(action))
            {
                showV7Dialog();
            }
        }
    };


    private void showCommonDialog()
    {
        if (mCommonAlertDialog == null)
        {
            mCommonAlertDialog = new android.app.AlertDialog.Builder(getApplicationContext())
                    .setPositiveButton("确定", null)
                    .setTitle("普通AlertDialog_Title")
                    .setMessage("普通AlertDialog_Message")
                    .create();
            mCommonAlertDialog.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
        }
        if (!mCommonAlertDialog.isShowing())
        {
            mCommonAlertDialog.show();
        }
    }

    private void showV7Dialog()
    {
        if (mV7Dialog == null)
        {
            mV7Dialog = new android.support.v7.app.AlertDialog.Builder(getApplicationContext(), R.style.AppTheme_AlertDialog)
                    .setPositiveButton("确定", null)
                    .setTitle("V7包AlertDialog_Title")
                    .setMessage("V7包AlertDialog_Message")
                    .create();
            mV7Dialog.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
        }
        if (!mV7Dialog.isShowing())
        {
            mV7Dialog.show();
        }
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ServiceDialogActivity.SHOW_COMMON_DIALOG_ACTION);
        filter.addAction(ServiceDialogActivity.SHOW_V7_DIALOG_ACTION);
        registerReceiver(mReceiver, filter);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
