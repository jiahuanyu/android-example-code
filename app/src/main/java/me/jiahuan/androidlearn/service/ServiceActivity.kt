package me.jiahuan.androidlearn.service


import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.layout_service_activity.*

import me.jiahuan.androidlearn.R

class ServiceActivity : AppCompatActivity() {

    // private static final String TAG = "ServiceActivity"
    companion object {
        private val TAG = "ServiceActivity"
    }

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.d(TAG, "onServiceConnected")
            val body = (service as MyService.MyBinder).service
            body.log()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.d(TAG, "onServiceDisconnected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_service_activity)
        activity_service_bind_button.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
        }
        activity_service_unbind_button.setOnClickListener {
            unbindService(mConnection)
        }
    }

}
