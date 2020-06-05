package com.eduvision.version2.corona;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.eduvision.version2.corona.Sellers.Main2Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirstActivity extends AppCompatActivity {
    Button Next;
    RelativeLayout relativeLayout;
    AnimationDrawable animationDrawable1;
    FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart ();

        firebaseUser = FirebaseAuth.getInstance ().getCurrentUser ();

        //redirect if user is not null

        if (firebaseUser != null) {
            startActivity (new Intent (FirstActivity.this, SplashActivity.class));
            finish ();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_first);
        this.getWindow ().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Next = findViewById (R.id.next);
        Next.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (getApplicationContext (),SlideActivity.class));
                finish ();
            }
        });

        relativeLayout = (RelativeLayout)findViewById (R.id.myLayout1);
        animationDrawable1 = (AnimationDrawable) relativeLayout.getBackground ();
        animationDrawable1.setEnterFadeDuration (5000);
        animationDrawable1.setExitFadeDuration (5000);
        animationDrawable1.start ();


    }
}
