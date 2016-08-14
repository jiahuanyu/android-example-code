package com.github.jiahuanyu.example.ui.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by doom on 16/7/22.
 */
public class CommonRecycleView extends RecyclerView {

    private View mLoadMoreView;
    private boolean mIsLoadingMore;
    private boolean mIsLoadingMoreEnabled;

    public CommonRecycleView(Context context) {
        this(context, null);
    }

    public CommonRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    private void initialize() {
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }

    /**
     * 是否开启上拉加载更多
     */
    public void setLoadMoreEnabled(boolean enabled) {
        mIsLoadingMoreEnabled = enabled;
    }

    /**
     * 设置加载更多的View
     */
    public void setLoadMoreView(View loadMoreView) {
        mLoadMoreView = loadMoreView;
    }

    //  上拉加载更多的包装类
    class LoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private RecyclerView.Adapter mInnerAdapter;

        private static final int VIEW_TYPE_LOAD_MODE = 40000;

        public LoadMoreAdapter(RecyclerView.Adapter innerAdapter) {
            mInnerAdapter = innerAdapter;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_LOAD_MODE) {
                return ViewHolder.createViewHolder(mLoadMoreView);
            }
            return mInnerAdapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (position == getItemCount() - 1) {
                return;
            }
            mInnerAdapter.onBindViewHolder(holder, position);
        }

        @Override
        public int getItemViewType(int position) {
            if (position == getItemCount() - 1) {
                return VIEW_TYPE_LOAD_MODE;
            }
            return mInnerAdapter.getItemViewType(position);
        }

        @Override
        public int getItemCount() {
            return mInnerAdapter.getItemCount() + 1;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        static ViewHolder createViewHolder(View itemView) {
            ViewHolder holder = new ViewHolder(itemView);
            return holder;
        }
    }
}
