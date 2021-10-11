package com.example.android.Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.R;

public class screen_loading extends AppCompatActivity {

    ImageView loading;
    TextView Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_loading);

        loading = findViewById(R.id.loading);
        Text = findViewById(R.id.loadingtext);

        try {
            Text.setText("서버 연결 중");
            loading.setImageResource(R.drawable.loading1);
            Thread.sleep(300);
            loading.setImageResource(R.drawable.loading2);
            Thread.sleep(300);
            loading.setImageResource(R.drawable.loading3);
            Thread.sleep(300);
            loading.setImageResource(R.drawable.loading4);
            Thread.sleep(300);
            loading.setImageResource(R.drawable.loading5);
            Thread.sleep(300);
            loading.setImageResource(R.drawable.loading6);
            Text.setText("상대 주소 확인 중");
            Thread.sleep(300);
            loading.setImageResource(R.drawable.loading7);
            Thread.sleep(300);
            loading.setImageResource(R.drawable.loading1);
            Thread.sleep(300);
            loading.setImageResource(R.drawable.loading2);
            Thread.sleep(300);
            Text.setText("이더 보낼 준비하는 중");
            loading.setImageResource(R.drawable.loading3);
            Thread.sleep(300);
            loading.setImageResource(R.drawable.loading4);
            Thread.sleep(300);
            loading.setImageResource(R.drawable.loading5);
            Thread.sleep(300);
            Text.setText("보내는 중");
            loading.setImageResource(R.drawable.loading6);
            Thread.sleep(300);
            loading.setImageResource(R.drawable.loading7);
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        finish();
    }
}