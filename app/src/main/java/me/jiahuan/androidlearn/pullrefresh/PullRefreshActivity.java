package me.jiahuan.androidlearn.pullrefresh;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import me.jiahuan.androidlearn.R;

public class PullRefreshActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, PullRefreshViewGroup.OnPullRefreshListener {


    private ListView mListView;
    private PullRefreshViewGroup mPullRefreshViewGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pull_refresh_activity);
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
