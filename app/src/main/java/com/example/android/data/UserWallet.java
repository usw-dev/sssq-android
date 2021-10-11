package com.example.android.data;

import android.app.Application;
import android.widget.TextView;

import org.reactivestreams.Subscription;

import java.math.BigDecimal;
import java.util.ArrayList;

public class UserWallet extends Application {

    // 개인 계좌 정보
    private String address;

    // 개인 계좌 잔액
    private BigDecimal ether;

    public UserWallet(String address, BigDecimal ether) {
        this.address = address;
        this.ether = ether;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getEther() {
        return this.ether;
    }

    public void setEther(BigDecimal ether) {
        this.ether = ether;
    }
}
