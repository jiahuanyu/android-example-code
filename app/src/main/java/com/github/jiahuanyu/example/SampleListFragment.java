package com.github.jiahuanyu.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.jiahuanyu.example.application.ApplicationMenuActivity;
import com.github.jiahuanyu.example.function.FunctionMenuActivity;
import com.github.jiahuanyu.example.sensor.SensorMenuActivity;
import com.github.jiahuanyu.example.ui.UIMenuActivity;

/**
 * Created by doom on 2017/1/18.
 */

public class SampleListFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private final Class[] mClass =
            {
                    UIMenuActivity.class,
                    SensorMenuActivity.class,
                    FunctionMenuActivity.class,
                    ApplicationMenuActivity.class
            };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeFragment(R.layout.activity_menu);
        ListView menuListView = (ListView) mContent.findViewById(R.id.activity_menu_list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.menu_list_main));
        menuListView.setAdapter(adapter);
        menuListView.setOnItemClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(new Intent(getContext(), mClass[i]));
    }
}
