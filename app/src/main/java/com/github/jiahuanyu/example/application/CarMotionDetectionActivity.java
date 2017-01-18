package com.github.jiahuanyu.example.application;

import android.content.Context;
import android.graphics.Color;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.jiahuanyu.example.R;
import com.github.jiahuanyu.example.ToolbarActivity;
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
 * Created by doom on 16/7/11.
 */
public class CarMotionDetectionActivity extends ToolbarActivity {
    private static final String TAG = "CarMotionDetectionActivity";
    private static final String POWER_LOCK_TAG = "CarMotionDetectionActivity";
    private static final String LOG_DIRECTORY = "AccelerateLog";
    private static final int COLLECTION_RATE = 100; // 采样频率 ms

    private static final int MSG_SHOW_DATA_CHART = 0;
    private static final int MSG_SHOW_CALIBRATION_RESULT = 1;

    private List<Float> mYAccelerationValueList = new ArrayList<>();
    //    private List<Float> mTotalAccelerationValueList = new ArrayList<>();
    private List<Float> mDiffAccelerationValueList = new ArrayList<>();

    private int mYAccelerationCollectionCount;
    private int mDiffAccelerationCollectionCount;

    private float mXAccelerationValueOffset = 0; // X轴的Offset
    private float mYAccelerationValueOffset = 0; // Y轴的Offset
    private float mZAccelerationValueOffset = 0; // Z轴的Offset

    private float mPreYAccelerationValue = 0;// 上一次Y轴加速度的值

    private float mCalibrationValue = 0; //  校准的参数值
    private boolean mLog;
    private File mLogFile;
    private FileOutputStream mFileOutputStream;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");


    private PowerManager mPowerManager;
    private PowerManager.WakeLock mWakeLock;
    private SensorManager mSensorManager;

    private float[] mAccelerateData = new float[3];

    private TextView mCalibrationInfoTextView;
    private TextView mLogName;
    private TextView mYChatValueTitle;
    private TextView mTotalChatValueTitle;

    private LineChart mYAxisLineChart;
    private List<String> mYAxisXValues = new ArrayList<>();
    private List<Entry> mYAxisYValues = new ArrayList<>();


    private LineChart mTotalAxisLineChart;
    private List<String> mTotalAxisXValues = new ArrayList<>();
    private List<Entry> mTotalAxisYValues = new ArrayList<>();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MSG_SHOW_CALIBRATION_RESULT: {
                    mCalibrationInfoTextView.setText(format(mXAccelerationValueOffset) + "    " + format(mYAccelerationValueOffset) + "    " + format(mZAccelerationValueOffset) + "    " + format(mCalibrationValue));
                }
                break;


                case MSG_SHOW_DATA_CHART: {
                    if (mAccelerateData[0] == 0 || mAccelerateData[1] == 0 || mAccelerateData[2] == 0) {
                        mHandler.sendEmptyMessageDelayed(MSG_SHOW_DATA_CHART, COLLECTION_RATE);
                        return;
                    }

                    if (mYAccelerationValueList.size() == 10) {
                        float yValueSum = 0;
                        for (float value : mYAccelerationValueList) {
                            yValueSum += (value);
                        }
                        float yValueAverage = yValueSum / mYAccelerationValueList.size();
                        mYChatValueTitle.setText(format(yValueAverage));
                        float time = (mYAccelerationCollectionCount * COLLECTION_RATE) / 1000f;
                        if (time > 20) {
                            mYAxisXValues.remove(0);
                            mYAxisYValues.remove(0);
                            for (Entry entry : mYAxisYValues) {
                                entry.setXIndex(entry.getXIndex() - 1);
                            }
                        }
                        mYAxisXValues.add(format(time));
                        mYAxisYValues.add(new Entry(yValueAverage, mYAxisXValues.size() - 1));
                        mYAxisLineChart.setData(generateData(1));
                        mYAxisLineChart.postInvalidate();
                        mYAccelerationCollectionCount++;
                    }

                    if (mDiffAccelerationValueList.size() == 19) {
                        float time = (mDiffAccelerationCollectionCount * COLLECTION_RATE) / 1000f;
                        float totalValueSum = 0;
                        for (float value : mDiffAccelerationValueList) {
                            totalValueSum += value;
                        }
                        float totalAverage = totalValueSum;
                        mTotalChatValueTitle.setText(format(totalAverage));
                        if (time > 20) {
                            mTotalAxisXValues.remove(0);
                            mTotalAxisYValues.remove(0);
                            for (Entry entry : mTotalAxisYValues) {
                                entry.setXIndex(entry.getXIndex() - 1);
                            }
                        }

                        mTotalAxisXValues.add(format(time));
                        mTotalAxisYValues.add(new Entry(totalAverage, mTotalAxisXValues.size() - 1));
                        mTotalAxisLineChart.setData(generateData(3));
                        mTotalAxisLineChart.postInvalidate();
                        mDiffAccelerationCollectionCount++;
                    }


                    if (mLog) {
                        try {
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
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                    mHandler.sendEmptyMessageDelayed(MSG_SHOW_DATA_CHART, COLLECTION_RATE);
                }
                break;
            }
        }
    };

    private LineData generateData(int type) {
        if (type == 1) {
            LineDataSet ySet = new LineDataSet(mYAxisYValues, "Y-Acceleration");
            ySet.setLineWidth(1.75f); // 线宽
            ySet.setDrawCircles(false);
            ySet.setColor(Color.RED);// 显示颜色
            ySet.setHighLightColor(Color.RED); // 高亮的线的颜色
            ySet.setDrawValues(false);
            LineData data = new LineData(mYAxisXValues, ySet);
            return data;
        } else if (type == 3) {
            LineDataSet totalSet = new LineDataSet(mTotalAxisYValues, "Total-Acceleration");
            totalSet.setLineWidth(1.75f); // 线宽
            totalSet.setDrawCircles(false);
            totalSet.setColor(Color.MAGENTA);// 显示颜色
            totalSet.setHighLightColor(Color.MAGENTA); // 高亮的线的颜色
            totalSet.setDrawValues(false);
            LineData data = new LineData(mTotalAxisXValues, totalSet);
            return data;
        }
        return null;
    }

    private String format(float value) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(value);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity(R.string.title_activity_car_motion_detection, true, R.layout.activity_car_motion_detection);
        initializeView();
    }


    /**
     * 校准
     */
    public void calibrate(View v) {
        showProgressDialog();
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (mAccelerateData[0] == 0 || mAccelerateData[1] == 0 || mAccelerateData[2] == 0) {
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                int count = 0;
                float xValueSum = 0, yValueSum = 0, zValueSum = 0;

                while (count < 20) {
                    xValueSum += mAccelerateData[0];
                    yValueSum += mAccelerateData[1];
                    zValueSum += mAccelerateData[2];
                    count++;
                    try {
                        sleep(COLLECTION_RATE);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                float xValueAverage = xValueSum / count;
                float yValueAverage = yValueSum / count;
                float zValueAverage = zValueSum / count;

                Log.d(TAG, "xValueAverage = " + xValueAverage);
                Log.d(TAG, "yValueAverage = " + yValueAverage);
                Log.d(TAG, "zValueAverage = " + zValueAverage);

                mXAccelerationValueOffset = xValueAverage;
                mYAccelerationValueOffset = yValueAverage;
                mZAccelerationValueOffset = zValueAverage;

                mCalibrationValue = (float) (Math.sqrt(zValueAverage * zValueAverage + yValueAverage * yValueAverage) / zValueAverage);

                dismissProgressDialog();
                mHandler.sendEmptyMessage(MSG_SHOW_CALIBRATION_RESULT);
            }
        }.start();
    }

    private void initializeView() {
        mPowerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = mPowerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, POWER_LOCK_TAG);
        mWakeLock.acquire();

        mCalibrationInfoTextView = (TextView) findViewById(R.id.activity_car_motion_detection_calibration_info_tv);
        mCalibrationInfoTextView.setText(format(mXAccelerationValueOffset) + "    " + format(mYAccelerationValueOffset) + "    " + format(mZAccelerationValueOffset) + "    " + format(mCalibrationValue));
        mLogName = (TextView) findViewById(R.id.activity_car_motion_detection_log_name_tv);
        mYChatValueTitle = (TextView) findViewById(R.id.activity_car_motion_detection_y_axis_chat_title);
        mTotalChatValueTitle = (TextView) findViewById(R.id.activity_car_motion_detection_total_axis_chat_title);

        mYAxisLineChart = (LineChart) findViewById(R.id.activity_car_motion_detection_y_axis_chat);
        mYAxisLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mYAxisLineChart.getAxis(YAxis.AxisDependency.LEFT).setAxisMaxValue(5);
        mYAxisLineChart.getAxis(YAxis.AxisDependency.LEFT).setAxisMinValue(-5);
        mYAxisLineChart.getAxis(YAxis.AxisDependency.RIGHT).setAxisMaxValue(5);
        mYAxisLineChart.getAxis(YAxis.AxisDependency.RIGHT).setAxisMinValue(-5);
        mYAxisLineChart.setDragEnabled(true);

        mTotalAxisLineChart = (LineChart) findViewById(R.id.activity_car_motion_detection_total_axis_chat);
        mTotalAxisLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mTotalAxisLineChart.getAxis(YAxis.AxisDependency.LEFT).setAxisMaxValue(10);
        mTotalAxisLineChart.getAxis(YAxis.AxisDependency.LEFT).setAxisMinValue(-1);
        mTotalAxisLineChart.getAxis(YAxis.AxisDependency.RIGHT).setAxisMaxValue(10);
        mTotalAxisLineChart.getAxis(YAxis.AxisDependency.RIGHT).setAxisMinValue(-1);
        mTotalAxisLineChart.setDragEnabled(true);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerateSensor != null) {
            mSensorManager.registerListener(mSensorEventListener, accelerateSensor, SensorManager.SENSOR_DELAY_FASTEST);
        }
        mHandler.sendEmptyMessageDelayed(MSG_SHOW_DATA_CHART, COLLECTION_RATE);
    }


    public void startLog(View v) {
        if (mLog) {
            ((Button) v).setText("开启日志");
            mLog = false;
        } else {
            File logDirectory = new File(Environment.getExternalStorageDirectory() + File.separator + LOG_DIRECTORY);
            if (!logDirectory.exists()) {
                logDirectory.mkdirs();
            }

            String logName = mDateFormat.format(new Date());

            mLogFile = new File(Environment.getExternalStorageDirectory() + File.separator + LOG_DIRECTORY + File.separator + logName);

            mLogName.setText(logName);

            ((Button) v).setText("停止日志");
            mLog = true;
        }
    }


    private SensorEventListener mSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            mAccelerateData[0] = event.values[0];
            mAccelerateData[1] = event.values[1];
            mAccelerateData[2] = event.values[2];
            if (mAccelerateData[0] == 0 || mAccelerateData[1] == 0 || mAccelerateData[2] == 0) {
                return;
            }

//            float xAfterOffsetValue = mAccelerateData[0] - mXAccelerationValueOffset;
            float yAfterOffsetValue = mAccelerateData[1] - mYAccelerationValueOffset;
//            float zAfterOffsetValue = mAccelerateData[2] - mZAccelerationValueOffset;

            mYAccelerationValueList.add(yAfterOffsetValue);

            mDiffAccelerationValueList.add(Math.abs(mAccelerateData[1] - mPreYAccelerationValue));

//            if(mDiffAccelerationValueList) {
//
//            }

//            float total = (float) Math.sqrt(xAfterOffsetValue * xAfterOffsetValue + yAfterOffsetValue * yAfterOffsetValue + zAfterOffsetValue * zAfterOffsetValue);
//            mTotalAccelerationValueList.add(total);
            if (mYAccelerationValueList.size() == 11) {
                mYAccelerationValueList.remove(0);
            }

            if (mDiffAccelerationValueList.size() == 20) {
                mDiffAccelerationValueList.remove(0);
            }
            mPreYAccelerationValue = mAccelerateData[1];
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(MSG_SHOW_DATA_CHART);
        mWakeLock.release();
        mSensorManager.unregisterListener(mSensorEventListener);
    }
}
