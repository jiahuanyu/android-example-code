package com.github.jiahuanyu.example.function.jni;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.jiahuanyu.example.R;
import com.github.jiahuanyu.example.ToolbarActivity;

/**
 * Created by doom on 2017/1/19.
 */

public class HelloJNIActivity extends ToolbarActivity {
    private static final String TAG = "HelloJNIActivity";

    private TextView mTextView;

    static {
        System.loadLibrary("hello-jni");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity(R.string.title_activity_hello_jni, true, R.layout.activity_hello_jni);
        initializeView();
    }

    private void initializeView() {
        mTextView = (TextView) findViewById(R.id.activity_hello_jni_textview);
        mTextView.setText(stringFromJNI());
    }


    public void writeFileJava(View v) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/jni.log";
        Log.d(TAG, "Path = " + path);
        writeFile(path);
    }

    //
    public native String stringFromJNI();

    // 写文件
    public native boolean writeFile(String path);
}
