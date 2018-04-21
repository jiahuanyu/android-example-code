package me.jiahuan.androidlearn.example.ui.scroller;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;


public class HorizontalView extends ViewGroup {

    private static final String TAG = "HorizontalView";

    private int mLastInterceptX;
    private int mLastInterceptY;

    private int mLastX;
    private int mLastY;

    private int mCurrentIndex;

    private VelocityTracker mVelocityTracker;

    private int childWidth;

    private Scroller mScroller;

    public HorizontalView(Context context) {
        super(context);
        initialize();
    }

    public HorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public HorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HorizontalView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize();
    }


    private void initialize() {
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }

    private void smoothScrollTo(int destX, int destY) {
        mScroller.startScroll(getScrollX(), getScrollY(), destX - getScrollX(), destY - getScrollY());
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.d(TAG, "onTouchEvent  " + event.getAction());

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                Log.d(TAG, deltaX + " deltaX");

                if ((mCurrentIndex == 0 && deltaX > 0) || (mCurrentIndex == getChildCount() - 1 && deltaX < 0)) {
//
                } else {
                    mVelocityTracker.addMovement(event);
                    scrollBy(-deltaX, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                int distance = getScrollX() - mCurrentIndex * childWidth;
                Log.d(TAG, distance + "  , " + childWidth / 2);
                if (Math.abs(distance) > childWidth / 2) {
                    if (distance > 0) {
                        if (mCurrentIndex < getChildCount() - 1) {
                            mCurrentIndex++;
                        }
                    } else {
                        if (mCurrentIndex > 0) {
                            mCurrentIndex--;
                        }
                    }
                } else {
                    mVelocityTracker.computeCurrentVelocity(1000);
                    float xV = mVelocityTracker.getXVelocity();
                    Log.d(TAG, "x velocity = " + xV);

                    if (Math.abs(xV) > 50) {
                        if (xV > 0) {
                            if (mCurrentIndex > 0) {
                                mCurrentIndex--;
                            }
                        } else {
                            if (mCurrentIndex < getChildCount() - 1) {
                                mCurrentIndex++;
                            }
                        }
                    }
                }

                Log.d(TAG, mCurrentIndex * childWidth + "");

                smoothScrollTo(mCurrentIndex * childWidth, 0);
                mVelocityTracker.clear();
                break;
        }
        mLastX = x;
        mLastY = y;
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        Log.d(TAG, "onInterceptTouchEvent " + ev.getAction());
        boolean intercept = false;

        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastInterceptX;
                int deltaY = y - mLastInterceptY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    intercept = true;
                }
                break;
        }

        mLastInterceptX = x;
        mLastInterceptY = y;
        return intercept;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 测量子View
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (getChildCount() == 0) {
            setMeasuredDimension(widthSize, heightSize);
        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            View child = getChildAt(0);
            childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            setMeasuredDimension(getChildCount() * childWidth, childHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            childWidth = getChildAt(0).getMeasuredWidth();
            setMeasuredDimension(getChildCount() * childWidth, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            childWidth = getChildAt(0).getMeasuredWidth();
            int childHeight = getChildAt(0).getMeasuredHeight();
            setMeasuredDimension(widthSize, childHeight);
        } else {
            childWidth = getChildAt(0).getMeasuredWidth();
            setMeasuredDimension(widthSize, heightSize);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                int width = child.getMeasuredWidth();
                child.layout(i * width, 0, (i + 1) * width, child.getMeasuredHeight());
            }
        }
    }
}
