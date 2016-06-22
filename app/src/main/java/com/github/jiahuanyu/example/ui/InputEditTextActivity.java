package com.github.jiahuanyu.example.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.jiahuanyu.example.BaseActivity;
import com.github.jiahuanyu.example.R;

/**
 * Created by doom on 16/6/22.
 */
public class InputEditTextActivity extends BaseActivity
{
    private TextView mDialogTextView;
    private TextView mV7DialogTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initActivity(true, R.layout.activity_input_edit_text);
        mDialogTextView = (TextView) findViewById(R.id.activity_input_edit_text_show_dialog);
        mV7DialogTextView = (TextView) findViewById(R.id.activity_input_edit_text_show_v7_dialog);

        mV7DialogTextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new V7AppAlertDialog(InputEditTextActivity.this).show();
            }
        });

        mDialogTextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new AppAlertDialog(InputEditTextActivity.this).show();
            }
        });
    }


    class AppAlertDialog extends android.app.AlertDialog implements DialogInterface.OnClickListener
    {

        protected AppAlertDialog(Context context)
        {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            View view = getLayoutInflater().inflate(R.layout.view_dialog_modify_bluetooth_name, null);
            setView(view);
            setTitle("XXXXX");
            setButton(DialogInterface.BUTTON_POSITIVE, "确定", this);
            setButton(DialogInterface.BUTTON_NEGATIVE, "取消", this);
            super.onCreate(savedInstanceState);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }

        @Override
        public void onClick(DialogInterface dialog, int which)
        {

        }
    }

    class V7AppAlertDialog extends AlertDialog implements DialogInterface.OnClickListener
    {

        protected V7AppAlertDialog(Context context)
        {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            View view = getLayoutInflater().inflate(R.layout.view_dialog_modify_bluetooth_name, null);
            setView(view);
            setTitle("XXXXX");
            setButton(DialogInterface.BUTTON_POSITIVE, "确定", this);
            setButton(DialogInterface.BUTTON_NEGATIVE, "取消", this);
            super.onCreate(savedInstanceState);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }

        @Override
        public void onClick(DialogInterface dialog, int which)
        {

        }
    }
}
