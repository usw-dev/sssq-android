package com.example.android.Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.android.R;

public class screen_qr extends AppCompatActivity {
    private ImageView QRView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_qr);

        QRView = findViewById(R.id.QRView);
//        String s = new screen_login().ID;

        Bitmap bitmap = com.example.android.Action.CreateQR.encodeAsBitmap("QR", 500, 500);
        QRView.setImageBitmap(bitmap);
    }
}