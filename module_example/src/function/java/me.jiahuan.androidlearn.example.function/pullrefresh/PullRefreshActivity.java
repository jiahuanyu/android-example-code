package me.jiahuan.androidlearn.example.function.pullrefresh;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.chenenyu.router.annotation.Route;

import me.jiahuan.androidlearn.base.BaseActivity;
import me.jiahuan.androidlearn.example.R;


@Route(value = "module_example/function/pull_refresh_activity")
public class PullRefreshActivity extends BaseActivity implements AdapterView.OnItemLongClickListener, PullRefreshViewGroup.OnPullRefreshListener {


    private ListView mListView;
    private PullRefreshViewGroup mPullRefreshViewGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity(R.layout.module_example_layout_pull_refresh_activity);
        initialize();
    }

    private String[] mDataArray = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N"};

    private void initialize() {
        mListView = findViewById(R.id.activity_pull_refresh_list_view);
        mPullRefreshViewGroup = findViewById(R.id.activity_pull_refresh_view_group);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDataArray);
        mListView.setAdapter(adapter);

        mListView.setOnItemLongClickListener(this);

        mPullRefreshViewGroup.setOnPullRefreshListener(this);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "long click with " + position, Toast.LENGTH_SHORT).show();
        return false;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mPullRefreshViewGroup.finishRefresh();
        }
    };

    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(0, 5000);
    }
}
