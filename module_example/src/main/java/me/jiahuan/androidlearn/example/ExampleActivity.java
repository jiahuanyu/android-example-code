package me.jiahuan.androidlearn.example;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;

import java.util.ArrayList;
import java.util.List;

import me.jiahuan.androidlearn.base.BaseActivity;
import me.jiahuan.androidlearn.base.DividerItemDecoration;
import me.jiahuan.androidlearn.example.function.jni.JNIActivity;
import me.jiahuan.androidlearn.example.ui.recyclerview.RecyclerViewActivity;
import me.jiahuan.androidlearn.example.ui.scroller.ScrollerActivity;

@Route(value = "module_example/example_activity")
public class ExampleActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivityWithToolbar(R.layout.module_example_layout_example_activity, true);
    }

    public void onTreadPoolButtonClicked(View v) {
        Router.build("module_example/function/thread_pool_activity").go(this);
    }


    public void onEventDispatchButtonClicked(View v) {
        Router.build("module_example/function/ecent_dispatch_activity").go(this);
    }


    public void onPullRefreshButtonClicked(View v) {
        Router.build("module_example/function/pull_refresh_activity").go(this);
    }

    public void onAIDLButtonClicked(View v) {
        Router.build("module_example/function/aidl_activity").go(this);
    }

    public void onRecyclerViewButtonClicked(View v) {
        startActivity(new Intent(this, RecyclerViewActivity.class));
    }

    public void onJNIButtonClicked(View v) {
        startActivity(new Intent(this, JNIActivity.class));
    }

    public void onScrollButtonClicked(View v) {
        startActivity(new Intent(this, ScrollerActivity.class));
    }
}
