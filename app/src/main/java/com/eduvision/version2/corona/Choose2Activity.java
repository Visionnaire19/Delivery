package com.eduvision.version2.corona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eduvision.version2.corona.Sellers.Main2Activity;
import com.eduvision.version2.corona.Sellers.PublishActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class Choose2Activity extends AppCompatActivity {

    private EditText UserEmail,UserPassword, UserLocation,UserStore,UserNumb;
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
    protected void onStart() {
        super.onStart ();

        firebaseUser = FirebaseAuth.getInstance ().getCurrentUser ();

        //redirect if user is not null

        if (firebaseUser != null) {
            startActivity (new Intent (Choose2Activity.this, Main2Activity.class));
            finish ();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_choose2);
        auth = FirebaseAuth.getInstance();
        UserEmail = (EditText)findViewById(R.id.Email);
        UserPassword = (EditText)findViewById(R.id.Password);
        UserStore = findViewById (R.id.Locat);
        UserNumb = findViewById (R.id.Number);
        UserLocation = findViewById (R.id.Location1);

        create = (Button) findViewById(R.id.Create);
        loadingprogress = (ProgressBar)findViewById(R.id.progressBar);




        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create.setVisibility(View.INVISIBLE);
                loadingprogress.setVisibility(View.VISIBLE);
                String str_email = UserEmail.getText().toString();
                String str_password = UserPassword.getText().toString();
                String str_numero = UserNumb.getText().toString();
                String str_store = UserStore.getText().toString();
                String str_local = UserLocation.getText ().toString ();

                Intent i = new Intent (getApplicationContext (), PublishActivity.class);
                i.putExtra ("Store_name",UserStore.getText ().toString ());


                if ( TextUtils.isEmpty(str_email)||TextUtils.isEmpty(str_password)|| TextUtils.isEmpty(str_numero)|| TextUtils.isEmpty(str_store)|| TextUtils.isEmpty (str_local)){
                    Toast.makeText(Choose2Activity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                    create.setVisibility(View.VISIBLE);
                    loadingprogress.setVisibility(View.INVISIBLE);

                }else if (str_password.length()< 5){
                    Toast.makeText(Choose2Activity.this, "Password must have 6 characters", Toast.LENGTH_SHORT).show();
                }
               else if (firebaseUser != null){
                    Toast.makeText(Choose2Activity.this, "User already exists", Toast.LENGTH_SHORT).show();


                }
                else{
                    register(str_email,str_password,str_store,str_numero,str_local);

                }
            }
        });

    }

    private void register( final String email , String password , final String store, final String numero,final String Local){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(Choose2Activity.this, new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String user_id = firebaseUser.getUid ();


                            reference = FirebaseDatabase.getInstance ().getReference ("Users").child ("Vendeurs");

                                HashMap<String,String > hashMap = new HashMap<> ();
                                hashMap.put ("id",user_id);
                                hashMap.put ("Number",numero);
                                hashMap.put ("Email",email);
                                hashMap.put ("Store",store);
                                hashMap.put ("Location",Local);




                            reference.child (user_id).setValue (hashMap).addOnCompleteListener (new OnCompleteListener<Void> () {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(Choose2Activity.this, "Votre boutique est prete!", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(Choose2Activity.this , Main2Activity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
                                        startActivity(intent);
                                        finish ();
                                    }
                                });
                        }
                        else{
                            create.setVisibility(View.VISIBLE);
                            loadingprogress.setVisibility(View.INVISIBLE);
                            Toast.makeText(Choose2Activity.this, "Change Your Email!" , Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

}
