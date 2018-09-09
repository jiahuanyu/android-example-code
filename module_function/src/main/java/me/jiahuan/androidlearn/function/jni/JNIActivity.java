package me.jiahuan.androidlearn.function.jni;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import me.jiahuan.androidlearn.base.BaseActivity;
import me.jiahuan.androidlearn.function.R;

public class JNIActivity extends BaseActivity {

    static {
        System.loadLibrary("native-lib");
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity(R.layout.module_function_layout_jni_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initialize();
    }

    private void initialize() {
        ((TextView) findViewById(R.id.id_module_function_layout_jni_activity_from_jni_text_view)).setText(stringFromJNI() + "    " + stringFromJNI2());
    }

    public native String stringFromJNI();

    public native String stringFromJNI2();
}
