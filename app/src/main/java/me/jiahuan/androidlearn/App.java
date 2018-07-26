package me.jiahuan.androidlearn;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chenenyu.router.Configuration;
import com.chenenyu.router.Router;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;


public class App extends Application {

    private static final String TAG = "App";
    
    private String mAppName = null;

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化日志系统
        LogStrategy logStrategy = new LogStrategy() {
            private String[] prefix = {". ", " ."};
            private int index = 0;

            @Override
            public void log(int priority, @Nullable String tag, @NonNull String message) {
                index = index ^ 1;
                Log.println(priority, prefix[index] + tag, message);
            }
        };
        FormatStrategy logFormatStrategy = PrettyFormatStrategy.newBuilder()
                .logStrategy(logStrategy)
                .showThreadInfo(true)
                .methodCount(2)
                .methodOffset(3)
                .tag(getAppName())
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(logFormatStrategy) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.DEBUG;
            }
        });
        //
        Router.initialize(new Configuration.Builder()
                .setDebuggable(BuildConfig.DEBUG)
                .registerModules("app", "module_function")
                .build());
    }

    private String getAppName() {
        if (mAppName == null) {
            try {
                PackageManager packageManager = getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
                return getResources().getString(packageInfo.applicationInfo.labelRes);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            mAppName = getResources().getResourceName(R.string.app_name);
        }
        return mAppName;
    }
}
