package me.jiahuan.androidlearn.skin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.layout_skin_change_activity.*
import me.jiahuan.androidlearn.R


class SkinChangeActivity : AppCompatActivity() {

    companion object {
        private val TAG: String = "SkinChangeActivity"
    }

    private var mSharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")

        mSharedPreferences = getSharedPreferences("setting", Context.MODE_PRIVATE)

        val skin = mSharedPreferences!!.getInt("theme", 0)

        Log.d(TAG,"skin = " + skin)

        if (skin == 0) {
            setTheme(R.style.AppTheme)
        } else {
            setTheme(R.style.AppTheme_Dark)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_skin_change_activity)
        val editor = mSharedPreferences!!.edit()
        layout_skin_change_activity_change_inner_button.setOnClickListener {
            if (skin == 0) {
                editor.putInt("theme", 1)
            } else {
                editor.putInt("theme", 0)
            }
            editor.commit()

            recreate()
        }
    }
}
