package me.jiahuan.androidlearn.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;

import me.jiahuan.androidlearn.base.BaseActivity;

@Route(value = "module_example/example_activity")
public class ExampleActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.module_example_layout_example_activity);
        initializeActivityWithToolbar(R.layout.module_example_layout_example_activity, true);
    }


    public void onTreadPoolButtonClicked(View v) {
        Router.build("module_example/function/thread_pool_activity").go(this);
    }

}
