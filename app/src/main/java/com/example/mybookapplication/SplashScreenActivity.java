package com.example.mybookapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;



import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Rediriger vers LoginActivity
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Fermer l'activité actuelle pour empêcher de revenir en arrière
            }
        }, 3000); // 3000 ms = 3 secondes
    }
}