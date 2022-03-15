package com.example.yourcar.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yourcar.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ControllerAllApplication extends AppCompatActivity implements AddOffreController.SendMessage {
    BottomNavigationView bottomNavigationView;
     ProfilController profilFragment= new ProfilController();
    SearchCarController searchFragment= new SearchCarController();
    AddOffreController addOffreController = new AddOffreController();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allapplication);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,searchFragment).commit();
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.profil:
                        Log.d("testa","a");
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,profilFragment).commit();
                        return true;

                    case R.id.add:
                        Log.d("testa","b");
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,addOffreController).commit();
                        return true;
                    case R.id.home:
                        Log.d("testa","c");
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,searchFragment).commit();
                        return true;
                }

                return false;
            }
        });

    }

    public void sendData(String message) {

    }

    @Override
    public void sendData(String modele, String marque, String km, String annee, String chevau) {


        AddPhotoController  addPhotoController = new AddPhotoController();
        AddPhotoController.displayReceivedData(modele, marque, km, annee,chevau);
    }
}
