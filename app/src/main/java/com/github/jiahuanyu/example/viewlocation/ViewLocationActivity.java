package com.github.jiahuanyu.example.viewlocation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jiahuanyu.example.BaseActivity;
import com.github.jiahuanyu.example.R;

/**
 * Created by doom on 16/5/13.
 */
public class ViewLocationActivity extends BaseActivity
{
    ImageView mImageView;
    TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initActivity(true, R.layout.activity_view_location);
        mImageView = (ImageView) findViewById(R.id.activity_view_location_iv);
        mTextView = (TextView) findViewById(R.id.activity_view_location_tv);
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mImageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
        {
            @Override
            public boolean onPreDraw()
            {
                mImageView.getViewTreeObserver().removeOnPreDrawListener(this);
                int[] screenLocation = new int[2];
                mImageView.getLocationOnScreen(screenLocation);
                int[] windowLocation = new int[2];
                mImageView.getLocationInWindow(windowLocation);

                mTextView.setText(
                        "Relative to Parent\n"
                                + " left = " + mImageView.getLeft() + "\n"
                                + " right = " + mImageView.getRight() + "\n"
                                + " top = " + mImageView.getTop() + "\n"
                                + " bottom = " + mImageView.getBottom() + "\n"
                                + "Relative to Screen\n"
                                + " x = " + screenLocation[0] + "\n"
                                + " y = " + screenLocation[1] + "\n"
                                + "Relative to Window\n"
                                + " x = " + windowLocation[0] + "\n"
                                + " y = " + windowLocation[1] + "\n"
                );
                return false;
            }
        });
    }
}
