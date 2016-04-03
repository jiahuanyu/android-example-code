package com.github.jiahuanyu.example.gridviewsort;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by doom on 16/3/31.
 */
public class DragSortGridView extends GridView implements AdapterView.OnItemLongClickListener
{

    private WindowManager mWindowManager;


    private WindowManager.LayoutParams mDragItemLayoutParams;

    private ImageView mDragItemView;


    private int mDownX;
    private int mDownY;

    private boolean mDragStarted;

    public DragSortGridView(Context context)
    {
        this(context, null);
    }

    public DragSortGridView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public DragSortGridView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize()
    {
        mDragItemView = new ImageView(getContext());

        mWindowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

        mDragItemLayoutParams = new WindowManager.LayoutParams();

        setOnItemLongClickListener(this);

    }

    private View mView;


    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        if (getChildCount() >= 2)
        {
            view.setDrawingCacheEnabled(true);

            mView = view;

            Bitmap bitmap = view.getDrawingCache();

            mDragItemLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;
            mDragItemLayoutParams.width = bitmap.getWidth();
            mDragItemLayoutParams.height = bitmap.getHeight();

            mDragItemLayoutParams.x = (mDownX - mDragItemLayoutParams.width / 2);
            mDragItemLayoutParams.y = (mDownY - mDragItemLayoutParams.height / 2);

            mDragItemLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

            mDragItemLayoutParams.format = PixelFormat.TRANSLUCENT;

            mDragItemLayoutParams.windowAnimations = 0;

            mDragItemView.setImageBitmap(bitmap);

            mWindowManager.addView(mDragItemView, mDragItemLayoutParams);

            ((GridViewSortAdapter) getAdapter()).init();

            ((GridViewSortAdapter) getAdapter()).hideView(i);

            Log.d("P_P", "long click = " + i);

            mDragStarted = true;

        }
        return true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        switch (ev.getAction() & ev.getActionMasked())
        {
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) ev.getRawX();
                mDownY = (int) ev.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                if (mDragStarted)
                {
                    mDragItemLayoutParams.x = (int) (ev.getRawX() - mDragItemView.getWidth() / 2);
                    mDragItemLayoutParams.y = (int) (ev.getRawY() - mDragItemView.getHeight() / 2);
                    mWindowManager.updateViewLayout(mDragItemView, mDragItemLayoutParams);

                    int position = pointToPosition((int) ev.getX(), (int) ev.getY());
                    if (position != AdapterView.INVALID_POSITION && !((GridViewSortAdapter) getAdapter()).isInAnimation())
                    {
                        Log.d("P_P", "position = " + position);
                        ((GridViewSortAdapter) getAdapter()).swap(position);
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                if (mDragStarted)
                {
                    mWindowManager.removeView(mDragItemView);
                    ((GridViewSortAdapter) getAdapter()).clear();
                    mDragStarted = false;
                    mView.destroyDrawingCache();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
}
