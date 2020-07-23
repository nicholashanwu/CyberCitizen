package com.example.translate.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.translate.DatabaseHelper;
import com.example.translate.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class LearningFragment extends Fragment {

	private FloatingActionButton mFabSave;
	private FloatingActionButton mFabAnswer;

	private ProgressBar mProgressBar;

	private ExtendedFloatingActionButton mBtnBack;

	private TextView mTxtPhrase;
	private TextView mTxtDefinition;
	private TextView mTxtProgress;
	private TextView mTxtLevelTitle;
	private TextView mTxtSavedMessage;
	private TextView mTxtAnswerMessage;
	private TextView mTxtUnsavedMessage;

	private Cursor res;

	private String learningType;

	private DatabaseHelper myDb;

	private int progressInt = 0;

	public LearningFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_learning, container, false);

		return view;
	}

	@Override
	public void onConfigurationChanged(@NonNull Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		myDb = new DatabaseHelper(getContext());

		mFabAnswer = view.findViewById(R.id.fabAnswer);
		mFabSave = view.findViewById(R.id.fabSave);
		FloatingActionButton mFabDone = view.findViewById(R.id.fabDone);
		mProgressBar = view.findViewById(R.id.progressBar);
		mTxtProgress = view.findViewById(R.id.txtProgress);
		mTxtPhrase = view.findViewById(R.id.txtPhrase);
		mTxtDefinition = view.findViewById(R.id.txtDefinition);
		mTxtLevelTitle = view.findViewById(R.id.txtLevelTitle);
		mTxtSavedMessage = view.findViewById(R.id.txtSavedMessage);
		mTxtAnswerMessage = view.findViewById(R.id.txtAnswerMessage);
		mTxtUnsavedMessage = view.findViewById(R.id.txtUnsavedMessage);
		mBtnBack = view.findViewById(R.id.btnBack);

		learningType = getArguments().getString("learningType");


		res = getData(learningType);
		//res = myDb.getCategory(learningType);

		System.out.println("hi" + res.getCount());
		setParameters(res);
		mTxtLevelTitle.setText(learningType);
		//setTitle(learningType);
		mTxtAnswerMessage.setVisibility(View.GONE);
		mTxtSavedMessage.setVisibility(View.GONE);
		mTxtUnsavedMessage.setVisibility(View.GONE);


		mFabDone.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				res.move(1);
				mFabAnswer.setImageResource(R.drawable.outline_visibility_off_white_48);

				if (res.getPosition() < res.getCount()) {
					mTxtPhrase.setText("?");
					mTxtDefinition.setText(res.getString(2));

					hideMessages();

					advanceProgressBar();

					showSavedStatus();

				} else {

					fillProgressBar();

					showMessage("You're Finished!");

					checkAchievement();

					if (res != null) {
						res.close();
						myDb.close();
					}

					returnToFragment();

					mProgressBar.setProgress(0, true);
				}
			}
		});

		mFabSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {


				if (res.getString(5).equals("1")) {
					markAsUnsaved();
				} else {
					markAsSaved();
				}

			}
		});

		mFabAnswer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mTxtPhrase.getText().equals("?")) {
					showAnswer();
				} else {
					hideAnswer();
				}
			}
		});


		mBtnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showBackConfirmation("Are you sure you want to exit?", learningType);
			}
		});

	}

	public void checkAchievement() {
		if (learningType.equals("numbers")) {
			if (myDb.progressAchievement("Number Novice")) {
				showAchievement("Number Novice");
				if (myDb.progressAchievement("Lingo Learner")) {
					showAchievement("Lingo Learner");
				}
			}
		} else if (learningType.equals("essentials")) {
			if (myDb.progressAchievement("Great Greeter")) {
				showAchievement("Great Greeter");
				if (myDb.progressAchievement("Lingo Learner")) {
					showAchievement("Lingo Learner");
				}
			}
		} else if (learningType.equals("food")) {
			if (myDb.progressAchievement("Food Fight")) {
				showAchievement("Food Fight");
				if (myDb.progressAchievement("Lingo Learner")) {
					showAchievement("Lingo Learner");
				}
			}
		} else if (learningType.equals("help")) {
			if (myDb.progressAchievement("Helping Hand")) {
				showAchievement("Helping Hand");
				if (myDb.progressAchievement("Lingo Learner")) {
					showAchievement("Lingo Learner");
				}
			}
		}


	}

	public void setParameters(Cursor res) {
		mProgressBar.setProgress(0, true);
		mTxtProgress.setText("1/" + res.getCount());
		res.moveToFirst();


		mTxtPhrase.setText("?");
		mTxtDefinition.setText(res.getString(2));

		if (res.getString(5).equals("1")) {
			mFabSave.setImageResource(R.drawable.baseline_bookmark_white_48);
		} else {
			mFabSave.setImageResource(R.drawable.outline_bookmark_border_white_48);
		}
	}

	public void setTitle(String learningType) {
		if (learningType.equals("numbers")) {
			mTxtLevelTitle.setText("Numbers");
		} else if (learningType.equals("essentials")) {
			mTxtLevelTitle.setText("Essentials");
		} else if (learningType.equals("food")) {
			mTxtLevelTitle.setText("Food");
		} else if (learningType.equals("help")) {
			mTxtLevelTitle.setText("Help");
		} else if (learningType.equals("saved")) {
			mTxtLevelTitle.setText("Saved Words");
		} else if (learningType.equals("learned")) {
			mTxtLevelTitle.setText("Mastered Words");
		} else if (learningType.equals("custom")) {
			mTxtLevelTitle.setText("Your Words");
		}
	}

	public Cursor getData(String learningType) {
		if (learningType.equals("saved")) {
			res = myDb.getSaved();
			System.out.println(res.getCount());
		} else if (learningType.equals("learned")) {
			res = myDb.getLearned();
		} else {
			res = myDb.getCategory(learningType);
		}

		return res;
	}

	private void showMessage(String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.Green));
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_alert_dialog_learning, null);
		TextView txtTitle = view.findViewById(R.id.title);
		ImageButton imageButton = view.findViewById(R.id.image);

		imageButton.setImageResource(R.mipmap.over_75);

		builder.setPositiveButton("AWESOME", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
			}
		});

		txtTitle.setText(title);
		builder.setView(view);
		builder.show();
	}

	private void showAchievement(String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.Yellow));
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_alert_dialog_achievement, null);
		TextView txtTitle = view.findViewById(R.id.title);
		ImageButton imageButton = view.findViewById(R.id.image);

		imageButton.setImageResource(R.mipmap.over_95);

		builder.setPositiveButton("AWESOME", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
			}
		});

		txtTitle.setText(title);
		builder.setView(view);
		builder.show();
	}

	private void showBackConfirmation(String title, final String learningType) {
		AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.Green));
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_alert_dialog_learning, null);
		TextView txtTitle = view.findViewById(R.id.title);
		ImageButton imageButton = view.findViewById(R.id.image);

		imageButton.setImageResource(R.mipmap.over_30);

		builder.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {

			}

		});
		builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				if (learningType.equals("saved") || learningType.equals("learned")) {
					Navigation.findNavController(getView()).navigate(R.id.action_navigation_learning_to_navigation_profile);
				} else {
					Navigation.findNavController(getView()).navigate(R.id.action_navigation_learning_to_navigation_home);
				}
				if (res != null) {
					res.close();
					myDb.close();
				}
			}
		});

		txtTitle.setText(title);
		builder.setView(view);
		builder.show();
	}

	private void hideMessages() {
		if(mTxtSavedMessage.getVisibility() == View.VISIBLE){
			YoYo.with(Techniques.FadeOutDown).duration(300).playOn(mTxtSavedMessage);
			mTxtSavedMessage.setVisibility(View.GONE);
		}

		if(mTxtUnsavedMessage.getVisibility() == View.VISIBLE) {
			YoYo.with(Techniques.FadeOutDown).duration(300).playOn(mTxtUnsavedMessage);
			mTxtUnsavedMessage.setVisibility(View.GONE);

		}

		if(mTxtAnswerMessage.getVisibility() == View.VISIBLE) {
			YoYo.with(Techniques.FadeOutDown).duration(300).playOn(mTxtAnswerMessage);
			mTxtAnswerMessage.setVisibility(View.GONE);

		}
	}

	private void advanceProgressBar() {
		progressInt = 100 * res.getPosition() / res.getCount();
		mTxtProgress.setText((res.getPosition() + 1) + "/" + res.getCount());
		mProgressBar.setProgress(progressInt, true);
	}

	private void showSavedStatus() {
		if (res.getString(5).equals("1")) {
			mFabSave.setImageResource(R.drawable.baseline_bookmark_white_48);
		} else {
			mFabSave.setImageResource(R.drawable.outline_bookmark_border_white_48);
		}
	}

	private void fillProgressBar() {
		mTxtProgress.setText("");
		mProgressBar.setProgress(100, true);
	}

	private void returnToFragment() {
		if (learningType.equals("saved") || learningType.equals("learned")) {
			Navigation.findNavController(getView()).navigate(R.id.action_navigation_learning_to_navigation_profile);
		} else {
			Navigation.findNavController(getView()).navigate(R.id.action_navigation_learning_to_navigation_home);
		}
	}

	private void markAsUnsaved() {
		myDb.updateSave(res.getString(1), false);

		mTxtSavedMessage.setVisibility(View.GONE);
		YoYo.with(Techniques.FadeOutDown).duration(300).playOn(mTxtSavedMessage);

		mTxtUnsavedMessage.setVisibility(View.VISIBLE);
		YoYo.with(Techniques.FadeInUp).duration(300).playOn(mTxtUnsavedMessage);

		mFabSave.setImageResource(R.drawable.outline_bookmark_border_white_48);
	}

	private void markAsSaved() {
		myDb.updateSave(res.getString(1), true);

		mTxtUnsavedMessage.setVisibility(View.GONE);
		YoYo.with(Techniques.FadeOutDown).duration(300).playOn(mTxtUnsavedMessage);

		mTxtSavedMessage.setVisibility(View.VISIBLE);
		YoYo.with(Techniques.FadeInUp).duration(300).playOn(mTxtSavedMessage);

		mFabSave.setImageResource(R.drawable.baseline_bookmark_white_48);
	}

	private void hideAnswer() {
		mTxtPhrase.setText("?");

		mTxtAnswerMessage.setVisibility(View.GONE);
		YoYo.with(Techniques.FadeOutDown).duration(300).playOn(mTxtAnswerMessage);

		mFabAnswer.setImageResource(R.drawable.outline_visibility_off_white_48);

	}

	private void showAnswer() {
		mTxtPhrase.setText(res.getString(1));

		mTxtAnswerMessage.setVisibility(View.VISIBLE);
		YoYo.with(Techniques.FadeInUp).duration(300).playOn(mTxtAnswerMessage);
		mFabAnswer.setImageResource(R.drawable.baseline_visibility_white_48);

	}

}
