package com.eduvision.version2.corona;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.eduvision.version2.corona.R;


public class
SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_splash);
        new Handler ().postDelayed (new Runnable () {
            @Override
            public void run() {

                if (!isConnected ()){
                    new AlertDialog.Builder (SplashActivity.this)
                            .setTitle ("Internet alert")
                            .setMessage ("Please check your connection")
                            .setPositiveButton ("Cancel", new DialogInterface.OnClickListener () {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish ();
                                }
                            }).show ();
                }
                else{
                    Intent intent1 = new Intent (SplashActivity.this,Choose2Activity.class);  
                    startActivity (intent1);
                    finish ();

                }

            }
        },SPLASH_TIME);

        if (!isConnected ()){
            new AlertDialog.Builder (this)
                    .setTitle ("Internet alert")
                    .setMessage ("Please check your connection")
                    .setPositiveButton ("Cancel", new DialogInterface.OnClickListener () {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish ();
                        }
                    }).show ();
        }
        else{

        }


    }
    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService (Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo ();
        return networkInfo != null && networkInfo.isConnected ();
    }
}
