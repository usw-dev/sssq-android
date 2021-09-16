package com.example.android.data;

<<<<<<< HEAD
import android.os.AsyncTask;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

public class Connect {

    String ether;

    public String connect(Web3j web3j) {

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    Web3j web3j = Web3j.build(new HttpService("http://52.79.255.216:8547"));
                    EthGetBalance ethGetBalance = web3j.ethGetBalance("0xf480fb1d4f32b7797c829d6f05bb0805c152b3ec", DefaultBlockParameterName.LATEST).sendAsync().get();
                    BigDecimal eth = Convert.fromWei(ethGetBalance.getBalance().toString(), Convert.Unit.ETHER);
                    ether = eth.toString();
                } catch (ExecutionException | InterruptedException e) {
                    ether = "err";
                }

                return null;
            }
        }.execute();

        return ether;
=======
import org.reactivestreams.Subscription;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Array;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.RawTransaction;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Collection;
import org.web3j.utils.Convert;

import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jnr.ffi.Struct;

public class Connect {

    public String EEEE() throws Exception {
        // web3j와 ganache-cli 연결
        Web3j web3j = Web3j.build(new HttpService("http://13.125.199.206:8547"));
        // 연결된 ganache-cli에 있는 계정 정보 get
        EthAccounts ethAccounts = web3j.ethAccounts().sendAsync().get();
        // ganache-cli 버전 get
        Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();

        EthGetBalance ethGetBalance = web3j.ethGetBalance("0xf480fb1d4f32b7797c829d6f05bb0805c152b3ec", DefaultBlockParameterName.LATEST).sendAsync().get();
        BigDecimal ether = Convert.fromWei(ethGetBalance.getBalance().toString(), Convert.Unit.ETHER);

        String eth = ether.toString();

        return eth;
>>>>>>> main
    }
}