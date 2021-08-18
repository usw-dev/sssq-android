package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView splash = findViewById(R.id.splash_image);

        Glide.with(this).load(R.raw.image_splash).into(splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                    Intent screen_1 = new Intent(SplashActivity.this, com.example.android.screen1.screen_1.class);
                    startActivity(screen_1);
                    finish();
            }
        },1700);
    }
}