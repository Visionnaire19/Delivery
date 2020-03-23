package com.eduvision.version2.corona;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class sign_up extends AppCompatActivity {

    EditText Nom,Password,Telephone,Email;
    String nom,email,password,telephone;
    Button SignUpButton;
  FirebaseAuth auth;
    DatabaseReference databaseReference;
     StorageReference storageReference;
    FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Nom = findViewById(R.id.sign_up_nom);
        Email = findViewById(R.id.sign_up_email);
        Password = findViewById(R.id.sign_up_password);
        Telephone = findViewById(R.id.sign_up_numero);
        SignUpButton = findViewById(R.id.sign_up_button);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();



        SignUpButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
             nom = Nom.getText().toString();
               email = Email.getText().toString();
                 password = Password.getText().toString();
                telephone = Telephone.getText().toString();


                if(TextUtils.isEmpty(nom)){
                    Toast.makeText(getApplicationContext(),"Entrez votre nom",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Entrez votre email",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Entrez votre mot de passe",Toast.LENGTH_LONG).show();
                }
                if(TextUtils.isEmpty(telephone)){
                    Toast.makeText(getApplicationContext(),"Entrez votre numero de telephone",Toast.LENGTH_LONG).show();
                }
                if (password.length()<8){
                    Toast.makeText(getApplicationContext(),"Le mot de passe doit avoir plus de 8 caracteres.", Toast.LENGTH_LONG).show();
                }
                else{
                    auth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(sign_up.this, new OnCompleteListener<AuthResult>() {
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(sign_up.this, "ERROR",Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        FirebaseUser user = auth.getCurrentUser();
                                        UserInformation userinformation = new UserInformation(nom, telephone);
                                        databaseReference.child(user.getUid()).setValue(userinformation);
                                        Toast.makeText(getApplicationContext(),nom,Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(sign_up.this,MainActivity.class));
                                        finish();
                                    }
                                }
                            });}
            }
        });
    }




    }

