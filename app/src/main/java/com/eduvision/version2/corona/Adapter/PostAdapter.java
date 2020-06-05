package com.eduvision.version2.corona.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eduvision.version2.corona.Post;
import com.eduvision.version2.corona.R;
import com.eduvision.version2.corona.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapter extends  RecyclerView.Adapter<PostAdapter.ViewHolder>  {

    public Context context;
    public List<Post> mPost;

    private FirebaseUser firebaseUser;

    public PostAdapter(Context context, List<Post> mPost) {
        this.context = context;
        this.mPost = mPost;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from (context).inflate (R.layout.post,viewGroup,false);

        return new  PostAdapter.ViewHolder (view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Post post = mPost.get (i);

        String IDO = FirebaseAuth.getInstance ().getCurrentUser ().getUid ();
        DatabaseReference reference;
        FirebaseDatabase firebaseDatabase;
        firebaseDatabase = FirebaseDatabase.getInstance ();
        reference = firebaseDatabase.getReference ("User_Posts").child (IDO);
        Query query = reference.orderByChild ("ID").equalTo (post.getID ());

        query.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren ()){
                    String cover= "" + ds.child ("Img").getValue ();
                    String title = ""+ds.child ("Title").getValue ();
                    String prix = "" + ds.child ("Price").getValue ();

                    Picasso.get ().load (cover).into (holder.imageView);
                    holder.titre.setText (title);
                    holder.price.setText (prix + " CFA");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    @Override
    public int getItemCount() {
        return mPost.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView titre;
        public TextView price;




        public ViewHolder(@NonNull View itemView) {
            super (itemView);
            imageView = itemView.findViewById (R.id.cove_Item);
            titre = itemView.findViewById (R.id.title_item);
            price = itemView.findViewById (R.id.price_item);


        }

    }







}
