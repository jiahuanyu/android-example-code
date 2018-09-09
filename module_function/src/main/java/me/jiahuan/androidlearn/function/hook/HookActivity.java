package me.jiahuan.androidlearn.function.hook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import me.jiahuan.androidlearn.base.BaseActivity;
import me.jiahuan.androidlearn.function.R;

public class HookActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity(R.layout.module_function_layout_hook_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void onButtonClicked(View v) {
        getApplication().startActivity(new Intent(this, HookActivity.class));
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try {
            HookHelper.attachContext();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
