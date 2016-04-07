package com.github.jiahuanyu.example.dropdownmenu;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.github.jiahuanyu.example.BaseActivity;
import com.github.jiahuanyu.example.R;

/**
 * Created by doom on 16/3/31.
 */
public class DropDownMenuActivity extends BaseActivity
{
    private ViewGroup mMenuContainer;
    private TabLayout mTabLayout;
    private boolean mShown;
    private boolean mInAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initActivity(true, R.layout.activity_drop_down_menu);

        mMenuContainer = (ViewGroup) findViewById(R.id.activity_drop_down_menu_container);
        mTabLayout = (TabLayout) findViewById(R.id.activity_drop_down_menu_tablayout);

        mTabLayout.addTab(mTabLayout.newTab().setText("上海"));
        mTabLayout.addTab(mTabLayout.newTab().setText("北京"));
        mTabLayout.addTab(mTabLayout.newTab().setText("成都"));
        mTabLayout.addTab(mTabLayout.newTab().setText("四川"));
        mTabLayout.addTab(mTabLayout.newTab().setText("重庆"));
        mTabLayout.addTab(mTabLayout.newTab().setText("深圳"));
        mTabLayout.addTab(mTabLayout.newTab().setText("天津"));
        mTabLayout.addTab(mTabLayout.newTab().setText("武汉"));
    }

    public void setMenuVisible(View v)
    {
        if (!mInAnimation)
        {
            if (mShown)
            {
                Animation menuOutAnim = AnimationUtils.loadAnimation(this, R.anim.menu_out);
                menuOutAnim.setAnimationListener(new Animation.AnimationListener()
                {
                    @Override
                    public void onAnimationStart(Animation animation)
                    {
                        mInAnimation = true;
                    }

                    @Override
                    public void onAnimationEnd(Animation animation)
                    {
                        mInAnimation = false;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation)
                    {

                    }
                });
                mMenuContainer.setVisibility(View.GONE);
                mMenuContainer.setAnimation(menuOutAnim);
                mShown = false;
            }
            else
            {
                Animation menuInAnim = AnimationUtils.loadAnimation(this, R.anim.menu_in);
                menuInAnim.setAnimationListener(new Animation.AnimationListener()
                {
                    @Override
                    public void onAnimationStart(Animation animation)
                    {
                        mInAnimation = true;
                    }

                    @Override
                    public void onAnimationEnd(Animation animation)
                    {
                        mInAnimation = false;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation)
                    {

                    }
                });
                mMenuContainer.setVisibility(View.VISIBLE);
                mMenuContainer.setAnimation(menuInAnim);
                mShown = true;
            }
        }
    }
}
