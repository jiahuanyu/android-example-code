package me.jiahuan.androidlearn.ui.recyclerview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.jiahuan.androidlearn.base.BaseActivity;
import me.jiahuan.androidlearn.ui.R;

public class RecyclerViewActivity extends BaseActivity {
    private static final String TAG = "RecyclerViewActivity";

    private RecyclerView mRecyclerView;

    private List<String> mStrings = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity(R.layout.module_ui_layout_recycler_view_activity);
        mRecyclerView = findViewById(R.id.id_module_ui_layout_recycler_view_recycler_view);
        initialize();
    }

    private void initialize() {
        configData();
        configRecyclerView();
    }

    private void configData() {
        for (int i = 0; i < 1000; i++) {
            mStrings.add("当前位置 " + i);
        }
    }

    private void configRecyclerView() {
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(mSelfActivity));
        mRecyclerView.setLayoutManager(new MyLayoutManager());
        mRecyclerView.setAdapter(new MyAdapter(mStrings));
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
//        mRecyclerView.setViewCacheExtension();
//        mRecyclerView.setItemViewCacheSize();
//        mRecyclerView.getRecycledViewPool().setMaxRecycledViews();
    }


    static class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<String> mStrings = null;

        public MyAdapter(List<String> strings) {
            mStrings = strings;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            Log.d(TAG, "onCreateViewHolder");
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.module_ui_layout_recycler_view_activity_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//            Log.d(TAG, "onBindViewHolder = " + position);
            ((MyViewHolder) holder).title.setText(mStrings.get(position));
        }

        @Override
        public int getItemCount() {
            return mStrings.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView title;

            public MyViewHolder(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.module_ui_layout_recycler_view_activity_item_text_view);
            }
        }
    }

    static class MyLayoutManager extends RecyclerView.LayoutManager {

        private int mVerticalScrollOffset = 0;  //

        @Override
        public RecyclerView.LayoutParams generateDefaultLayoutParams() {
            return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        }


        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            if (getItemCount() == 0) {
                return;
            }

            // 调用了两次
            Log.d(TAG, "onLayoutChildren");
            detachAndScrapAttachedViews(recycler);
            Log.d(TAG, "attached size = " + recycler.getScrapList().size());
            fill(recycler);
        }

        @Override
        public boolean canScrollVertically() {
            return true;
        }

        @Override
        public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
            if (getItemCount() == 0 || getChildCount() == 0 || dy == 0) {
                return 0;
            }
            int distance = dy;

            mVerticalScrollOffset += -distance;
            recycleViews(recycler);
            reFill(recycler);
            offsetChildrenVertical(-distance);
            Log.d(TAG, "child count = " + getChildCount());
            return distance;
        }

        private void recycleViews(RecyclerView.Recycler recycler) {
            int childCount = getChildCount();
            List<View> recycleViews = new ArrayList<>();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                int childTop = getDecoratedTop(child);
                int childBottom = getDecoratedBottom(child);
                if (childBottom <= 0 || childTop >= getVerticalSpace()) {
                    recycleViews.add(child);
                }
            }

            for (View child : recycleViews) {
                removeAndRecycleView(child, recycler);
                Log.d(TAG, "removeAndRecycleView");
            }
        }

        private void reFill(RecyclerView.Recycler recycler) {
            // 取第一个
            View child = getChildAt(0);
            int currentFillItemPosition = getPosition(child) - 1;
            Log.d(TAG, "currentFillItemPosition = " + currentFillItemPosition);
            int currentFillItemBottom = getDecoratedTop(child);
            Log.d(TAG, "currentFillItemBottom = " + currentFillItemBottom);
            while (currentFillItemPosition >= 0 && currentFillItemBottom > 0) {
                View view = recycler.getViewForPosition(currentFillItemPosition);
                addView(view, 0);
                measureChildWithMargins(view, 0, 0);
                int itemHeight = getDecoratedMeasuredHeight(view);
                layoutDecorated(view, 0, currentFillItemBottom - itemHeight, getHorizontalSpace(), currentFillItemBottom);
                currentFillItemBottom -= itemHeight;
                currentFillItemPosition--;
            }

            // 最后一个
            int itemCount = getItemCount();
            child = getChildAt(getChildCount() - 1);
            currentFillItemPosition = getPosition(child) + 1;
            int currentFillItemTop = getDecoratedBottom(child);
            while (currentFillItemPosition < itemCount && currentFillItemTop < getVerticalSpace()) {
                View view = recycler.getViewForPosition(currentFillItemPosition);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                int itemHeight = getDecoratedMeasuredHeight(view);
                layoutDecorated(view, 0, currentFillItemTop, getHorizontalSpace(), currentFillItemTop + itemHeight);
                currentFillItemTop += itemHeight;
                currentFillItemPosition++;
            }
        }

        private void fill(RecyclerView.Recycler recycler) {
            int itemCount = getItemCount();
            if (itemCount == 0) {
                return;
            }
            int currentFillItemPosition = 0;
            int currentFillItemTop = 0;
            while (currentFillItemPosition < itemCount && currentFillItemTop < getVerticalSpace()) {
                View view = recycler.getViewForPosition(currentFillItemPosition);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                int itemHeight = getDecoratedMeasuredHeight(view);
                layoutDecorated(view, 0, currentFillItemTop, getHorizontalSpace(), currentFillItemTop + itemHeight);
                currentFillItemTop += itemHeight;
                currentFillItemPosition++;
            }
        }

        private int getVerticalSpace() {
            return getHeight() - getPaddingTop() - getPaddingBottom();
        }


        private int getHorizontalSpace() {
            return getWidth() - getPaddingLeft() - getPaddingRight();
        }
    }
}
