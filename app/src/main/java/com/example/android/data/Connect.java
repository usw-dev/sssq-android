//package com.example.android.data;
//
//import android.os.AsyncTask;
//
//import org.web3j.abi.FunctionEncoder;
//import org.web3j.abi.datatypes.Address;
//import org.web3j.abi.datatypes.Function;
//import org.web3j.protocol.Web3j;
//import org.web3j.protocol.admin.Admin;
//import org.web3j.protocol.core.DefaultBlockParameterName;
//import org.web3j.protocol.core.methods.request.Transaction;
//import org.web3j.protocol.core.methods.response.EthAccounts;
//import org.web3j.protocol.core.methods.response.EthGetBalance;
//import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
//import org.web3j.protocol.core.methods.response.EthSendTransaction;
//import org.web3j.protocol.core.methods.response.TransactionReceipt;
//import org.web3j.protocol.core.methods.response.Web3ClientVersion;
//import org.web3j.protocol.http.HttpService;
//import org.web3j.utils.Convert;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.concurrent.ExecutionException;
//
//public class Connect {
//
//    String ether;
//
//    public String connect() {
//
//        new AsyncTask() {
//            @Override
//            protected Object doInBackground(Object[] objects) {
//
//                // web3j와 ganache-cli 연결
//                Web3j web3j = Web3j.build(new HttpService("http://13.124.190.163:8547"));
//                Admin admin = Admin.build(new HttpService("http://13.124.190.163:8547"));
//
//                // 스마트 컨트랙트
//                String fromTx = "0xf480fb1d4f32b7797c829d6f05bb0805c152b3ec";
//                String toTx = "0x10016a91938f7982fe9e6ff20d0fd160e6f29e2f";
//                String contractAddress = "0xb4B6a6Aa93b5Dd8aACD02b83Eb6b5017a3Fdd94C";
//                String etherTx = "10";
//
//                // set
//                Function function = new Function(
//                        "set", Arrays.asList(new Address(toTx)), Collections.emptyList()
//                );
//                String encodedFunction = FunctionEncoder.encode(function);
//                Transaction transaction = Transaction.createFunctionCallTransaction(
//                        fromTx,
//                        null, null, null,
//                        contractAddress,
//                        encodedFunction
//                );
//
//                EthSendTransaction transactionResponse = null;
//                try {
//                    transactionResponse = web3j.ethSendTransaction(transaction).sendAsync().get();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//
//                // transfer
//                function = new Function(
//                        "transfer", Collections.emptyList(), Collections.emptyList()
//                );
//                encodedFunction = FunctionEncoder.encode(function);
//                transaction = Transaction.createFunctionCallTransaction(
//                        fromTx,
//                        null, null, null,
//                        contractAddress,
//                        Convert.toWei(etherTx,Convert.Unit.ETHER).toBigInteger(),
//                        encodedFunction
//                );
//                try {
//                    EthSendTransaction transactionResponse2 = web3j.ethSendTransaction(transaction).sendAsync().get();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//        }.execute();
//
//        return ether;
//    }
//}
//package com.example.android.data;
//
//import org.reactivestreams.Subscription;
//import org.web3j.abi.FunctionEncoder;
//import org.web3j.abi.TypeReference;
//import org.web3j.abi.datatypes.Address;
//import org.web3j.abi.datatypes.Array;
//import org.web3j.abi.datatypes.Function;
//import org.web3j.abi.datatypes.Type;
//import org.web3j.abi.datatypes.generated.Uint256;
//import org.web3j.crypto.RawTransaction;
//import org.web3j.protocol.Web3j;
//import org.web3j.protocol.admin.Admin;
//import org.web3j.protocol.core.DefaultBlockParameterName;
//import org.web3j.protocol.core.DefaultBlockParameterNumber;
//import org.web3j.protocol.core.methods.request.EthFilter;
//import org.web3j.protocol.core.methods.request.Transaction;
//import org.web3j.protocol.core.methods.response.EthAccounts;
//import org.web3j.protocol.core.methods.response.EthCall;
//import org.web3j.protocol.core.methods.response.EthGetBalance;
//import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
//import org.web3j.protocol.core.methods.response.EthSendTransaction;
//import org.web3j.protocol.core.methods.response.TransactionReceipt;
//import org.web3j.protocol.core.methods.response.Web3ClientVersion;
//import org.web3j.protocol.http.HttpService;
//import org.web3j.utils.Collection;
//import org.web3j.utils.Convert;
//
//import java.lang.annotation.Target;
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import jnr.ffi.Struct;
//
//public class Connect {
//
//    public String connect() throws Exception {
//        // web3j와 ganache-cli 연결
//        Web3j web3j = Web3j.build(new HttpService("http://13.124.190.163:8547"));
//        // 연결된 ganache-cli에 있는 계정 정보 get
//        EthAccounts ethAccounts = web3j.ethAccounts().sendAsync().get();
//        // ganache-cli 버전 get
//        Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
//
//        EthGetBalance ethGetBalance = web3j.ethGetBalance("0xf480fb1d4f32b7797c829d6f05bb0805c152b3ec", DefaultBlockParameterName.LATEST).sendAsync().get();
//        BigDecimal ether = Convert.fromWei(ethGetBalance.getBalance().toString(), Convert.Unit.ETHER);
//
//        String eth = ether.toString();
//
//        return eth;
//    }
//}
package com.example.android.data;

import android.os.AsyncTask;

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
import java.util.concurrent.ExecutionException;

import jnr.ffi.Struct;



    public class Connect {

        String ether;

        public String connect() {

            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] objects) {

                    // web3j와 ganache-cli 연결
                    Web3j web3j = Web3j.build(new HttpService("http://13.124.190.163:8547"));
                    Admin admin = Admin.build(new HttpService("http://13.124.190.163:8547"));

                    // 스마트 컨트랙트
                    String fromTx = "0xf480fb1d4f32b7797c829d6f05bb0805c152b3ec";
                    String toTx = "0x10016a91938f7982fe9e6ff20d0fd160e6f29e2f";
                    String contractAddress = "0xb4B6a6Aa93b5Dd8aACD02b83Eb6b5017a3Fdd94C";
                    String etherTx = "50";

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

                    EthSendTransaction transactionResponse = null;
                    try {
                        transactionResponse = web3j.ethSendTransaction(transaction).sendAsync().get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

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
                    try {
                        EthSendTransaction transactionResponse2 = web3j.ethSendTransaction(transaction).sendAsync().get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();

            return ether;
        }
    }