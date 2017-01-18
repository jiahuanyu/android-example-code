package com.github.jiahuanyu.example;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;


/**
 * Created by doom on 15/6/18.
 */
public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    private ProgressDialog mProgressDialog; // Progress Dialog
    private ProgressBar mProgressBar;  // Progress Bar

    protected Activity mSelfActivity; // 自己

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSelfActivity = this;
        buildProgressBar();
    }

    private void buildProgressBar() {
        mProgressBar = new ProgressBar(this);
        mProgressBar.setVisibility(View.GONE);
    }

    protected void initializeActivity(int layoutId) {
        setContentView(layoutId);
        initializeActivity(null);
    }

    protected void initializeActivity(View contentView) {
        if (contentView != null) {
            setContentView(contentView);
        }
        addProgressBar();
    }

    /**
     * 将ProgressBar添加到ContentView中
     */
    protected void addProgressBar() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        addContentView(mProgressBar, params);
    }


    protected void showProgressDialog() {
        dismissProgressDialog();
        mProgressDialog = ProgressDialog.show(this, "", getResources().getString(R.string.common_loading), true, false);
    }

    protected void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }


    protected void showProgressBar() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    protected void dismissProgressBar() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }
}
