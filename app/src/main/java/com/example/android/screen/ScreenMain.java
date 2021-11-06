package com.example.android.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.action.Chart;
import com.example.android.R;
import com.example.android.action.BackButtonEvent;
import com.example.android.data.HistorySet;
import com.example.android.data.SendEther;
import com.example.android.data.Refresh;
import com.example.android.data.TxHistory;
import com.example.android.data.UserWallet;
import com.example.android.action.Drawer;
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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ScreenMain extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerView;
    private BackButtonEvent backButtonEvent;
    private Drawer screen1Drawer;
    private static Chart screen1Chart;
    private com.example.android.data.SendEther sendEther;
    private static BarChart chartMonth;
    private ImageButton buttonRefresh;
    private Button buttonVer;
    private Button buttonInfo;
    private Button buttonSup;
    private LinearLayout sendData;
    private LinearLayout scrollView;
    private BottomSheetBehavior behavior;
    public static String ID, PW, ADDRESS, ETHER;
    public static List<TxHistory> HISTORY;
    public static ArrayList<HistorySet> myHistory;


    private Button buttonSendEther;
    private Button qrButton; //QR
    private Button createQR;
    private static TextView sendAddress, sendEth, cardEth, cardAddress;
    private IntentIntegrator qrScan;
    private static UserWallet myUserWallet;
    private Credentials credentials;
    static String[] labels = Chart.labels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_main);

        chartMonth = (BarChart) findViewById(R.id.chart_month);

        chartMonth.setTouchEnabled(false); // 터치x
        chartMonth.getAxisLeft().setDrawGridLines(false);                  // y축(가로줄) 격자 x 두개 같이써야 없어짐
        chartMonth.getAxisLeft().setDrawAxisLine(false);                   // y축 왼쪽 선 제거
        chartMonth.getAxisLeft().setDrawLabels(false);                     // y축 왼쪽 라벨 제거
        chartMonth.getAxisRight().setDrawGridLines(false);                 // y축(가로줄) 격자 x 두개 같이써야 없어짐
        chartMonth.getAxisRight().setDrawAxisLine(false);                  // y축 오른쪽 선 제거
        chartMonth.getAxisRight().setDrawLabels(false);                    // y축 오른쪽 라벨 제거
        chartMonth.getXAxis().setDrawGridLines(false);                     // x축(세로줄) 격자 제거
        chartMonth.getXAxis().setDrawAxisLine(false);                      // x축 선 제거
        chartMonth.getXAxis().setDrawLabels(true);                         // x축 라벨 사용
        chartMonth.getXAxis().setTextColor(Color.WHITE);                   // x축 라벨 색
        chartMonth.getXAxis().setLabelCount(2);                            // 라벨6개 고정
        chartMonth.getXAxis().setCenterAxisLabels(false);                   // 라벨 가운데로
        chartMonth.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);     // 라벨 위치 아래로
        chartMonth.getLegend().setEnabled(false);                          // 레전드(차트밑에 색과 라벨을 나타내는 설정)을 제거
        chartMonth.getDescription().setEnabled(false);                     // 데스크립션 제거

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

            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent loading = new Intent(ScreenMain.this, ScreenLoading.class);
            startActivity(loading);
        }
        //
        //

        //new
        //
        sendEther = new SendEther();
        screen1Drawer = new Drawer();
        backButtonEvent = new BackButtonEvent(this);
        screen1Chart = new Chart();
        //
        //end of new


        //findview
        //
        scrollView = findViewById(R.id.scrollView);
        buttonSendEther = findViewById(R.id.data_send);
        cardEth = findViewById(R.id.card_ETH);
        cardAddress = findViewById(R.id.card_account_address);
        buttonRefresh = findViewById(R.id.button_refresh);
        sendData = findViewById(R.id.senddata);
        behavior = BottomSheetBehavior.from(sendData);
        ImageButton account_button = findViewById(R.id.account_button);
        drawerLayout = findViewById(R.id.screen1);
        drawerView = findViewById(R.id.drawer);
        ImageButton btn_mymenu = findViewById(R.id.icon_mymenu);
        buttonVer = findViewById(R.id.app_ver);
        buttonInfo = findViewById(R.id.app_info);
        qrButton = findViewById(R.id.QRbutton);
        sendAddress = findViewById(R.id.sendaddress);
        sendEth = findViewById(R.id.sendEth);
        createQR = findViewById(R.id.CreateQR);
        //
        //end of findview

        //onclicklistener
        //
        buttonSendEther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((sendAddress.getText().toString().isEmpty()) || (sendEth.getText().toString().isEmpty()))
                    Toast.makeText(ScreenMain.this, "기입하지 않은 칸이 있습니다", Toast.LENGTH_SHORT).show();
                else {
                    float compareEther = Float.parseFloat(sendEth.getText().toString());

                    if (compareEther > Float.parseFloat(myUserWallet.getEther().toString())) {
                        Toast.makeText(ScreenMain.this, "이더가 부족합니다", Toast.LENGTH_SHORT).show();
                    } else if (compareEther == 0) {
                        Toast.makeText(ScreenMain.this, "0은 보낼 수 없습니다", Toast.LENGTH_SHORT).show();
                    } else if (sendAddress.getText().toString().length() != 42) {
                        Toast.makeText(ScreenMain.this, "주소 길이는 42여야 합니다", Toast.LENGTH_SHORT).show();
                    } else {
                        sendEther sendEther = new sendEther(sendAddress.getText().toString(), sendEth.getText().toString());
                        sendEther.execute();

                        Toast.makeText(ScreenMain.this, "전송 완료! 새로고침을 눌러주세요", Toast.LENGTH_SHORT).show();
                    }
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

        buttonVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScreenMain.this, ScreenAppVer.class);
                startActivity(intent);
            }
        });
        //메인 -> 메뉴 -> 앱 버전

        buttonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScreenMain.this, ScreenAppInfo.class);
                startActivity(intent);
            }
        });
        //메인 -> 메뉴 -> 앱 정보

        buttonSup = findViewById(R.id.app_sup);

        buttonSup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScreenMain.this, ScreenSupport.class);
                startActivity(intent);
            }
        });

        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connect_geth connect_geth = new Connect_geth(ADDRESS);
                connect_geth.execute();

                Intent loading = new Intent(ScreenMain.this, ScreenLoading.class);
                startActivity(loading);
            }
        });

        qrButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                qrScan.setPrompt("Scanning");
                qrScan.initiateScan();
            }
        });
        //
        //end of onclicklistener

        screen1Drawer.drawer(drawerLayout, drawerView, btn_mymenu);
        //드로어 레이아웃 오픈

        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false);

        createQR.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ScreenMain.this, ScreenQr.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        backButtonEvent.backbutton();
    }
    //뒤로가기 버튼 이벤트

    public class Connect_geth extends AsyncTask<Void, Void, UserWallet> {

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
            myUserWallet = s;
            Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {

                    ETHER = myUserWallet.getEther().toString();
                    HISTORY = myUserWallet.getTxHistory();

                    //지난 달과 이번 달 입출금
                    float pastIn = 0;
                    float pastOut = 0;
                    float presentIn = 0;
                    float presentOut = 0;

                    long now = System.currentTimeMillis();

                    Date d = new Date(now);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                    String Curtime = sdf.format(d);

                    //현재 년 월
                    int curYear = Integer.parseInt(Curtime.substring(2, 4));
                    int curMonth = Integer.parseInt(Curtime.substring(5, 7));

                    //내 내역들을 담을 리스트
                    myHistory = new ArrayList<>();

                    //지난달과 이번달의 입금 출금
                    if (HISTORY != null) {
                        for (TxHistory history : HISTORY) {
                            //보내거나 받는 사람이 나일때
                            if ((history.getTxTo().compareTo(ADDRESS) == 0) || (history.getTxFrom().compareTo(ADDRESS) == 0)) {
                                //내역 하나
                                HistorySet oneHistory = new HistorySet();

                                //내역의 년 월 일
                                String txTime = history.getTimestamp();
                                int txYear = Integer.parseInt(txTime.substring(2, 4));
                                int txMonth = Integer.parseInt(txTime.substring(5, 7));
                                int txDay = Integer.parseInt(txTime.substring(8, 10));

                                //날짜를 만들어서 내역에 저장
                                String date = (Integer.toString(txYear) + "." + Integer.toString(txMonth) + "." + Integer.toString(txDay));
                                oneHistory.setDate(date);

                                //이번달
                                if ((curYear == txYear) && (curMonth == txMonth)) {
                                    //내가 받았을 때
                                    if (history.getTxTo().compareTo(ADDRESS) == 0) {
                                        presentIn += history.getTxValue().floatValue();

                                        //상대 주소, 이더 내역 저장
                                        oneHistory.setOpponent(history.getTxFrom());
                                        oneHistory.setEther(history.getTxValue().toString());
                                        oneHistory.setWasMe(false);
                                    }
                                    //내가 줬을 때
                                    else if (history.getTxFrom().compareTo(ADDRESS) == 0) {
                                        presentOut += history.getTxValue().floatValue();

                                        //상대 주소, 이더 내역 저장
                                        oneHistory.setOpponent(history.getTxTo());
                                        oneHistory.setEther(history.getTxValue().toString());
                                        oneHistory.setWasMe(true);
                                    }
                                }
                                //지난달
                                else if ((curYear == txYear) && ((curMonth - txMonth) == 1)) {
                                    //내가 받았을 때
                                    if (history.getTxTo().compareTo(ADDRESS) == 0) {
                                        pastIn += history.getTxValue().floatValue();

                                        //상대 주소, 이더 내역 저장
                                        oneHistory.setOpponent(history.getTxFrom());
                                        oneHistory.setEther(history.getTxValue().toString());
                                        oneHistory.setWasMe(false);
                                    }
                                    //내가 줬을 때
                                    else if (history.getTxFrom().compareTo(ADDRESS) == 0) {
                                        pastOut += history.getTxValue().floatValue();

                                        //상대 주소, 이더 내역 저장
                                        oneHistory.setOpponent(history.getTxTo());
                                        oneHistory.setEther(history.getTxValue().toString());
                                        oneHistory.setWasMe(true);
                                    }
                                }
                                //내역 하나 추가
                                myHistory.add(oneHistory);
                            }
                        }
                    }

                    scrollView.removeAllViews();

                    //스크롤 뷰에 내역 추가
                    for (int i = 0; i < myHistory.size(); i++) {
                        LinearLayout lin = new LinearLayout(scrollView.getContext());
                        lin.setOrientation(LinearLayout.HORIZONTAL);
                        lin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

                        //날짜
                        TextView date = new TextView(scrollView.getContext());
                        date.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
                        date.setText(myHistory.get(i).getDate());
                        date.setGravity(Gravity.CENTER);
                        date.setTextSize(20f);
                        date.setTextColor(Color.WHITE);
                        lin.addView(date);

                        //상대
                        TextView opponent = new TextView(scrollView.getContext());
                        opponent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
                        opponent.setText(myHistory.get(i).getOpponent());
                        date.setGravity(Gravity.CENTER);
                        opponent.setTextSize(20f);
                        opponent.setTextColor(Color.WHITE);
                        lin.addView(opponent);

                        //액수
                        TextView amount = new TextView(scrollView.getContext());
                        amount.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
                        amount.setTextSize(20f);

                        //보낸 사람이 나인가?
                        if (myHistory.get(i).getWasMe() == true) {
                            amount.setText("- " + myHistory.get(i).getEther());
                            date.setGravity(Gravity.CENTER);
                            amount.setTextColor(Color.RED);
                        } else {
                            amount.setText("+ " + myHistory.get(i).getEther());
                            date.setGravity(Gravity.CENTER);
                            amount.setTextColor(Color.rgb(121, 231, 231));
                        }
                        lin.addView(amount);

                        scrollView.addView(lin);
                    }

                    cardAddress.setText(ADDRESS);

                    if (ETHER.length() > 10)
                        cardEth.setText(ETHER.substring(0, 10));
                    else
                        cardEth.setText(ETHER);

                    chartMonth.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
                    chartMonth.setData(screen1Chart.barchart(pastIn, pastOut, presentIn, presentOut));
                    chartMonth.invalidate();
                }
            }, 30000);
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

        }
    }

    //스캔한거 받아서 처리하는 함수
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //qrcode 가 없으면
            if (result.getContents() == null) {
                Toast.makeText(ScreenMain.this, "취소!", Toast.LENGTH_SHORT).show();
            } else {
                //qrcode 결과가 있으면
                Toast.makeText(ScreenMain.this, "스캔완료!", Toast.LENGTH_SHORT).show();
            }
            try {
                //data를 json으로 변환
                JSONObject obj = new JSONObject(result.getContents()); //스캔한거 받아옴
                sendAddress.setText(obj.getString("sendaddress"));
                sendEth.setText(obj.getString("sendEth"));
            } catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(ScreenMain.this, result.getContents(), Toast.LENGTH_LONG).show();
                sendAddress.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
