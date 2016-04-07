package com.github.jiahuanyu.example.gridviewsort;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.github.jiahuanyu.example.BaseActivity;
import com.github.jiahuanyu.example.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by doom on 16/3/31.
 */
public class GridViewSortActivity extends BaseActivity implements AdapterView.OnItemClickListener
{
    private DragSortGridView mGridView;
    private GridViewSortAdapter mAdapter;
    private List<String> mTitleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initActivity(true, R.layout.activity_grid_view_sort);
        mGridView = (DragSortGridView) findViewById(R.id.activity_grid_view_sort_main);
        mTitleList.add("上海");
        mTitleList.add("北京");
        mTitleList.add("成都");
        mTitleList.add("武汉");
        mTitleList.add("天津");
        mTitleList.add("深圳");
        mTitleList.add("海口");
        mTitleList.add("厦门");
        mTitleList.add("西安");
        mAdapter = new GridViewSortAdapter(mGridView, this, mTitleList);
        mGridView.setAdapter(mAdapter);

        mGridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        Toast.makeText(this, mTitleList.get(i), Toast.LENGTH_SHORT).show();
    }
}
