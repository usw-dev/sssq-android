package com.example.android.Action;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.android.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class slide extends AppCompatActivity {

//    static boolean flag; //올라오고 내려온 상태
//    Animation transup; //올라오는 애니메이션
//    Animation transdown; //내려가는 애니메이션
//    LinearLayout bottomSheet; // 올라오고 내려갈 최근거래내역
    public static boolean flag = true; //올라오고 내려온 상태
    public Animation transup; //올라오는 애니메이션
    public Animation transdown; //내려가는 애니메이션


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.screen_main);
//
//        bottomSheet = findViewById(R.id.bottomSheet);
//        transup = AnimationUtils.loadAnimation(this, R.anim.translateup); //xml 위로올리는거 적용
//        transdown = AnimationUtils.loadAnimation(this, R.anim.translatedown); //xml 밑으로 내리는거 적용
//        slide.Sliding animationListener = new slide.Sliding(); //sliding 리스너 생성
//        transup.setAnimationListener(animationListener); //transup에 리스너 적용
//        transdown.setAnimationListener(animationListener); //transup에 리스너 적용
//
//        IB_clicked();
//    }

//    public void IB_clicked(View v) {
//        if (flag) {
//            senddata.startAnimation(transup); //올림
//        } else {
//            senddata.setVisibility(View.VISIBLE);
//            senddata.startAnimation(transdown); //내림
//        }
//    }

    public static class Sliding implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {
            //애니메이션 시작할때 호출되는 메서드
        }
        @Override
        public void onAnimationEnd(Animation animation) {
            //애니메이션 종료할때 호출되는 메서드
            if (flag) {
//                senddata.setVisibility(View.INVISIBLE);
                flag = false; //
            }
            else {
                flag = true; //
            }
        }
        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }
}