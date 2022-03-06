package com.example.yourcar.controller;

import static com.example.yourcar.adapter.AdapterOffresCar.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import com.example.yourcar.R;
import com.example.yourcar.adapter.AdapterOffresCar;
import com.example.yourcar.dto.Car;
import com.example.yourcar.dto.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchCarController extends AppCompatActivity {

    RecyclerView recyclerViewCar;
    DatabaseReference database;
    AdapterOffresCar myAdapter;
    ArrayList<Car> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acceuil_search);
        SearchView searchCar = findViewById(R.id.searchCar);
        recyclerViewCar = findViewById(R.id.maliste);
        database = FirebaseDatabase.getInstance().getReference("cars");
        //Car car = new Car(" bmw", "x5", 2015,210,6000,45000,"https://freepngimg.com/thumb/bmw/58046-car-bmw-2015-2014-2016-x5.png");
        //database.child("bmw x5").setValue(car);
        recyclerViewCar.setHasFixedSize(true);
        recyclerViewCar.setLayoutManager(new LinearLayoutManager(this));

       //AJOUT DE L'ESPACE ENTRE LES ITEMS
            recyclerViewCar.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL));
           DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
            itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.divider)));
            recyclerViewCar.addItemDecoration(itemDecorator);
        // FIN AJOUT ESPACE ENTRE LES ITEMS


        list = new ArrayList<>();
        myAdapter = new AdapterOffresCar(this,list);
        recyclerViewCar.setAdapter(myAdapter);
        Log.d("test", "onDataChange: ");



        database.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                   Car car = dataSnapshot.getValue(Car.class);
                    list.add(car);
                    //Log.d("test", "onDataChange: "+car);

                }
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        searchCar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }

            private void filter(String newText) {
                List<Car> filteredList = new ArrayList<>();
                for(Car item : list){
                    if(item.getMarque_car().toLowerCase().contains(newText.toLowerCase())){
                        filteredList.add(item);
                    }

                }
              myAdapter.filterList(filteredList);
            }
        });

    }
    /*private List<Car> carList = new ArrayList<>();
    private AdapterOffresCar carAdapter;
    /*private static final String url = "jdbc:mysql://8889/myDB";
    private static final String user = "hitesh";
    private static final String pass = "1234";*/
    /*DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acceuil_search);
        RecyclerView recyclerViewCar = findViewById(R.id.maliste);

        database = FirebaseDatabase.getInstance().getReference("Users");
        /*recyclerViewCar.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));*/
       /* DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
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
        DataSnapshot snapshot = null;
        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

            Car car = dataSnapshot.getValue(Car.class);
            carList.add(car);


        }
        carAdapter.notifyDataSetChanged();
    }
       /* Car car = new Car("audi", "RSQ3", 2020,225,12000,26000);
        carList.add(car);
        car = new Car("mercedes", "c63", 2021,645,200000,34000);
        carList.add(car);
        car = new Car("mercedes", "gle", 2019,254,123000,16000);
        carList.add(car);
        car = new Car("bmw", "116d", 2015,224,199000,8000);
        carList.add(car);*/




    }


