package com.github.jiahuanyu.example.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.jiahuanyu.example.BaseActivity;
import com.github.jiahuanyu.example.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by doom on 16/7/20.
 */
public class RecyclerViewActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private DataAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity(true, R.layout.activity_recycler_view);
        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_recycler_view_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter = new DataAdapter());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    protected void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }


    class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {
        @Override
        public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            DataViewHolder holder = new DataViewHolder(LayoutInflater.from(RecyclerViewActivity.this).inflate(R.layout.item_text, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(DataViewHolder holder, int position) {
            holder.tv.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class DataViewHolder extends RecyclerView.ViewHolder {
            TextView tv;

            public DataViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.item_text_content);
            }
        }
    }
}
