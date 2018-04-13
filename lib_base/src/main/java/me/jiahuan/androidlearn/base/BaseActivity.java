package me.jiahuan.androidlearn.base;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;


public class BaseActivity extends AppCompatActivity {

    private View mContent;
    private Toolbar mToolbar; // 由布局文件去决定是否存在，id相 = id_layout_tool_bar_tool_bar
    protected Activity mSelfActivity;

    protected void initializeActivity(int layoutId) {
        mSelfActivity = this;
        mContent = LayoutInflater.from(this).inflate(layoutId, null);
        setContentView(mContent);
    }

    protected void initializeActivityWithToolbar(int layoutId, boolean displayHomeAsUpEnabled) {
        initializeActivity(layoutId);
        mToolbar = mContent.findViewById(R.id.id_layout_tool_bar_tool_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled);
    }


    protected Toolbar getToolbar() {
        return mToolbar;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
