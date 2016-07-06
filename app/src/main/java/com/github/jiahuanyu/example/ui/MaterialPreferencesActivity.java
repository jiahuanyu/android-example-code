package com.github.jiahuanyu.example.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.jiahuanyu.example.BaseActivity;
import com.github.jiahuanyu.example.R;

/**
 * Created by doom on 16/7/6.
 */
public class MaterialPreferencesActivity extends BaseActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initActivity(true, R.layout.activity_material_preferences);
    }
}
