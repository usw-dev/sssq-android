package com.example.android.data;

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

    public String connect() {

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
    }
}