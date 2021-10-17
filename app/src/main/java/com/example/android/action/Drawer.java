package com.example.android.action;

import androidx.drawerlayout.widget.DrawerLayout;

import android.view.View;
import android.view.MotionEvent;
import android.widget.ImageButton;

public class Drawer {
    public static void drawer(DrawerLayout drawerLayout, View drawerView, ImageButton btn)
//                    매개변수(드로어 레이아웃 ,드로어 뷰 ,이미지 버튼)
    {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(drawerView);
            }
        });
        //클릭 시 드로어 뷰 오픈

        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
         });

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
        //드로어 뷰 정의
    }
}
