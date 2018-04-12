package me.jiahuan.androidlearn.example.function.threadpool;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.chenenyu.router.annotation.Route;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import me.jiahuan.androidlearn.example.R;


@Route(value = "module_example/function/threadpool")
public class ThreadPoolActivity extends AppCompatActivity {

    private static final int TASK_COUNT = 12;

    private ProgressBar[] mProgressBarArray = new ProgressBar[TASK_COUNT];
    private ThreadPoolExecutor mThreadPoolExecutor;

    private MyHandler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private WeakReference<ThreadPoolActivity> mActivity;

        public MyHandler(ThreadPoolActivity activity) {
            mActivity = new WeakReference<ThreadPoolActivity>(activity);
        }


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ThreadPoolActivity activity = mActivity.get();
            if (activity != null) {
                activity.updateUI(msg.what, (Integer) msg.obj);
            }
        }
    }

    public void updateUI(int index, int progress) {
        mProgressBarArray[index].setProgress(progress);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thread_pool_activity);
        initialize();
    }

    private void initialize() {
        ViewGroup wrapperViewGroup = findViewById(R.id.id_layout_thread_pool_activity_wrapper_view_group);
        for (int i = 0; i < TASK_COUNT; i++) {
            ProgressBar progressBar = (ProgressBar) LayoutInflater.from(this).inflate(R.layout.layout_thread_pool_activity_progress_bar, null);
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
                    while (!isQuite && count < 100) {
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
//        mThreadPoolExecutor.submit(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).cancel();
    }

    boolean isQuite = false;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        isQuite = true;
        mThreadPoolExecutor.shutdownNow();
        mHandler.removeCallbacksAndMessages(null);
    }
}
