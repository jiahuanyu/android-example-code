package me.jiahuan.androidlearn.threadpool

import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.util.Log
import me.jiahuan.androidlearn.R
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


class ThreadPoolActivity : AppCompatActivity() {

    private var mThreadPoolExecutor: ThreadPoolExecutor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_thread_pool_activity)
        initialize()
    }


    fun initialize() {
        mThreadPoolExecutor = ThreadPoolExecutor(3, 7, 1, TimeUnit.SECONDS, LinkedBlockingQueue<Runnable>(128))
        val runnable = Runnable {
            //            Log.d(TAG,"xxxxx")
        }
    }
}