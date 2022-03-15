package com.example.yourcar.controller;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.yourcar.R;

public class AddOffreController extends Fragment {
    private SendMessage sendMessage;
    Button suivant;
    EditText modele;
    EditText km;
    EditText annee;
    EditText marque;
    EditText chevaux;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        return inflater.inflate(R.layout.add_car, container, false);
    }

    public void onStart() {
        super.onStart();

        suivant=getView().findViewById(R.id.goToEtape2);
        modele=getView().findViewById(R.id.modele);
        km=getView().findViewById(R.id.kilometrage);
        annee=getView().findViewById(R.id.annee);
        marque=getView().findViewById(R.id.marque);
        chevaux=getView().findViewById(R.id.chv);



        suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test","go to 2");
                String modeleText= modele.getText().toString().trim();
                String marqueText= marque.getText().toString().trim();
                String anneeText= annee.getText().toString().trim();
                String kmText= km.getText().toString().trim();
                String chevauText= chevaux.getText().toString().trim();
                if (modeleText.isEmpty()) {
                    modele.setError(getString(R.string.modeleVide));
                    modele.requestFocus();
                    return;
                }
                if (chevauText.isEmpty()) {
                    chevaux.setError(getString(R.string.modeleVide));
                    chevaux.requestFocus();
                    return;
                }
                if (marqueText.isEmpty()){
                    marque.setError(getString(R.string.marqueVide));
                    marque.requestFocus();
                    return;
                }
                if (anneeText.isEmpty()){
                    annee.setError(getString(R.string.anneevide));
                    annee.requestFocus();
                    return;
                }

                if (kmText.isEmpty()){
                    km.setError(getString(R.string.kmVide));
                    km.requestFocus();
                    return;
                }



                sendMessage.sendData(modeleText, marqueText, kmText, anneeText,chevauText);
                getFragmentManager().beginTransaction().replace(R.id.container, new AddPhotoController()).commit();

            }
        });

    }
    interface SendMessage {
        void sendData(String modele, String marque, String km, String annee,String chevau);
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            sendMessage = (SendMessage) getActivity();
        }
        catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }
}