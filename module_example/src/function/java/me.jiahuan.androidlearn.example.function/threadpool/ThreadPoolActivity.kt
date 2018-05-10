package me.jiahuan.androidlearn.example.function.threadpool

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import me.jiahuan.androidlearn.base.BaseActivity
import me.jiahuan.androidlearn.example.R
import java.lang.ref.WeakReference
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


class ThreadPoolActivity : BaseActivity() {

    companion object {
        private const val TASK_COUNT = 12
    }

    private val mProgressBarArray = arrayOfNulls<ProgressBar?>(TASK_COUNT)
    private var mThreadPoolExecutor: ThreadPoolExecutor? = null

    private val mHandler = MyHandler(this)

    private var isQuit = false

    private class MyHandler(activity: ThreadPoolActivity) : Handler() {
        private val mActivity: WeakReference<ThreadPoolActivity> = WeakReference(activity)


        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val activity = mActivity.get()
            activity?.updateUI(msg.what, msg.obj as Int)
        }
    }

    private fun updateUI(index: Int, progress: Int) {
        mProgressBarArray[index]!!.progress = progress
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeActivityWithToolbar(R.layout.module_example_layout_thread_pool_activity, true)
        initialize()
    }

    private fun initialize() {
        val wrapperViewGroup = findViewById<ViewGroup>(R.id.id_layout_thread_pool_activity_wrapper_view_group)
        for (i in 0 until TASK_COUNT) {
            val progressBar = LayoutInflater.from(this).inflate(R.layout.module_example_layout_thread_pool_activity_progress_bar, null) as ProgressBar
            wrapperViewGroup.addView(progressBar)
            mProgressBarArray[i] = progressBar
        }

        mThreadPoolExecutor = ThreadPoolExecutor(4, 6, 1, TimeUnit.SECONDS, LinkedBlockingQueue(6), Executors.defaultThreadFactory())
        for (i in 0 until TASK_COUNT) {
            mThreadPoolExecutor!!.execute {
                var count = 0
                while (!isQuit && count < 100) {
                    try {
                        Thread.sleep(((i + 1) * 10).toLong())
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                    mHandler.obtainMessage(i, ++count).sendToTarget()
                }
            }
//            (object : Runnable {
//                private var count = 0
//
//                override fun run() {
//                    while (!isQuit && count < 100) {
//                        try {
//                            Thread.sleep(((i + 1) * 10).toLong())
//                        } catch (e: InterruptedException) {
//                            e.printStackTrace()
//                        }
//
//                        mHandler.obtainMessage(i, ++count).sendToTarget()
//                    }
//                }
//            })
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        isQuit = true
        mThreadPoolExecutor!!.shutdownNow()
        mHandler.removeCallbacksAndMessages(null)
    }

}
