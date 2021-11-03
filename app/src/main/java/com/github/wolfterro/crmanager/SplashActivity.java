package com.github.wolfterro.crmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.github.wolfterro.crmanager.utils.Utils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showLoginActivityOrMainActivity();
            }
        }, 3000);
    }

    private void showLoginActivityOrMainActivity() {
        Intent intent;
        boolean isLogged = true; /*Utils.isLogged();*/

        if(isLogged) {
            intent = new Intent(
                    SplashActivity.this, MainActivity.class
            );
        } else {
            intent = new Intent(
                    SplashActivity.this, LoginActivity.class
            );
        }
        startActivity(intent);
        finish();
    }
}