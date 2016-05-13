package com.github.jiahuanyu.example.leak;

import android.os.Bundle;

import com.github.jiahuanyu.example.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by doom on 16/5/13.
 */
public class LeakActivity extends BaseActivity
{
    private List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //模拟Activity一些其他的对象
        for (int i = 0; i < 10000; i++)
        {
            list.add("Memory Leak!");
        }

        //开启线程
        new MyThread().start();
    }

    public class MyThread extends Thread
    {
        @Override
        public void run()
        {
            super.run();

            //模拟耗时操作
            try
            {
                Thread.sleep(10 * 60 * 1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

        }
    }
}
