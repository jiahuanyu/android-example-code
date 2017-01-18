package com.github.jiahuanyu.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.jiahuanyu.example.R;
import com.github.jiahuanyu.example.ToolbarActivity;

/**
 * Created by doom on 16/7/11.
 */
public class ApplicationMenuActivity extends ToolbarActivity implements AdapterView.OnItemClickListener {
    private ListView mMenuListView;

    private final Class[] mClass =
            {
                    CarMotionDetectionActivity.class,
            };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity(R.string.title_activity_application_menu, true, R.layout.activity_menu);
        mMenuListView = (ListView) findViewById(R.id.activity_menu_list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.menu_list_application));
        mMenuListView.setAdapter(adapter);
        mMenuListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(new Intent(this, mClass[i]));
    }
}
