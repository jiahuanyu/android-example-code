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
        Log.d(TAG, "onCreate")

        val myLopperThread = MyLopperThread("x")
        myLopperThread.start()
        //
        while (myLopperThread.getLooper() == null) {

        }
        mHandler = object : Handler(myLopperThread.getLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                Log.d(TAG, "msg.what = " + msg.what + "  current thread = " + Thread.currentThread().name)
            }
        }
        mHandler!!.sendEmptyMessageDelayed(10000, 2000)
        myLopperThread
    }

    internal inner class MyLopperThread(x: String) : Thread() {

        private var mLooper: Looper? = null

        override fun run() {
            super.run()
            Looper.prepare()
            mLooper = Looper.myLooper()
            Looper.loop()
        }


        fun getLooper(): Looper? = mLooper
    }

    companion object {
        private val TAG = "HandlerActivity"
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }


    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }


    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }


}
