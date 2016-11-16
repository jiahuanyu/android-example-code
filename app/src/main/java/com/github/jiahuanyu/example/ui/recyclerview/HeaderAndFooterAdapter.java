package com.github.jiahuanyu.example.ui.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by doom on 16/8/4.
 */
public class HeaderAndFooterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int BASE_ITEM_TYPE_HEADER = 100000;

    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    private SparseArray<View> mHeaderViews = new SparseArray<>(); // 头部布局
    private SparseArray<View> mFooterViews = new SparseArray<>();// 底部布局


    private RecyclerView.Adapter mInnerAdapter;

    public HeaderAndFooterAdapter(RecyclerView.Adapter innerAdapter) {
        mInnerAdapter = innerAdapter;
    }

    // 当前item时候是头部item
    private boolean isHeaderViewPosition(int position) {
        return position < getHeadersCount();
    }

    // 当前item时候是底部item
    private boolean isFooterViewPosition(int position) {
        return position >= getFootersCount() + getRealItemCount();
    }

    // 添加头部布局
    public void addHeaderView(View view) {
        mHeaderViews.put(getHeadersCount() + BASE_ITEM_TYPE_HEADER, view);
    }

    // 添加底部布局
    public void addFootView(View view) {
        mFooterViews.put(getFootersCount() + BASE_ITEM_TYPE_FOOTER, view);
    }

    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    public int getFootersCount() {
        return mFooterViews.size();
    }

    private int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mInnerAdapter.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    if (mHeaderViews.get(viewType) != null) {
                        return gridLayoutManager.getSpanCount();
                    } else if (mFooterViews.get(viewType) != null) {
                        return gridLayoutManager.getSpanCount();
                    }
                    if (spanSizeLookup != null)
                        return spanSizeLookup.getSpanSize(position);
                    return 1;
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderViewPosition(position) || isFooterViewPosition(position)) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            HeaderAndFooterViewHolder holder = HeaderAndFooterViewHolder.createViewHolder(mHeaderViews.get(viewType));
            return holder;
        } else if (mFooterViews.get(viewType) != null) {
            HeaderAndFooterViewHolder holder = HeaderAndFooterViewHolder.createViewHolder(mFooterViews.get(viewType));
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPosition(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPosition(position)) {
            return mFooterViews.keyAt(position - getHeadersCount() - getRealItemCount());
        }
        return mInnerAdapter.getItemViewType(position - getHeadersCount());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeaderViewPosition(position)) {
            return;
        }
        if (isFooterViewPosition(position)) {
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position - getHeadersCount());
    }

    @Override
    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + getRealItemCount();
    }

    static class HeaderAndFooterViewHolder extends RecyclerView.ViewHolder {

        public HeaderAndFooterViewHolder(View itemView) {
            super(itemView);
        }

        static HeaderAndFooterViewHolder createViewHolder(View view) {
            HeaderAndFooterViewHolder holder = new HeaderAndFooterViewHolder(view);
            return holder;
        }
    }
}
