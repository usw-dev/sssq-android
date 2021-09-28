package com.example.android.data;

import org.reactivestreams.Subscription;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import android.app.Application;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BlockChainDAO extends Application{
    UserWallet userWallet = new UserWallet();
    List<TxHistory> txHistory = new ArrayList<>();
    String MyAddress;

//    public BlockChainDAO() throws Exception{
//        UserWallet userWallet = new UserWallet("WX08-9828-1930-0942-8852-1056",10,Subscription subscripton);
//        this.userWallet = userWallet;
//    }
//

    public void txin(String txHash, String txFrom, String txTo, BigInteger txValue, String timestamp) {
        if(MyAddress.equals(txFrom))
            txHistory.add(new TxHistory(txHash,txFrom,txTo,txValue,timestamp));
    }

    public BlockChainDAO(String address) throws Exception{
        // Web3j 연결부분
        Web3j web3j = Web3j.build(new HttpService("http://3.35.235.189:8547"));

        // 잔액 조회 부분
        EthGetBalance ethGetBalance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).sendAsync().get();
        BigDecimal ether = Convert.fromWei(ethGetBalance.getBalance().toString(), Convert.Unit.ETHER);

        // 거래 내역 txhistory 생성
        Subscription subscription = (Subscription) web3j
                .replayPastTransactionsFlowable(DefaultBlockParameterName.EARLIEST,DefaultBlockParameterName.LATEST)
                .subscribe(tx -> {
//            System.out.println(tx.getBlockNumber() + " : " + tx.getBlockHash());
//            System.out.println("tx : " + tx.getHash());
//            System.out.println("from : " + tx.getFrom() + "    to : " + tx.getTo());
//            System.out.println("value : " + tx.getValue());
                    EthBlock ethBlock = web3j.ethGetBlockByHash(tx.getBlockHash(),false).send();
                    long timestamp = ethBlock.getBlock().getTimestamp().longValue();
                    SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
                    String ts = sdf.format( new Date(timestamp*1000L));

                    txin(tx.getHash(),tx.getFrom(),tx.getTo(),tx.getValue(),ts);
                });
        this.MyAddress = address;

        // UserWallet 생성 + 생성자 값 대입
        UserWallet userWallet = new UserWallet(address, ether);
        this.userWallet = userWallet;
    }

    public UserWallet getUserWallet() {
        return userWallet;
    }
    public List<TxHistory> getTxHistory() { return txHistory; }
}