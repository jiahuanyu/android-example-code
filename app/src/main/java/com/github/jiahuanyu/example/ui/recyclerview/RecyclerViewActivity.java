package com.github.jiahuanyu.example.ui.recyclerview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.github.jiahuanyu.example.BaseActivity;
import com.github.jiahuanyu.example.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by doom on 16/7/20.
 */
public class RecyclerViewActivity extends BaseActivity {
    private CommonRecycleView mRecyclerView;
    private List<String> mDataList = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity(true, R.layout.activity_recycler_view);
        initializeData();
        mRecyclerView = (CommonRecycleView) findViewById(R.id.activity_recycler_view_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        CommonRecyclerAdapter commonRecyclerAdapter = new CommonRecyclerAdapter<String>(mSelfActivity, R.layout.item_text, mDataList) {

            @Override
            public void convert(CommonRecyclerViewHolder holder, String o) {
                holder.setText(R.id.item_text_content, o);
            }
        };
        HeaderAndFooterAdapter headerAndFooterAdapter = new HeaderAndFooterAdapter(commonRecyclerAdapter);
        TextView headerView = new TextView(mSelfActivity);
        headerView.setText("I'm Header or Footer. U Bitch");
        headerView.setTextColor(Color.RED);
        headerAndFooterAdapter.addHeaderView(headerView);
        headerAndFooterAdapter.addFootView(headerView);
        mRecyclerView.setAdapter(headerAndFooterAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration());
    }

    private void initializeData() {
        for (int i = 'A'; i < 'z'; i++) {
            mDataList.add("" + (char) i);
        }
    }
}
