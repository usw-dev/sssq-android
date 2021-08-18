package com.example.android.backbutton_event;

import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.widget.Toast;

public class backbutton_event {

    private static Activity act;
    private static long backbutton_TIME = 0;

    public backbutton_event(Activity activity) {
        this.act = activity;
    }

    public static void backbutton() {
        long cur_TIME = System.currentTimeMillis();
        //현재 시간
        long gap_TIME = cur_TIME - backbutton_TIME;
        //현재 시간과 버튼을 누른 시간의 차

        if (0 <= gap_TIME && 2000 >= gap_TIME) {
            act.finish();
            ActivityCompat.finishAffinity(act);
            System.exit(0);
            //2초 안에 두번 클릭시 종료
        } else {
            backbutton_TIME = cur_TIME;
            Toast.makeText(act, "한 번 더 누를 시 종료합니다", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
