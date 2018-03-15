package me.jiahuan.androidlearn.threadpool;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import me.jiahuan.androidlearn.R;


public class ThreadPoolActivity extends AppCompatActivity {

    private static final int TASK_COUNT = 12;

    private ProgressBar[] mProgressBarArray = new ProgressBar[TASK_COUNT];
    private ThreadPoolExecutor mThreadPoolExecutor;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mProgressBarArray[msg.what].setProgress((Integer) msg.obj);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thread_pool_activity);
        initialize();
    }

    private void initialize() {
        ViewGroup wrapperViewGroup = findViewById(R.id.id_layout_thread_pool_activity_wrapper_view_group);
        for (int i = 0; i < TASK_COUNT; i++) {
            ProgressBar progressBar = (ProgressBar) LayoutInflater.from(this).inflate(R.layout.layout_view_thread_pool_activity_progress_bar, null);
            wrapperViewGroup.addView(progressBar);
            mProgressBarArray[i] = progressBar;
        }


        mThreadPoolExecutor = new ThreadPoolExecutor(4, 6, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(6), Executors.defaultThreadFactory());
        for (int i = 0; i < TASK_COUNT; i++) {
            final int index = i;
            mThreadPoolExecutor.execute(new Runnable() {
                private int count = 0;

                @Override
                public void run() {
                    while (count < 100) {
                        try {
                            Thread.sleep((index + 1) * 10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mHandler.obtainMessage(index, ++count).sendToTarget();
                    }
                }
            });
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mThreadPoolExecutor.shutdownNow();
    }
}
