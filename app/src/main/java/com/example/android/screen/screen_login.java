package com.example.android.screen;

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

import com.example.android.action.BackButtonEvent;
import com.example.android.PreferenceManager;
import com.example.android.R;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.IOException;

public class screen_login extends AppCompatActivity {
    private Context mContext;
    private ImageButton buttonLogin;
    private ImageButton buttonGotocreateAccount;
    private ImageButton buttonForget;
    private EditText editAddress;
    private EditText editPassword;
    private CheckBox checkbox;
    private BackButtonEvent backbuttonEvent;

    String ID, PW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_login);
        mContext = this;

        backbuttonEvent = new BackButtonEvent(this);
        buttonLogin = findViewById(R.id.button_login);
        buttonGotocreateAccount = findViewById(R.id.button_gotocreateAccount);
        buttonForget = findViewById(R.id.buttonForget);

        editAddress = findViewById(R.id.EditText_address);
        editPassword = findViewById(R.id.EditText_pw);
        checkbox = findViewById(R.id.checkbox_save);


        boolean bool = PreferenceManager.getBoolean(mContext, "check");

        if (bool) {//체크되어 있으면 주소 패스워드 불러옴
            editAddress.setText(PreferenceManager.getString(mContext, "address"));
            editPassword.setText(PreferenceManager.getString(mContext, "pw"));
            checkbox.setChecked(true);
        }

        Intent getintent = getIntent();

        if (getintent.hasExtra("address"))
            editAddress.setText(getintent.getStringExtra("address"));

        checkbox.setChecked(false);
        PreferenceManager.clear(mContext);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.setString(mContext, "address", editAddress.getText().toString());
                PreferenceManager.setString(mContext, "pw", editPassword.getText().toString());

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

        buttonGotocreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent screen_1 = new Intent(screen_login.this, screen_createaccount.class);
                startActivity(screen_1);
            }
        });

        checkbox.setOnClickListener(v -> {
            if (((CheckBox) v).isChecked()) {
                PreferenceManager.setString(mContext, "address", editAddress.getText().toString());
                PreferenceManager.setString(mContext, "pw", editPassword.getText().toString());
                PreferenceManager.setBoolean(mContext, "check", checkbox.isChecked());
            } else {
                PreferenceManager.setBoolean(mContext, "check", checkbox.isChecked());
                PreferenceManager.clear(mContext);
            }
        });

        buttonForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, getExternalFilesDir(null) + "/codi에 있는 \n json 파일의 이름을 복사해주세요", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBackPressed() {
        backbuttonEvent.backbutton();
    }
}