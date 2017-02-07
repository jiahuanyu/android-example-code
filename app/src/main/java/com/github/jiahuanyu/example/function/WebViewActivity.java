package com.github.jiahuanyu.example.function;

import android.os.Bundle;
import android.webkit.WebView;

import com.github.jiahuanyu.example.R;
import com.github.jiahuanyu.example.ToolbarActivity;

/**
 * Created by doom on 2017/1/20.
 */

public class WebViewActivity extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity(R.string.title_activity_webview, true, R.layout.activity_webview);
        initializeView();
    }

    private void initializeView() {
        WebView webView = (WebView) findViewById(R.id.activity_webview_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://jiahuan.me/phytest/");
    }
}
