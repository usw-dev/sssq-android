package com.example.android.data;

import android.app.Application;
import android.widget.TextView;

import org.reactivestreams.Subscription;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class UserWallet extends Application {

    // 개인 계좌 정보
    private String address;

    // 개인 계좌 잔액
    private BigDecimal ether;

    // 개인 내역
    List<TxHistory> txHistory;

    public UserWallet(String address, BigDecimal ether,List<TxHistory> txHistory) {
        this.address = address;
        this.ether = ether;
        this.txHistory = txHistory;
    }

    public String getAddress() {
        return address;
    }

    public BigDecimal getEther() {
        return this.ether;
    }

    public List<TxHistory> getTxHistory() { return txHistory; }
}
