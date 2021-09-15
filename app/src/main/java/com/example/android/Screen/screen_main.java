package com.example.android.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.Action.chart;
import com.example.android.R;
import com.example.android.Action.backbutton_event;
import com.example.android.data.Connect;
import com.example.android.data.UserWallet;
import com.example.android.Action.drawer;
import com.example.android.Action.slide;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class screen_main extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerView;
    private Activity activity;
    private backbutton_event backbutton_event;
    private com.example.android.Action.drawer screen_1_drawer;
    private com.example.android.Action.slide screen_1_slide;
    private com.example.android.Action.chart screen_1_chart;
    private com.example.android.data.Connect connect;
    private BarChart chart_month;
    private Button but_setting;
    private ImageButton but_refresh;
    private TextView address;
    private UserWallet user_wallet;
    private Button button_ver;
    private Button button_info;
    private Button button_sup;
    private LinearLayout senddata;
    private BottomSheetBehavior behavior;
    private EditText sendaddress; //송금하기 주소
    private EditText sendmoney; //송금하기 이더
    private TextView zkem;
    public static Web3j web3j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_main);

        zkem = findViewById(R.id.card_ETH);

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

        senddata = findViewById(R.id.senddata);
        behavior = BottomSheetBehavior.from(senddata);
        ImageButton account_button = findViewById(R.id.account_button);
        account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(behavior.getState()!=BottomSheetBehavior.STATE_HALF_EXPANDED){
                    behavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);

                }
                else {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        sendaddress = findViewById(R.id.sendaddress);
        sendmoney = findViewById(R.id.sendmoney);

//        transup = AnimationUtils.loadAnimation(this, R.anim.translateup); //xml 위로올리는거 적용
//        transdown = AnimationUtils.loadAnimation(this, R.anim.translatedown); //xml 밑으로 내리는거 적용
//        Sliding animationListener = new Sliding(); //sliding 리스너 생성
//        transup.setAnimationListener(animationListener); //transup에 리스너 적용
//        transdown.setAnimationListener(animationListener);
//
//        sendaddress = findViewById(R.id.sendaddress);
//        sendmoney = findViewById(R.id.sendmoney);
        //계좌 터치 시 애니메이션 이벤트

        button_ver = findViewById(R.id.app_ver);

        button_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(screen_main.this, screen_appver.class);
                startActivity(intent);
            }
        });
        //메인 -> 메뉴 -> 앱 버전

        button_info = findViewById(R.id.app_info);

        button_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(screen_main.this, screen_appinfo.class);
                startActivity(intent);
            }
        });
        //메인 -> 메뉴 -> 앱 정보

        button_sup = findViewById(R.id.app_sup);

        button_sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(screen_main.this, screen_sup.class);
                startActivity(intent);
            }
        });
        //메인 -> 메뉴 -> 문의하기
//        but_refresh = findViewById(R.id.button_refresh);
//        but_refresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                user_wallet = BDAO.getUserWallet();
//            }
//        });
        //새로고침 버튼

        screen_1_chart = new chart();

        chart_month = (BarChart) findViewById(R.id.chart_month);

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
        chart_month.getXAxis().setLabelCount(6); // 라벨6개 고정
        chart_month.getXAxis().setCenterAxisLabels(true); //라벨 가운데로
        chart_month.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM); // 라벨위치 아래로
        chart_month.getLegend().setEnabled(false); //레전드(차트밑에 색과 라벨을 나타내는 설정)을 제거
        chart_month.getDescription().setEnabled(false); // 데스크립션 제거
        String[] labels = chart.labels;
        chart_month.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels)); //115줄의 x축 String 라벨적용
        // false true
        chart_month.setData(screen_1_chart.barchart());
        //차트

        but_refresh = findViewById(R.id.button_refresh);


        connect = new Connect();



        but_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EEEE = null;

//                new AsyncTask() {
//                    String ether;
//                    @Override
//                    protected Object doInBackground(Object[] objects) {
//
//
//                        try {
//                            web3j = Web3j.build(new HttpService("http://52.79.255.216:8547"));
//                            EthGetBalance ethGetBalance = web3j.ethGetBalance("0xf480fb1d4f32b7797c829d6f05bb0805c152b3ec", DefaultBlockParameterName.LATEST).sendAsync().get();
//                            BigDecimal eth = Convert.fromWei(ethGetBalance.getBalance().toString(), Convert.Unit.ETHER);
//                            ether = eth.toString();
//                        } catch (ExecutionException | InterruptedException e) {
//                            ether = "err";
//                        }
//
//                        return null;
//                    }
//                }.execute();
                    EEEE = connect.connect(web3j);


                zkem.setText(EEEE);
            }
        });


    };
    @Override
    public void onBackPressed() {
        backbutton_event.backbutton();
    }
    //뒤로가기 버튼 이벤트
}
