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
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
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

	private View.OnClickListener redeemAmazonClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			mRpbAmazonButtonClicked();
		}
	};

	private View.OnClickListener redeemKfcClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			mRpbKfcButtonClicked();
		}
	};

	private View.OnClickListener redeemJbClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			mRpbJbButtonClicked();
		}
	};

	private View.OnClickListener redeemMyerClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			mRpbMyerButtonClicked();
		}
	};

	private View.OnClickListener redeemSteamClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			mRpbSteamButtonClicked();
		}
	};

	private View.OnClickListener redeemBunningsClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			mRpbBunningsButtonClicked();
		}
	};

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



		refreshProgress();

	}



	private void mRpbAmazonButtonClicked() {
		myDb.spendTokens(TOKEN_AMAZON);
		mFabTokens.setText(String.valueOf(myDb.getTokens()));
		myDb.updateScore("Amazon");
		myDb.progressCoupons();
		YoYo.with(Techniques.Tada).duration(1000).playOn(mRpbAmazon);
		YoYo.with(Techniques.Tada).duration(1000).playOn(mTxtAmazon);
		mRpbAmazon.setOnClickListener(null);
		refreshProgress();
	}

	private void mRpbKfcButtonClicked() {
		myDb.spendTokens(TOKEN_KFC);
		mFabTokens.setText(String.valueOf(myDb.getTokens()));
		myDb.updateScore("KFC");
		myDb.progressCoupons();
		YoYo.with(Techniques.Tada).duration(1000).playOn(mRpbKfc);
		YoYo.with(Techniques.Tada).duration(1000).playOn(mTxtKfc);
		mRpbKfc.setOnClickListener(null);
		refreshProgress();
	}

	private void mRpbJbButtonClicked() {
		myDb.spendTokens(TOKEN_JB);
		mFabTokens.setText(String.valueOf(myDb.getTokens()));
		myDb.updateScore("Jb");
		myDb.progressCoupons();
		YoYo.with(Techniques.Tada).duration(1000).playOn(mRpbJb);
		YoYo.with(Techniques.Tada).duration(1000).playOn(mTxtJb);
		mRpbJb.setOnClickListener(null);
		refreshProgress();
	}

	private void mRpbMyerButtonClicked() {
		myDb.spendTokens(TOKEN_MYER);
		mFabTokens.setText(String.valueOf(myDb.getTokens()));
		myDb.updateScore("Myer");
		myDb.progressCoupons();
		YoYo.with(Techniques.Tada).duration(1000).playOn(mRpbMyer);
		YoYo.with(Techniques.Tada).duration(1000).playOn(mTxtMyer);
		mRpbMyer.setOnClickListener(null);
		refreshProgress();
	}

	private void mRpbSteamButtonClicked() {
		myDb.spendTokens(TOKEN_STEAM);
		mFabTokens.setText(String.valueOf(myDb.getTokens()));
		myDb.updateScore("Steam");
		myDb.progressCoupons();
		YoYo.with(Techniques.Tada).duration(1000).playOn(mRpbSteam);
		YoYo.with(Techniques.Tada).duration(1000).playOn(mTxtSteam);
		mRpbSteam.setOnClickListener(null);
		refreshProgress();
	}

	private void mRpbBunningsButtonClicked() {
		myDb.spendTokens(TOKEN_BUNNINGS);
		mFabTokens.setText(String.valueOf(myDb.getTokens()));
		myDb.updateScore("Bunnings");
		myDb.progressCoupons();
		YoYo.with(Techniques.Tada).duration(1000).playOn(mRpbBunnings);
		YoYo.with(Techniques.Tada).duration(1000).playOn(mTxtBunnings);
		mRpbBunnings.setOnClickListener(null);
		refreshProgress();
	}

	private void refreshProgress() {

		tokenCount = myDb.getTokens();



		if(myDb.getScore("Amazon") != 0) {
			mTxtAmazon.setText("PRIMETIME");
			mRpbAmazon.setProgress(0
			);
		} else if (tokenCount >= TOKEN_AMAZON) {
			mTxtAmazon.setText("TAP TO UNLOCK");
			mRpbAmazon.setOnClickListener(redeemAmazonClickListener);
			mRpbAmazon.setProgress(100 * tokenCount / TOKEN_AMAZON);
		} else {

			mTxtAmazon.setText(TOKEN_AMAZON - tokenCount + " TOKENS LEFT TO UNLOCK");
			mRpbAmazon.setProgress(100 * tokenCount / TOKEN_AMAZON);
			mRpbAmazon.setOnClickListener(null);
		}

		if(myDb.getScore("KFC") != 0) {
			mTxtKfc.setText("FINGERLICKINGOOD");
			mRpbKfc.setProgress(0);
		} else if (tokenCount >= TOKEN_KFC) {
			mTxtKfc.setText("TAP TO UNLOCK");
			mRpbKfc.setOnClickListener(redeemKfcClickListener);
			mRpbKfc.setProgress(100 * tokenCount / TOKEN_KFC);
		} else {
			mTxtKfc.setText(TOKEN_KFC - tokenCount + " TOKENS LEFT TO UNLOCK");
			mRpbKfc.setProgress(100 * tokenCount / TOKEN_KFC);
			mRpbKfc.setOnClickListener(null);
		}

		if(myDb.getScore("Jb") != 0) {
			mTxtJb.setText("SALESALESALESALE");
			mRpbJb.setProgress(0);
		} else 	if (tokenCount >= TOKEN_JB) {
			mTxtJb.setText("TAP TO UNLOCK");
			mRpbJb.setOnClickListener(redeemJbClickListener);
			mRpbJb.setProgress(100 * tokenCount / TOKEN_JB);
		} else {
			mTxtJb.setText(TOKEN_JB - tokenCount + " TOKENS LEFT TO UNLOCK");
			mRpbJb.setProgress(100 * tokenCount / TOKEN_JB);
			mRpbJb.setOnClickListener(null);
		}

		if(myDb.getScore("Myer") != 0) {
			mTxtMyer.setText("ISMYSTORE");
			mRpbMyer.setProgress(0);
		} else if (tokenCount >= TOKEN_MYER) {
			mTxtMyer.setText("TAP TO UNLOCK");
			mRpbMyer.setOnClickListener(redeemMyerClickListener);
			mRpbMyer.setProgress(100 * tokenCount / TOKEN_MYER);
		} else {
			mTxtMyer.setText(TOKEN_MYER - tokenCount + " TOKENS LEFT TO UNLOCK");
			mRpbMyer.setProgress(100 * tokenCount / TOKEN_MYER);
			mRpbMyer.setOnClickListener(null);
		}


		if(myDb.getScore("Steam") != 0) {
			mTxtSteam.setText("PCMASTERRACE");
			mRpbSteam.setProgress(0);
		} else if (tokenCount >= TOKEN_STEAM) {
			mTxtSteam.setText("TAP TO UNLOCK");
			mRpbSteam.setOnClickListener(redeemSteamClickListener);
			mRpbSteam.setProgress(100 * tokenCount / TOKEN_STEAM);
		} else {
			mTxtSteam.setText(TOKEN_STEAM - tokenCount + " TOKENS LEFT TO UNLOCK");
			mRpbSteam.setProgress(100 * tokenCount / TOKEN_STEAM);
			mRpbSteam.setOnClickListener(null);
		}

		if(myDb.getScore("Bunnings") != 0) {
			mTxtBunnings.setText("LOWESTPRICES...");
			mRpbBunnings.setProgress(0);
		} else if (tokenCount >= TOKEN_BUNNINGS) {
			mTxtBunnings.setText("TAP TO UNLOCK");
			mRpbBunnings.setOnClickListener(redeemBunningsClickListener);
			mRpbBunnings.setProgress(100 * tokenCount / TOKEN_BUNNINGS);
		} else {
			mTxtBunnings.setText(TOKEN_BUNNINGS - tokenCount + " TOKENS LEFT TO UNLOCK");
			mRpbBunnings.setProgress(100 * tokenCount / TOKEN_BUNNINGS);
			mRpbBunnings.setOnClickListener(null);
		}


	}
}
