package com.eduvision.version2.corona.Sellers;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.eduvision.version2.corona.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Main3Activity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView1;
    Fragment selectFragment1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main3);
        bottomNavigationView1 = findViewById(R.id.bottom_navigation1);
        bottomNavigationView1.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container1,new BigSellerFrag ()).commit();

    }
    private  BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()){
                case R.id.nav_store:
                    selectFragment1 = new BigSellerFrag ();
                    break;

                case R.id.nav_profile:
                    selectFragment1 = new ProfileFragment ();
                    break;

            }
            if (selectFragment1!= null){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container1 , selectFragment1).commit();
            }
            return true;
        }


    };

}
