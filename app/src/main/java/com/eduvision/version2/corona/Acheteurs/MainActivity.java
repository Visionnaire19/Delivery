package com.eduvision.version2.corona.Acheteurs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import com.eduvision.version2.corona.HomeFragment;
import com.eduvision.version2.corona.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_spinner_dropdown_item;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

private DrawerLayout drawerLayout;
 Toolbar toolbar;
 private  ActionBarDrawerToggle toggle;
 Spinner spinner;
 ImageView imageView ;
 TextView number,Email;
    String firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number = findViewById (R.id.number);
        Email = findViewById (R.id.email);
        spinner = findViewById (R.id.spinner1);
        List<String> categories = new ArrayList<> ();

        categories.add (0,"HOMMES");
        categories.add ("FEMMES");

        ArrayAdapter<String> adapter ;
       adapter = new ArrayAdapter<> (this,R.layout.color_spinner,categories);

        adapter.setDropDownViewResource (simple_spinner_dropdown_item);

        spinner.setAdapter (adapter);
        spinner.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition (position).equals ("HOMMES")){
                }else{

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//  TODO Auto-generated method stu
            }
        });


        drawerLayout = findViewById (R.id.drawer_layout);
           toggle = new ActionBarDrawerToggle (this,drawerLayout,R.string.Open,R.string.Close);
           drawerLayout.addDrawerListener (toggle);
           toggle.syncState ();
        NavigationView navigationView = findViewById (R.id.nav_view);
        navigationView.setNavigationItemSelectedListener (this);

        if (savedInstanceState==null){
            getSupportFragmentManager ().beginTransaction ().replace (R.id.fragment_container,new HomeFragment ()).commit ();

            navigationView.setCheckedItem (R.id.nav_home);
        }



    }

    private void setSupportActionBar(Toolbar toolbar) {
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId ()){
            case R.id.nav_home:
                getSupportFragmentManager ().beginTransaction ().replace (R.id.fragment_container,new HomeFragment ()).commit ();
                break;


        }
        drawerLayout.closeDrawer (GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen (GravityCompat.START)){
            drawerLayout.closeDrawer (GravityCompat.START);
        }else{
            super.onBackPressed ();

        }
    }
}
