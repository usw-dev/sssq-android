package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.Mainscreen.screen_main;

public class screen_3_qr extends AppCompatActivity {

    private Button button_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen3_qr);

        button_home=findViewById(R.id.button_home);

        button_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(screen_3_qr.this, screen_main.class);
                startActivity(intent);
            }
        });
    }
}