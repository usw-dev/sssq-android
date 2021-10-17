package com.example.android.Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.example.android.R;

import java.lang.annotation.Target;

import io.reactivex.annotations.Nullable;

public class screen_loading extends AppCompatActivity {

    ImageView loading;
    TextView Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_loading);

        loading = findViewById(R.id.loading);
        Text = findViewById(R.id.loadingtext);

        //로딩 이미지
        Glide.with(this).asGif()
                .load(R.drawable.loadinggif)
                .into(loading);

        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                Text.setText("상대 정보 확인 중");
            }
        }, 1000);

        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                Text.setText("전송 중");
            }
        }, 3000);


        Handler handler3 = new Handler();
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 5000);
    }
}