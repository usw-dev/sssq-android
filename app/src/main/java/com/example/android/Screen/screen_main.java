package com.example.android.Screen;

import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.R;
import com.example.android.Action.backbutton_event;
import com.example.android.data.UserWallet;
import com.example.android.Action.drawer;
import com.example.android.Action.slide;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;

public class screen_main extends slide {

    private DrawerLayout drawerLayout;
    private View drawerView;
    private Activity activity;
    private backbutton_event backbutton_event;
    private com.example.android.Action.drawer screen_1_drawer;
    private com.example.android.Action.slide screen_1_slide;
    private Button but_setting;
    private ImageButton but_refresh;
    private BarChart chart_month;
    private TextView address;
    private UserWallet user_wallet;
    private Button button_ver;
//    private BlockChainDAO BDAO = (BlockChainDAO) getApplicationContext();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_main);

        screen_1_drawer = new drawer();
        //드로어 레이아웃
        backbutton_event = new backbutton_event(this);
        //뒤로가기 이벤트
        screen_1_slide = new slide();
        //계좌 터치 이벤트

        drawerLayout = findViewById(R.id.screen1);
        drawerView = findViewById(R.id.drawer);
        ImageButton btn_mymenu = findViewById(R.id.icon_mymenu);
        //매개변수에 필요한 id 탐색

        screen_1_drawer.drawer(drawerLayout, drawerView, btn_mymenu);
        //드로어 레이아웃 오픈

        ImageButton account_button = (ImageButton) findViewById(R.id.account_button);
        //어카운트 이미지

        bottomSheet = findViewById(R.id.bottomSheet);
        transup = AnimationUtils.loadAnimation(this, R.anim.translateup); //xml 위로올리는거 적용
        transdown = AnimationUtils.loadAnimation(this, R.anim.translatedown); //xml 밑으로 내리는거 적용
        Sliding animationListener = new Sliding(); //sliding 리스너 생성
        transup.setAnimationListener(animationListener); //transup에 리스너 적용
        transdown.setAnimationListener(animationListener);
        //계좌 터치 시 애니메이션 이벤트

        but_setting = findViewById(R.id.but_setting);
        but_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(screen_main.this, setting.class);
                startActivity(intent);
            }
        });
        //메인 -> 메뉴 -> 환경 설정


        button_ver = findViewById(R.id.app_ver);

        button_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(screen_main.this, screen_appver.class);
                startActivity(intent);
            }
        });
        //메인 -> 메뉴 -> 앱 버전

//        but_refresh = findViewById(R.id.button_refresh);
//        but_refresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                user_wallet = BDAO.getUserWallet();
//            }
//        });
        //새로고침 버튼

        chart_month = (BarChart) findViewById(R.id.chart_month);

        BarDataSet barDataSet = new BarDataSet(chart_values(),"수입");
        barDataSet.setColors(Color.rgb(121,231,231));
        BarDataSet barDataSet2 = new BarDataSet(chart_values2(),"지출");
        barDataSet2.setColors(Color.WHITE);

        ArrayList<String> labels = new ArrayList<>(Arrays.asList("1월","2월","3월","4월","5월","6월"));//x축 String 라벨

        barDataSet.setDrawValues(false); //데이터 텍스트값 나타내지않음
        barDataSet2.setDrawValues(false); //데이터 텍스트값 나타내지않음

        BarData barData = new BarData(barDataSet,barDataSet2);

        float groupSpace = 0.2f; //그룹사이 거리
        float barSpace = 0.02f; //그룹안 바 사이 거리
        float barWidth = 0.3f;// 전체 x축에서 한그룹의 넓이
        barData.setBarWidth(barWidth); //적용
        barData.groupBars(0,groupSpace,barSpace); //적용

        chart_month.setTouchEnabled(false); // 터치x
        chart_month.getAxisLeft().setDrawGridLines(false); //y축(가로줄) 격자 x 두개 같이써야 없어짐
        chart_month.getAxisLeft().setDrawAxisLine(false); // y축 왼쪽 선 제거
        chart_month.getAxisLeft().setDrawLabels(false); // y축 왼쪽 라벨 제거
        chart_month.getAxisRight().setDrawGridLines(false); //y축(가로줄) 격자 x 두개 같이써야 없어짐
        chart_month.getAxisRight().setDrawAxisLine(false); // y축 오른쪽 선 제거
        chart_month.getAxisRight().setDrawLabels(false); // y축 오른쪽 라벨 제거
        chart_month.getXAxis().setDrawGridLines(false); // x축(세로줄) 격자 제거
        chart_month.getXAxis().setDrawAxisLine(false); // x축 선 제거
        chart_month.getXAxis().setDrawLabels(true); // x축 라벨 사용
        chart_month.getXAxis().setTextColor(Color.WHITE); //x축라벨 색
        chart_month.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM); // 라벨위치 아래로
        chart_month.getLegend().setEnabled(false); //레전드(차트밑에 색과 라벨을 나타내는 설정)을 제거
        chart_month.getDescription().setEnabled(false); // 데스크립션 제거
        chart_month.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels)); //115줄의 x축 String 라벨적용

        chart_month.setData(barData);
        //차트
    };
    private ArrayList<BarEntry> chart_values() {
        ArrayList dataVals = new ArrayList<>();

        dataVals.add(new BarEntry(0, new float[]{3}, 5));
        dataVals.add(new BarEntry(1, new float[]{4}));
        dataVals.add(new BarEntry(2, new float[]{5}));
        dataVals.add(new BarEntry(3, new float[]{2}));
        dataVals.add(new BarEntry(4, new float[]{2}));
        dataVals.add(new BarEntry(5, new float[]{4}));
        return dataVals;
    }
    //q
    private ArrayList<BarEntry> chart_values2(){
        ArrayList dataVals = new ArrayList<>();

        dataVals.add(new BarEntry(0,new float[]{4},5));
        dataVals.add(new BarEntry(1,new float[]{2}));
        dataVals.add(new BarEntry(2,new float[]{2}));
        dataVals.add(new BarEntry(3,new float[]{5}));
        dataVals.add(new BarEntry(4,new float[]{4}));
        dataVals.add(new BarEntry(5,new float[]{3}));
        return dataVals;
    }
    //q
    @Override
    public void onBackPressed() {
        backbutton_event.backbutton();
    }
    //뒤로가기 버튼 이벤트
}