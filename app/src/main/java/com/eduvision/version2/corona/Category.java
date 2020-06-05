package com.eduvision.version2.corona;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.eduvision.version2.corona.Sellers.Main2Activity;
import com.eduvision.version2.corona.Sellers.Main3Activity;
import com.eduvision.version2.corona.Sellers.SellerFrag;

public class Category extends AppCompatActivity {
    Button P1,P2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.categori_layout);
        P1 = findViewById (R.id.p1);
        P2 = findViewById (R.id.p2);

        P1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                startActivity (new Intent (Category.this, Main2Activity.class));
                finish ();
            }
        });

        P2.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (Category.this, Main3Activity.class));
                finish ();
            }
        });
    }
}

