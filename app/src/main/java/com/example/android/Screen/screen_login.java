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
import com.example.android.screen_createaccount;

public class screen_login extends AppCompatActivity {
    private Context mContext;
    private ImageButton button_login;
    private ImageButton button_gotocreateAccount;
    private EditText ET_ad;
    private EditText ET_pw;
    private CheckBox checkbox;
    private com.example.android.Action.backbutton_event backbutton_event;

    String ID,PW;

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

        button_gotocreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent screen_1 = new Intent(screen_login.this, screen_createaccount.class);
                startActivity(screen_1);
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
    public void onBackPressed() {
        backbutton_event.backbutton();
    }
}