package me.jiahuan.androidlearn.example.ui.chart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import me.jiahuan.androidlearn.base.BaseActivity;
import me.jiahuan.androidlearn.example.R;

public class ChartActivity extends BaseActivity {

    private LineChart mLineChartView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivityWithToolbar(R.layout.module_example_layout_chat_view, true);
        initialize();
    }

    private void initialize() {
        configLineChart();
    }

    private void configLineChart() {
        mLineChartView = findViewById(R.id.id_chat_activity_module_example_layout_chat_view_line_chart);
        //
        mLineChartView.setDrawGridBackground(false);// 不绘制背景表格
        mLineChartView.setDrawBorders(false); // 不绘制图表边界

        List<Entry> dataEntry1 = new ArrayList<>();
        dataEntry1.add(new Entry(1, 20));
        dataEntry1.add(new Entry(2, 100));
        dataEntry1.add(new Entry(3, 50));

        LineDataSet lineDataSet1 = new LineDataSet(dataEntry1, "label1");
        lineDataSet1.setColor(Color.BLUE);

        List<ILineDataSet> lineDataSetList = new ArrayList<>();
        lineDataSetList.add(lineDataSet1);

        LineData lineData = new LineData(lineDataSetList);
        mLineChartView.setData(lineData);

        mLineChartView.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mLineChartView.getXAxis().setGranularity(1f); // 最小间距

        mLineChartView.getAxisLeft().setEnabled(false);// 设置左边Y轴不可见
        mLineChartView.getAxisRight().setEnabled(false); // 设置右边Y轴不可见

        mLineChartView.invalidate();
    }

}
