package com.eduvision.version2.corona.Sellers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eduvision.version2.corona.Adapter.PostAdapter;
import com.eduvision.version2.corona.Post;
import com.eduvision.version2.corona.R;
import com.eduvision.version2.corona.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicMarkableReference;

import static com.google.firebase.remoteconfig.FirebaseRemoteConfig.TAG;


public class ProfileFragment extends Fragment {
ImageView profil;
TextView name,number,Local,Edit;
String firebaseUser;
    private RecyclerView recyclerView;
    public List<Post> mPost;
    PostAdapter postAdapter;
    public List<UserInformation> userInformations;

    public ProfileFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_profile, container , false);
        Edit =view.findViewById (R.id.edit);
        profil = view.findViewById (R.id.cover_item);
        name = view.findViewById (R.id.Nom);
        number = view.findViewById (R.id.sign_up_numero);
        Local  = view.findViewById (R.id.location);
        recyclerView = view.findViewById (R.id.recycler_view2);
        mPost = new ArrayList<> ();
        userInformations = new ArrayList<> ();
        readPosts ();

        Edit.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (getContext (),EditActivity.class));
            }
        });

        firebaseUser= FirebaseAuth.getInstance ().getCurrentUser ().getUid ();
        DatabaseReference reference;
        FirebaseDatabase firebaseDatabase;
        firebaseDatabase = FirebaseDatabase.getInstance ();

        reference = firebaseDatabase.getReference ("Users").child ("Vendeurs");
        reference.keepSynced (true);
        Query query = reference.orderByChild ("id").equalTo (firebaseUser);

        query.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren ()){
                    String numero = ""+ds.child ("Number").getValue ();
                    String nom = ""+ds.child ("Store").getValue ();
                    String local = ""+ds.child ("Location").getValue ();

                    Local.setText (local);
                    name.setText (nom);
                    number.setText ("+226 "+numero);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    private void Upload() {

    }

    private void readPosts(){

        recyclerView.setHasFixedSize (true);
        recyclerView.setLayoutManager (new GridLayoutManager (getContext (),2));
        String ID = FirebaseAuth.getInstance ().getCurrentUser ().getUid ();

        DatabaseReference reference = FirebaseDatabase.getInstance ().getReference ().child ("User_Posts").child (ID);

        reference.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mPost.clear ();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);
                    mPost.add (post);
                }
                PostAdapter postAdapter = new PostAdapter (getContext (),mPost);
                recyclerView.setAdapter (postAdapter);
                postAdapter.notifyDataSetChanged ();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

}
