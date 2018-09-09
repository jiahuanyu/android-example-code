package me.jiahuan.androidlearn.base;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


public class BaseActivity extends AppCompatActivity {

    protected Activity mSelfActivity;

    protected void initializeActivity(int layoutId) {
        setContentView(layoutId);
        Toolbar toolbar = findViewById(R.id.id_layout_tool_bar_tool_bar);
        setSupportActionBar(toolbar);
        mSelfActivity = this;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
