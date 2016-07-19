package com.github.jiahuanyu.example;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by doom on 16/4/7.
 */
public class BaseActivity extends AppCompatActivity
{
    private ProgressDialog mProgressDialog;

    protected void initActivity(boolean showHomeUp, int layoutId)
    {
        if (layoutId < 0)
        {
            throw new RuntimeException("Layout Id must greater than 0");
        }
        initActivity(showHomeUp, getLayoutInflater().inflate(layoutId, null));
    }

    protected void initActivity(boolean showHomeUp, View layout)
    {
        if (layout == null)
        {
            throw new RuntimeException("layout should not be null");
        }
        setContentView(layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeUp);
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

    protected void showProgressDialog()
    {
        dismissProgressDialog();
        mProgressDialog = ProgressDialog.show(this, "", "waiting...", true, false);
    }

    protected void dismissProgressDialog()
    {
        if (mProgressDialog != null && mProgressDialog.isShowing())
        {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
