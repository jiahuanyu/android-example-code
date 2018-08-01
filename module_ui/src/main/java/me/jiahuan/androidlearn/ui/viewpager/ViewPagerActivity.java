package me.jiahuan.androidlearn.ui.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import me.jiahuan.androidlearn.base.BaseActivity;
import me.jiahuan.androidlearn.ui.R;

public class ViewPagerActivity extends BaseActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<TabFragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity(R.layout.module_ui_layout_view_pager_activity);
        initialize();
    }

    private void initialize() {
        mViewPager = findViewById(R.id.module_ui_layout_view_pager_activity_view_pager);
        mTabLayout = findViewById(R.id.module_ui_layout_view_pager_activity_tab_layout);
        configData();
        configViewPager();
        configTabLayout();
    }

    private void configData() {
        for (int i = 0; i < 10; i++) {
            mFragments.add(TabFragment.newInstance(i + ""));
        }
    }

    private void configViewPager() {
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mViewPager.setOffscreenPageLimit(2);
    }

    private void configTabLayout() {
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);
    }


    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return "Tab" + position;
        }
    }
}
