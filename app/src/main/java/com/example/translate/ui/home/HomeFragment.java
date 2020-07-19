package com.example.translate.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.translate.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.makeramen.roundedimageview.RoundedImageView;

public class HomeFragment extends Fragment {

	private CardView mCvOneOne;
	private CardView mCvOneTwo;
	private Button mBtnStartHelp;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {

		View root = inflater.inflate(R.layout.fragment_home, container, false);
		return root;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mCvOneOne = view.findViewById(R.id.cvOneOne);
		mCvOneTwo = view.findViewById(R.id.cvOneTwo);
		mBtnStartHelp = view.findViewById(R.id.btnStartHelp);

		mCvOneOne.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("learningType", "What is Cyber?");
				Navigation.findNavController(getView()).navigate(R.id.action_navigation_home_to_contentScrollerFragment, bundle);
			}
		});

		mCvOneTwo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("learningType", "What is Cyber?");
				Navigation.findNavController(getView()).navigate(R.id.action_navigation_home_to_navigation_learning, bundle);
			}
		});

		mBtnStartHelp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("learningType", "food");
				Navigation.findNavController(getView()).navigate(R.id.action_navigation_home_to_navigation_learning, bundle);
			}
		});


	}



}
