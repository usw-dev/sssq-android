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

        chartMonth.setTouchEnabled(false); // ??????x
        chartMonth.getAxisLeft().setDrawGridLines(false);                  // y???(?????????) ?????? x ?????? ???????????? ?????????
        chartMonth.getAxisLeft().setDrawAxisLine(false);                   // y??? ?????? ??? ??????
        chartMonth.getAxisLeft().setDrawLabels(false);                     // y??? ?????? ?????? ??????
        chartMonth.getAxisRight().setDrawGridLines(false);                 // y???(?????????) ?????? x ?????? ???????????? ?????????
        chartMonth.getAxisRight().setDrawAxisLine(false);                  // y??? ????????? ??? ??????
        chartMonth.getAxisRight().setDrawLabels(false);                    // y??? ????????? ?????? ??????
        chartMonth.getXAxis().setDrawGridLines(false);                     // x???(?????????) ?????? ??????
        chartMonth.getXAxis().setDrawAxisLine(false);                      // x??? ??? ??????
        chartMonth.getXAxis().setDrawLabels(true);                         // x??? ?????? ??????
        chartMonth.getXAxis().setTextColor(Color.WHITE);                   // x??? ?????? ???
        chartMonth.getXAxis().setLabelCount(2);                            // ??????6??? ??????
        chartMonth.getXAxis().setCenterAxisLabels(false);                   // ?????? ????????????
        chartMonth.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);     // ?????? ?????? ?????????
        chartMonth.getLegend().setEnabled(false);                          // ?????????(???????????? ?????? ????????? ???????????? ??????)??? ??????
        chartMonth.getDescription().setEnabled(false);                     // ??????????????? ??????

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
                    Toast.makeText(ScreenMain.this, "???????????? ?????? ?????? ????????????", Toast.LENGTH_SHORT).show();
                else {
                    float compareEther = Float.parseFloat(sendEth.getText().toString());

                    if (compareEther > Float.parseFloat(myUserWallet.getEther().toString())) {
                        Toast.makeText(ScreenMain.this, "????????? ???????????????", Toast.LENGTH_SHORT).show();
                    } else if (compareEther == 0) {
                        Toast.makeText(ScreenMain.this, "0??? ?????? ??? ????????????", Toast.LENGTH_SHORT).show();
                    } else if (sendAddress.getText().toString().length() != 42) {
                        Toast.makeText(ScreenMain.this, "?????? ????????? 42?????? ?????????", Toast.LENGTH_SHORT).show();
                    } else {
                        sendEther sendEther = new sendEther(sendAddress.getText().toString(), sendEth.getText().toString());
                        sendEther.execute();

                        Toast.makeText(ScreenMain.this, "?????? ??????! ??????????????? ???????????????", Toast.LENGTH_SHORT).show();
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
        //?????? -> ?????? -> ??? ??????

        buttonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScreenMain.this, ScreenAppInfo.class);
                startActivity(intent);
            }
        });
        //?????? -> ?????? -> ??? ??????

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
        //????????? ???????????? ??????

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
    //???????????? ?????? ?????????

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

                    //?????? ?????? ?????? ??? ?????????
                    float pastIn = 0;
                    float pastOut = 0;
                    float presentIn = 0;
                    float presentOut = 0;

                    long now = System.currentTimeMillis();

                    Date d = new Date(now);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                    String Curtime = sdf.format(d);

                    //?????? ??? ???
                    int curYear = Integer.parseInt(Curtime.substring(2, 4));
                    int curMonth = Integer.parseInt(Curtime.substring(5, 7));

                    //??? ???????????? ?????? ?????????
                    myHistory = new ArrayList<>();

                    //???????????? ???????????? ?????? ??????
                    if (HISTORY != null) {
                        for (TxHistory history : HISTORY) {
                            //???????????? ?????? ????????? ?????????
                            if ((history.getTxTo().compareTo(ADDRESS) == 0) || (history.getTxFrom().compareTo(ADDRESS) == 0)) {
                                //?????? ??????
                                HistorySet oneHistory = new HistorySet();

                                //????????? ??? ??? ???
                                String txTime = history.getTimestamp();
                                int txYear = Integer.parseInt(txTime.substring(2, 4));
                                int txMonth = Integer.parseInt(txTime.substring(5, 7));
                                int txDay = Integer.parseInt(txTime.substring(8, 10));

                                //????????? ???????????? ????????? ??????
                                String date = (Integer.toString(txYear) + "." + Integer.toString(txMonth) + "." + Integer.toString(txDay));
                                oneHistory.setDate(date);

                                //?????????
                                if ((curYear == txYear) && (curMonth == txMonth)) {
                                    //?????? ????????? ???
                                    if (history.getTxTo().compareTo(ADDRESS) == 0) {
                                        presentIn += history.getTxValue().floatValue();

                                        //?????? ??????, ?????? ?????? ??????
                                        oneHistory.setOpponent(history.getTxFrom());
                                        oneHistory.setEther(history.getTxValue().toString());
                                        oneHistory.setWasMe(false);
                                    }
                                    //?????? ?????? ???
                                    else if (history.getTxFrom().compareTo(ADDRESS) == 0) {
                                        presentOut += history.getTxValue().floatValue();

                                        //?????? ??????, ?????? ?????? ??????
                                        oneHistory.setOpponent(history.getTxTo());
                                        oneHistory.setEther(history.getTxValue().toString());
                                        oneHistory.setWasMe(true);
                                    }
                                }
                                //?????????
                                else if ((curYear == txYear) && ((curMonth - txMonth) == 1)) {
                                    //?????? ????????? ???
                                    if (history.getTxTo().compareTo(ADDRESS) == 0) {
                                        pastIn += history.getTxValue().floatValue();

                                        //?????? ??????, ?????? ?????? ??????
                                        oneHistory.setOpponent(history.getTxFrom());
                                        oneHistory.setEther(history.getTxValue().toString());
                                        oneHistory.setWasMe(false);
                                    }
                                    //?????? ?????? ???
                                    else if (history.getTxFrom().compareTo(ADDRESS) == 0) {
                                        pastOut += history.getTxValue().floatValue();

                                        //?????? ??????, ?????? ?????? ??????
                                        oneHistory.setOpponent(history.getTxTo());
                                        oneHistory.setEther(history.getTxValue().toString());
                                        oneHistory.setWasMe(true);
                                    }
                                }
                                //?????? ?????? ??????
                                myHistory.add(oneHistory);
                            }
                        }
                    }

                    scrollView.removeAllViews();

                    //????????? ?????? ?????? ??????
                    for (int i = 0; i < myHistory.size(); i++) {
                        LinearLayout lin = new LinearLayout(scrollView.getContext());
                        lin.setOrientation(LinearLayout.HORIZONTAL);
                        lin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

                        //??????
                        TextView date = new TextView(scrollView.getContext());
                        date.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
                        date.setText(myHistory.get(i).getDate());
                        date.setGravity(Gravity.CENTER);
                        date.setTextSize(20f);
                        date.setTextColor(Color.WHITE);
                        lin.addView(date);

                        //??????
                        TextView opponent = new TextView(scrollView.getContext());
                        opponent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
                        opponent.setText(myHistory.get(i).getOpponent());
                        date.setGravity(Gravity.CENTER);
                        opponent.setTextSize(20f);
                        opponent.setTextColor(Color.WHITE);
                        lin.addView(opponent);

                        //??????
                        TextView amount = new TextView(scrollView.getContext());
                        amount.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
                        amount.setTextSize(20f);

                        //?????? ????????? ??????????
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

    //???????????? ????????? ???????????? ??????
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //qrcode ??? ?????????
            if (result.getContents() == null) {
                Toast.makeText(ScreenMain.this, "??????!", Toast.LENGTH_SHORT).show();
            } else {
                //qrcode ????????? ?????????
                Toast.makeText(ScreenMain.this, "????????????!", Toast.LENGTH_SHORT).show();
            }
            try {
                //data??? json?????? ??????
                JSONObject obj = new JSONObject(result.getContents()); //???????????? ?????????
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
