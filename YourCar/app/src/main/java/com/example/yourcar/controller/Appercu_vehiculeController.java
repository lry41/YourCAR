
package com.example.yourcar.controller;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yourcar.ConnectionDB.ConnectionBdCar;

import com.example.yourcar.MaDatabase;
import com.example.yourcar.R;
import com.example.yourcar.adapter.AdapterCaracCar;
import com.example.yourcar.adapter.AdapterOffresCar;
import com.example.yourcar.dto.Car;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Appercu_vehiculeController extends AppCompatActivity {
    //private static final String TAG_BD = "erreur_Bd";
    private List<String> caracCar = new ArrayList<>();
    private AdapterCaracCar carCaracAdapter;
    int id =1;
    MaDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appercu_vehicule);
        db=new MaDatabase(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        RecyclerView recyclerViewCar = findViewById(R.id.list_carac);
        String carburant=getIntent().getStringExtra("carburant");
        String chevaux = getIntent().getStringExtra("chevaux");
        String kilometrage = getIntent().getStringExtra("kilometrage");
        String annee = getIntent().getStringExtra("annee");




    //Image Vehicule
        String img = getIntent().getStringExtra("img");
        ImageView imgCar = findViewById(R.id.imgCar);
        Glide.with(imgCar.getContext()).load(img).into(imgCar);

    //Description Vehicule
        String description = getIntent().getStringExtra("description");
        TextView descriptionView=findViewById(R.id.description);
        descriptionView.setText(description);



    //Marque + Modele Vehicule
        TextView marqueTextView = findViewById(R.id.modeleEtMarque);
        String marque = getIntent().getStringExtra("marqueCar");
        String model = getIntent().getStringExtra("modelCar");
        marqueTextView.setText(marque+" "+model);


    //Logo Vehicule
        ImageView logoView = findViewById(R.id.logoView);
        Context context = logoView.getContext();

        switch (marque){

            case "audi" : img="@drawable/logoaudi";
            //Log.d("test",img);
                break;

            case "mercedes" : img="@drawable/logomercedes";
                break;

            case "bmw" : img="@drawable/logobmw";
                break;

            case "peugeot" : img="@drawable/logoaudi";
                break;

            case "porsche" : img="@drawable/logoporsche";
                break;

            default : break;

        }
        int idLogo = context.getResources().getIdentifier(img, "drawable", context.getPackageName());
        logoView.setImageResource(idLogo);


    //Prix Vehicule
        String prix = getIntent().getStringExtra("prix");
        TextView prixView=findViewById(R.id.prix);
        prixView.setText(prix+"€");

    //Button for come back at Layout of Search
        ImageButton buttonGoSearch = findViewById(R.id.buttonRetour);
        buttonGoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent IntentSearch = new Intent(Appercu_vehiculeController.this, ControllerAllApplication.class);
                startActivity(IntentSearch);
            }
        });




    //Button pour reserver et envoyer un message au vendeur
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
// MY_PERMISSIONS_REQUEST_CALL_PHONE is an app-defined int constant. The callback method //gets the result of the request.
        } else {
            try {
                Log.d("test","permission");

            } catch(SecurityException e) {
                e.printStackTrace(); }
        }
        Button btnreserver = findViewById(R.id.btReserver);

        btnreserver.setOnClickListener(new View.OnClickListener() {
            private static final int PERMISSION_CODE = 1001 ;

            @Override
            public void onClick(View view) {

                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                                Log.d("testCo",user.getEmail());

                                Log.d("testCo", user.getUid());
                            Date aujourdhui = new Date();

                            DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
                                    DateFormat.SHORT,
                                    DateFormat.SHORT);

                            String datemsg = shortDateFormat.format(aujourdhui);
                        //ENVOI UN MSG
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if(checkSelfPermission(Manifest.permission.SEND_SMS)==
                                        PackageManager.PERMISSION_DENIED){
                                    String [] permission = {Manifest.permission.SEND_SMS, Manifest.permission.SEND_SMS};
                                    requestPermissions(permission, PERMISSION_CODE);
                                }
                                else {
                                    String numTel = getIntent().getStringExtra("numProprio");
                                    SmsManager manager = SmsManager.getDefault();
                                    String txtMessage = "Bonjour madame,monsieur, ±\n " +
                                            "+je suis fortement interressé par votre véhicule : " + model + " " + marque +
                                            "Veuillez me reconctacter au";

                                    manager.sendTextMessage(numTel, null, txtMessage
                                            , null, null);
                                    Toast.makeText(Appercu_vehiculeController.this, getString(R.string.reussiteSMS), Toast.LENGTH_LONG).show();
                                }
                            }
                    //SVG DES ENVOIS DES MSG DANS UN BD SQLITE
                    boolean isInserted = db.insertData(marque,model,annee,kilometrage,prix,chevaux,carburant,datemsg);
                    if(isInserted==true)Log.d("testSQL",""+datemsg);
                    else Log.d("testSQL","erreur"+datemsg);
                }else{
                    Toast.makeText(Appercu_vehiculeController.this,getString(R.string.pasCo) ,Toast.LENGTH_LONG).show();
                }




            }
        });

        carCaracAdapter = new AdapterCaracCar(caracCar);

        LinearLayoutManager carLayoutManager = new LinearLayoutManager(getApplicationContext());
        carLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewCar.setLayoutManager(carLayoutManager);
        recyclerViewCar.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCar.setAdapter(carCaracAdapter);
        prepareCarData(chevaux,kilometrage,carburant,annee);

    }

    @SuppressLint("NotifyDataSetChanged")
    private void prepareCarData(String chevaux, String kilometrage, String carburant , String annee) { // PROBLEME RÈGLÉ
        /*
        *Recuperation donnée du vehicule
        *puis ajout dans la list des caractéristiques
        *Voir AdaptaterCarac
         */

        caracCar.add(chevaux);
        caracCar.add(kilometrage);
        caracCar.add(carburant);
        caracCar.add(annee);
        carCaracAdapter.notifyDataSetChanged();

    }



}



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