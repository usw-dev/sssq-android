package com.example.android.Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.android.R;

public class screen_sup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_sup);

        TextView sup_thd = (TextView) findViewById(R.id.sup_thd);
        TextView sup_rla = (TextView) findViewById(R.id.sup_rla);
        TextView sup_als = (TextView) findViewById(R.id.sup_als);
        TextView sup_dks = (TextView) findViewById(R.id.sup_dks);

        sup_thd.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                String[] address = {"clgns0415@gmail.com"};
                email.putExtra(Intent.EXTRA_EMAIL, address);
                startActivity(email);
            }
        });
        //송치훈

        sup_rla.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                String[] address = {"kimgyuimin153@gmail.com"};
                email.putExtra(Intent.EXTRA_EMAIL, address);
                startActivity(email);
            }
        });
        //김규민

        sup_rla.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                String[] address = {"cjlrh9987@gmail.com"};
                email.putExtra(Intent.EXTRA_EMAIL, address);
                startActivity(email);
            }
        });
        //민철홍

        sup_dks.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                String[] address = {"ayt0913@gmail.com"};
                email.putExtra(Intent.EXTRA_EMAIL, address);
                startActivity(email);
            }
        });
        //안유태
    }
}