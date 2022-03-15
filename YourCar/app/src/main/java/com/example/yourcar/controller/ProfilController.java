package com.example.yourcar.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.yourcar.MaDatabase;
import com.example.yourcar.R;
import com.example.yourcar.dto.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilController extends Fragment {

    Button voirHistorique,seCoBtn;
    Button seDeco;
    MaDatabase db;
    AlertDialog.Builder a;
    Cursor res;
    StringBuffer buffer;
    DatabaseReference database;
    TextView emailT,nomp,phone;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        return inflater.inflate(R.layout.profil, container, false);
    }

    public void onStart() {
        super.onStart();
        emailT=getView().findViewById(R.id.emailView);
        nomp=getView().findViewById(R.id.nomPrenom);
        phone=getView().findViewById(R.id.phoneView);
        seCoBtn=getView().findViewById(R.id.seCo);
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            Log.d("testCo",user.getEmail());

            Log.d("testCo", user.getUid());
            database = FirebaseDatabase.getInstance().getReference();
          database .child("users").child(user.getUid()).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Get user value
                            User user = dataSnapshot.getValue(User.class);
                            // Log.d("testCo","etape1");

                            String nompT = user.getPrenom()+" "+user.getNom();
                            nomp.setText(nompT);
                            emailT.setText(user.getEmail());
                            phone.setText("numTel: "+user.getPhoneNumber());


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }

        db = new MaDatabase(getActivity());
        seDeco = getView().findViewById(R.id.seDecoBtn);
        seCoBtn=getView().findViewById(R.id.seCo);
        voirHistorique = getView().findViewById(R.id.voirHistorique);

        a = new AlertDialog.Builder(getActivity());
        a.setTitle("Historique");

        voirHistorique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("testq",""+db);
                res = db.getData();
                buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("\n\n");
                    buffer.append("marque : " + res.getString(1) + "\n");
                    buffer.append("model : " + res.getString(2) + "\n");
                    buffer.append("annee : " + res.getString(3) + "\n");
                    buffer.append("kilometrage : " + res.getString(4) + "km\n");
                    buffer.append("prix : " + res.getString(5) + "€\n");
                    buffer.append("chv : " + res.getString(6) + "chv\n");
                    buffer.append("carburant : " + res.getString(7) + "\n");
                    buffer.append("envoi du message à : " + res.getString(8) + "\n\n");
                    buffer.append("          --------------------------- ");
                    a.setMessage(buffer.toString());

                }
                a.show();
            }
        });
        seDeco.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Log.d("test","test");
                Activity activity = getActivity();
                if(user!=null) {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(activity, getString(R.string.deco), Toast.LENGTH_LONG).show();
                    nomp.setText(getString(R.string.pasCoView));
                    emailT.setText(getString(R.string.pasCoView));
                    phone.setText(getString(R.string.pasCoView));
                }
                Toast.makeText(activity, getString(R.string.pasCo), Toast.LENGTH_LONG).show();
            }
        });
        seCoBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Log.d("test","test");
                Activity activity = getActivity();
                if(user==null) {
                    Intent allerSeCo = new Intent(getActivity(),LoginController.class);
                    startActivity(allerSeCo);
                }
                else{
                    Toast.makeText(activity, getString(R.string.dejaCo), Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
