package com.example.android.Mainscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.android.R;
import com.example.android.backbutton_event.backbutton_event;

public class screen_main extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerView;
    private Activity activity;
    private backbutton_event backbutton_event;
    private com.example.android.drawer_sllide.drawer screen_1_drawer;
    private com.example.android.drawer_sllide.slide screen_1_slide;
    private Button button_setting;

    boolean flag = true; //올라오고 내려온 상태
    Animation transup; //올라오는 애니메이션
    Animation transdown; //내려가는 애니메이션
    LinearLayout bottomSheet; // 올라오고 내려갈 최근거래내역

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_main);

        screen_1_drawer = new com.example.android.drawer_sllide.drawer();
        //드로어 레이아웃
        backbutton_event = new backbutton_event(this);
        //뒤로가기 이벤트
        screen_1_slide = new com.example.android.drawer_sllide.slide();
        //계좌 터치 이벤트

        drawerLayout = (DrawerLayout) findViewById(R.id.screen1);
        drawerView = (View) findViewById(R.id.drawer);
        ImageButton btn_mymenu = (ImageButton) findViewById(R.id.icon_mymenu);
        //매개변수에 필요한 id 탐색

        screen_1_drawer.drawer(drawerLayout, drawerView, btn_mymenu);
        //드로어 레이아웃 오픈

        ImageButton account_button = (ImageButton) findViewById(R.id.account_button);
        //어카운트 이미지

        Button button_setting = findViewById(R.id.but_setting);

        account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screen_1_slide.onCreate(savedInstanceState);}
        });
        //계좌 터치 시 애니메이션 이벤트
    };

    @Override
    public void onBackPressed()
    {
        backbutton_event.backbutton();
    }
    //뒤로가기 버튼 이벤트
}