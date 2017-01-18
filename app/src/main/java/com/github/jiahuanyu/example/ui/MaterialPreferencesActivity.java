package com.github.jiahuanyu.example.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.jiahuanyu.example.R;
import com.github.jiahuanyu.example.ToolbarActivity;

/**
 * Created by doom on 16/7/6.
 */
public class MaterialPreferencesActivity extends ToolbarActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity(R.string.title_activity_material_preferences, true, R.layout.activity_material_preferences);
    }
}
