package com.eduvision.version2.corona.Sellers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.eduvision.version2.corona.R;

public class BigSellerFrag extends Fragment {
    Button publish1;

    public BigSellerFrag(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bigseller, container , false);
        publish1 = view.findViewById (R.id.Publish);

        publish1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (getContext (), PublishActivity.class));

            }
        });
        return view;
    }
}
