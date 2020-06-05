package com.eduvision.version2.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.eduvision.version2.corona.Acheteurs.MainActivity;
import com.eduvision.version2.corona.Sellers.Main2Activity;

public class ChooseActivity extends AppCompatActivity {
        ImageView Client,Vendeurs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_choose);

        Client = findViewById (R.id.client);
        Vendeurs= findViewById (R.id.sellers);

        Client.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (ChooseActivity.this, MainActivity.class));

            }
        });

        Vendeurs.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (ChooseActivity.this, Choose2Activity.class));
            }
        });

    }
}
