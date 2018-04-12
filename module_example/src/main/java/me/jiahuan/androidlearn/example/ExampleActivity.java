package me.jiahuan.androidlearn.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.chenenyu.router.annotation.Route;

@Route(value = "module_example/example_activity")
public class ExampleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_example_activity);
    }
}
