package com.example.android;

import java.math.BigDecimal;

public class UserWallet {
    private String address;
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
        return ether;
    }

    public void setEther(BigDecimal ether) {
        this.ether = ether;
    }
}
