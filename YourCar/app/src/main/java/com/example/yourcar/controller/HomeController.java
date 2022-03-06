package com.example.yourcar.controller;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yourcar.R;

import java.util.Timer;
import java.util.TimerTask;

public class HomeController extends AppCompatActivity {
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                Intent loginIntent = new Intent(HomeController.this, LoginController.class);
                startActivity(loginIntent);
                finish();
            }
        },5000);

    }

}
