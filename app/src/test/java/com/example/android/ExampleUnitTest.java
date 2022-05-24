package com.example.android;

import org.junit.Test;
import org.reactivestreams.Subscription;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Array;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Collection;
import org.web3j.utils.Convert;

import java.io.File;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.Provider;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jnr.ffi.Struct;

import static org.junit.Assert.*;

import android.os.Environment;

import com.example.android.data.TxHistory;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    public org.web3j.protocol.core.methods.response.Transaction tx;
    public org.web3j.protocol.core.methods.response.Transaction getTx(Transaction tx) { return this.tx; }

    public void setTx(org.web3j.protocol.core.methods.response.Transaction tx) {
        this.tx = tx;
    }

    List<tx> txInfo = new ArrayList<>();
    List<tx> txMyInfo = new ArrayList<>();
    String ft;

    public void testin(String txHash, String txFrom, String txTo, BigInteger txValue, String timestamp) {
        if(ft.equals(txFrom) || ft.equals(txTo))
            txInfo.add(new tx(txHash,txFrom,txTo,txValue,timestamp));
    }

    @Test
    public void testEth() throws Exception {
        // web3j와 ganache-cli 연결
        Web3j web3j = Web3j.build(new HttpService("http://52.78.121.223:8547"));
        // 연결된 ganache-cli에 있는 계정 정보 get
        EthAccounts ethAccounts = web3j.ethAccounts().sendAsync().get();
        // ganache-cli 버전 get
        Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();

        // ganache-cli 버전 출력
        System.out.println(web3ClientVersion.getWeb3ClientVersion());

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
        for (UserWallet userWallet : userWallets) {
            System.out.println("address : " + userWallet.getAddress() + ", ether : " + userWallet.getEther());
        }

        // 0번 계좌 -> 1번 계좌 10 이더 전송
        Credentials credentials = WalletUtils.loadCredentials("swu", "E:\\UTC--2021-10-08T11-10-46.978Z--8fbdd84d6d42e2d592aaa7776840f20842e06fff.json");
        String fromTx = userWallets.get(0).getAddress();
        String toTx = userWallets.get(2).getAddress();
        ft = fromTx;
        System.out.println(ft);
        String etherTx = "2";
//        Admin admin = Admin.build(new HttpService("http://3.38.108.59:8547"));
//
//        Transaction transaction = Transaction.createEtherTransaction(
//                fromTx,
//                null,null,null,
//                toTx,
//                Convert.toWei(etherTx,Convert.Unit.ETHER).toBigInteger()
//        );
//        EthSendTransaction ethSendTransaction = admin.ethSendTransaction(transaction).sendAsync().get();
//
//        System.out.println("\nsend 10 ether from account[0] to account[1] \n");
//
//        userWallets = new ArrayList<>();
//
//        for (String account : accounts) {
//            EthGetBalance ethGetBalance = web3j.ethGetBalance(account, DefaultBlockParameterName.LATEST).sendAsync().get();
//            BigDecimal ether = Convert.fromWei(ethGetBalance.getBalance().toString(), Convert.Unit.ETHER);
//            userWallets.add(new UserWallet(account, ether));
//        }
//        for (UserWallet userWallet : userWallets) {
//            System.out.println("address : " + userWallet.getAddress() + ", ether : " + userWallet.getEther());
//        }
//
        System.out.println();

        // Subscription subscription = (Subscription) web3j.transactionFlowable().subscribe(tx -> System.out.println(tx.getHash()));

        // Subscription subscription = (Subscription) web3j.replayPastTransactionsFlowable(DefaultBlockParameterName.EARLIEST,DefaultBlockParameterName.LATEST).subscribe(System.out::println);

        // String txHash = ethSendTransaction.getTransactionHash();

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

                    testin(tx.getHash(),tx.getFrom(),tx.getTo(),tx.getValue(),ts);
                });

//        EthGetTransactionReceipt transactionReceipt = web3j.ethGetTransactionReceipt(txHash).send();
//        TransactionReceipt receipt = transactionReceipt.getResult();
//        System.out.println("receipt = " + receipt);

        // web3j.ethLogFlowable((EthFilter) subscription).subscribe(log -> System.out.println(log.toString()));

        //System.out.println(subscription);
        // System.out.println(this.tx);

        Thread.sleep(8000);

        System.out.println();
        System.out.println();

//        for(tx ti : txInfo) {
//            if(fromTx.equals(ti.getTxFrom()))
//                txMyInfo.add(new tx(ti.getTxHash(),ti.getTxFrom(),ti.getTxTo(),ti.getTxValue(),ti.getTimestamp()));
//        }

        System.out.println();
        System.out.println();

        for(tx ti : txInfo) {
            System.out.println("txHash : " + ti.getTxHash());
            System.out.println("txFrom : " + ti.getTxFrom());
            System.out.println("txTo : " + ti.getTxTo());
            System.out.println("txValue : " + ti.getTxValue());
            System.out.println("timestamp : " + ti.getTimestamp());

            System.out.println();
        }
    }

    @Test
    public void transfer() throws Exception {
        // web3j와 ganache-cli 연결
        Web3j web3j = Web3j.build(new HttpService("http://3.38.95.44:8547"));
        Admin admin = Admin.build(new HttpService("http://3.38.95.44:8547"));
        // 연결된 ganache-cli에 있는 계정 정보 get
        EthAccounts ethAccounts = web3j.ethAccounts().sendAsync().get();
        // ganache-cli 버전 get
        Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();

        // ganache-cli 버전 출력
        System.out.println(web3ClientVersion.getWeb3ClientVersion());

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
        for (UserWallet userWallet : userWallets) {
            System.out.println("address : " + userWallet.getAddress() + ", ether : " + userWallet.getEther());
        }

        // 스마트 컨트랙트
        String fromTx = userWallets.get(0).getAddress();
        String toTx = userWallets.get(1).getAddress();
        String contractAddress = "0xb4B6a6Aa93b5Dd8aACD02b83Eb6b5017a3Fdd94C";
        String etherTx = "3";

        // set
        Function function = new Function(
                "set", Arrays.asList(new Address(toTx)), Collections.emptyList()
        );
        String encodedFunction = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createFunctionCallTransaction(
                fromTx,
                null, null, null,
                contractAddress,
                encodedFunction
        );
        EthSendTransaction transactionResponse = web3j.ethSendTransaction(transaction).sendAsync().get();
//        String transactionHash = transactionResponse.getTransactionHash();
//        System.out.println(transactionHash);
//        Thread.sleep(3000);
//        EthGetTransactionReceipt transactionReceipt = web3j.ethGetTransactionReceipt(transactionHash).send();
//        if(transactionReceipt.getTransactionReceipt().isPresent())
//        {
//            // 9. 결과확인
//            System.out.println("transactionReceipt.getResult().getContractAddress() = " +
//                    transactionReceipt.getResult());
//        }
//        else {
//            System.out.println("transaction complete not yet");
//        }
//        TransactionReceipt receipt = transactionReceipt.getResult();
//        System.out.println("receipt : " + receipt);

        // transfer
        function = new Function(
                "transfer", Collections.emptyList(), Collections.emptyList()
        );
        encodedFunction = FunctionEncoder.encode(function);
        transaction = Transaction.createFunctionCallTransaction(
                fromTx,
                null, null, null,
                contractAddress,
                Convert.toWei(etherTx,Convert.Unit.ETHER).toBigInteger(),
                encodedFunction
        );
        EthSendTransaction transactionResponse2 = web3j.ethSendTransaction(transaction).sendAsync().get();
//        String transactionHash2 = transactionResponse2.getTransactionHash();
//        System.out.println(transactionHash2);
//        EthGetTransactionReceipt transactionReceipt2 = web3j.ethGetTransactionReceipt(transactionHash2).send();
//        TransactionReceipt receipt2 = transactionReceipt2.getResult();
//        System.out.println("receipt2 : " + receipt2);

        // EthCall ethCall = web3j.ethCall(transaction,DefaultBlockParameterName.LATEST).send();
        // System.out.println(ethCall.getResult());

        // 로그
//        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, contractAddress);
//        web3j.ethLogFlowable(filter).subscribe(event -> {
//            System.out.println("Event received : " + event);
//        },error -> {
//            System.out.println("Error : " + error);
//        });
    }

    @Test
    public void payment() throws Exception {
        // web3j와 ganache-cli 연결
        Web3j web3j = Web3j.build(new HttpService("http://13.125.181.148:8547"));
        Admin admin = Admin.build(new HttpService("http://13.125.181.148:8547"));
        // 연결된 ganache-cli에 있는 계정 정보 get
        EthAccounts ethAccounts = web3j.ethAccounts().sendAsync().get();

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
        for (UserWallet userWallet : userWallets) {
            System.out.println("address : " + userWallet.getAddress() + ", ether : " + userWallet.getEther());
        }

        // 주소 설정
        String fromTx = userWallets.get(2).getAddress();
        String contractAddress = "0x79BA3903cA5A015e3A6244647F7bBf837f70F294";
        String etherTx = "10";

        // transfer
        Function function = new Function(
                "transfer", Collections.emptyList(), Collections.emptyList()
        );
        String encodedFunction = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createFunctionCallTransaction(
                fromTx,
                null, null, null,
                contractAddress,
                Convert.toWei(etherTx,Convert.Unit.ETHER).toBigInteger(),
                encodedFunction
        );
        EthSendTransaction transactionResponse = web3j.ethSendTransaction(transaction).sendAsync().get();
        String transactionHash = transactionResponse.getTransactionHash();
        System.out.println(transactionHash);
        EthGetTransactionReceipt transactionReceipt = web3j.ethGetTransactionReceipt(transactionHash).send();
        TransactionReceipt receipt = transactionReceipt.getResult();
        System.out.println("receipt : " + receipt);
    }

    @Test
    public void createAccount() throws Exception {
//        Web3j web3j = Web3j.build(new HttpService("http://52.79.188.15:8547"));
//        Admin admin = Admin.build(new HttpService("http://52.79.188.15:8547"));

        String passwd = "swu";
        String path = "D:";
   //     ECKeyPair keyPair = Keys.createEcKeyPair();
        File walletDir = new File(path);

//        System.out.println("Private Key : " + keyPair.getPrivateKey());
//        System.out.println("Account : " + walletFile.getAddress());

        String fileName = WalletUtils.generateLightNewWalletFile(
                passwd,
                walletDir
        );
        walletDir = new File(path + "/" + fileName);

//        Credentials credentials = Credentials.create(Wallet.decrypt(passwd,walletFile));

        Credentials credentials = WalletUtils.loadCredentials(passwd, walletDir);

        System.out.println("Private Key : " + credentials.getEcKeyPair().getPrivateKey());
        System.out.println("Account : " + credentials.getAddress());

    }

    @Test
    public void unLocking() throws Exception {
    //    Web3j web3j = Web3j.build(new HttpService("http://52.79.188.15:8547"));
  //      Admin admin = Admin.build(new HttpService("http://52.79.188.15:8547"));

        String passwd = "swu";
        Credentials credentials = WalletUtils.loadCredentials("swu", "D:UTC--2021-10-10T17-29-43.748Z--b6fa08deaea6e34bbb7d96e8f14bfc3426fb51bc.json");
        System.out.println("Account : " + credentials.getAddress());


//        PersonalUnlockAccount unlockAccount = admin.personalUnlockAccount (
//                credentials.getAddress(),
//                passwd
//        ).send();

//        if(unlockAccount.getError() != null) {
//            System.out.println(unlockAccount.getError().getMessage());
//        }

//        if(unlockAccount.getResult()) {
//            System.out.println("unlock success !!");
//        }
    }

    @Test
    public void LocalAddress() throws Exception {
        Web3j web3j = Web3j.build(new HttpService("http://52.78.121.223:8547"));
        Admin admin = Admin.build(new HttpService("http://52.78.121.223:8547"));
//
//        Credentials credentials = WalletUtils.loadCredentials("swu", "E:\\UTC--2021-10-08T11-10-46.978Z--8fbdd84d6d42e2d592aaa7776840f20842e06fff.json");
//        Credentials credentials2 = WalletUtils.loadCredentials("swu", "E:\\UTC--2021-10-08T11-15-42.229Z--63db1a2778ee694698a499f1bad95d8dcb98f2b5.json");

        String etherTx = "15";
        Transaction transaction = Transaction.createEtherTransaction(
                "0xcca5f0181d11ad01faaf7ebe26d8a7abcc4db72a",
                null,null,null,
                "0xc61e1259de139cbb4093a4e8c00394e1bf520ea",
                Convert.toWei(etherTx,Convert.Unit.ETHER).toBigInteger()
        );
        EthSendTransaction ethSendTransaction = admin.ethSendTransaction(transaction).sendAsync().get();

        System.out.println("\nsend 15 ether from account[0] to card \n");

//        TransactionReceipt receipt = Transfer.sendFunds(web3j,credentials, "0x36032475e40354704f074b941293a355fe24854f",new BigDecimal(1), Convert.Unit.ETHER).sendAsync().get();
//
//        System.out.println("Transaction complete : " + receipt.getTransactionHash());



//        EthGetBalance ethGetBalance = web3j.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
//        BigDecimal ether = Convert.fromWei(ethGetBalance.getBalance().toString(), Convert.Unit.ETHER);
//
//        System.out.println("eth1 : " + ether);
////
//        EthGetBalance ethGetBalance2 = web3j.ethGetBalance(credentials2.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
//        BigDecimal ether2 = Convert.fromWei(ethGetBalance2.getBalance().toString(), Convert.Unit.ETHER);
//
//        System.out.println("eth2 : " + ether2);
    }
}
