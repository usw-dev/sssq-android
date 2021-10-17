package com.example.android.action;

import androidx.appcompat.app.AppCompatActivity;

import android.view.animation.Animation;

public class slide extends AppCompatActivity {

    public static boolean flag = true; //올라오고 내려온 상태
    public Animation transup; //올라오는 애니메이션
    public Animation transdown; //내려가는 애니메이션

    public static class Sliding implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {
            //애니메이션 시작할때 호출되는 메서드
        }
        @Override
        public void onAnimationEnd(Animation animation) {
            //애니메이션 종료할때 호출되는 메서드
            if (flag) {
                flag = false;
            }
            else {
                flag = true;
            }
        }
        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }
}