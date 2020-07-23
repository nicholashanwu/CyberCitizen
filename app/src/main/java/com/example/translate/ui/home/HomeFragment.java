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
	private CardView mCvOneThree;

	private CardView mCvTwoOne;
	private CardView mCvTwoTwo;
	private CardView mCvTwoThree;
	private CardView mCvTwoFour;

	private CardView mCvThreeOne;
	private CardView mCvThreeTwo;
	private CardView mCvThreeThree;
	private CardView mCvThreeFour;

	private CardView mCvFourOne;
	private CardView mCvFourTwo;
	private CardView mCvFourThree;
	private CardView mCvFourFour;


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
		mCvOneThree = view.findViewById(R.id.cvOneThree);

		mCvTwoOne = view.findViewById(R.id.cvTwoOne);
		mCvTwoTwo = view.findViewById(R.id.cvTwoTwo);
		mCvTwoThree = view.findViewById(R.id.cvTwoThree);
		mCvTwoFour = view.findViewById(R.id.cvTwoFour);

		mCvThreeOne = view.findViewById(R.id.cvThreeOne);
		mCvThreeTwo = view.findViewById(R.id.cvThreeTwo);
		mCvThreeThree = view.findViewById(R.id.cvThreeThree);
		mCvThreeFour = view.findViewById(R.id.cvThreeFour);

		mCvFourOne = view.findViewById(R.id.cvFourOne);
		mCvFourTwo = view.findViewById(R.id.cvFourTwo);
		mCvFourThree = view.findViewById(R.id.cvFourThree);
		mCvFourFour = view.findViewById(R.id.cvFourFour);

		// ***************************************** LEVEL ONE ***************************************** //
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

		mCvOneThree.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("testingType", "What is Cyber?");
				Navigation.findNavController(getView()).navigate(R.id.action_navigation_home_to_navigation_test, bundle);
			}
		});

		// ***************************************** LEVEL TWO ***************************************** //

		mCvTwoOne.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("learningType", "Cyber 101");
				Navigation.findNavController(getView()).navigate(R.id.action_navigation_home_to_contentScrollerFragment, bundle);
			}
		});

		mCvTwoTwo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("learningType", "Cyber 101");
				Navigation.findNavController(getView()).navigate(R.id.action_navigation_home_to_navigation_learning, bundle);
			}
		});

		mCvTwoThree.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("testingType", "Cyber 101");
				Navigation.findNavController(getView()).navigate(R.id.action_navigation_home_to_navigation_test, bundle);
			}
		});

		mCvTwoFour.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//navigate to story scenario
			}
		});

		// ***************************************** LEVEL THREE ***************************************** //

		mCvThreeOne.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("learningType", "Social Engineering");
				Navigation.findNavController(getView()).navigate(R.id.action_navigation_home_to_contentScrollerFragment, bundle);
			}
		});

		mCvThreeTwo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("learningType", "Social Engineering");
				Navigation.findNavController(getView()).navigate(R.id.action_navigation_home_to_navigation_learning, bundle);
			}
		});

		mCvThreeThree.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("testingType", "Social Engineering");
				Navigation.findNavController(getView()).navigate(R.id.action_navigation_home_to_navigation_test, bundle);
			}
		});

		mCvThreeFour.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//navigate to story scenario
			}
		});

		// ***************************************** LEVEL FOUR ***************************************** //

		mCvFourOne.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("learningType", "Protecting Yourself");
				Navigation.findNavController(getView()).navigate(R.id.action_navigation_home_to_contentScrollerFragment, bundle);
			}
		});

		mCvFourTwo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("learningType", "Protecting Yourself");
				Navigation.findNavController(getView()).navigate(R.id.action_navigation_home_to_navigation_learning, bundle);
			}
		});

		mCvFourThree.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("testingType", "Protecting Yourself");
				Navigation.findNavController(getView()).navigate(R.id.action_navigation_home_to_navigation_test, bundle);
			}
		});

		mCvFourFour.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//navigate to story scenario
			}
		});

	}



}
