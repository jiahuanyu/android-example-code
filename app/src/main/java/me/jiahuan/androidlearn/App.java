package me.jiahuan.androidlearn;

import android.app.Application;
import android.util.Log;

import com.chenenyu.router.Configuration;
import com.chenenyu.router.Router;


public class App extends Application {

    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Applicatin onCreate");
        Router.initialize(new Configuration.Builder()
                .setDebuggable(BuildConfig.DEBUG)
                .registerModules("app", "module_example", "module_home")
                .build());
    }
}
