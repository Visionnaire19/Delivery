package com.eduvision.version2.corona.Sellers;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.eduvision.version2.corona.Acheteurs.MainActivity;
import com.eduvision.version2.corona.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class Publish2Activity extends AppCompatActivity {

    AppCompatEditText editTextTitle;
    ProgressBar progressBar1;
    ImageView Img1;
    private FirebaseAuth auth;
    static int REQUESCODE = 1;
    CheckBox Men,Women;
    StorageReference mreference;
    StorageTask uploadTask;
    DatabaseReference reference;
    Uri image_uri;
    Button Choose,Upload;
    EditText Name,Price,Size;
    String MyUrl="";
    FirebaseUser firebaseUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.fragment_publish2);

        progressBar1 = findViewById (R.id.prog1);
        Img1 = findViewById (R.id.img1);
        Choose = findViewById (R.id.choose1);
        Upload = findViewById (R.id.upload1);
        Name = findViewById (R.id.name1);
        Price = findViewById (R.id.price1);
        Size = findViewById (R.id.size1);
        Men = findViewById (R.id.mens);
        Women = findViewById (R.id.womens);



        reference= FirebaseDatabase.getInstance ().getReference("Stores");
        mreference = FirebaseStorage.getInstance ().getReference ("SHOES");
        Choose.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                pickFromGallery();
            }
        });
        firebaseUser= FirebaseAuth.getInstance ().getCurrentUser ();

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


        Upload.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String str_Name = Name.getText().toString();
                String str_Price = Price.getText().toString();

                if (TextUtils.isEmpty(str_Name) || TextUtils.isEmpty(str_Price)){
                    Toast.makeText(Publish2Activity.this, "All fields are required!", Toast.LENGTH_SHORT).show();

                }else
                {
                    UserPost ();
                    UploadImage ();
                }
            }
        });
    }

    private String getFileExtension(Uri  uri){
        ContentResolver contentResolver = getContentResolver ();
        MimeTypeMap  map = MimeTypeMap.getSingleton ();
        return map.getExtensionFromMimeType (contentResolver.getType (uri));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult (requestCode, resultCode, data);

        if (requestCode == REQUESCODE && resultCode == RESULT_OK && data!= null ){

            image_uri = data.getData ();
            Img1.setImageURI (image_uri);

        }else {
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

                        HashMap<String, String> hashMap = new HashMap<> ();
                        hashMap.put ("PostId",uploadId);
                        hashMap.put ("Title",Name.getText ().toString ());
                        hashMap.put ("Price",Price.getText ().toString ());
                        hashMap.put ("Size",Size.getText ().toString ());
                        hashMap.put ("Publisher", String.valueOf (firebaseUser));
                        hashMap.put ("Img",String.valueOf (uri));

                        if (Men.isChecked ()){
                            reference.child ("MEN").child ("Men_Shoes").child (uploadId).setValue (hashMap).addOnCompleteListener (new OnCompleteListener<Void> () {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar1.setVisibility (View.INVISIBLE);
                                    Toast.makeText (getApplicationContext (), "Post succesfull..!", Toast.LENGTH_SHORT).show ();

                                    startActivity (new Intent (getApplicationContext (), Main2Activity.class));
                                }
                            });

                        }

                        else if (Women.isChecked ()){
                            reference.child ("WOMEN").child ("Women_Shoes").child (uploadId).setValue (hashMap).addOnCompleteListener (new OnCompleteListener<Void> () {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar1.setVisibility (View.INVISIBLE);
                                    Toast.makeText (getApplicationContext (), "Post succesfull..!", Toast.LENGTH_SHORT).show ();

                                    startActivity (new Intent (getApplicationContext (), Main2Activity.class));
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
                        hashMap.put ("Title", Name.getText ().toString ());
                        hashMap.put ("Price",Price.getText ().toString ());
                        hashMap.put ("Img",String.valueOf (uri));
                        hashMap.put ("Size",Size.getText ().toString ());


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
