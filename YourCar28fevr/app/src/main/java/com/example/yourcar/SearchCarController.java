package com.example.yourcar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.yourcar.adapter.AdapterOffresCar;
import com.example.yourcar.dto.Car;

import java.util.ArrayList;
import java.util.List;

public class SearchCarController extends AppCompatActivity {
    private List<Car> carList = new ArrayList<>();
    private AdapterOffresCar carAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acceuil_search);
        RecyclerView recyclerViewCar = findViewById(R.id.maliste);

        /*recyclerViewCar.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));*/
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerViewCar.addItemDecoration(itemDecorator);

        carAdapter = new AdapterOffresCar(carList);

        LinearLayoutManager carLayoutManager = new LinearLayoutManager(getApplicationContext());
        carLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewCar.setLayoutManager(carLayoutManager);
        recyclerViewCar.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCar.setAdapter(carAdapter);
       prepareCarData();


    }
    @SuppressLint("NotifyDataSetChanged")
    private void prepareCarData() {
        Car car = new Car("audi", "RSQ3", 2020,225,12000,26000);
        carList.add(car);
        car = new Car("mercedes", "c63", 2021,645,200000,34000);
        carList.add(car);
        car = new Car("mercedes", "gle", 2019,254,123000,16000);
        carList.add(car);
        car = new Car("bmw", "116d", 2015,224,199000,8000);
        carList.add(car);
        carAdapter.notifyDataSetChanged();

    }
    }


