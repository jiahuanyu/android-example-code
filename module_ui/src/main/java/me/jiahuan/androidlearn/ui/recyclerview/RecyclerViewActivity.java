package me.jiahuan.androidlearn.ui.recyclerview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.jiahuan.androidlearn.base.BaseActivity;
import me.jiahuan.androidlearn.ui.R;

public class RecyclerViewActivity extends BaseActivity {
    private static final String TAG = "RecyclerViewActivity";

    private RecyclerView mRecyclerView;

    private List<String> mStrings = new ArrayList<>();


    private static final int RECYCLE_THRESHOLD = 400;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity(R.layout.module_ui_layout_recycler_view_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView = findViewById(R.id.id_module_ui_layout_recycler_view_recycler_view);
        initialize();
    }

    private void initialize() {
        configData();
        configRecyclerView();
    }

    private void configData() {
        for (int i = 0; i < 100; i++) {
            mStrings.add("当前位置 " + i);
        }
    }

    private void configRecyclerView() {
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(mSelfActivity));
        mRecyclerView.setLayoutManager(new MyLayoutManager());
        mRecyclerView.addItemDecoration(new MyItemDecoration());
        final MyAdapter adapter = new MyAdapter(mStrings);
        mRecyclerView.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.LEFT;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                adapter.itemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.itemRemove(viewHolder.getAdapterPosition());
            }
        });
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
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


        public void itemMove(int fromPosition, int toPosition) {
            Collections.swap(mStrings, fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
        }

        public void itemRemove(int removePosition) {
            mStrings.remove(removePosition);
            notifyItemRemoved(removePosition);
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

        private RecyclerView.Recycler mRecycler;

        @Override
        public RecyclerView.LayoutParams generateDefaultLayoutParams() {
            return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        }


        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            if (getItemCount() == 0) {
                return;
            }
            if (mRecycler == null) {
                mRecycler = recycler;
            }
            // 调用了两次
            Log.d(TAG, "onLayoutChildren");
            detachAndScrapAttachedViews(recycler);
            Log.d(TAG, "attached size = " + recycler.getScrapList().size());
            fill(recycler);
        }


        @Override
        public void onScrollStateChanged(int state) {
            super.onScrollStateChanged(state);
            if (state == RecyclerView.SCROLL_STATE_IDLE) {
                reFill(mRecycler);
            }
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

            // 边界判断
            if (mVerticalScrollOffset + distance < 0) {
                distance = -mVerticalScrollOffset;
            } else {
                View lastChild = getChildAt(getChildCount() - 1);
                int lastPosition = getPosition(lastChild);
                int lastChildBottom = getDecoratedBottom(lastChild);
                if (lastPosition == getItemCount() - 1) {
                    if (lastChildBottom - distance < getVerticalSpace()) {
                        distance = lastChildBottom - getVerticalSpace();
                    }
                }
            }

            mVerticalScrollOffset += distance;

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
            while (currentFillItemPosition >= 0 && currentFillItemBottom > -RECYCLE_THRESHOLD) {
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
            while (currentFillItemPosition < itemCount && currentFillItemTop < getVerticalSpace() + RECYCLE_THRESHOLD) {
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
            while (currentFillItemPosition < itemCount && currentFillItemTop < getVerticalSpace() + RECYCLE_THRESHOLD) {
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


    class MyItemDecoration extends RecyclerView.ItemDecoration {
        private Paint mPaint;

        public MyItemDecoration() {
            mPaint = new Paint();
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.BLACK);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//            c.drawColor(Color.BLACK);
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + 20;
                c.drawRect(left, top, right, bottom, mPaint);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(0, 0, 0, 20);
        }
    }
}
