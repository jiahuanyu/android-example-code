package com.github.jiahuanyu.example.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.github.jiahuanyu.example.BaseActivity;
import com.github.jiahuanyu.example.R;

/**
 * Created by doom on 16/4/12.
 */
public class PreferenceEntranceActivity extends BaseActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initActivity(true, R.layout.activity_preference_entrance);
    }

    public void goToAndroid(View v)
    {
        startActivity(new Intent(this, PreferenceActivity.class));
    }

    public void goToLibrary(View v)
    {
        startActivity(new Intent(this, PreferenceSupportActivity.class));
    }
}
