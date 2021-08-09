package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.pm.ActivityInfoCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class screen_1 extends AppCompatActivity {

    private Button button_transaction;
    private long backbutton_TIME = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen1);

//        button_transaction=findViewById(R.id.button_transaction);
//
//        button_transaction.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(screen_1.this, screen_2.class);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        long cur_TIME = System.currentTimeMillis();
        long gap_TIME = cur_TIME - backbutton_TIME;

        if(0 <= gap_TIME && 2000 >= gap_TIME)
        {
            ActivityCompat.finishAffinity(this);
            System.exit(0);
        }

        else{
            backbutton_TIME = cur_TIME;
            Toast.makeText(this, "한 번 더 누를 시 종료합니다",Toast.LENGTH_SHORT).show();;
        }
    }
}