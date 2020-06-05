package com.eduvision.version2.corona.Sellers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvision.version2.corona.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.layout.simple_spinner_dropdown_item;

public class Publish3activity extends AppCompatActivity {
    private FirebaseAuth auth;
    static int REQUESCODE = 1;
    CheckBox Men,Women;
    StorageReference mreference;
    StorageTask uploadTask;
    DatabaseReference reference;
    Uri image_uri;
    Button Choose,Upload;
    EditText Name,Price;
    String MyUrl="";
    ProgressBar progressBar1;
    ImageView Img1;
    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_publish3activity);

        progressBar1 = findViewById (R.id.prog0);
        Img1 = findViewById (R.id.img0);
        Choose = findViewById (R.id.choose0);
        Upload = findViewById (R.id.upload0);
        Name = findViewById (R.id.name0);
        Price = findViewById (R.id.price0);
        Men =findViewById (R.id.men0);
        Women = findViewById (R.id.women0);
        spinner = findViewById (R.id.spinnerO);


        List<String> categories = new ArrayList<> ();

        categories.add (0,"Category");
        categories.add ("Bags");
        categories.add ("Caps & Hats");
        categories.add ("Gifts");
        categories.add ("Jewellery");
        categories.add ("Scarves");
        categories.add ("Socks");
        categories.add ("Sunglasses");
        categories.add ("Ties");
        categories.add ("Wallets");
        categories.add ("Watches");




        ArrayAdapter<String> adapter ;
        adapter = new ArrayAdapter<> (this,R.layout.color_spinner,categories);

        adapter.setDropDownViewResource (simple_spinner_dropdown_item);

        spinner.setAdapter (adapter);
        spinner.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition (position).equals ("Category")){
                }else{

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//  TODO Auto-generated method stu
            }
        });

        reference= FirebaseDatabase.getInstance ().getReference ("Stores");
        mreference = FirebaseStorage.getInstance ().getReference ().child ("Accessoires");
        Choose.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                pickFromGallery();
            }
        });

        Upload.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String str_Name = Name.getText().toString();
                String str_Price = Price.getText().toString();

                if (TextUtils.isEmpty(str_Name) || TextUtils.isEmpty(str_Price)){
                    Toast.makeText(Publish3activity.this, "All fields are required!", Toast.LENGTH_SHORT).show();

                }else
                {
                    UploadImage ();
                    UserPost ();

                }
            }
        });

        Women.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (Women.isChecked ()){
                    Men.setEnabled (false);
                }else{
                    Men.setEnabled (true);
                }
            }
        });

        Men.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (Men.isChecked ()){
                    Women.setEnabled (false);
                }else{
                    Women.setEnabled (true);
                }
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult (requestCode, resultCode, data);

        if (requestCode == REQUESCODE && resultCode == RESULT_OK && data!= null ){

            image_uri = data.getData ();
            Img1.setImageURI (image_uri);


        }else
        {
            Toast.makeText (this,"We have a problem",Toast.LENGTH_SHORT).show ();

        }


    }

    private void pickFromGallery() {
        Intent galleryintent = new Intent (Intent.ACTION_GET_CONTENT);
        galleryintent.setType ("image/*");
        startActivityForResult (galleryintent,REQUESCODE);
    }

    private void UploadImage(){

        final StorageReference Image = mreference.child (image_uri.getLastPathSegment ());
        Image.putFile (image_uri).addOnSuccessListener (new OnSuccessListener<UploadTask.TaskSnapshot> () {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Image.getDownloadUrl ().addOnSuccessListener (new OnSuccessListener<Uri> () {
                    @Override
                    public void onSuccess(Uri uri) {
                        String uploadId = reference.push ().getKey ();
                        String ID = FirebaseAuth.getInstance ().getCurrentUser ().getUid ();



                        HashMap<String, String> hashMap = new HashMap<> ();
                        hashMap.put ("PostId",uploadId);
                        hashMap.put ("publisher",ID);
                        hashMap.put ("Title",Name.getText ().toString ());
                        hashMap.put ("Price",Price.getText ().toString ());
                        hashMap.put ("Img",String.valueOf (uri));

                        if (Women.isChecked ()){
                            reference.child ("WOMEN").child ("Women_Gift").child (uploadId).setValue (hashMap).addOnCompleteListener (new OnCompleteListener<Void> () {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar1.setVisibility (View.INVISIBLE);
                                    Toast.makeText (getApplicationContext (), "Post succesfull..!", Toast.LENGTH_SHORT).show ();

                                    startActivity (new Intent (getApplicationContext (), Main2Activity.class));
                                    finish ();
                                }
                            });

                        }
                        else if (Men.isChecked ()){
                            reference.child ("MEN").child ("Men_Gift").child (uploadId).setValue (hashMap).addOnCompleteListener (new OnCompleteListener<Void> () {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar1.setVisibility (View.INVISIBLE);
                                    Toast.makeText (getApplicationContext (), "Post succesfull..!", Toast.LENGTH_SHORT).show ();

                                    startActivity (new Intent (getApplicationContext (), Main2Activity.class));
                                    finish ();
                                }
                            });

                        }




                    }
                });
            }
        }).addOnProgressListener (new OnProgressListener<UploadTask.TaskSnapshot> () {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred ()/taskSnapshot.getTotalByteCount ());
                progressBar1.setProgress ((int) progress);
                Upload.setVisibility (View.INVISIBLE);

                Choose.setOnClickListener (new View.OnClickListener () {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText (getApplicationContext (),"in progress...",Toast.LENGTH_LONG).show ();


                    }
                });

            }
        });
    }

    private void UserPost() {
        final StorageReference Image = mreference.child (image_uri.getLastPathSegment ());
        Image.putFile (image_uri).addOnSuccessListener (new OnSuccessListener<UploadTask.TaskSnapshot> () {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Image.getDownloadUrl ().addOnSuccessListener (new OnSuccessListener<Uri> () {
                    @Override
                    public void onSuccess(Uri uri) {
                        DatabaseReference databaseReference;
                        databaseReference = FirebaseDatabase.getInstance ().getReference ("User_Posts");
                        String uploadId = databaseReference.push ().getKey ();
                        String ID = FirebaseAuth.getInstance ().getCurrentUser ().getUid ();


                        HashMap<String, String> hashMap = new HashMap<> ();
                        hashMap.put ("ID",uploadId);
                        hashMap.put ("publisher",ID);
                        hashMap.put ("Title",Name.getText ().toString ());
                        hashMap.put ("Price",Price.getText ().toString ());
                        hashMap.put ("Img",String.valueOf (uri));



                        databaseReference.child (ID).child (uploadId).setValue (hashMap).addOnCompleteListener (new OnCompleteListener<Void> () {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressBar1.setVisibility (View.INVISIBLE);

                            }
                        });
                    }
                });
            }
        }).addOnProgressListener (new OnProgressListener<UploadTask.TaskSnapshot> () {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred ()/taskSnapshot.getTotalByteCount ());
                progressBar1.setProgress ((int) progress);
                Upload.setVisibility (View.INVISIBLE);

                Choose.setOnClickListener (new View.OnClickListener () {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText (getApplicationContext (),"in progress...",Toast.LENGTH_LONG).show ();


                    }
                });

            }
        });
    }

}
