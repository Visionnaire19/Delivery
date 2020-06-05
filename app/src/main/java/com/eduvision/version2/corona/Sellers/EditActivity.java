package com.eduvision.version2.corona.Sellers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.eduvision.version2.corona.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class EditActivity extends AppCompatActivity {

    ImageView profile;
    EditText NewNum,NewLocation;
    static int REQUESCODE = 1;
    Uri image_uri1;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    Button Upload1;
    ProgressBar sprogressBar1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.edit_activity);
        profile = findViewById (R.id.scover_item);
        NewNum = findViewById (R.id.sNumber);
        Upload1 = findViewById (R.id.Save);
        sprogressBar1 = findViewById (R.id.sprogressBar);
        databaseReference =FirebaseDatabase.getInstance ().getReference ("Users").child ("Vendeurs");

        profile.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                pickFromGallery();
            }
        });

        Upload1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                UploadImage ();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult (requestCode, resultCode, data);

        if (requestCode == REQUESCODE && resultCode == RESULT_OK && data!= null ){

            image_uri1 = data.getData ();
            profile.setImageURI (image_uri1);


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

        final StorageReference Image = storageReference.child (image_uri1.getLastPathSegment ());
        Image.putFile (image_uri1).addOnSuccessListener (new OnSuccessListener<UploadTask.TaskSnapshot> () {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Image.getDownloadUrl ().addOnSuccessListener (new OnSuccessListener<Uri> () {
                    @Override
                    public void onSuccess(Uri uri) {
                        String uploadId = databaseReference.push ().getKey ();
                        String ID = FirebaseAuth.getInstance ().getCurrentUser ().getUid ();


                        HashMap<String, String> hashMap = new HashMap<> ();
                        hashMap.put ("Profile_img",image_uri1.toString ());
                        databaseReference.child (ID).setValue (hashMap);

                    }
                });
            }
        }).addOnProgressListener (new OnProgressListener<UploadTask.TaskSnapshot> () {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred ()/taskSnapshot.getTotalByteCount ());
                sprogressBar1.setProgress ((int) progress);
                Upload1.setVisibility (View.INVISIBLE);


            }
        });
    }


}
