
package com.example.yourcar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.yourcar.adapter.AdapterCaracCar;
import com.example.yourcar.adapter.AdapterOffresCar;
import com.example.yourcar.dto.Car;

import java.util.ArrayList;
import java.util.List;

public class Appercu_vehiculeController extends AppCompatActivity {
    private List<String> caracCar = new ArrayList<>();
    private AdapterCaracCar carCaracAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appercu_vehicule);
        RecyclerView recyclerViewCar = findViewById(R.id.list_carac);

       /*
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerViewCar.addItemDecoration(itemDecorator);
        */
        carCaracAdapter = new AdapterCaracCar(caracCar);

        LinearLayoutManager carLayoutManager = new LinearLayoutManager(getApplicationContext());
        carLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewCar.setLayoutManager(carLayoutManager);
        recyclerViewCar.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCar.setAdapter(carCaracAdapter);
        prepareCarData();


    }
    @SuppressLint("NotifyDataSetChanged")
    private void prepareCarData() { //ON AURA UN PROBLEME À AMELIORER
        Car car = new Car("audi", "RSQ3", 2020,225,12000,26000,"diesel");
        String prix = ""+car.getPrix();
        String chv = ""+car.getChevaux();
        String kilometrage = ""+car.getKilometrage();
        String carburant = ""+car.getCarburant();
        caracCar.add(prix);
        caracCar.add(chv);
        caracCar.add(kilometrage);
        caracCar.add(carburant);
        carCaracAdapter.notifyDataSetChanged();

    }
}

