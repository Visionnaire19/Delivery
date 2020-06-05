package com.eduvision.version2.corona;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import com.eduvision.version2.corona.Acheteurs.MainActivity;
import com.eduvision.version2.corona.Acheteurs.SeconActitvity;
import com.eduvision.version2.corona.Sellers.ProfileFragment;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.HashMap;
import java.util.concurrent.BlockingDeque;

import static com.eduvision.version2.corona.R.string.default_web_client_id;

public class sign_up extends AppCompatActivity {

    Button login, register;
    FirebaseUser firebaseUser;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    static final int GOOGLE_SIGN = 123;
    private Button btnLogin;
    private Intent homeactivity;
    VideoView videoView;

    @Override
    protected void onStart() {
        super.onStart ();

        firebaseUser = FirebaseAuth.getInstance ().getCurrentUser ();

        //redirect if user is not null

        if (firebaseUser != null) {
            startActivity (new Intent (sign_up.this, MainActivity.class));
            finish ();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        videoView = findViewById (R.id.video);

        String path = "android.resource://com.eduvision.version2.corona/"+R.raw.bring;
        Uri u = Uri.parse (path);
        videoView.setVideoURI (u);
        videoView.start ();
        videoView.setOnPreparedListener (new MediaPlayer.OnPreparedListener () {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping (true);
            }
        });


        register = findViewById (R.id.sign_up);
        btnLogin = (Button) findViewById (R.id.log_in);
        homeactivity = new Intent (this, MainActivity.class);

        register.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (getApplicationContext (), SeconActitvity.class));
            Toast.makeText (sign_up.this,"Please wait..",Toast.LENGTH_SHORT).show ();
            }
        });

        btnLogin.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (getApplicationContext (),RegisterActivity.class));
            }
        });

    }





    @Override
    protected void onResume() {
        videoView.resume ();
        super.onResume ();
    }

    @Override
    protected void onDestroy() {
        videoView.stopPlayback ();
        super.onDestroy ();
    }

    @Override
    protected void onPause() {
        videoView.suspend ();
        super.onPause ();
    }
}

