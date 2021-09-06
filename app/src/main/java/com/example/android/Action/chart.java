package com.example.android.Action;

import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class chart {
    public static String[] labels = new String[]{"01월", "02월", "03월", "04월", "05월", "06월"};

    public BarData barchart() {

        BarDataSet barDataSet = new BarDataSet(chart_values(), "수입");
        barDataSet.setColors(Color.rgb(121, 231, 231));
        BarDataSet barDataSet2 = new BarDataSet(chart_values2(), "지출");
        barDataSet2.setColors(Color.WHITE);

//        ArrayList<String> labels = new ArrayList<>(Arrays.asList("1월","2월","3월","4월","5월","6월"));//x축 String 라벨
         //위처럼 x축 string

        barDataSet.setDrawValues(false); //데이터 텍스트값 나타내지않음
        barDataSet2.setDrawValues(false); //데이터 텍스트값 나타내지않음

        BarData barData = new BarData(barDataSet, barDataSet2);

        float groupSpace = 0.56f; //그룹사이 거리
        float barSpace = 0.02f; //그룹안 바 사이 거리
        float barWidth = 0.2f;// 전체 x축에서 한그룹의 넓이
        barData.setBarWidth(barWidth); //적용
        barData.groupBars(0, groupSpace, barSpace); //적용
//        barData.getGroupWidth(0,0);

        //차트
        return barData;
    }
    private ArrayList<BarEntry> chart_values() {
        ArrayList dataVals = new ArrayList<>();

        dataVals.add(new BarEntry(0, new float[]{3}));
        dataVals.add(new BarEntry(1, new float[]{4}));
        dataVals.add(new BarEntry(2, new float[]{5}));
        dataVals.add(new BarEntry(4, new float[]{2}));
        dataVals.add(new BarEntry(5, new float[]{2}));
        dataVals.add(new BarEntry(6, new float[]{4}));
        return dataVals;
    }
    private ArrayList<BarEntry> chart_values2() {
        ArrayList dataVals = new ArrayList<>();

        dataVals.add(new BarEntry(0, new float[]{4}));
        dataVals.add(new BarEntry(1, new float[]{2}));
        dataVals.add(new BarEntry(2, new float[]{2}));
        dataVals.add(new BarEntry(4, new float[]{5}));
        dataVals.add(new BarEntry(5, new float[]{4}));
        dataVals.add(new BarEntry(6, new float[]{3}));
        return dataVals;
    }
}