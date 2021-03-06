package com.example.android.data;

import android.os.AsyncTask;

import org.reactivestreams.Subscription;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


public class Refresh {

    UserWallet userWallet;
    List<TxHistory> txHistory = new ArrayList<>();
    String address;

    public UserWallet refresh(String a) {

        address = a;

        // Web3j 연결부분
        Web3j web3j = Web3j.build(new HttpService("http://13.124.59.174:8547"));

        // 잔액 조회 부분
        EthGetBalance ethGetBalance = null;
        try {
            ethGetBalance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).sendAsync().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        BigDecimal ether = Convert.fromWei(ethGetBalance.getBalance().toString(), Convert.Unit.ETHER);


        Subscription subscription = (Subscription) web3j
                .replayPastTransactionsFlowable(DefaultBlockParameterName.EARLIEST,DefaultBlockParameterName.LATEST)
                .subscribe(tx -> {
                    EthBlock ethBlock = web3j.ethGetBlockByHash(tx.getBlockHash(),false).send();
                    long timestamp = ethBlock.getBlock().getTimestamp().longValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                    String ts = sdf.format( new Date(timestamp*1000L));
                    txin(tx.getHash(),tx.getFrom(),tx.getTo(),tx.getValue(),ts);
                });
        userWallet = new UserWallet(address, ether, txHistory);


        return userWallet;
    }

    public void txin(String txHash, String txFrom, String txTo, BigInteger txValue, String timestamp) {
        if(address.equals(txFrom) || address.equals(txTo))
            txHistory.add(new TxHistory(txHash,txFrom,txTo,txValue,timestamp));
    }
}
