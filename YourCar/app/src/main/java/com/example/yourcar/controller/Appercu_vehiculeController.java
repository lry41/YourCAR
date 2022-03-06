
package com.example.yourcar.controller;

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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yourcar.ConnectionDB.ConnectionBdCar;
import com.example.yourcar.R;
import com.example.yourcar.adapter.AdapterCaracCar;
import com.example.yourcar.adapter.AdapterOffresCar;
import com.example.yourcar.dto.Car;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Appercu_vehiculeController extends AppCompatActivity {
    private static final String TAG_BD = "erreur_Bd";
    private List<String> caracCar = new ArrayList<>();
    private AdapterCaracCar carCaracAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appercu_vehicule);
        RecyclerView recyclerViewCar = findViewById(R.id.list_carac);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        TextView t1 = findViewById(R.id.textViewNom);
        Button b1 = findViewById(R.id.btReserver);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference myRef = database.getReference("message2");

                myRef.setValue("test");
               /* DatabaseReference databaseReference;
                databaseReference= FirebaseDatabase.getInstance().getReference("this is the path");
                databaseReference.setValue("here test").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(),"succes",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_SHORT).show();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });





               /* t1.setText("test1");
                try {
                    t1.setText("test2");
                    Connection con = ConnectionBdCar.connect();

                    ResultSet rs = con.createStatement().executeQuery("select * from Car");
                    t1.setText("test2Bis");
                    while (rs.next()) {//boucle tant que des données existe on rentre les données
                        Car car = new Car();
                        car.setPrix(rs.getInt("prix"));
                        //t1.setText(car.getPrix() + "");
                        t1.setText("test3");
                    }

                } catch (Exception exception) {
                    Log.d(TAG_BD, "erreur bd ");
                }*/
            }
        });

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


