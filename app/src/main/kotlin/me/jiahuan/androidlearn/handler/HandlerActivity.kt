package me.jiahuan.androidlearn.handler

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.util.Log

class HandlerActivity : AppCompatActivity() {

    private var mHandler: Handler? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myLopperThread = MyLopperThread("x")
        myLopperThread.start()
        //
        mHandler = object : Handler(myLopperThread.getLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                Log.d(TAG, "msg.what = " + msg.what + "  current thread = " + Thread.currentThread().name)
            }
        }
        mHandler!!.sendEmptyMessageDelayed(10000, 2000)
    }

    internal inner class MyLopperThread(x: String) : Thread() {

        override fun run() {
            super.run()
            Looper.prepare()
            Looper.loop()
        }


        fun getLooper(): Looper? = Looper.myLooper()
    }

    companion object {
        private val TAG = "HandlerActivity"
    }
}
