package me.jiahuan.androidlearn.example.function.jni;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import java.util.Date;

import me.jiahuan.androidlearn.base.BaseActivity;
import me.jiahuan.androidlearn.example.R;


public class JNIActivity extends BaseActivity {

    private static float staticField1 = 11.3f;

    private double field1 = 10.9;

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivityWithToolbar(R.layout.module_example_layout_jni_activity, true);
        TextView textView = findViewById(R.id.id_layout_jni_activity_text_view);
        textView.setText(stringFromJNI() + "    " + stringFromJNI2());

        callJavaMethod();
    }

    public native String stringFromJNI();

    public native String stringFromJNI2();


    public native void callJavaMethod();

    public static void callStaticInJNI(String param) {
        Log.d("AndroidLearn", "callStaticInJNI with param " + param);
    }


    public void callInJNI(String param) {
        Log.d("AndroidLearn", "callInJNI with param " + param);
        Log.d("AndroidLearn", field1 + "   " + staticField1);
    }


}
