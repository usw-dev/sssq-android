package com.example.android.data;

import org.reactivestreams.Subscription;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class BlockChainDAO {

    private UserWallet userWallet = new UserWallet();
    private String tx;

    public String getTx() { return this.tx; }

    public void Eth() throws Exception {
        // web3j와 ganache-cli 연결
        Web3j web3j = Web3j.build(new HttpService("http://13.124.24.91:8547"));
        // 연결된 ganache-cli에 있는 계정 정보 get
        EthAccounts ethAccounts = web3j.ethAccounts().sendAsync().get();
        // ganache-cli 버전 get
        Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();

        // ganache-cli 버전 출력
        //System.out.println(web3ClientVersion.getWeb3ClientVersion());

        // 계좌 주소 리스트에 저장
        List<String> accounts = ethAccounts.getAccounts();

        // 유저 계좌 주소 및 이더 값 리스트 생성
        List<UserWallet> userWallets = new ArrayList<>();

        // 각 유저 계좌의 이더 값 저장
        for (String account : accounts) {
            EthGetBalance ethGetBalance = web3j.ethGetBalance(account, DefaultBlockParameterName.LATEST).sendAsync().get();
            BigDecimal ether = Convert.fromWei(ethGetBalance.getBalance().toString(), Convert.Unit.ETHER);
            userWallets.add(new UserWallet(account, ether));
        }

        // 계좌 정보 출력
//        for (UserWallet userWallet : userWallets) {
//            System.out.println("address : " + userWallet.getAddress() + ", ether : " + userWallet.getEther());
//        }

        // 0번 계좌 -> 1번 계좌 10 이더 전송
        String fromTx = userWallets.get(0).getAddress();
        String toTx = userWallets.get(1).getAddress();
        String etherTx = "10";
        Admin admin = Admin.build(new HttpService("http://13.124.24.91:8547"));

        Transaction transaction = Transaction.createEtherTransaction(
                fromTx,
                null,null,null,
                toTx,
                Convert.toWei(etherTx,Convert.Unit.ETHER).toBigInteger()
        );
        EthSendTransaction ethSendTransaction = admin.ethSendTransaction(transaction).sendAsync().get();

//        System.out.println("\nsend 10 ether from account[0] to account[1] \n");

        userWallets = new ArrayList<>();

//        for (String account : accounts) {
//            EthGetBalance ethGetBalance = web3j.ethGetBalance(account, DefaultBlockParameterName.LATEST).sendAsync().get();
//            BigDecimal ether = Convert.fromWei(ethGetBalance.getBalance().toString(), Convert.Unit.ETHER);
//            userWallets.add(new UserWallet(account, ether));
//        }
//        for (UserWallet userWallet : userWallets) {
//            System.out.println("address : " + userWallet.getAddress() + ", ether : " + userWallet.getEther());
//        }
        Subscription subscription = (Subscription) web3j.replayPastTransactionsFlowable(DefaultBlockParameterName.EARLIEST,DefaultBlockParameterName.LATEST).subscribe(tx -> System.out.println(tx.getHash()));
        this.tx = tx;

    }
}

