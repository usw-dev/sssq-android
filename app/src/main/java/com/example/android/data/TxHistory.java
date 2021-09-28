package com.example.android.data;

import java.math.BigInteger;

public class TxHistory {
    private String txHash;
    private String txFrom;
    private String txTo;
    private BigInteger txValue;
    private String timestamp;

    public TxHistory(String txHash, String txFrom, String txTo, BigInteger txValue, String timestamp) {
        this.txHash = txHash;
        this.txFrom = txFrom;
        this.txTo = txTo;
        this.txValue = txValue;
        this.timestamp = timestamp;
    }

    public TxHistory() {

    }

    public String getTxHash() { return  txHash; }
    public void setTxHash(String txHash) { this.txHash = txHash; }

    public String getTxFrom() { return txFrom; }
    public void setTxFrom(String txFrom) { this.txFrom = txFrom; }

    public String getTxTo() { return  txTo; }
    public void setTxTo(String txTo) { this.txTo = txTo; }

    public BigInteger getTxValue() { return  txValue; }
    public void setTxValue(BigInteger txValue) { this.txValue = txValue; }

    public String getTimestamp() { return  timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
