package me.jiahuan.androidlearn.example.function.threadpool;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class EventDispatchViewGroup extends LinearLayout {

    private static final String TAG = "EventDispatchViewGroup";


    public EventDispatchViewGroup(Context context) {
        super(context);
    }

    public EventDispatchViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EventDispatchViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, getTag() + "  ViewGroup dispatchTouchEvent = " + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, getTag() + "  ViewGroup onTouchEvent = " + event.getAction());
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, getTag() + "  ViewGroup onInterceptTouchEvent = " + ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }
}
