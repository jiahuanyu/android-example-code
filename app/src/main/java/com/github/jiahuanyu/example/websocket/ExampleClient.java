package com.github.jiahuanyu.example.websocket;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * Created by doom on 16/5/4.
 */
public class ExampleClient extends WebSocketClient
{

    private static final String TAG = "ExampleClient";

    public ExampleClient(URI serverURI)
    {
        super(serverURI);
    }


    @Override
    public void onOpen(ServerHandshake serverHandshake)
    {
        Log.d(TAG, "onOpen");
    }

    @Override
    public void onMessage(String s)
    {
        Log.d(TAG, "onMessage = " + s);
    }

    @Override
    public void onClose(int i, String s, boolean b)
    {
        Log.d(TAG, "onClose = " + s);
    }

    @Override
    public void onError(Exception e)
    {
        Log.d(TAG, "onError = " + e.getMessage());
    }
}
