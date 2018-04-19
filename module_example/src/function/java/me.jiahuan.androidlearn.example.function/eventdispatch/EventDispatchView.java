package me.jiahuan.androidlearn.example.function.eventdispatch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class EventDispatchView extends View {

    private static final String TAG = "EventDispatchView";

    public EventDispatchView(Context context) {
        super(context);
    }

    public EventDispatchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EventDispatchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, getTag() + "  View dispatchTouchEvent = " + event.getAction());
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, getTag() + "  View onTouchEvent = " + event.getAction());
        return super.onTouchEvent(event);
    }
}
