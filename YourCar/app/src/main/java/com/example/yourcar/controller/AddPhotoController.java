package com.example.yourcar.controller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.yourcar.R;
import com.example.yourcar.dto.Car;
import com.example.yourcar.dto.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddPhotoController extends Fragment {
    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    DatabaseReference database;
    Button captPhoto ;
    Uri image_url;
    ImageView visuImg;
    Button postAnnonce;
    EditText prix;
    static List<String>offres = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        return inflater.inflate(R.layout.add_photo,container,false);
    }
    @Override
    public void onStart() {
        super.onStart();
        prix=getView().findViewById(R.id.prixView);
       visuImg = getView().findViewById(R.id.visuPhoto);
        captPhoto = getView().findViewById(R.id.ajoutPhoto);
        postAnnonce = getView().findViewById(R.id.posterAnnonce);

        captPhoto.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

               if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if(ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.CAMERA)==
                            PackageManager.PERMISSION_DENIED ||
                            ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                            PackageManager.PERMISSION_DENIED ){
                        String [] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else{
                        openCamera();
                    }
                }
                else{
                        openCamera();
                }

            }

        });
        postAnnonce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null) {
                    Log.d("testCo", user.getEmail());

                    Log.d("testCo", user.getUid());
                    database = FirebaseDatabase.getInstance().getReference();
                    database.child("users").child(user.getUid()).addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    User user = dataSnapshot.getValue(User.class);
                                    String numTelProprio= user.getPhoneNumber();

                                    Log.d("testA", "test");
                                    String img = "" + image_url;
                                    String prixText = prix.getText().toString().trim();
                                    int prixInt = Integer.parseInt(prixText);
                                    database = FirebaseDatabase.getInstance().getReference("cars");
                                    String modele = offres.get(0).toString();
                                    String marque = offres.get(1).toString();
                                    String annee = offres.get(2).toString();
                                    int anneeInt = Integer.parseInt(annee);
                                    String km = offres.get(3).toString();
                                    int kmInt = Integer.parseInt(km);
                                    String chv = offres.get(4).toString();
                                    int chvInt = Integer.parseInt(chv);
                                    //ON RECUPERE LES VALEURS DU TABLEAU PUIS ON CONVERTIE EN ENTIER
                                    Log.d("testA", "modele" + modele);
                                    Log.d("testA", "marque" + marque);
                                    Log.d("testA", "annee" + annee);
                                    Log.d("testA", "KM" + km);
                                    Log.d("testA", "chv" + chv);
                                    Log.d("testA", "prix" + prixText);
                                    Car car = new Car(modele, marque, anneeInt, kmInt, chvInt, prixInt, "Essence", img,
                                            "Bon véhicule", numTelProprio);

                                    String marqueEtModel = marque + modele;
                                    database.child(marqueEtModel).setValue(car);
                                    Log.d("testA", marqueEtModel);
                                    Activity activity = getActivity();
                                    Toast.makeText(activity, getString(R.string.AnnonceCreer), Toast.LENGTH_LONG).show();

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                }
                else{
                    Toast.makeText(getActivity(), getString(R.string.pasCo), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"image");
        values.put(MediaStore.Images.Media.DESCRIPTION,"from camera");
        image_url = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent,IMAGE_CAPTURE_CODE);
        Log.d("img", ""+image_url);
    }


    @SuppressLint("MissingSuperCall")
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Activity activity = getActivity();
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();

                } else {
                    Toast.makeText(activity, getString(R.string.perssionNon), Toast.LENGTH_LONG).show();
                }
            }
        }




    }


    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_CAPTURE_CODE ) {
            Log.d("img", ""+image_url);

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            visuImg.setImageBitmap(bitmap);
        }
    }

     static public List<String> displayReceivedData(String modele, String marque, String km, String annee, String chevau){

        Log.d("testA",modele);
        offres.add(modele);
         offres.add(marque);
         offres.add(km);
         offres.add(annee);
         offres.add(chevau);
       return offres ;


    }
}
