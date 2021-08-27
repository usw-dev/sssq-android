package com.example.android.data;

import org.reactivestreams.Subscription;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import android.app.Application;

import java.math.BigDecimal;

public class BlockChainDAO extends Application{
    UserWallet userWallet = new UserWallet();

//    public BlockChainDAO() throws Exception{
//        UserWallet userWallet = new UserWallet("WX08-9828-1930-0942-8852-1056",10,Subscription subscripton);
//        this.userWallet = userWallet;
//    }
//
    public BlockChainDAO(String address) throws Exception{
        // Web3j 연결부분
        Web3j web3j = Web3j.build(new HttpService("http://3.35.235.189:8547"));

        // 잔액 조회 부분
        EthGetBalance ethGetBalance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).sendAsync().get();
        BigDecimal ether = Convert.fromWei(ethGetBalance.getBalance().toString(), Convert.Unit.ETHER);

        // 거래 내역 부분
        Subscription subscription = (Subscription) web3j
                .replayPastTransactionsFlowable(DefaultBlockParameterName.EARLIEST,DefaultBlockParameterName.LATEST)
                .subscribe(tx->{tx.getFrom();});

        // UserWallet 생성 + 생성자 값 대입
        UserWallet userWallet = new UserWallet(address, ether, subscription);
        this.userWallet = userWallet;
    }

    public UserWallet getUserWallet() {
        return userWallet;
    }
}