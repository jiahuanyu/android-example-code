package com.github.jiahuanyu.example.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by doom on 16/7/22.
 */
public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<CommonRecyclerViewHolder> {

    private Context mContext;
    private List<T> mData;
    private int mItemLayoutID;

    public CommonRecyclerAdapter(Context context, int itemLayoutID, List<T> data) {
        mContext = context;
        mData = data;
        mItemLayoutID = itemLayoutID;
    }


    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommonRecyclerViewHolder viewHolder = CommonRecyclerViewHolder.get(mContext, parent, mItemLayoutID);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommonRecyclerViewHolder holder, int position) {
        convert(holder, mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public abstract void convert(RecyclerView.ViewHolder holder, T t);

}
