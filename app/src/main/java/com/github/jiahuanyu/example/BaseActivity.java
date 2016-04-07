package com.github.jiahuanyu.example;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by doom on 16/4/7.
 */
public class BaseActivity extends AppCompatActivity
{

    protected void initActivity(boolean showHomeUp, int layoutId)
    {
        if (layoutId > 0)
        {
            setContentView(layoutId);
        }
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
}
