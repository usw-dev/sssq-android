package com.example.android;

import java.math.BigInteger;

public class tx {
    private String txHash;
    private String txTo;
    private BigInteger txValue;

    public tx(String txHash, String txTo, BigInteger txValue) {
        this.txHash = txHash;
        this.txTo = txTo;
        this.txValue = txValue;
    }

    public String getTxHash() { return  txHash; }
    public void setTxHash(String txHash) { this.txHash = txHash; }

    public String getTxTo() { return  txTo; }
    public void setTxTo(String txTo) { this.txTo = txTo; }

    public BigInteger getTxValue() { return  txValue; }
    public void setTxValue(BigInteger txValue) { this.txValue = txValue; }
}
