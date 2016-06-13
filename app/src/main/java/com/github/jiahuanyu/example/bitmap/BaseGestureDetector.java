package com.github.jiahuanyu.example.bitmap;

import android.content.Context;
import android.view.MotionEvent;

/**
 * Created by doom on 16/6/13.
 */
public abstract class BaseGestureDetector
{
    protected boolean mGestureInProgress;

    protected MotionEvent mPreMotionEvent;
    protected MotionEvent mCurrentMotionEvent;

    protected Context mContext;

    protected abstract void handleInProgressEvent(MotionEvent event);

    protected abstract void handleStartProgressEvent(MotionEvent event);

    protected abstract void updateStateByEvent(MotionEvent event);

    public BaseGestureDetector(Context context)
    {
        mContext = context;
    }


    public boolean onTouchEvent(MotionEvent event)
    {
        if (!mGestureInProgress)
        {
            handleStartProgressEvent(event);
        }
        else
        {
            handleInProgressEvent(event);
        }

        return true;

    }

    protected void resetState()
    {
        if (mPreMotionEvent != null)
        {
            mPreMotionEvent.recycle();
            mPreMotionEvent = null;
        }
        if (mCurrentMotionEvent != null)
        {
            mCurrentMotionEvent.recycle();
            mCurrentMotionEvent = null;
        }
        mGestureInProgress = false;
    }
}
