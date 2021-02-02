package com.dpplatform.howltalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.BuildConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.security.PrivateKey;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linear_splash;
    private FirebaseRemoteConfig FirebaseRemoteConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        linear_splash =findViewById(R.id.linear_splash);
        FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build();
        FirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        FirebaseRemoteConfig.setDefaultsAsync(R.xml.default_config);

        FirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            FirebaseRemoteConfig.activate();
                        } else {

                        }
                        displayMessage();
                    }
                });

    }

    private void displayMessage() {
        String splash_background = FirebaseRemoteConfig.getString("splash_background");
        boolean caps = FirebaseRemoteConfig.getBoolean("splash_message_caps");
        String splash_message = FirebaseRemoteConfig.getString("splash_message");

        linear_splash.setBackgroundColor(Color.parseColor(splash_background));

        if (caps) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(splash_message).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            builder.create().show();
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

}