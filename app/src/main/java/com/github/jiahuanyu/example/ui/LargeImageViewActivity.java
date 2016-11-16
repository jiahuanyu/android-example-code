package com.github.jiahuanyu.example.ui;

import android.os.Bundle;

import com.github.jiahuanyu.example.BaseActivity;
import com.github.jiahuanyu.example.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by doom on 16/6/13.
 */
public class LargeImageViewActivity extends BaseActivity
{
    private LargeImageView mLargeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initActivity(true, R.layout.activity_large_image_view);
        mLargeImageView = (LargeImageView) findViewById(R.id.id_largeImageview);
        try
        {
            InputStream inputStream = getAssets().open("qm.jpg");
            mLargeImageView.setInputStream(inputStream);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
