package com.example.yourcar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acceuil_search);
        ListView l1 = (ListView) findViewById(R.id.maliste);

        String listCar[] = {"audi","mercedes","bmw","peugeot","ferrari"};
        String prix[]={"22000","16000","45000","3000","160000"};

        ArrayAdapter<String> tableau = new ArrayAdapter<String>(
                l1.getContext(), R.layout.offres, R.id.offresText,listCar);

        ArrayAdapter<String> tab2 = new ArrayAdapter<String>(
                l1.getContext(), R.layout.offres, R.id.prix,prix);


              //  l1.setAdapter(tab2);
                l1.setAdapter(tableau);


    }

}



