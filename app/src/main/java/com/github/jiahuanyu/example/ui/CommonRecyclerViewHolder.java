package com.github.jiahuanyu.example.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by doom on 16/7/22.
 */
public class CommonRecyclerViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews = new SparseArray<>();
    private View mContent;

    public CommonRecyclerViewHolder(View itemView) {
        super(itemView);
        mContent = itemView;
    }


    public static CommonRecyclerViewHolder get(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        CommonRecyclerViewHolder holder = new CommonRecyclerViewHolder(itemView);
        return holder;
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mContent.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public CommonRecyclerViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public CommonRecyclerViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public CommonRecyclerViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }


    public CommonRecyclerViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }
}
