package com.github.jiahuanyu.example;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;


/**
 * Created by doom on 16/3/30.
 */
public class MainActivity extends ToolbarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity(R.string.app_name, false, R.layout.activity_main);
        initializeView();
        setDefaultFragment();
    }

    private void initializeView() {
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.activity_main_bottom_navigation_bar);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_alarm, "Home"))
                .addItem(new BottomNavigationItem(R.drawable.ic_alarm, "Books"))
                .addItem(new BottomNavigationItem(R.drawable.ic_alarm, "Music"))
                .addItem(new BottomNavigationItem(R.drawable.ic_alarm, "Movies & TV"))
                .initialise();
    }


    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.activity_main_content, new SampleListFragment());
        transaction.commit();
    }

}