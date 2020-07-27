package com.example.translate.ui.compass;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.translate.R;

public class ResourceFragment extends Fragment {

    private CardView mCvYoutube;
    private CardView mCvLinkedin;
    private CardView mCvKhan;
    private CardView mCvAcsc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resource, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCvYoutube = view.findViewById(R.id.cvYoutube);
        mCvLinkedin = view.findViewById(R.id.cvLinkedin);
        mCvKhan = view.findViewById(R.id.cvKhan);
        mCvAcsc = view.findViewById(R.id.cvKhan);

        mCvYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_navigation_resource_to_resourceYoutubeFragment);
            }
        });

        mCvLinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "hi", Toast.LENGTH_LONG);
            }
        });



    }
}