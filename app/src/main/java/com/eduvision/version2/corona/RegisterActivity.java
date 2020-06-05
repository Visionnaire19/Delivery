package com.eduvision.version2.corona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.eduvision.version2.corona.Acheteurs.MainActivity;
import com.eduvision.version2.corona.Sellers.Main2Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private EditText UserEmail,UserPassword;
    private Button create;
    private FirebaseAuth auth;
    static  int PReqcode =1;
    ImageView profil ;

    static int REQUESCODE = 1;

    FirebaseUser firebaseUser;

    ProgressBar loadingprogress;
    DatabaseReference reference;
    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.register_activity);
        auth = FirebaseAuth.getInstance();
        UserEmail = (EditText)findViewById(R.id.Email1);
        UserPassword = (EditText)findViewById(R.id.pass1);
        create = (Button) findViewById(R.id.Create1);
        loadingprogress = (ProgressBar)findViewById(R.id.progressBar1);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create.setVisibility(View.INVISIBLE);
                loadingprogress.setVisibility(View.VISIBLE);
                String str_email = UserEmail.getText().toString();
                String str_password = UserPassword.getText().toString();




                if ( TextUtils.isEmpty(str_email)||TextUtils.isEmpty(str_password)){
                    Toast.makeText(RegisterActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                    create.setVisibility(View.VISIBLE);
                    loadingprogress.setVisibility(View.INVISIBLE);

                }else if (str_password.length()< 6){
                    Toast.makeText(RegisterActivity.this, "Password must have 6 characters", Toast.LENGTH_SHORT).show();
                }else{
                    register(str_email,str_password);

                }
            }
        });


    }

    private void register( final String email , String password ){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String user_id = firebaseUser.getUid ();


                            reference = FirebaseDatabase.getInstance ().getReference ("Users").child ("Acheteurs");

                            HashMap<String,String > hashMap = new HashMap<> ();
                            hashMap.put ("id",user_id);

                            hashMap.put ("Email",email);

                            reference.child (user_id).setValue (hashMap).addOnCompleteListener (new OnCompleteListener<Void> () {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(RegisterActivity.this, "Votre boutique est pret!", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(RegisterActivity.this , MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
                                    startActivity(intent);
                                    finish ();
                                }
                            });




                        }


                        else{
                            create.setVisibility(View.VISIBLE);
                            loadingprogress.setVisibility(View.INVISIBLE);
                            Toast.makeText(RegisterActivity.this, "Sorry we have a problem", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

}
