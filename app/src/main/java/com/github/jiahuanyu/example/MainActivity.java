package com.github.jiahuanyu.example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.jiahuanyu.example.function.FunctionMenuActivity;
import com.github.jiahuanyu.example.sensor.SensorMenuActivity;
import com.github.jiahuanyu.example.ui.UIMenuActivity;


/**
 * Created by doom on 16/3/30.
 */
public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener
{
    private ListView mMenuListView;

    private final Class[] mClass =
            {
                    UIMenuActivity.class,
                    SensorMenuActivity.class,
                    FunctionMenuActivity.class
            };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initActivity(false, R.layout.activity_menu);
        mMenuListView = (ListView) findViewById(R.id.activity_menu_list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.menu_list_main));
        mMenuListView.setAdapter(adapter);
        mMenuListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        startActivity(new Intent(this, mClass[i]));
    }
}