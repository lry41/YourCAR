package com.example.yourcar.controller;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;


//import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.yourcar.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;


public class LoginController extends AppCompatActivity {
    private static final int REQUEST_CODE = 101010;

    ImageView imageViewlogin;


    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    FirebaseAuth mAuth;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //EDIT TEXT
            EditText email = findViewById(R.id.emailCo);
            EditText password = findViewById(R.id.passwordCo);
        /////

        //BUTTON
            Button buttonGoToInscription = findViewById(R.id.goToInscription);
            Button connection = findViewById(R.id.connection);
        ////

        ImageButton skipCo = findViewById(R.id.buttoncross);

        imageViewlogin = findViewById(R.id.fingerprint);

        TextView erreurlog = findViewById(R.id.erreurLogin);



        //Instanciation firebaseAuth
                 mAuth=FirebaseAuth.getInstance();


//GESTION DE L'EMPREINTE POUR ACCEDER AU COMPTE

        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.d("MY_APP_TAG", "App can authenticate using biometrics.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:

                Toast.makeText(this, "le capteur d'empreinte digital n'existe pas",Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(this, "capteur non utilisé ou occupé  ",Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                // Prompts the user to create credentials that your app accepts.
                final Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                enrollIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG | DEVICE_CREDENTIAL);
                startActivityForResult(enrollIntent, REQUEST_CODE);
                break;
        }


        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(LoginController.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                startActivity(new Intent(LoginController.this, ControllerAllApplication.class ));
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
        String string = getString(android.R.string.cancel);
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.empreinteTitle))
                .setSubtitle(getString(R.string.empreinteTitle2))
                .setNegativeButtonText(getString(R.string.empreinteNegativBtn))
                .build();


       imageViewlogin.setOnClickListener(view -> {
            biometricPrompt.authenticate(promptInfo);
        });




       //FIN GESTION EMPREINTE

        // GoToInscription
            buttonGoToInscription.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent inscription = new Intent(LoginController.this, InscriptionController.class);
                    startActivity(inscription);
                }

            });
        //Fin GoToInscription

        // GoToInscription
        skipCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchCarWitoutCo = new Intent(LoginController.this, ControllerAllApplication.class);

                startActivity(searchCarWitoutCo);
              // Animatoo.animateSlideDown(LoginController.this);
            }

        });
        //Fin GoToInscription

        connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

Log.d("test","bouton marche");
                String emailText = email.getText().toString().trim();
                String passwordText = password.getText().toString().trim();

                if(emailText.isEmpty()){
                    email.setError(getString(R.string.emailvide));

                    email.requestFocus();
                    return;

                }
                if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
                    email.setError(getString(R.string.emailpasConforme));
                    email.requestFocus();
                    return;
                }

                if(passwordText.isEmpty()){
                    password.setError(getString(R.string.mdpvide));
                    password.requestFocus();
                    return;
                }


                mAuth.signInWithEmailAndPassword(emailText,passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(LoginController.this,getString(R.string.reussiteCo),Toast.LENGTH_LONG).show();
                                        erreurlog.setText("");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                       // updateUI(user);
                                       startActivity(new Intent(LoginController.this, ControllerAllApplication.class ));

                                    }
                                    else{
                                        Toast.makeText(LoginController.this,getString(R.string.echecCo),Toast.LENGTH_LONG).show();
                                        erreurlog.setText(getString(R.string.echecCo));
                                        Log.d("test","erro");
                                    }
                    }


                });


    }
});


}
}