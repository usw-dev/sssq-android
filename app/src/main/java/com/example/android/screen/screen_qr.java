package com.example.android.screen;

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
        String addressQR = screen_main.ADDRESS;

        Bitmap bitmap = com.example.android.action.CreateQR.encodeAsBitmap(addressQR, 500, 500);
        QRView.setImageBitmap(bitmap);
    }
}