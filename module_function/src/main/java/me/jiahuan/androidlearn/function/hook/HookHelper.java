package me.jiahuan.androidlearn.function.hook;

import android.app.Instrumentation;

import java.lang.reflect.Field;

public class HookHelper {


    public static void attachContext() throws Exception {
        Class activityThreadClass = Class.forName("android.app.ActivityThread");
        Field currentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
        currentActivityThreadField.setAccessible(true);
        Object currentActivityThread = currentActivityThreadField.get(null);

        Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
        mInstrumentationField.setAccessible(true);
        Instrumentation instrumentation = (Instrumentation) mInstrumentationField.get(currentActivityThread);


        ProxyInstrumentation proxyInstrumentation = new ProxyInstrumentation(instrumentation);
        mInstrumentationField.set(currentActivityThread, proxyInstrumentation);
    }

}
