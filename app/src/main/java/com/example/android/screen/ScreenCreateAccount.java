package com.example.android.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.android.R;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;

public class ScreenCreateAccount extends AppCompatActivity {

    private ImageButton button_createaccount;
    private EditText pw;

    private void setupBouncyCastle() {
        final Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
        if (provider == null) {
            // Web3j will set up the provider lazily when it's first used.
            return;
        }
        if (provider.getClass().equals(BouncyCastleProvider.class)) {
            // BC with same package name, shouldn't happen in real life.
            return;
        }
        // Android registers its own BC provider. As it might be outdated and might not include
        // all needed ciphers, we substitute it with a known BC bundled in the app.
        // Android's BC has its package rewritten to "com.android.org.bouncycastle" and because
        // of that it's possible to have another BC implementation loaded in VM.
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }

    public void checkVerify() {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            }
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    private File getSaveFolder() {
        File dir = new File(getExternalFilesDir(null) + "/codi");

        if (!dir.exists()) {
            if (!dir.mkdir())
                Toast.makeText(ScreenCreateAccount.this, "생성 오류", Toast.LENGTH_SHORT).show();
        }
        return dir;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_createaccount);

        checkVerify();
        setupBouncyCastle();

        button_createaccount = findViewById(R.id.button_createAccount);
        pw = findViewById(R.id.EText_pw);

        button_createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setupBouncyCastle();

                String passwd = pw.getText().toString();

                if (passwd.isEmpty()) {
                    Toast.makeText(ScreenCreateAccount.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    getSaveFolder();

                    String path = new String(getExternalFilesDir(null) + "/codi");

                    File walletDir = new File(path);

                    String fileName = null;
                    try {
                        fileName = WalletUtils.generateLightNewWalletFile(
                                passwd,
                                walletDir
                        );

                        walletDir = new File(path + "/" + fileName);

                        Toast.makeText(ScreenCreateAccount.this, "지갑 생성 완료!", Toast.LENGTH_SHORT).show();

                        Intent screen_1 = new Intent(ScreenCreateAccount.this, ScreenLogin.class);
                        screen_1.putExtra("address", fileName);
                        startActivity(screen_1);

                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (NoSuchProviderException e) {
                        e.printStackTrace();
                    } catch (InvalidAlgorithmParameterException e) {
                        e.printStackTrace();
                    } catch (CipherException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}