package com.github.jiahuanyu.example.ui;

import android.os.Bundle;

import com.github.jiahuanyu.example.R;
import com.github.jiahuanyu.example.ToolbarActivity;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by doom on 16/6/13.
 */
public class LargeImageViewActivity extends ToolbarActivity {
    private LargeImageView mLargeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity(R.string.title_activity_large_image_view, true, R.layout.activity_large_image_view);
        mLargeImageView = (LargeImageView) findViewById(R.id.id_largeImageview);
        try {
            InputStream inputStream = getAssets().open("qm.jpg");
            mLargeImageView.setInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
