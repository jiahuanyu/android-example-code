package com.github.jiahuanyu.example.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.jiahuanyu.example.R;
import com.github.jiahuanyu.example.ToolbarActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by doom on 16/4/7.
 */
public class EmptyViewActivity extends ToolbarActivity {
    private ListView mListView;

    private List<String> mData = new ArrayList<>();

    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity(R.string.title_activity_empty_view, true, R.layout.activity_empty_view);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mData);

        mListView = (ListView) findViewById(R.id.activity_empty_view_list_view);

        View emptyView = LayoutInflater.from(this).inflate(R.layout.view_empty_view, null);

        ((ViewGroup) mListView.getParent()).addView(emptyView);

        mListView.addHeaderView(makeHeaderFooterView());
        mListView.addFooterView(makeHeaderFooterView());

        mListView.setAdapter(mAdapter);
        loadData(null);
        mListView.setEmptyView(emptyView);

    }

    private View makeHeaderFooterView() {
        TextView t = new TextView(this);
        t.setText("ListView头部或者底部");
        t.setGravity(Gravity.CENTER);
        return t;
    }

    public void clearData(View v) {
        mData.clear();
        mAdapter.notifyDataSetChanged();
    }

    public void loadData(View v) {
        if (mData.size() == 0) {
            mData.add("上海");
            mData.add("北京");
            mData.add("成都");
            mData.add("武汉");
            mData.add("天津");
            mData.add("深圳");
            mData.add("海口");
            mData.add("厦门");
            mData.add("西安");
            mAdapter.notifyDataSetChanged();
        }
    }
}
