package com.example.translate.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.alespero.expandablecardview.ExpandableCardView;
import com.example.translate.DatabaseHelper;
import com.example.translate.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class HomeFragment extends Fragment {

	private ExpandableCardView mEcvOne;
	private ExpandableCardView mEcvTwo;
	private ExpandableCardView mEcvThree;
	private ExpandableCardView mEcvFour;

	private ExtendedFloatingActionButton mBtnLockedTwo;
	private ExtendedFloatingActionButton mBtnLockedThree;
	private ExtendedFloatingActionButton mBtnLockedFour;

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

	private DatabaseHelper myDb;


	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {

		View root = inflater.inflate(R.layout.fragment_home, container, false);

		myDb = DatabaseHelper.getInstance(getContext().getApplicationContext());

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

		mEcvOne = view.findViewById(R.id.ecvOne);
		mEcvTwo = view.findViewById(R.id.ecvTwo);
		mEcvThree = view.findViewById(R.id.ecvThree);
		mEcvFour = view.findViewById(R.id.ecvFour);

		mBtnLockedTwo = view.findViewById(R.id.btnLockedTwo);
		mBtnLockedThree = view.findViewById(R.id.btnLockedThree);
		mBtnLockedFour = view.findViewById(R.id.btnLockedFour);

		mEcvTwo.setVisibility(View.GONE);
		mEcvThree.setVisibility(View.GONE);
		mEcvFour.setVisibility(View.GONE);

		mEcvTwo.setVisibility(View.VISIBLE);
		mEcvThree.setVisibility(View.VISIBLE);
		mEcvFour.setVisibility(View.VISIBLE);

		if(myDb.checkAchievementStatus("Certified Cyber Novice")){
			mEcvTwo.setVisibility(View.VISIBLE);
			mBtnLockedTwo.setVisibility(View.GONE);
		}

		if(myDb.checkAchievementStatus("Certified Cyber Skilled")){
			mEcvThree.setVisibility(View.VISIBLE);
			mBtnLockedThree.setVisibility(View.GONE);
		}

		if(myDb.checkAchievementStatus("Certified Anti-Social Engineer")){
			mEcvFour.setVisibility(View.VISIBLE);
			mBtnLockedFour.setVisibility(View.GONE);
		}

		mBtnLockedTwo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showMessage("Complete Level One to unlock this section!");
			}
		});

		mBtnLockedThree.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showMessage("Complete Level Two to unlock this section!");
			}
		});

		mBtnLockedFour.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showMessage("Complete Level Three to unlock this section!");
			}
		});

		// check complete achievements
		// disable all cardviews that do not have the right achievement to unlock it

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
				Bundle bundle = new Bundle();
				bundle.putInt("storyId", 0);
				Navigation.findNavController(getView()).navigate(R.id.action_navigation_home_to_storyFragment, bundle);

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

	private void showMessage(String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.Green));
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_alert_dialog_learning, null);
		TextView txtTitle = view.findViewById(R.id.title);
		ImageButton imageButton = view.findViewById(R.id.image);

		imageButton.setImageResource(R.mipmap.over_50);

		builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
			}
		});

		txtTitle.setText(title);
		builder.setView(view);
		builder.show();
	}


}
