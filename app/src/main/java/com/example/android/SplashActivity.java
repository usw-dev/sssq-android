package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.android.Mainscreen.screen_main;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_splash);

        ImageView splash = findViewById(R.id.splash_image);

        Glide.with(this).load(R.raw.image_splash).into(splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                    Intent screen_1 = new Intent(SplashActivity.this, screen_main.class);
                    startActivity(screen_1);
                    finish();
            }
        },1700);
    }
}