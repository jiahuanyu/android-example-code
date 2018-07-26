package me.jiahuan.androidlearn.function.lru;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.LruCache;
import android.view.View;


import com.orhanobut.logger.Logger;

import me.jiahuan.androidlearn.base.BaseActivity;
import me.jiahuan.androidlearn.function.R;

public class LruCacheActivity extends BaseActivity {
    private static final String TAG = "LruCacheActivity";

    private LruCache<String, String> mLruCache = new LruCache<String, String>(10) { // 10个字节

        @Override
        protected int sizeOf(String key, String value) {
            return value.getBytes().length;
        }

        @Override
        protected void entryRemoved(boolean evicted, String key, String oldValue, String newValue) {
            Logger.d("剔除或者替换，key = %s, oldValue = %s, newValue = %s", key, oldValue, newValue);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity(R.layout.module_function_layout_lru_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void addItem(View v) {
        mLruCache.put(Math.random() + "", "test");
    }
}
