package com.example.android.Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.android.Action.backbutton_event;
import com.example.android.PreferenceManager;
import com.example.android.R;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.IOException;

public class screen_login extends AppCompatActivity {
    private Context mContext;
    private ImageButton button_login;
    private ImageButton button_gotocreateAccount;
    private EditText ET_ad;
    private EditText ET_pw;
    private CheckBox checkbox;
    private com.example.android.Action.backbutton_event backbutton_event;

    String ID, PW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_login);
        mContext = this;

        backbutton_event = new backbutton_event(this);
        button_login = findViewById(R.id.button_login);
        button_gotocreateAccount = findViewById(R.id.button_gotocreateAccount);
        ET_ad = findViewById(R.id.EditText_address);
        ET_pw = findViewById(R.id.EditText_pw);
        checkbox = findViewById(R.id.checkbox_save);

        boolean bool = PreferenceManager.getBoolean(mContext, "check");

        if (bool) {//체크되어 있으면 주소 패스워드 불러옴
            ET_ad.setText(PreferenceManager.getString(mContext, "address"));
            ET_pw.setText(PreferenceManager.getString(mContext, "pw"));
            checkbox.setChecked(true);
        }

        Intent getintent = getIntent();

        if (getintent.hasExtra("address"))
            ET_ad.setText(getintent.getStringExtra("address"));

        checkbox.setChecked(false);
        PreferenceManager.clear(mContext);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.setString(mContext, "address", ET_ad.getText().toString());
                PreferenceManager.setString(mContext, "pw", ET_pw.getText().toString());

                String checkAd = PreferenceManager.getString(mContext, "address");
                String checkPw = PreferenceManager.getString(mContext, "pw");

                Credentials credentials = null;

                if (TextUtils.isEmpty(checkAd) || TextUtils.isEmpty(checkPw)) {
                    Toast.makeText(mContext, "주소나 비밀번호가 비어있습니다", Toast.LENGTH_SHORT).show();

                } else {
                    try {
                        credentials = WalletUtils.loadCredentials(checkPw, getExternalFilesDir(null) + "/codi/" + checkAd);

                        Intent login = new Intent(screen_login.this, screen_main.class);
                        login.putExtra("ID", getExternalFilesDir(null) + "/codi/" + checkAd);
                        login.putExtra("PW", checkPw);
                        startActivity(login);
                    } catch (IOException e) {
                        Toast.makeText(mContext, "올바르지 않은 파일 입니다", Toast.LENGTH_SHORT).show();
                        return;
                    } catch (CipherException e) {
                        Toast.makeText(mContext, "올바르지 않은 비밀번호 입니다", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }
            }
        });

        button_gotocreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent screen_1 = new Intent(screen_login.this, screen_createaccount.class);
                startActivity(screen_1);
            }
        });

        checkbox.setOnClickListener(v -> {
            if (((CheckBox) v).isChecked()) {
                PreferenceManager.setString(mContext, "address", ET_ad.getText().toString());
                PreferenceManager.setString(mContext, "pw", ET_pw.getText().toString());
                PreferenceManager.setBoolean(mContext, "check", checkbox.isChecked());
            } else {
                PreferenceManager.setBoolean(mContext, "check", checkbox.isChecked());
                PreferenceManager.clear(mContext);
            }
        });
    }

    public void onBackPressed() {
        backbutton_event.backbutton();
    }
}