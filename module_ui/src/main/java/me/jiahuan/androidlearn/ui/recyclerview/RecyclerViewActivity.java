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
            Log.d(TAG, "onCreateViewHolder");
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.module_ui_layout_recycler_view_activity_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Log.d(TAG, "onBindViewHolder = " + position);
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

        private int mVerticalScrollOffset = 0;

        @Override
        public RecyclerView.LayoutParams generateDefaultLayoutParams() {
            return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        }


        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            if (getItemCount() == 0) {
                return;
            }
            // 获取每个Item的位置信息
            for (int i = 0; i < getItemCount(); i++) {

            }

            // 调用了两次
            Log.d(TAG, "onLayoutChildren");
            detachAndScrapAttachedViews(recycler);
            Log.d(TAG, "attached size = " + recycler.getScrapList().size());
            fillDown(recycler);
        }

        private void fillDown(RecyclerView.Recycler recycler) {
            // 屏幕内添加View
            int itemPosition = 0;
            int itemCount = getItemCount();
            int itemBottom = getVerticalSpace();
            int totalItemHeight = mVerticalScrollOffset;
            while (true) {
                if (itemPosition >= itemCount) {
                    break;
                }
                View view = recycler.getViewForPosition(itemPosition);
                addView(view);
//                Log.d(TAG, "after add View attached size = " + recycler.getScrapList().size());
                measureChildWithMargins(view, 0, 0);
                int itemWidth = getDecoratedMeasuredWidth(view); // 计算view实际大小，包括了ItemDecorator中设置的偏移量
                int itemHeight = getDecoratedMeasuredHeight(view);
//                Log.d(TAG, "itemWidth = " + itemWidth + "   itemHeight = " + itemHeight);

                layoutDecorated(view, 0, totalItemHeight, itemWidth, totalItemHeight + itemHeight);

                totalItemHeight += itemHeight;
                itemPosition++;

                if (getDecoratedBottom(view) >= itemBottom) {
                    break;
                }
            }
//            Log.d(TAG, "children count =" + getChildCount());
        }

        private int getVerticalSpace() {
            return getHeight() - getPaddingTop() - getPaddingBottom();
        }


        private int getHorizontalSpace() {
            return getWidth() - getPaddingLeft() - getPaddingRight();
        }


        @Override
        public boolean canScrollVertically() {
            return true;
        }


        @Override
        public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
//            Log.d(TAG, "dy = " + dy);

//            Log.d(TAG, "scrollVerticallyBy attached size = " + recycler.getScrapList().size());

            int distance = dy;

            mVerticalScrollOffset += -distance;

            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                int childBottom = getDecoratedBottom(child);
                int childTop = getDecoratedTop(child);
                if (childBottom <= -mVerticalScrollOffset || childTop >= -mVerticalScrollOffset + getVerticalSpace()) {
                    removeAndRecycleView(child, recycler);
                }
            }

            //
            detachAndScrapAttachedViews(recycler);
            offsetChildrenVertical(-distance);
            fillDown(recycler);
            Log.d(TAG, "child count = " + getChildCount());
            return distance;
        }
    }
}
