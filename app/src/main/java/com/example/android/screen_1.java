package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.pm.ActivityInfoCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class screen_1 extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerView;
    private long backbutton_TIME = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen1);

        drawerLayout = (DrawerLayout) findViewById(R.id.screen1);
        drawerView = (View) findViewById(R.id.drawer);

        ImageButton btn_open = (ImageButton) findViewById(R.id.icon_mymenu);

        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

//    네비게이션 드로어

        DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        };
    };
//      네비게이션 드로어 속성

    public void onBackPressed(){
        long cur_TIME = System.currentTimeMillis();
        long gap_TIME = cur_TIME - backbutton_TIME;

        if(0 <= gap_TIME && 2000 >= gap_TIME)
        {
            ActivityCompat.finishAffinity(this);
            System.exit(0);
        }

        else{
            backbutton_TIME = cur_TIME;
            Toast.makeText(this, "한 번 더 누를 시 종료합니다",Toast.LENGTH_SHORT).show();
        }
    }
//    뒤로가기 두번 버튼
}