package com.github.jiahuanyu.example;

import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.jiahuanyu.example.helper.TextHelper;
import com.github.jiahuanyu.example.helper.ToolBarHelper;


/**
 * Created by doom on 15/6/18.
 */
public class ToolbarActivity extends BaseActivity {
    private static final String TAG = "ToolbarActivity";

    protected ToolBarHelper mToolBarHelper;

    protected void initializeActivity(int titleId, boolean showHomeUp, int layoutId) {
        initializeActivity(titleId > 0 ? getResources().getString(titleId) : null, showHomeUp, layoutId);
    }

    protected void initializeActivity(String title, boolean showHomeUp, int layoutId) {
        mToolBarHelper = new ToolBarHelper(this, layoutId);
        Toolbar toolbar = mToolBarHelper.getToolBar();
        initializeActivity(mToolBarHelper.getContentView());
        setSupportActionBar(toolbar);
        if (showHomeUp) {
            toolbar.setNavigationIcon(R.drawable.common_navigation_back_arrow);
        }
        if (!TextHelper.isEmpty(title)) {
            getSupportActionBar().setTitle(title);
        }
    }


    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setStatusColor();
    }

    protected void setStatusColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 生成一个状态栏大小的矩形
            View statusView = createStatusView();
            // 添加 statusView 到布局中
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            decorView.addView(statusView);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }


    private View createStatusView() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        View statusView = new View(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        return statusView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
