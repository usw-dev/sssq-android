package com.example.android.data;

import java.math.BigDecimal;
import java.util.ArrayList;

public class UserWallet {

    // 개인 계좌 정보
    private String address;

    // 개인 계좌 잔액
    private BigDecimal ether;

    // 개인 거래 내역
    private ArrayList<String> history;

    public UserWallet() {
    }

    public UserWallet(String address, BigDecimal ether, ArrayList<String> history) {
        this.address = address;
        this.ether = ether;
        this.history = history;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getEther() {
        return ether;
    }

    public void setEther(BigDecimal ether) {
        this.ether = ether;
    }

    public ArrayList<String> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<String> history) {
        this.history = history;
    }
}
