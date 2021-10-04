package com.example.android.Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.android.PreferenceManager;
import com.example.android.R;

public class screen_login extends AppCompatActivity {
    private Context mContext;
    private ImageButton button_login;
    private ImageButton button_createAccount;
    private EditText ET_ad;
    private EditText ET_pw;
    private CheckBox checkbox;
    String ID,PW;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_login);
        mContext = this;
        
        button_login = findViewById(R.id.button_login);
        button_createAccount = findViewById(R.id.button_createAccount);
        ET_ad = findViewById(R.id.EditText_address);
        ET_pw = findViewById(R.id.EditText_pw);
        checkbox = findViewById(R.id.checkbox_save);
        
        boolean bool = PreferenceManager.getBoolean(mContext,"check");

        if(bool){//체크되어 있으면 주소 패스워드 불러옴
            ET_ad.setText(PreferenceManager.getString(mContext,"address"));
            ET_pw.setText(PreferenceManager.getString(mContext,"pw"));
            checkbox.setChecked(true);
        }

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.setString(mContext,"address",ET_ad.getText().toString());
                PreferenceManager.setString(mContext,"pw",ET_pw.getText().toString());

                String checkAd = PreferenceManager.getString(mContext,"address");
                String checkPw = PreferenceManager.getString(mContext,"pw");

                if(TextUtils.isEmpty(checkAd)||TextUtils.isEmpty(checkPw)){
                    Toast.makeText(mContext, "주소나 비밀번호가 비어있습니다", Toast.LENGTH_SHORT).show();
                }else{
                    /*
                    여기서 메인화면으로 넘어가는 기능
                     */
                }
            }
        });

        checkbox.setOnClickListener(v ->  {
                if(((CheckBox)v).isChecked()){
                    PreferenceManager.setString(mContext, "address", ET_ad.getText().toString());
                    PreferenceManager.setString(mContext, "pw", ET_pw.getText().toString());
                    PreferenceManager.setBoolean(mContext, "check", checkbox.isChecked());
                }else{
                    PreferenceManager.setBoolean(mContext,"check",checkbox.isChecked());
                    PreferenceManager.clear(mContext);
                }
        });

    }
}
