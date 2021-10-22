package com.example.android.data;

import android.app.Application;

public class HistorySet extends Application {
    String date;

    String opponent;

    String Ether;

    //보낸 사람이 나인가?
    boolean wasMe;

    public HistorySet() { }

    public String getDate() {
        return date;
    }

    public String getOpponent() {
        return opponent;
    }

    public String getEther() {
        return Ether;
    }

    public boolean getWasMe(){
        return wasMe;
    }

    public void setDate(String d) {
        date = d;
    }

    public void setOpponent(String o) {
        opponent = o;
    }

    public void setEther(String e) {
        Ether = e;
    }

    public void setWasMe(boolean b){
        wasMe = b;
    }
}
