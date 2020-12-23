package com.example.biometricauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;
    Executor executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);
        executor = ContextCompat.getMainExecutor(this);

        biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

                tv.setText("Error");
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                tv.setText("Succeeded");
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();

                tv.setText("Biometric auth failed");
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Unlock with Biometric")
                .setNegativeButtonText("Cancel/put password")
                .setConfirmationRequired(false)
                .build();
    }

    public void click(View view){
        BiometricManager manager = BiometricManager.from(this);

        if(manager.canAuthenticate() != BiometricManager.BIOMETRIC_SUCCESS){
            tv.setText("Biometric hardware not found");
        }

        biometricPrompt.authenticate(promptInfo);
    }
}