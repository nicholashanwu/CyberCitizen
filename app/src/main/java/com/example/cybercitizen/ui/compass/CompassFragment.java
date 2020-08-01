package com.example.cybercitizen.ui.compass;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.cybercitizen.DatabaseHelper;
import com.example.cybercitizen.R;
import com.example.cybercitizen.ui.achievements.AchievementAdapter;

public class CompassFragment extends Fragment {

	private AchievementAdapter mAdapter;
	private DatabaseHelper myDb;
	private CardView mCvAdditionalResources;
	private CardView mCvGoogle;

	private View.OnClickListener additionalResourcesClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			additionalResourcesButtonClicked();
		}
	};

	private View.OnClickListener googleClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			googleButtonClicked();
		}
	};


	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {

		View root = inflater.inflate(R.layout.fragment_compass, container, false);
		return root;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		mCvAdditionalResources = view.findViewById(R.id.cvAdditionalResources);
		mCvGoogle = view.findViewById(R.id.cvGoogle);


		mCvAdditionalResources.setOnClickListener(additionalResourcesClickListener);
		mCvGoogle.setOnClickListener(googleClickListener);

		myDb = DatabaseHelper.getInstance(getActivity().getApplicationContext());

	}

	private void additionalResourcesButtonClicked() {
		Navigation.findNavController(getView()).navigate(R.id.action_navigation_compass_to_navigation_resource);
	}

	private void googleButtonClicked() {
		Navigation.findNavController(getView()).navigate(R.id.action_navigation_compass_to_navigation_google);
	}


}
