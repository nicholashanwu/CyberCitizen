package com.example.translate.ui.achievements;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.translate.DatabaseHelper;
import com.example.translate.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class BadgeFragment extends Fragment {

	private DatabaseHelper myDb;

	private TextView mTxtNextBadge;
	private int achievedCount;
	private int toNextBadgeCount;
	private ImageView mBb1;
	private ImageView mBb2;
	private ImageView mBb3;
	private ImageView mBb4;
	private ImageView mBb5;
	private ImageView mBb6;
	private ImageView mBb7;
	private ImageView mBb8;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_badge, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);


		mTxtNextBadge = view.findViewById(R.id.txtNextBadge);
		mBb1 = view.findViewById(R.id.bb1);
		mBb2 = view.findViewById(R.id.bb2);
		mBb3 = view.findViewById(R.id.bb3);
		mBb4 = view.findViewById(R.id.bb4);
		mBb5 = view.findViewById(R.id.bb5);
		mBb6 = view.findViewById(R.id.bb6);
		mBb7 = view.findViewById(R.id.bb7);
		mBb8 = view.findViewById(R.id.bb8);


		myDb = DatabaseHelper.getInstance(getActivity().getApplicationContext());

		achievedCount = myDb.getAchieved();

		if(achievedCount % 5 != 0) {
			toNextBadgeCount = 5 - achievedCount % 5;
			if(toNextBadgeCount == 1) {
				mTxtNextBadge.setText("Next Badge in " + toNextBadgeCount + " achievement");
			} else {
				mTxtNextBadge.setText("Next Badge in " + toNextBadgeCount + " achievements");
			}
		} else {
			toNextBadgeCount = 5;
			mTxtNextBadge.setText("Next Badge in " + toNextBadgeCount + " achievements");
		}

		if(achievedCount >= 5) {
			mBb1.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
		}

		if(achievedCount >= 10) {
			mBb2.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
		}

		if(achievedCount >= 15) {
			mBb3.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
		}

		if(achievedCount >= 20) {
			mBb4.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
		}

		if(achievedCount >= 25) {
			mBb5.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
		}

		if(achievedCount >= 30) {
			mBb6.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
		}

		if(achievedCount >= 35) {
			mBb7.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
		}

		if(achievedCount >= 40) {
			mBb8.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
		}








	}
}