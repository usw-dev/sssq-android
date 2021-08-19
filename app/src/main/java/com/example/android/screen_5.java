package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.Mainscreen.screen_main;

public class screen_5 extends AppCompatActivity {

    private Button button_OK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen5);

        button_OK = findViewById(R.id.button_OK);

        button_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(screen_5.this, screen_main.class);
                startActivity(intent);
            }
        });
    }
}