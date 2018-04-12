package me.jiahuan.androidlearn.service


import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MyService : Service() {

    private val mBinder = MyBinder()

    override fun onCreate() {
        Log.d(TAG, "onCreate")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }

    override fun onRebind(intent: Intent) {
        Log.d(TAG, "onRebind")
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent): Boolean {
        Log.d(TAG, "onUnbind")
        return super.onUnbind(intent)
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d(TAG, "onBind")
        return mBinder
    }


    internal inner class MyBinder : Binder() {

        val service: MyService
            get() = this@MyService

    }


    fun log() {
        Log.d(TAG, "log in service")
    }

    companion object {

        private val TAG = "MyService"
    }
}
