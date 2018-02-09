package com.github.wuxudong.rncharts.charts;

import android.view.View;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.wuxudong.rncharts.data.BarDataExtract;
import com.github.wuxudong.rncharts.data.DataExtract;
import com.github.wuxudong.rncharts.listener.RNOnChartValueSelectedListener;
import com.github.wuxudong.rncharts.listener.RNOnChartGestureListener;

public class HorizontalBarChartManager extends BarChartManager {

    @Override
    public String getName() {
        return "RNHorizontalBarChart";
    }

    @Override
    protected BarChart createViewInstance(ThemedReactContext reactContext) {
        HorizontalBarChart horizontalBarChart = new HorizontalBarChart(reactContext);
        horizontalBarChart.setOnChartValueSelectedListener(new RNOnChartValueSelectedListener(horizontalBarChart));
        horizontalBarChart.setOnChartGestureListener(new RNOnChartGestureListener(horizontalBarChart));
        return horizontalBarChart;
    }

    @ReactProp(name = "extraOffsets")
    public void setExtraOffsets(BarChart chart, ReadableArray array) {
        chart.setExtraOffsets((float) array.getDouble(0), (float) array.getDouble(1),
                (float) array.getDouble(2), (float) array.getDouble(3));
    }
}
