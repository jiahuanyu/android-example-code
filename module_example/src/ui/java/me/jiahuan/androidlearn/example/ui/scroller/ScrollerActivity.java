package me.jiahuan.androidlearn.example.ui.scroller;

import android.os.Bundle;
import android.support.annotation.Nullable;

import me.jiahuan.androidlearn.base.BaseActivity;
import me.jiahuan.androidlearn.example.R;

public class ScrollerActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity(R.layout.module_example_layout_scroller_activity);
    }
}
