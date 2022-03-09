package com.example.yourcar.controller;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yourcar.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class connexionReussiController extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion_succes);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("messagephone2");

        myRef.setValue("Hello, World! 2");
    }
}
