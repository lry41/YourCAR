package com.example.yourcar.controller;

import androidx.annotation.NonNull;


import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;


import com.example.yourcar.DialogFilter;
import com.example.yourcar.DialogFilter.DialogFilterInterface;
import com.example.yourcar.R;
import com.example.yourcar.adapter.AdapterOffresCar;
import com.example.yourcar.dto.Car;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchCarController extends Fragment implements DialogFilterInterface {

    RecyclerView recyclerViewCar;
    DatabaseReference database;
    AdapterOffresCar myAdapter;
    ArrayList<Car> list;

    BottomNavigationView bottomNavigationView;
   ;

    private AdapterOffresCar.RecyclerViewClickListener listener ;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        return inflater.inflate(R.layout.acceuil_search,container,false);
    }

    @Override
    public void onStart() {

        super.onStart();
       //FirebaseAuth mAuth;
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        //bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
       // Log.d("test",""+navListener);
        Log.d("test",""+bottomNavigationView);


        Button btnFilter= getView().findViewById(R.id.buttonfilter);

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        SearchView searchCar = getView().findViewById(R.id.searchCar);
        recyclerViewCar = getView().findViewById(R.id.maliste);
        database = FirebaseDatabase.getInstance().getReference("cars");

        /* Createur de vehicule
        *Car car = new Car("audi", "RSQ3", 2021,445,12000,36200,"Essence","https://tse1.mm.bing.net/th?id=OIP.NhbpQmxGaslaeb_Kdtx-PgHaEo&pid=Api","Audi verte pomme look parfait pour l'été,confortable et tres spacieuse,boite automatique avec palette, 3 modes de conduite, siege en cuir blanc");
        *database.child("audi rsq3").setValue(car);
        */

        recyclerViewCar.setHasFixedSize(true);
        recyclerViewCar.setLayoutManager(new LinearLayoutManager(getActivity()));



       //AJOUT DE L'ESPACE ENTRE LES ITEMS
            recyclerViewCar.addItemDecoration(new DividerItemDecoration(getActivity(),
                    DividerItemDecoration.VERTICAL));
           DividerItemDecoration itemDecorator = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
            itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getActivity(), R.drawable.divider)));
            recyclerViewCar.addItemDecoration(itemDecorator);
        // FIN AJOUT ESPACE ENTRE LES ITEMS


        list = new ArrayList<>();
        setOnclikListner();
        myAdapter = new AdapterOffresCar(list,listener);
        recyclerViewCar.setAdapter(myAdapter);
        //Log.d("test", "onDataChange: ");

        database.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                   Car car = dataSnapshot.getValue(Car.class);   //Recuperation donne de firebase
                    list.add(car);                                 //Ajout dans la list des objets Car
                   // Log.d("test", "onDataChange: "+car);

                }
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//SEARCHBAR

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
                for(Car item : list){ //Pour chaque voiture de la list on regarde si le contenu de SearchView est contenu dans la valeur de la marque
                    if(item.getMarque_car().toLowerCase().contains(newText.toLowerCase())){
                        filteredList.add(item);
                    }

                }
              myAdapter.filterList(filteredList);
            }
        });

    }


//FIN SEARCHBAR

private void openDialog() {
    DialogFilter dialogFilter = new DialogFilter();
    Log.d("testD","1"+this.getContext());
    Log.d("testD","2"+this);
    Log.d("testD","3"+this.getChildFragmentManager());
    Log.d("testD","4"+SearchCarController.this);
    dialogFilter.setTargetFragment(this, 123);
    FragmentManager fm = getActivity().getSupportFragmentManager();
    //FragmentManager fm = this.getChildFragmentManager();

    dialogFilter.show(fm,"test");

}


    private void setOnclikListner() {

        //Log.d("test","RENTRE DANS SETONCLICK");

        listener = new AdapterOffresCar.RecyclerViewClickListener() {

            public void OnClick(View v, int position) {

                //Log.d("test","click");

                Intent visuVehicule = new Intent(getActivity(),Appercu_vehiculeController.class);

                //Passage des donnée de la page Recherche à celle de visualisation du vehicule
                visuVehicule.putExtra("description",list.get(position).getDescription());
                visuVehicule.putExtra("carburant",list.get(position).getCarburant());
                visuVehicule.putExtra("img",list.get(position).getUrl());
                visuVehicule.putExtra("marqueCar",list.get(position).getMarque_car());
                visuVehicule.putExtra("modelCar",list.get(position).getModel_car());
                visuVehicule.putExtra("numProprio",list.get(position).getNumProprio());
                String passerChv=""+list.get(position).getChevaux(); //putExtra only for string
                visuVehicule.putExtra("chevaux",passerChv);

                String passerPrix=""+list.get(position).getPrix(); //putExtra only for string
                visuVehicule.putExtra("prix", passerPrix);

                String passerKilometrage=""+list.get(position).getKilometrage();//putExtra only for string
                visuVehicule.putExtra("kilometrage",passerKilometrage);

                String passerAnnee=""+list.get(position).getAnnee(); //putExtra only for string
                visuVehicule.putExtra("annee",passerAnnee);

                startActivity(visuVehicule); //Lancement activité


            }
        };
    }


/*
*applyfilter pour filtrer les recherchers
* renvoi une liste filtré en fonction des test et des choic
 */
    @Override
    public void applyFilter(String marqueChoose, int kmChoose) {
        Log.d("testp","b"+kmChoose);
            List<Car> filteredList = new ArrayList<>();
            for(Car item : list){ //Pour chaque voiture de la list on regarde si le contenu de SearchView est contenu dans la valeur de la marque
               if(!marqueChoose.equals("vide") && kmChoose!=0) {
                   if (item.getMarque_car().toLowerCase().contains(marqueChoose.toLowerCase())
                           && item.getKilometrage() <= kmChoose) {
                       filteredList.add(item);

                   }

               }
               else if (!marqueChoose.equals("vide") && kmChoose==0){
                   if (item.getMarque_car().toLowerCase().contains(marqueChoose.toLowerCase())){
                       filteredList.add(item);
                   }
               }

               else if (marqueChoose.equals("vide") && kmChoose!=0){
                   if (item.getKilometrage() <= kmChoose){

                       filteredList.add(item);
                   }
               }
               else if (marqueChoose.equals("vide") && kmChoose==0){


                       filteredList.add(item);

               }
               Log.d("testk",marqueChoose);
            }
            myAdapter.filterList(filteredList);
        }

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







