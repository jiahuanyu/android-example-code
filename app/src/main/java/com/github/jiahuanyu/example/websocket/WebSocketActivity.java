package com.github.jiahuanyu.example.websocket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.github.jiahuanyu.example.BaseActivity;
import com.github.jiahuanyu.example.R;

import java.net.URI;

/**
 * Created by doom on 16/5/4.
 */
public class WebSocketActivity extends BaseActivity
{
    private ExampleClient mExampleClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initActivity(true, R.layout.activity_web_socket);
        initialize();
    }

    private void initialize()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                mExampleClient = new ExampleClient(URI.create("ws://ip:port"));
                mExampleClient.connect();
            }
        }.start();
    }

    public void sendMessage(View v)
    {
        if (mExampleClient != null)
        {
            mExampleClient.send("{\"type\":\"asd\"}");
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mExampleClient.close();
    }
}
