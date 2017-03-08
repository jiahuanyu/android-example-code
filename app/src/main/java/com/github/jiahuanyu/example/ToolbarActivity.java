package com.github.jiahuanyu.example;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.github.jiahuanyu.example.helper.TextHelper;
import com.github.jiahuanyu.example.helper.ToolBarHelper;


/**
 * Created by doom on 15/6/18.
 */
public class ToolbarActivity extends BaseActivity {
    private static final String TAG = "ToolbarActivity";

    protected ToolBarHelper mToolBarHelper;
    protected Activity mSelfActivity;

    protected void initializeActivity(int titleId, boolean showHomeUp, int layoutId) {
        initializeActivity(titleId > 0 ? getResources().getString(titleId) : null, showHomeUp, layoutId);
    }

    protected void initializeActivity(String title, boolean showHomeUp, int layoutId) {
        mToolBarHelper = new ToolBarHelper(this, layoutId);
        Toolbar toolbar = mToolBarHelper.getToolBar();
        initializeActivity(mToolBarHelper.getContentView());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeUp);
        if (!TextHelper.isEmpty(title)) {
            getSupportActionBar().setTitle(title);
        }
        mSelfActivity = this;
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
