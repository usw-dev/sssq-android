package com.example.android.Settingscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.R;
import com.example.android.ScreenVerActivity;

public class setting extends AppCompatActivity {

    private Button button_ver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_setting);

        button_ver = findViewById(R.id.app_ver);

        button_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(setting.this, ScreenVerActivity.class);
                startActivity(intent);
            }
        });
    }
}