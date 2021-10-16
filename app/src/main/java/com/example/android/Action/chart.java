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
    public static String[] labels = new String[]{"저번 달","이번 달"};

    public BarData barchart(float past_in, float past_out, float present_in,float present_out ){

        BarDataSet barDataSet = new BarDataSet(chart_values(past_in,present_in), "수입");
        barDataSet.setColors(Color.rgb(121, 231, 231));

        BarDataSet barDataSet2 = new BarDataSet(chart_values2(past_out,present_out), "지출");
        barDataSet2.setColors(Color.WHITE);

//        ArrayList<String> labels = new ArrayList<>(Arrays.asList("1월","2월","3월","4월","5월","6월"));//x축 String 라벨
         //위처럼 x축 string

        barDataSet.setDrawValues(false); //데이터 텍스트값 나타내지않음
        barDataSet2.setDrawValues(false); //데이터 텍스트값 나타내지않음

        BarData barData = new BarData(barDataSet, barDataSet2);

        float groupSpace = 0.2f; //그룹사이 거리
        float barSpace = 0.01f; //그룹안 바 사이 거리
        float barWidth = 0.3f;// 전체 x축에서 한그룹의 넓이

        barData.setBarWidth(barWidth); //적용
        barData.groupBars(-0.32f, groupSpace, barSpace); //적용
//        barData.getGroupWidth(0,0);

        //차트
        return barData;
    }
    private ArrayList<BarEntry> chart_values(float past_in,float present_in) {
        ArrayList dataVals = new ArrayList<>();

        dataVals.add(new BarEntry(0, past_in));
        dataVals.add(new BarEntry(1, present_in));
        return dataVals;
    }
    private ArrayList<BarEntry> chart_values2(float past_out,float present_out) {
        ArrayList dataVals = new ArrayList<>();

        dataVals.add(new BarEntry(0, past_out));
        dataVals.add(new BarEntry(1, present_out));
        return dataVals;
    }
}