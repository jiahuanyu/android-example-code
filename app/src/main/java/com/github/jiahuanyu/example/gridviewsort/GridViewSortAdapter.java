package com.github.jiahuanyu.example.gridviewsort;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.github.jiahuanyu.example.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by doom on 16/3/31.
 */
public class GridViewSortAdapter extends BaseAdapter
{

    private Context mContext;

    private List<String> mTypeTitle;

    private List<Integer> mPositionList = new ArrayList<>();
    private int mCurrentHideItemPosition = AdapterView.INVALID_POSITION;
    private int mStartHideItemPosition = AdapterView.INVALID_POSITION;

    private int mHorizontalSpace;
    private int mVerticalSpace;

    private int mTranslateX;
    private int mTranslateY;

    private int mColsNum;

    private GridView mGridView;

    private boolean mInAnimation;

    public GridViewSortAdapter(GridView gridView, Context context, List<String> typeTitle)
    {
        mContext = context;
        mTypeTitle = typeTitle;
        mHorizontalSpace = gridView.getRequestedHorizontalSpacing();
        mVerticalSpace = gridView.getRequestedHorizontalSpacing();
        mGridView = gridView;
    }


    public void hideView(int item)
    {
        resetPositionList();
        mStartHideItemPosition = mCurrentHideItemPosition = item;
        notifyDataSetChanged();
    }

    public void clear()
    {
        String value = mTypeTitle.get(mStartHideItemPosition);
        if (mStartHideItemPosition < mCurrentHideItemPosition)
        {
            mTypeTitle.add(mCurrentHideItemPosition + 1, value);
            mTypeTitle.remove(mStartHideItemPosition);
        }
        else if (mStartHideItemPosition > mCurrentHideItemPosition)
        {
            mTypeTitle.add(mCurrentHideItemPosition, value);
            mTypeTitle.remove(mStartHideItemPosition + 1);
        }

        mStartHideItemPosition = mCurrentHideItemPosition = AdapterView.INVALID_POSITION;

        notifyDataSetChanged();

        for (int i = 0; i < mGridView.getChildCount(); i++)
        {
            mGridView.getChildAt(i).setTranslationX(0);
            mGridView.getChildAt(i).setTranslationY(0);
        }
    }


    public void init()
    {
        View view = mGridView.getChildAt(0);
        mTranslateX = view.getWidth() + mHorizontalSpace;
        mTranslateY = view.getHeight() + mVerticalSpace;
        mColsNum = mGridView.getNumColumns();
    }

    public void swap(int position)
    {
        int r_p = mPositionList.indexOf(position);

        Log.d("P_P", "r_p = " + r_p);

        if (mCurrentHideItemPosition < r_p)
        {
            for (int i = mCurrentHideItemPosition + 1; i <= r_p; i++)
            {
                View v = mGridView.getChildAt(mPositionList.get(i));
                v.clearAnimation();
                if (i % mColsNum == 0 && i > 0)
                {
                    startMoveAnimation(v, v.getTranslationX() + mTranslateX * (mColsNum - 1), v.getTranslationY() -
                            mTranslateY);
                }
                else
                {
                    startMoveAnimation(v, v.getTranslationX() - mTranslateX, 0);
                }
            }
        }
        else if (mCurrentHideItemPosition > r_p)
        {
            for (int i = r_p; i < mCurrentHideItemPosition; i++)
            {
                View v = mGridView.getChildAt(mPositionList.get(i));
                v.clearAnimation();
                if ((i + 1) % mColsNum == 0)
                {
                    startMoveAnimation(v, v.getTranslationX() - mTranslateX * (mColsNum - 1), v.getTranslationY() + mTranslateY);
                }
                else
                {
                    startMoveAnimation(v, v.getTranslationX() + mTranslateX, 0);
                }
            }
        }

        resetPositionList();

        int value = mPositionList.get(mStartHideItemPosition);
        if (mStartHideItemPosition < r_p)
        {
            mPositionList.add(r_p + 1, value);
            mPositionList.remove(mStartHideItemPosition);
        }
        else if (mStartHideItemPosition > r_p)
        {
            mPositionList.add(r_p, value);
            mPositionList.remove(mStartHideItemPosition + 1);
        }

        mCurrentHideItemPosition = r_p;
    }

    public boolean isInAnimation()
    {
        return mInAnimation;
    }

    private void resetPositionList()
    {
        mPositionList.clear();
        for (int i = 0; i < mGridView.getChildCount(); i++)
        {
            mPositionList.add(i);
        }
    }

    private void startMoveAnimation(View myView, float x, float y)
    {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(myView, "translationX", myView.getTranslationX(), x),
                ObjectAnimator.ofFloat(myView, "translationY", myView.getTranslationY(), y)
        );
        set.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animator)
            {
                mInAnimation = true;
            }

            @Override
            public void onAnimationEnd(Animator animator)
            {
                mInAnimation = false;
            }

            @Override
            public void onAnimationCancel(Animator animator)
            {

            }

            @Override
            public void onAnimationRepeat(Animator animator)
            {

            }
        });
        set.setDuration(150).start();
    }

    @Override
    public int getCount()
    {
        return mTypeTitle.size();
    }

    @Override
    public Object getItem(int i)
    {
        return mTypeTitle.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_item_grid_view_sort, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.view_item_grid_view_sort_title);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(mTypeTitle.get(position));
        if (mStartHideItemPosition == position)
        {
            convertView.setVisibility(View.INVISIBLE);
        }
        else
        {
            convertView.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    class ViewHolder
    {
        public TextView title;
    }
}
