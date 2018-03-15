package me.jiahuan.androidlearn

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.main_layout_main_activity.*
import me.jiahuan.androidlearn.handler.HandlerActivity
import me.jiahuan.androidlearn.propertyanimation.PropertyAnimationActivity
import me.jiahuan.androidlearn.pullrefresh.PullRefreshActivity
import me.jiahuan.androidlearn.react.ReactNativeActivity
import me.jiahuan.androidlearn.service.ServiceActivity
import me.jiahuan.androidlearn.skin.SkinChangeActivity
import me.jiahuan.androidlearn.threadpool.ThreadPoolActivity


class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate, savedInstanceState = " + savedInstanceState)
        setContentView(R.layout.main_layout_main_activity)
        initialize()
        activity_main_pull_refresh_text_view.setOnClickListener {
            val intent = Intent(this, PullRefreshActivity::class.java)
            startActivity(intent)
        }
        activity_main_property_animation_text_view.setOnClickListener {
            val intent = Intent(this, PropertyAnimationActivity::class.java)
            startActivity(intent)
        }
        activity_main_bind_service_text_view.setOnClickListener {
            startActivity(Intent(this, ServiceActivity::class.java))
        }
        activity_main_open_ta_button.setOnClickListener {
            startActivity(Intent(this, TransparentActivity::class.java))
        }
        activity_main_open_dialog_button.setOnClickListener {
            AlertDialog.Builder(this@MainActivity).setMessage("XXXX").show()
        }
        activity_main_handler_activity_button.setOnClickListener {
            startActivity(Intent(this@MainActivity, HandlerActivity::class.java))
        }
        activity_main_react_native_button.setOnClickListener {
            startActivity(Intent(this, ReactNativeActivity::class.java))
        }
        activity_main_change_skin_button.setOnClickListener {
            startActivity(Intent(this, SkinChangeActivity::class.java))
        }
        activity_main_thread_pool_button.setOnClickListener {
            startActivity(Intent(this, ThreadPoolActivity::class.java))
        }
    }


    private fun initialize() {
        setSupportActionBar(main_layout_main_activity_tool_bar)
        if (supportActionBar != null) {
            supportActionBar!!.title = "测试"
        }

        val actionBarDrawerToggle = ActionBarDrawerToggle(this@MainActivity, main_layout_main_activity_drawer_layout, main_layout_main_activity_tool_bar, R.string.main_string_main_activity_navigation_drawer_open, R.string.main_string_main_activity_navigation_drawer_close)
        actionBarDrawerToggle.syncState()
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


    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        Log.d(TAG, "onConfigurationChanged")
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        Log.d(TAG, "onSaveInstanceState new")
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState")
    }
}
