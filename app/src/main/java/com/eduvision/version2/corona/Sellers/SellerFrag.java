package com.eduvision.version2.corona.Sellers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.eduvision.version2.corona.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SellerFrag extends Fragment {
    TextView name;
    ImageView shoess,Clothes,Gift;
    String firebaseUser;

    public SellerFrag(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller, container , false);
        shoess = view.findViewById (R.id.shoes1);
        Clothes = view.findViewById (R.id.hauts);
        name = view.findViewById (R.id.store_name);
        Gift = view.findViewById (R.id.gift);

        firebaseUser= FirebaseAuth.getInstance ().getCurrentUser ().getUid ();
        DatabaseReference reference;
        FirebaseDatabase firebaseDatabase;
        firebaseDatabase = FirebaseDatabase.getInstance ();
        reference = firebaseDatabase.getReference ("Users").child ("Vendeurs");
        Query query = reference.orderByChild ("id").equalTo (firebaseUser);
        query.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren ()){

                    String store = ""+ds.child ("Store").getValue ();
                    name.setText (store);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        Clothes.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (getContext (), PublishActivity.class));

            }
        });

        shoess.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (getContext (),Publish2Activity.class));
            }
        });

        Gift.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (getContext (),Publish3activity.class));
            }
        });
        return view;
    }
}
