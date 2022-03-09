package com.example.yourcar.controller;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yourcar.R;
import com.example.yourcar.dto.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class InscriptionController extends AppCompatActivity {



    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription);

        EditText password = findViewById(R.id.password);
        EditText nom = findViewById(R.id.nom);
        EditText prenom = findViewById(R.id.prenom);
        EditText email = findViewById(R.id.email);
        EditText numeroTel  = findViewById(R.id.phoneNumber);
        Button inscription = findViewById(R.id.inscription);

        TextView erreurInscr= findViewById(R.id.ErreurInscr);

        ImageButton goToConnection = findViewById(R.id.goToConnection);
        mAuth = FirebaseAuth.getInstance();

        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //database = FirebaseDatabase.getInstance();
                String nomText= nom.getText().toString().trim();
                String prenomText= prenom.getText().toString().trim();
                String passwordText= password.getText().toString().trim();
                String emailText= email.getText().toString().trim();
                String numTelText= numeroTel.getText().toString().trim();

                if (nomText.isEmpty()){
                    nom.setError("veuillez rentrez votre prénom");
                    nom.requestFocus();
                    return;
                }
                if (prenomText.isEmpty()){
                    prenom.setError("veuillez rentrez votre nom");
                    prenom.requestFocus();
                    return;
                }
                if (emailText.isEmpty()){
                    email.setError("veuillez rentrez votre email");
                    email.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
                    email.setError("votre email n'est pas conforme");
                    email.requestFocus();
                    return;
                }
                if (numTelText.isEmpty()){
                    numeroTel.setError("veuillez rentrez votre numéro");
                    numeroTel.requestFocus();
                    return;
                }
                if (passwordText.isEmpty()){
                    password.setError("veuillez rentrez votre mot de passe");
                    password.requestFocus();
                    return;
                }
                if (passwordText.length()<6){
                    password.setError("6 charactères minimum pour le mot de passe");
                    password.requestFocus();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(emailText,passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(emailText,numTelText,nomText,prenomText);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(InscriptionController.this,"l'user a bien été créer !",Toast.LENGTH_LONG).show();
                                        erreurInscr.setText("");
                                    }
                                    else{
                                        Toast.makeText(InscriptionController.this,"echec de l'inscription !",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }
                });

                /*DatabaseReference myRef = database.getReference("users");
                myRef.child("inscription");

                myRef.setValue(user);
*/

            }

        });

        goToConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent connection = new Intent(InscriptionController.this, LoginController.class);
                startActivity(connection);


            }

        });


    }
}