package com.example.android.Screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.Action.chart;
import com.example.android.R;
import com.example.android.Action.backbutton_event;
import com.example.android.data.SendEther;
import com.example.android.data.Refresh;
import com.example.android.data.TxHistory;
import com.example.android.data.UserWallet;
import com.example.android.Action.drawer;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class screen_main extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerView;
    private backbutton_event backbutton_event;
    private com.example.android.Action.drawer screen_1_drawer;
    private static com.example.android.Action.chart screen_1_chart;
    private com.example.android.data.SendEther sendEther;
    private static BarChart chart_month;
    private ImageButton but_refresh;
    private Button button_ver;
    private Button button_info;
    private Button button_sup;
    private LinearLayout senddata;
    private BottomSheetBehavior behavior;
    public static Web3j web3j;
    public static String ID, PW, ADDRESS, ETHER;
    public static List<TxHistory> HISTORY;

    private Button button_sendEther;
    private Button QRbutton; //QR
    private static TextView sendaddress, sendEth, card_eth, card_address;
    private IntentIntegrator qrScan;
    private static UserWallet MYUSERWALLET;
    private Credentials credentials;
    static String[] labels = chart.labels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_main);

        //login->main
        //
        Intent getidpw = getIntent();

        if (getidpw.hasExtra("ID")) {
            ID = getidpw.getStringExtra("ID");
            PW = getidpw.getStringExtra("PW");

            credentials = null;

            try {
                credentials = WalletUtils.loadCredentials(PW, ID);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CipherException e) {
                e.printStackTrace();
            }

            ADDRESS = credentials.getAddress();

            Connect_geth connect_geth = new Connect_geth(ADDRESS);
            connect_geth.execute();
            Intent loading = new Intent(screen_main.this, screen_loading.class);
            startActivity(loading);
        }
        //
        //

        //new
        //
        sendEther = new SendEther();
        screen_1_drawer = new drawer();
        backbutton_event = new backbutton_event(this);
        screen_1_chart = new chart();
        //
        //end of new


        //findview
        //
        button_sendEther = findViewById(R.id.data_send);
        card_eth = findViewById(R.id.card_ETH);
        card_address = findViewById(R.id.card_account_address);
        chart_month = (BarChart) findViewById(R.id.chart_month);
        but_refresh = findViewById(R.id.button_refresh);
        senddata = findViewById(R.id.senddata);
        behavior = BottomSheetBehavior.from(senddata);
        ImageButton account_button = findViewById(R.id.account_button);
        drawerLayout = findViewById(R.id.screen1);
        drawerView = findViewById(R.id.drawer);
        ImageButton btn_mymenu = findViewById(R.id.icon_mymenu);
        button_ver = findViewById(R.id.app_ver);
        button_info = findViewById(R.id.app_info);
        QRbutton = findViewById(R.id.QRbutton);
        sendaddress = findViewById(R.id.sendaddress);
        sendEth = findViewById(R.id.sendEth);
        //
        //end of findview

        //onclicklistener
        //
        button_sendEther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int compareEther = Integer.parseInt(sendEth.getText().toString());

                if (compareEther > Integer.parseInt(MYUSERWALLET.getEther().toString())) {
                    Toast.makeText(screen_main.this, "이더가 부족합니다", Toast.LENGTH_SHORT).show();

                } else {
                    sendEther sendEther = new sendEther(sendaddress.getText().toString(), sendEth.getText().toString());
                    sendEther.execute();
                }
            }
        });

        account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (behavior.getState() != BottomSheetBehavior.STATE_HALF_EXPANDED) {
                    behavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);

                } else {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        button_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(screen_main.this, screen_appver.class);
                startActivity(intent);
            }
        });
        //메인 -> 메뉴 -> 앱 버전

        button_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(screen_main.this, screen_appinfo.class);
                startActivity(intent);
            }
        });
        //메인 -> 메뉴 -> 앱 정보

        button_sup = findViewById(R.id.app_sup);

        button_sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(screen_main.this, screen_sup.class);
                startActivity(intent);
            }
        });

        but_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connect_geth connect_geth = new Connect_geth(ADDRESS);
                connect_geth.execute();
            }
        });

        QRbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //scan option
                qrScan.setPrompt("Scanning");
                //qrScan.setOrientationLocked(false);
                qrScan.initiateScan();
            }
        });
        //
        //end of onclicklistener

        screen_1_drawer.drawer(drawerLayout, drawerView, btn_mymenu);
        //드로어 레이아웃 오픈

        chart_month.setTouchEnabled(false); // 터치x
        chart_month.getAxisLeft().setDrawGridLines(false);                  // y축(가로줄) 격자 x 두개 같이써야 없어짐
        chart_month.getAxisLeft().setDrawAxisLine(false);                   // y축 왼쪽 선 제거
        chart_month.getAxisLeft().setDrawLabels(false);                     // y축 왼쪽 라벨 제거
        chart_month.getAxisRight().setDrawGridLines(false);                 // y축(가로줄) 격자 x 두개 같이써야 없어짐
        chart_month.getAxisRight().setDrawAxisLine(false);                  // y축 오른쪽 선 제거
        chart_month.getAxisRight().setDrawLabels(false);                    // y축 오른쪽 라벨 제거
        chart_month.getXAxis().setDrawGridLines(false);                     // x축(세로줄) 격자 제거
        chart_month.getXAxis().setDrawAxisLine(false);                      // x축 선 제거
        chart_month.getXAxis().setDrawLabels(true);                         // x축 라벨 사용
        chart_month.getXAxis().setTextColor(Color.WHITE);                   // x축 라벨 색
        chart_month.getXAxis().setLabelCount(2);                            // 라벨6개 고정
        chart_month.getXAxis().setCenterAxisLabels(false);                   // 라벨 가운데로
        chart_month.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);     // 라벨 위치 아래로
        chart_month.getLegend().setEnabled(false);                          // 레전드(차트밑에 색과 라벨을 나타내는 설정)을 제거
        chart_month.getDescription().setEnabled(false);                     // 데스크립션 제거


        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false);
    }

    @Override
    public void onBackPressed() {
        backbutton_event.backbutton();
    }
    //뒤로가기 버튼 이벤트

    public static class Connect_geth extends AsyncTask<Void, Void, UserWallet> {

        private String address;

        public Connect_geth(String address) {
            this.address = address;
        }

        @Override
        protected UserWallet doInBackground(Void... params) {

            UserWallet result = null;

            Refresh refresh = new Refresh();

            try {
                result = refresh.refresh(address);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(UserWallet s) {
            super.onPostExecute(s);
            MYUSERWALLET = s;

            ETHER = MYUSERWALLET.getEther().toString();
            HISTORY = MYUSERWALLET.getTxHistory();

            //    지난달 입금    출금          이번달 입금      출금
            float pastin = 0, pastout = 0, presentin = 0, presentout = 0;

            long now = System.currentTimeMillis();

            Date date = new Date(now);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String Curtime = sdf.format(date);

            int CurY = Integer.parseInt(Curtime.substring(0, 4));
            int CurM = Integer.parseInt(Curtime.substring(5, 7));

            //지난달과 이번달의 입금 출금
            if (HISTORY != null) {
                for (TxHistory history : HISTORY) {
                    String Txtime = history.getTimestamp();

                    int TxY = Integer.parseInt(Txtime.substring(0, 4));
                    int TxM = Integer.parseInt(Txtime.substring(5, 7));

                    //이번달
                    if ((CurY == TxY) && (CurM == TxM)) {
                        //내가 받았을 때
                        if (history.getTxTo() == ADDRESS)
                            presentin += history.getTxValue().floatValue();
                            //내가 줬을 때
                        else if (history.getTxFrom() == ADDRESS)
                            presentout += history.getTxValue().floatValue();
                    }
                    //지난달
                    else if ((CurY == TxY) && ((CurM - TxM) == 1)) {
                        //내가 받았을 때
                        if (history.getTxTo() == ADDRESS)
                            pastin += history.getTxValue().floatValue();
                            //내가 줬을 때
                        else if (history.getTxFrom() == ADDRESS)
                            pastout += history.getTxValue().floatValue();
                    }
                }
            }

            card_address.setText(ADDRESS);
            card_eth.setText(ETHER);

            chart_month.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
            chart_month.setData(screen_1_chart.barchart(pastin, pastout, presentin, presentout));
            chart_month.invalidate();
        }
    }

    public class sendEther extends AsyncTask<Void, Void, String> {

        String toAddress;
        int ether;

        public sendEther(String a, String e) {
            toAddress = a;
            ether = Integer.parseInt(e);
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "";

            SendEther sendEther = new SendEther();

            try {
                result = sendEther.SendEther(credentials, toAddress, ether);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

//            Intent loading = new Intent(screen_main.this, screen_loading.class);
//            startActivity(loading);

            Connect_geth connect_geth = new Connect_geth(ADDRESS);
            connect_geth.execute();
        }
    }

    //스캔한거 받아서 처리하는 함수
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //qrcode 가 없으면
            if (result.getContents() == null) {
                Toast.makeText(screen_main.this, "취소!", Toast.LENGTH_SHORT).show();
            } else {
                //qrcode 결과가 있으면
                Toast.makeText(screen_main.this, "스캔완료!", Toast.LENGTH_SHORT).show();
            }
            try {
                //data를 json으로 변환
                JSONObject obj = new JSONObject(result.getContents()); //스캔한거 받아옴
                sendaddress.setText(obj.getString("sendaddress"));
                sendEth.setText(obj.getString("sendEth"));
            } catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(screen_main.this, result.getContents(), Toast.LENGTH_LONG).show();
                sendaddress.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
