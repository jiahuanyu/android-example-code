package com.github.jiahuanyu.example;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.jiahuanyu.example.application.ApplicationMenuActivity;
import com.github.jiahuanyu.example.function.FunctionMenuActivity;
import com.github.jiahuanyu.example.sensor.SensorMenuActivity;
import com.github.jiahuanyu.example.ui.UIMenuActivity;


/**
 * Created by doom on 16/3/30.
 */
public class MainActivity extends ToolbarActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "MainActivity";

    private final Class[] mClass =
            {
                    UIMenuActivity.class,
                    SensorMenuActivity.class,
                    FunctionMenuActivity.class,
                    ApplicationMenuActivity.class
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity(R.string.app_name, false, R.layout.activity_menu);
        ListView menuListView = (ListView) findViewById(R.id.activity_menu_list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.menu_list_main));
        menuListView.setAdapter(adapter);
        menuListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(new Intent(mSelfActivity, mClass[i]));
    }
}