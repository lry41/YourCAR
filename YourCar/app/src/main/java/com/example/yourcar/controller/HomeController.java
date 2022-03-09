package com.example.yourcar.controller;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yourcar.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class HomeController extends AppCompatActivity {
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
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
