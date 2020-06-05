package com.eduvision.version2.corona.Acheteurs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eduvision.version2.corona.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SeconActitvity extends AppCompatActivity {
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    private EditText UserMail,UserPassword;
    private Button btnLogin;
    ProgressBar Loginprogress;
    private Intent homeactivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_secon_actitvity);
        UserMail= (EditText) findViewById(R.id.Login_mail);
        UserPassword =(EditText)findViewById(R.id.Login_password);
        btnLogin = (Button) findViewById(R.id.Sign_in);
        Loginprogress = (ProgressBar)findViewById(R.id.progressBar);

        homeactivity = new Intent(this, MainActivity.class);

        Loginprogress.setVisibility(View.INVISIBLE);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loginprogress.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.INVISIBLE);

                final String mail = UserMail.getText().toString();
                final String password = UserPassword.getText().toString();

                if (mail.isEmpty()|| password.isEmpty())
                {//something goes wrong
                    // we need to display a message
                    showMessage("Please verify all fields");
                    btnLogin.setVisibility(View.VISIBLE);
                    Loginprogress.setVisibility(View.INVISIBLE);

                }else{

                    sigIn(mail,password);
                }



            }
        });

        mAuth = FirebaseAuth.getInstance();

    }

    private void sigIn(String mail, String password) {
        mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(SeconActitvity.this,new OnCompleteListener<AuthResult> () {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Loginprogress.setVisibility(View.INVISIBLE);
                    btnLogin.setVisibility(View.VISIBLE);
                    updateUI();

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users")
                            .child(mAuth.getCurrentUser().getUid());
                    reference.keepSynced (true);

                    reference.addValueEventListener(new ValueEventListener () {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Intent intent = new Intent(SeconActitvity.this , MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            showMessage ("Aucun Compte Correspondant");
                        }

                    });


                }else{
                    showMessage(task.getException().getMessage());
                    Loginprogress.setVisibility(View.INVISIBLE);
                    btnLogin.setVisibility(View.VISIBLE);

                }

            }
        });

    }

    private void updateUI() {
        startActivity(homeactivity);
        finish();
    }

    private void showMessage(String text) {
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user!=null){
            updateUI();
        }
    }
}
