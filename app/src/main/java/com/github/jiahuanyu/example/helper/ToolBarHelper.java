package com.github.jiahuanyu.example.helper;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.jiahuanyu.example.R;


/**
 * Created by doom on 16/7/5.
 */
public class ToolBarHelper {
    /*上下文，创建view的时候需要用到*/
    private Context mContext;

    /*base view*/
    private FrameLayout mContentView;

    /*用户定义的view*/
    private View mUserView;

    /*toolbar*/
    private Toolbar mToolBar;

    /*视图构造器*/
    private LayoutInflater mInflater;

    /*
    * 两个属性
    * 1、toolbar是否悬浮在窗口之上
    * 2、toolbar的高度获取
    * */
    private static int[] ATTRS = {
            R.attr.windowActionBarOverlay,
            R.attr.actionBarSize
    };

    public ToolBarHelper(Context context, int layoutId) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        /*初始化整个内容*/
        initContentView();
        /*初始化用户定义的布局*/
        initUserView(layoutId);
        /*初始化toolbar*/
        initToolBar();
    }

    private void initContentView() {
        /*直接创建一个帧布局，作为视图容器的父容器*/
        mContentView = new FrameLayout(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView.setLayoutParams(params);
    }

    private void initToolBar() {
        View toolbar = mInflater.inflate(R.layout.view_app_bar, mContentView);
        mToolBar = (Toolbar) toolbar.findViewById(R.id.app_bar);
    }

    private void initUserView(int id) {
        setUserView(id);
    }


    public void setUserView(View userView) {
        if (mUserView != null) {
            mContentView.removeView(mUserView);
        }
        mUserView = userView;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(ATTRS);
        /*获取主题中定义的悬浮标志*/
        boolean overly = typedArray.getBoolean(0, false);
        /*获取主题中定义的toolbar的高度*/
        int toolBarSize = (int) typedArray.getDimension(1, (int) mContext.getResources().getDimension(R.dimen.abc_action_bar_default_height_material));
        typedArray.recycle();
        /*如果是悬浮状态，则不需要设置间距*/
        params.topMargin = overly ? 0 : toolBarSize;
        mContentView.addView(mUserView, params);
    }

    public void setUserView(int layoutID) {
        setUserView(mInflater.inflate(layoutID, null));
    }

    public FrameLayout getContentView() {
        return mContentView;
    }

    public Toolbar getToolBar() {
        return mToolBar;
    }

    public View getUserView() {
        return mUserView;
    }
}
