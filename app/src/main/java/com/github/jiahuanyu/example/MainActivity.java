package com.github.jiahuanyu.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.jiahuanyu.example.dropdownmenu.DropDownMenuActivity;
import com.github.jiahuanyu.example.emptyview.EmptyViewActivity;
import com.github.jiahuanyu.example.gridviewsort.GridViewSortActivity;
import com.github.jiahuanyu.example.preference.PreferenceApplyActivity;
import com.github.jiahuanyu.example.servicedialog.ServiceDialogActivity;


/**
 * Created by doom on 16/3/30.
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    private ListView mContent;
    private final String[] mTitle =
            {
                    "在Service中弹出对话框",
                    "DropDownMenu",
                    "拖放排序GridView",
                    "擅用EmptyView",
                    "Preference"
            };

    private final Class[] mClass =
            {
                    ServiceDialogActivity.class,
                    DropDownMenuActivity.class,
                    GridViewSortActivity.class,
                    EmptyViewActivity.class,
                    PreferenceApplyActivity.class
            };

    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContent = (ListView) findViewById(R.id.activity_main_content);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mTitle);
        mContent.setAdapter(mAdapter);
        mContent.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        startActivity(new Intent(this, mClass[i]));
    }
}