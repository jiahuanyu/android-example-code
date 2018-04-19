package me.jiahuan.androidlearn.example.function.eventdispatch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;

import com.chenenyu.router.annotation.Route;

import me.jiahuan.androidlearn.base.BaseActivity;
import me.jiahuan.androidlearn.example.R;


@Route(value = "module_example/function/ecent_dispatch_activity")
public class EventDispatchActivity extends BaseActivity {

    private static final String TAG = "EventDispatchActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity(R.layout.module_example_layout_event_dispatch_activity);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "Activity dispatchTouchEvent = " + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

}
