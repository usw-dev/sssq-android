package com.example.android.Mainscreen;

import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.R;
import com.example.android.Settingscreen.setting;
import com.example.android.backbutton_event.backbutton_event;
import com.example.android.data.BlockChainDAO;
import com.example.android.data.UserWallet;
import com.example.android.drawer_sllide.drawer;
import com.example.android.drawer_sllide.slide;
import com.example.android.screen_2;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class screen_main extends slide {

    private DrawerLayout drawerLayout;
    private View drawerView;
    private Activity activity;
    private backbutton_event backbutton_event;
    private com.example.android.drawer_sllide.drawer screen_1_drawer;
    private com.example.android.drawer_sllide.slide screen_1_slide;
    private Button but_setting;
    private ImageButton but_refresh;
    private BarChart chart_month;
    private TextView address;
    private UserWallet user_wallet;
    private BlockChainDAO BDAO = (BlockChainDAO) getApplicationContext();

    int[] colorArray = new int[]{Color.RED, Color.BLUE};
//    boolean flag = true; //올라오고 내려온 상태
//    Animation transup; //올라오는 애니메이션
//    Animation transdown; //내려가는 애니메이션
//    LinearLayout bottomSheet; // 올라오고 내려갈 최근거래내역

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
//        //어카운트 이미지
//
//        Button button_setting = findViewById(R.id.but_setting);
//
//        account_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                screen_1_slide.onCreate(savedInstanceState);}
//        });
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

        but_refresh = findViewById(R.id.button_refresh);
        but_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_wallet = BDAO.getUserWallet();
            }
        });
        //새로고침 버튼

        chart_month = (BarChart) findViewById(R.id.chart_month);

        BarDataSet barDataSet = new BarDataSet(chart_values(), "Bar");
        barDataSet.setColors(colorArray);

        BarData barData = new BarData(barDataSet);
        chart_month.setData(barData);
        //차트
    };
    private ArrayList<BarEntry> chart_values() {
        ArrayList dataVals = new ArrayList<>();

        dataVals.add(new BarEntry(0, new float[]{3}, 5));
        dataVals.add(new BarEntry(1, new float[]{4}));
        dataVals.add(new BarEntry(3, new float[]{5}));
        dataVals.add(new BarEntry(4, new float[]{2}));
        dataVals.add(new BarEntry(6, new float[]{2}));
        dataVals.add(new BarEntry(7, new float[]{4}));
        return dataVals;
    }

    @Override
    public void onBackPressed() {
        backbutton_event.backbutton();
    }
    //뒤로가기 버튼 이벤트
}