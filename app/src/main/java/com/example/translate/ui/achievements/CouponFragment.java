package com.example.translate.ui.achievements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.translate.DatabaseHelper;
import com.example.translate.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class CouponFragment extends Fragment {

	private ExtendedFloatingActionButton mFabTokens;
	private DatabaseHelper myDb;
	private int tokenCount;
	private RoundCornerProgressBar mRpbAmazon;
	private RoundCornerProgressBar mRpbKfc;
	private RoundCornerProgressBar mRpbJb;
	private RoundCornerProgressBar mRpbMyer;
	private RoundCornerProgressBar mRpbSteam;
	private RoundCornerProgressBar mRpbBunnings;
	private TextView mTxtAmazon;
	private TextView mTxtKfc;
	private TextView mTxtJb;
	private TextView mTxtMyer;
	private TextView mTxtSteam;
	private TextView mTxtBunnings;
	private final int TOKEN_AMAZON = 2000;
	private final int TOKEN_KFC = 1000;
	private final int TOKEN_JB = 1500;
	private final int TOKEN_MYER = 2000;
	private final int TOKEN_STEAM = 1500;
	private final int TOKEN_BUNNINGS = 2000;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_coupon, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mFabTokens = view.findViewById(R.id.fabTokens);

		mRpbAmazon = view.findViewById(R.id.rpbAmazon);
		mRpbKfc = view.findViewById(R.id.rpbKfc);
		mRpbJb = view.findViewById(R.id.rpbJb);
		mRpbMyer = view.findViewById(R.id.rpbMyer);
		mRpbSteam = view.findViewById(R.id.rpbSteam);
		mRpbBunnings = view.findViewById(R.id.rpbBunnings);

		mTxtAmazon = view.findViewById(R.id.txtAmazon);
		mTxtKfc = view.findViewById(R.id.txtKfc);
		mTxtJb = view.findViewById(R.id.txtJb);
		mTxtMyer = view.findViewById(R.id.txtMyer);
		mTxtSteam = view.findViewById(R.id.txtSteam);
		mTxtBunnings = view.findViewById(R.id.txtBunnings);

		myDb = DatabaseHelper.getInstance(getContext().getApplicationContext());

		tokenCount = myDb.getTokens();


		mFabTokens.setText(String.valueOf(tokenCount));

		tokenCount = 1000;

		if (tokenCount >= TOKEN_AMAZON) {
			mTxtAmazon.setText("TAP TO UNLOCK");
		} else {
			mTxtAmazon.setText((TOKEN_AMAZON - tokenCount) / 200 + " ACHIEVEMENTS LEFT TO UNLOCK");
		}


		if (tokenCount >= TOKEN_KFC) {
			mTxtKfc.setText("TAP TO UNLOCK");
		} else {
			mTxtKfc.setText((TOKEN_KFC - tokenCount) / 200 + " ACHIEVEMENTS LEFT TO UNLOCK");
		}


		if (tokenCount >= TOKEN_JB) {
			mTxtJb.setText("TAP TO UNLOCK");
		} else {
			mTxtJb.setText((TOKEN_JB - tokenCount) / 200 + " ACHIEVEMENTS LEFT TO UNLOCK");
		}


		if (tokenCount >= TOKEN_MYER) {
			mTxtMyer.setText("TAP TO UNLOCK");
		} else {
			mTxtMyer.setText((TOKEN_MYER - tokenCount) / 200 + " ACHIEVEMENTS LEFT TO UNLOCK");
		}


		if (tokenCount >= TOKEN_STEAM) {
			mTxtSteam.setText("TAP TO UNLOCK");
		} else {
			mTxtSteam.setText((TOKEN_STEAM - tokenCount) / 200 + " ACHIEVEMENTS LEFT TO UNLOCK");
		}

		if (tokenCount >= TOKEN_BUNNINGS) {
			mTxtBunnings.setText("TAP TO UNLOCK");
		} else {
			mTxtBunnings.setText((TOKEN_BUNNINGS - tokenCount) / 200 + " ACHIEVEMENTS LEFT TO UNLOCK");
		}


		mRpbAmazon.setProgress(100 * tokenCount / TOKEN_AMAZON);
		mRpbKfc.setProgress(100 * tokenCount / TOKEN_KFC);
		mRpbJb.setProgress(100 * tokenCount / TOKEN_JB);
		mRpbMyer.setProgress(100 * tokenCount / TOKEN_MYER);
		mRpbSteam.setProgress(100 * tokenCount / TOKEN_STEAM);
		mRpbBunnings.setProgress(100 * tokenCount / TOKEN_BUNNINGS);

		mRpbAmazon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("hi");
			}
		});
	}
}
