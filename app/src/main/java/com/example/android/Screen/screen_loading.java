package com.example.android.Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
            Toast.makeText(this, "잠시만 기다려주세요...", Toast.LENGTH_SHORT).show();
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        finish();
    }
}