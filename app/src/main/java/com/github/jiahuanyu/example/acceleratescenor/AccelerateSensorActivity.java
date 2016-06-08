package com.github.jiahuanyu.example.acceleratescenor;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jiahuanyu.example.BaseActivity;
import com.github.jiahuanyu.example.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by doom on 16/6/5.
 */
public class AccelerateSensorActivity extends BaseActivity
{
    private static final String TAG = "AccelerateSensorActivity";

    private static final String POWER_LOCK_TAG = "AccelerateSensorActivity";

    private static final String LOG_DIRECTORY = "AccelerateLog";

    private boolean mLog = false;

    private SensorManager mSensorManager;

    private File mLogFile;
    private FileOutputStream mFileOutputStream;


    private String[] mAxisArray = {"X", "Y", "Z"};

    private TextView mXValue;
    private TextView mYValue;
    private TextView mZValue;
    private TextView mTotalValue;
    private TextView mDiffValue;

    private TextView mLogName;

    private TextView mAxisParallel;

    private Button mDeleteLogBtn;

    private Button mSetAxisParallelBtn;

    private Button mCleanChatBtn;

    private PowerManager mPowerManager;

    private PowerManager.WakeLock mWakeLock;

    private int mCollectionRate = 100;

    private EditText mCollectionRateEdit;

    private float[] mAccelerateData = new float[3];

    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");

    private String mCurrentAxisParallel = "Y";

    private int mCollectionCount;

    private LineChart mXAxisLineChart;
    private List<String> mXAxisXValues = new ArrayList<>();
    private List<Entry> mXAxisYValues = new ArrayList<>();


    private LineChart mYAxisLineChart;
    private List<String> mYAxisXValues = new ArrayList<>();
    private List<Entry> mYAxisYValues = new ArrayList<>();


    private LineChart mTotalAxisLineChart;
    private List<String> mTotalAxisXValues = new ArrayList<>();
    private List<Entry> mTotalAxisYValues = new ArrayList<>();


    private LineChart mZAxisLineChart;
    private List<String> mZAxisXValues = new ArrayList<>();
    private List<Entry> mZAxisYValues = new ArrayList<>();

    private LineChart mDiffAxisLineChart;
    private List<String> mDiffAxisXValues = new ArrayList<>();
    private List<Entry> mDiffAxisYValues = new ArrayList<>();


    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            if (msg.what == 0)
            {

                if (mAccelerateData[0] == 0 || mAccelerateData[1] == 0 || mAccelerateData[2] == 0)
                {
                    display();
                    return;
                }

                mXValue.setText(format(mAccelerateData[0]));
                mYValue.setText(format(mAccelerateData[1]));
                mZValue.setText(format(mAccelerateData[2]));
                float total = (float) Math.sqrt(mAccelerateData[0] * mAccelerateData[0] + mAccelerateData[1] * mAccelerateData[1] + mAccelerateData[2] * mAccelerateData[2]);
                mTotalValue.setText(format(total));
                mDiffValue.setText(format(total - mAccelerateData[2]));

                float time = (mCollectionCount * mCollectionRate) / 1000f;

                if (time > 15)
                {
                    mXAxisXValues.remove(0);
                    mXAxisYValues.remove(0);
                    for (Entry entry : mXAxisYValues)
                    {
                        entry.setXIndex(entry.getXIndex() - 1);
                    }

                    mYAxisXValues.remove(0);
                    mYAxisYValues.remove(0);
                    for (Entry entry : mYAxisYValues)
                    {
                        entry.setXIndex(entry.getXIndex() - 1);
                    }

                    mZAxisXValues.remove(0);
                    mZAxisYValues.remove(0);
                    for (Entry entry : mZAxisYValues)
                    {
                        entry.setXIndex(entry.getXIndex() - 1);
                    }


                    mTotalAxisXValues.remove(0);
                    mTotalAxisYValues.remove(0);
                    for (Entry entry : mTotalAxisYValues)
                    {
                        entry.setXIndex(entry.getXIndex() - 1);
                    }

                    mDiffAxisXValues.remove(0);
                    mDiffAxisYValues.remove(0);
                    for (Entry entry : mDiffAxisYValues)
                    {
                        entry.setXIndex(entry.getXIndex() - 1);
                    }
                }

                mXAxisXValues.add(format(time));
                mXAxisYValues.add(new Entry(mAccelerateData[0], mXAxisXValues.size() - 1));
                mXAxisLineChart.setData(generateData(0));
                mXAxisLineChart.postInvalidate();

                mYAxisXValues.add(format(time));
                mYAxisYValues.add(new Entry(mAccelerateData[1], mYAxisXValues.size() - 1));
                mYAxisLineChart.setData(generateData(1));
                mYAxisLineChart.postInvalidate();

                mZAxisXValues.add(format(time));
                mZAxisYValues.add(new Entry(mAccelerateData[2], mZAxisXValues.size() - 1));
                mZAxisLineChart.setData(generateData(2));
                mZAxisLineChart.postInvalidate();

                mTotalAxisXValues.add(format(time));
                mTotalAxisYValues.add(new Entry(total, mTotalAxisXValues.size() - 1));
                mTotalAxisLineChart.setData(generateData(3));
                mTotalAxisLineChart.postInvalidate();


                mDiffAxisXValues.add(format(time));
                mDiffAxisYValues.add(new Entry(total - mAccelerateData[2], mDiffAxisXValues.size() - 1));
                mDiffAxisLineChart.setData(generateData(4));
                mDiffAxisLineChart.postInvalidate();


                mCollectionCount++;

                if (mLog)
                {
                    try
                    {
                        StringBuilder sb = new StringBuilder();

                        sb.append(format(mAccelerateData[0]));
                        sb.append("\t");
                        sb.append(format(mAccelerateData[1]));
                        sb.append("\t");
                        sb.append(format(mAccelerateData[2]));
                        sb.append("\t\n");

                        mFileOutputStream = new FileOutputStream(mLogFile, true);
                        mFileOutputStream.write(sb.toString().getBytes());
                        mFileOutputStream.close();
                    }
                    catch (Exception e)
                    {
                        Log.d(TAG, e.getMessage());
                        e.printStackTrace();
                    }
                }


                double angle = Math.acos(mAccelerateData[2] / 10.0f);

                Log.d(TAG, "handleMessage: angle = " + angle);

                display();
            }
        }
    };

    private SensorEventListener mSensorEventListener = new SensorEventListener()
    {
        @Override
        public void onSensorChanged(SensorEvent event)
        {
            mAccelerateData[0] = event.values[0];
            mAccelerateData[1] = event.values[1];
            mAccelerateData[2] = event.values[2];
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy)
        {
            Log.d(TAG, "onAccuracyChanged: ");
        }
    };

    private String format(float value)
    {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(value);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initActivity(true, R.layout.activity_accelerate_sensor);

        mPowerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = mPowerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, POWER_LOCK_TAG);
        mWakeLock.acquire();

        mXValue = (TextView) findViewById(R.id.activity_accelerate_sensor_x_value_tv);
        mYValue = (TextView) findViewById(R.id.activity_accelerate_sensor_y_value_tv);
        mZValue = (TextView) findViewById(R.id.activity_accelerate_sensor_z_value_tv);
        mTotalValue = (TextView) findViewById(R.id.activity_accelerate_sensor_total_value_tv);
        mDiffValue = (TextView) findViewById(R.id.activity_accelerate_sensor_diff_value_tv);

        mLogName = (TextView) findViewById(R.id.activity_accelerate_log_name_tv);
        mAxisParallel = (TextView) findViewById(R.id.activity_accelerate_axis_parallel_tv);
        mAxisParallel.setText(mCurrentAxisParallel);

        mCollectionRateEdit = (EditText) findViewById(R.id.activity_accelerate_sensor_rate_et);


        mSetAxisParallelBtn = (Button) findViewById(R.id.activity_accelerate_set_axis_parallel_btn);
        mSetAxisParallelBtn.setOnClickListener(mOnClickListener);

        mCleanChatBtn = (Button) findViewById(R.id.activity_accelerate_clean_chart_btn);
        mCleanChatBtn.setOnClickListener(mOnClickListener);

        mXAxisLineChart = (LineChart) findViewById(R.id.activity_accelerate_x_axis_chat);
        mXAxisLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mXAxisLineChart.getAxis(YAxis.AxisDependency.LEFT).setAxisMaxValue(10);
        mXAxisLineChart.getAxis(YAxis.AxisDependency.LEFT).setAxisMinValue(-10);
        mXAxisLineChart.getAxis(YAxis.AxisDependency.RIGHT).setAxisMaxValue(10);
        mXAxisLineChart.getAxis(YAxis.AxisDependency.RIGHT).setAxisMinValue(-10);
        mXAxisLineChart.setDragEnabled(true);

        mYAxisLineChart = (LineChart) findViewById(R.id.activity_accelerate_y_axis_chat);
        mYAxisLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mYAxisLineChart.getAxis(YAxis.AxisDependency.LEFT).setAxisMaxValue(10);
        mYAxisLineChart.getAxis(YAxis.AxisDependency.LEFT).setAxisMinValue(-10);
        mYAxisLineChart.getAxis(YAxis.AxisDependency.RIGHT).setAxisMaxValue(10);
        mYAxisLineChart.getAxis(YAxis.AxisDependency.RIGHT).setAxisMinValue(-10);
        mYAxisLineChart.setDragEnabled(true);


        mZAxisLineChart = (LineChart) findViewById(R.id.activity_accelerate_z_axis_chat);
        mZAxisLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mZAxisLineChart.getAxis(YAxis.AxisDependency.LEFT).setAxisMaxValue(12);
        mZAxisLineChart.getAxis(YAxis.AxisDependency.LEFT).setAxisMinValue(-12);
        mZAxisLineChart.getAxis(YAxis.AxisDependency.RIGHT).setAxisMaxValue(12);
        mZAxisLineChart.getAxis(YAxis.AxisDependency.RIGHT).setAxisMinValue(-12);
        mZAxisLineChart.setDragEnabled(true);


        mTotalAxisLineChart = (LineChart) findViewById(R.id.activity_accelerate_total_axis_chat);
        mTotalAxisLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mTotalAxisLineChart.getAxis(YAxis.AxisDependency.LEFT).setAxisMaxValue(20);
        mTotalAxisLineChart.getAxis(YAxis.AxisDependency.LEFT).setAxisMinValue(-20);
        mTotalAxisLineChart.getAxis(YAxis.AxisDependency.RIGHT).setAxisMaxValue(20);
        mTotalAxisLineChart.getAxis(YAxis.AxisDependency.RIGHT).setAxisMinValue(-20);
        mTotalAxisLineChart.setDragEnabled(true);


        mDiffAxisLineChart = (LineChart) findViewById(R.id.activity_accelerate_diff_axis_chat);
        mDiffAxisLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mDiffAxisLineChart.getAxis(YAxis.AxisDependency.LEFT).setAxisMaxValue(10);
        mDiffAxisLineChart.getAxis(YAxis.AxisDependency.LEFT).setAxisMinValue(-10);
        mDiffAxisLineChart.getAxis(YAxis.AxisDependency.RIGHT).setAxisMaxValue(10);
        mDiffAxisLineChart.getAxis(YAxis.AxisDependency.RIGHT).setAxisMinValue(-10);
        mDiffAxisLineChart.setDragEnabled(true);


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Sensor accelerateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelerateSensor != null)
        {
            mSensorManager.registerListener(mSensorEventListener, accelerateSensor, SensorManager.SENSOR_DELAY_FASTEST);
        }

        display();
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.activity_accelerate_set_axis_parallel_btn:
                    new AlertDialog.Builder(AccelerateSensorActivity.this).setTitle("选择主轴").setItems(mAxisArray, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            mCurrentAxisParallel = mAxisArray[which];
                            mAxisParallel.setText(mCurrentAxisParallel);
                        }
                    }).show();
                    break;

                case R.id.activity_accelerate_clean_chart_btn:
                    mXAxisXValues.clear();
                    mXAxisYValues.clear();

                    mYAxisXValues.clear();
                    mYAxisYValues.clear();

                    mZAxisXValues.clear();
                    mZAxisYValues.clear();

                    mTotalAxisXValues.clear();
                    mTotalAxisYValues.clear();

                    mDiffAxisXValues.clear();
                    mDiffAxisYValues.clear();

                    mCollectionCount = 0;
                    break;
            }
        }
    };

    public void startLog(View v)
    {
        if (mLog)
        {
            ((Button) v).setText("开启日志");
            mLog = false;
            mDeleteLogBtn.setEnabled(true);
        }
        else
        {
            File logDirectory = new File(Environment.getExternalStorageDirectory() + File.separator + LOG_DIRECTORY);
            if (!logDirectory.exists())
            {
                logDirectory.mkdirs();
            }

            String logName = mDateFormat.format(new Date()) + mCurrentAxisParallel;

            mLogFile = new File(Environment.getExternalStorageDirectory() + File.separator + LOG_DIRECTORY + File.separator + logName);

            mLogName.setText(logName);

            ((Button) v).setText("停止日志");
            mLog = true;
            mDeleteLogBtn.setEnabled(false);
        }
    }

    public void setCollectionRate(View v)
    {
        String rate = mCollectionRateEdit.getText().toString();
        if (!TextUtils.isEmpty(rate))
        {
            mCollectionRate = Integer.parseInt(rate);
        }
    }

    private void display()
    {
        Message msg = new Message();
        msg.what = 0;
        mHandler.sendMessageDelayed(msg, mCollectionRate);
    }

    private LineData generateData(int type)
    {
        if (type == 0)
        {
            LineDataSet xSet = new LineDataSet(mXAxisYValues, "X-Acceleration");
            xSet.setLineWidth(1.75f); // 线宽
            xSet.setDrawCircles(false);
            xSet.setColor(Color.BLUE);// 显示颜色
            xSet.setHighLightColor(Color.BLUE); // 高亮的线的颜色
            xSet.setDrawValues(false);
            LineData data = new LineData(mXAxisXValues, xSet);
            return data;
        }
        else if (type == 1)
        {
            LineDataSet ySet = new LineDataSet(mYAxisYValues, "Y-Acceleration");
            ySet.setLineWidth(1.75f); // 线宽
            ySet.setDrawCircles(false);
            ySet.setColor(Color.RED);// 显示颜色
            ySet.setHighLightColor(Color.RED); // 高亮的线的颜色
            ySet.setDrawValues(false);
            LineData data = new LineData(mYAxisXValues, ySet);
            return data;
        }
        else if (type == 2)
        {
            LineDataSet zSet = new LineDataSet(mZAxisYValues, "Z-Acceleration");
            zSet.setLineWidth(1.75f); // 线宽
            zSet.setDrawCircles(false);
            zSet.setColor(Color.GREEN);// 显示颜色
            zSet.setHighLightColor(Color.GREEN); // 高亮的线的颜色
            zSet.setDrawValues(false);
            LineData data = new LineData(mZAxisXValues, zSet);
            return data;
        }
        else if (type == 3)
        {
            LineDataSet totalSet = new LineDataSet(mTotalAxisYValues, "Total-Acceleration");
            totalSet.setLineWidth(1.75f); // 线宽
            totalSet.setDrawCircles(false);
            totalSet.setColor(Color.MAGENTA);// 显示颜色
            totalSet.setHighLightColor(Color.MAGENTA); // 高亮的线的颜色
            totalSet.setDrawValues(false);
            LineData data = new LineData(mTotalAxisXValues, totalSet);
            return data;
        }
        else if (type == 4)
        {
            LineDataSet diffSet = new LineDataSet(mDiffAxisYValues, "Diff-Acceleration");
            diffSet.setLineWidth(1.75f); // 线宽
            diffSet.setDrawCircles(false);
            diffSet.setColor(Color.CYAN);// 显示颜色
            diffSet.setHighLightColor(Color.CYAN); // 高亮的线的颜色
            diffSet.setDrawValues(false);
            LineData data = new LineData(mDiffAxisXValues, diffSet);
            return data;
        }
        return null;
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mHandler.removeMessages(0);
        mWakeLock.release();
        mSensorManager.unregisterListener(mSensorEventListener);
    }
}
