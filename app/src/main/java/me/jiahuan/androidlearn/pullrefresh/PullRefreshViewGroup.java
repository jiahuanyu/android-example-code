package me.jiahuan.androidlearn.pullrefresh;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class PullRefreshViewGroup extends ViewGroup {

    public static final String TAG = "PullRefreshViewGroup";


    // 下拉看到的界面头
    private View mRefreshView;
    // 主体
    private View mTargetView;

    // 下拉长度
    private int mMoveDeltaY = 0;
    private float mMoveLastY = 0;
    private float mMoveRatioY = 1;

    public PullRefreshViewGroup(Context context) {
        this(context, null);
    }

    public PullRefreshViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullRefreshViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public PullRefreshViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize();
    }

    private void initialize() {
        Log.d(TAG, "initialize");
    }


    public void finishRefresh() {
        mMoveRatioY = 1;
        mMoveDeltaY = 0;
        requestLayout();
    }


    private float mMoveInterceptDownY = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.d(TAG, "onInterceptTouchEvent");
        boolean retValue = false;
        switch (event.getAction() & event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mMoveLastY = mMoveInterceptDownY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mTargetView.canScrollVertically(-1) && event.getY() - mMoveInterceptDownY > 0) {
                    MotionEvent obtain = MotionEvent.obtain(event);
                    obtain.setAction(MotionEvent.ACTION_CANCEL);
                    mTargetView.dispatchTouchEvent(obtain);
                    retValue = true;
                } else {
                    retValue = false;
                }
                break;
        }


        return retValue;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent");
        switch (event.getAction() & event.getActionMasked()) {
//            case MotionEvent.ACTION_DOWN:
//                mMoveLastY = event.getY();
//                break;
            case MotionEvent.ACTION_MOVE:
                mMoveDeltaY += (event.getY() - mMoveLastY) / mMoveRatioY;
                if (mMoveDeltaY < 0) {
                    mMoveDeltaY = 0;
                } else if (mMoveDeltaY > getMeasuredHeight()) {
                    mMoveDeltaY = getMeasuredHeight();
                }
                mMoveLastY = event.getY();
                // 根据下拉距离改变比例
                mMoveRatioY = (float) (2 + 2 * Math.tan(Math.PI / 2 / getMeasuredHeight() * mMoveDeltaY));
                requestLayout();
                break;
            case MotionEvent.ACTION_UP:
                if (mMoveDeltaY < 300) {
                    mMoveRatioY = 1;
                    mMoveDeltaY = 0;
                } else {
                    mMoveDeltaY = 260;
                    // refresh listener
                    if (mPullRefreshListener != null) {
                        mPullRefreshListener.onRefresh();
                    }
                }
                requestLayout();
                break;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mRefreshView = getChildAt(0);
        mTargetView = getChildAt(1);

        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        Log.d(TAG, "onLayout");

        mRefreshView.layout(0, mMoveDeltaY - mRefreshView.getMeasuredHeight(), mRefreshView.getMeasuredWidth(), mMoveDeltaY);
        mTargetView.layout(0, mMoveDeltaY, mTargetView.getMeasuredWidth(), mTargetView.getMeasuredHeight() + mMoveDeltaY);
    }

    private OnPullRefreshListener mPullRefreshListener;


    public void setOnPullRefreshListener(OnPullRefreshListener listener) {
        mPullRefreshListener = listener;
    }

    public interface OnPullRefreshListener {
        void onRefresh();
    }
}
