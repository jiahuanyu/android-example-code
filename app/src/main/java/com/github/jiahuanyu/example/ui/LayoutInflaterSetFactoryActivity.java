package com.github.jiahuanyu.example.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.github.jiahuanyu.example.BaseActivity;
import com.github.jiahuanyu.example.R;

/**
 * Created by doom on 16/6/13.
 */
public class LayoutInflaterSetFactoryActivity extends BaseActivity
{
    private static final String TAG = "LayoutInflaterSetFactoryActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        LayoutInflaterCompat.setFactory(LayoutInflater.from(this), new LayoutInflaterFactory()
        {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs)
            {
                Log.d(TAG, "Component Name = " + name);
                int n = attrs.getAttributeCount();
                for (int i = 0; i < n; i++)
                {
                    Log.d(TAG, attrs.getAttributeName(i) + " , " + attrs.getAttributeValue(i));
                }
                return null;
            }
        });
        super.onCreate(savedInstanceState);
        initActivity(true, R.layout.activity_set_factory);
    }
}
