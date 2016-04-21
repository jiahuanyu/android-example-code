package com.github.jiahuanyu.example.earphonerecycle;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.jiahuanyu.example.BaseActivity;
import com.github.jiahuanyu.example.R;

import java.util.LinkedList;

/**
 * Created by doom on 16/4/21.
 */
public class EarphoneRecycleActivity extends BaseActivity
{
    private AudioRecord mRecord
            ;
    private boolean mRunning = true;
    private int mRecordBufferSize;
    private byte[] mRecordBytes;

    private AudioTrack mTrack;
    private int mTrackBufferSize;
    private byte[] mTrackBytes;

    private LinkedList<byte[]> mData; // 存放音频数据

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initActivity(true, R.layout.activity_earphone_recycle);

        mRecordBufferSize = AudioRecord.getMinBufferSize(8000, // 设置音频数据的采样率
                AudioFormat.CHANNEL_CONFIGURATION_MONO, // 设置输出声道
                AudioFormat.ENCODING_PCM_16BIT);// 设置音频数据块是8位还是16位
        mRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, // 指定流类型
                8000, // 设置音频数据的采样率
                AudioFormat.CHANNEL_CONFIGURATION_MONO, // 设置输出声道
                AudioFormat.ENCODING_PCM_16BIT, // 设置音频数据块是8位还是16位
                mRecordBufferSize);
        mRecordBytes = new byte[mRecordBufferSize];


        mTrackBufferSize = AudioTrack.getMinBufferSize(8000,
                AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT);
        mTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 8000,
                AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT, mTrackBufferSize,
                AudioTrack.MODE_STREAM);
        mTrackBytes = new byte[mTrackBufferSize];

        mData = new LinkedList<>();

        new RecordThread().start();
        new PlayThread().start();
    }


    class RecordThread extends Thread
    {
        @Override
        public void run()
        {
            byte[] bytes_pkg;
            // 开始录音
            mRecord.startRecording();
            while (mRunning)
            {
                mRecord.read(mRecordBytes, 0, mRecordBufferSize);
                bytes_pkg = mRecordBytes.clone();
                if (mData.size() >= 2)
                {
                    mData.removeFirst();
                }
                mData.add(bytes_pkg);
            }
        }

    }


    class PlayThread extends Thread
    {
        @Override
        public void run()
        {
            byte[] bytes_pkg = null;
            mTrack.play();
            while (mRunning)
            {
                try
                {
                    mTrackBytes = mData.getFirst();
                    bytes_pkg = mTrackBytes.clone();
                    mTrack.write(bytes_pkg, 0, bytes_pkg.length);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mRunning = false;
        mRecord.stop();
        mRecord = null;
        mTrack.stop();
        mTrack = null;
    }
}
