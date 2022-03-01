package com.example.yourcar;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class home extends AppCompatActivity {
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                Intent loginIntent = new Intent(home.this,login.class);
                startActivity(loginIntent);
                finish();
            }
        },5000);

    }

}
