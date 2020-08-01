package com.example.cybercitizen.ui.learn;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.cybercitizen.DatabaseHelper;
import com.example.cybercitizen.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FlashcardsFragment extends Fragment {

	private FloatingActionButton mFabSave;
	private FloatingActionButton mFabAnswer;

	private ProgressBar mProgressBar;

	private TextView mTxtPhrase;
	private TextView mTxtDefinition;
	private TextView mTxtProgress;
	private TextView mTxtLevelTitle;
	private TextView mTxtSavedMessage;
	private TextView mTxtAnswerMessage;
	private TextView mTxtUnsavedMessage;

	private CardView mCardView;

	private Cursor res;

	private String learningType;

	private DatabaseHelper myDb;

	private int progressInt = 0;

	public FlashcardsFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
			@Override
			public void handleOnBackPressed() {
				res.close();
				Navigation.findNavController(getView()).popBackStack();
			}
		};
		requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
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

		myDb = DatabaseHelper.getInstance(getContext().getApplicationContext());

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
		mCardView = view.findViewById(R.id.cvPhrase);

		learningType = getArguments().getString("learningType");


		res = getData(learningType);

		setParameters(res);
		mTxtLevelTitle.setText(learningType);
		mTxtAnswerMessage.setVisibility(View.GONE);
		mTxtSavedMessage.setVisibility(View.GONE);
		mTxtUnsavedMessage.setVisibility(View.GONE);

		mFabDone.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				mFabAnswer.setImageResource(R.drawable.outline_visibility_off_white_48);

				if (res.move(1)) {

					hideMessages();
					advanceProgressBar();
					showSavedStatus();
					YoYo.with(Techniques.SlideOutLeft).duration(300).playOn(mCardView);
					YoYo.with(Techniques.SlideInRight).delay(200).duration(300).playOn(mCardView);

					new CountDownTimer(250, 250) {
						public void onTick(long millisUntilFinished) {
						}
						public void onFinish() {
							mTxtPhrase.setText("?");
							mTxtDefinition.setText(res.getString(2));
						}
					}.start();

				} else {

					fillProgressBar();
					if (res != null) {
						res.close();
					}
					mProgressBar.setProgress(0, true);
					checkAchievement();
					showMessage("You're Finished!");


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



	}

	public void checkAchievement() {
		if (learningType.equals("What is Cyber?")) {
			if (myDb.progressAchievement("Cyber Novice II")) {
				showAchievement("Cyber Novice II");
				if (myDb.progressAchievement("Cyber Scholar")) {
					showAchievement("Cyber Scholar");
				}
				if (myDb.progressAchievement("Certified Cyber Novice")){
					showAchievement("Certified Cyber Novice");
				}
			}
		} else if (learningType.equals("Cyber 101")) {
			if (myDb.progressAchievement("Cyber Skilled II")) {
				showAchievement("Cyber Skilled II");
				if (myDb.progressAchievement("Cyber Scholar")) {
					showAchievement("Cyber Scholar");
				}
				if (myDb.progressAchievement("Certified Cyber Skilled")){
					showAchievement("Certified Cyber Skilled");
				}
			}
		} else if (learningType.equals("Social Engineering")) {
			if (myDb.progressAchievement("Anti-Social Engineer II")) {
				showAchievement("Anti-Social Engineer II");
				if (myDb.progressAchievement("Cyber Scholar")) {
					showAchievement("Cyber Scholar");
				}
				if (myDb.progressAchievement("Certified Anti-Social Engineer")){
					showAchievement("Certified Anti-Social Engineer");
				}
			}
		} else if (learningType.equals("Protecting Yourself")) {
			if (myDb.progressAchievement("Cyber Defender II")) {
				showAchievement("Cyber Defender II");
				if (myDb.progressAchievement("Cyber Scholar")) {
					showAchievement("Cyber Scholar");
				}
				if (myDb.progressAchievement("Certified Cyber Defender")){
					showAchievement("Certified Cyber Defender");
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
				Navigation.findNavController(getView()).popBackStack();
			}
		});

		txtTitle.setText(title);
		builder.setView(view);
		AlertDialog dialog = builder.show();
		dialog.setCanceledOnTouchOutside(false);
	}

	private void showAchievement(String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.Yellow));
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_alert_dialog_achievement, null);
		TextView txtTitle = view.findViewById(R.id.title);
		ImageButton imageButton = view.findViewById(R.id.image);
		TextView mTxtAchievementDescription = view.findViewById(R.id.txtAchievementDescription);

		String description = myDb.returnAchievementDescription(title);
		mTxtAchievementDescription.setText(description);

		imageButton.setImageResource(R.mipmap.over_95);

		builder.setPositiveButton("AWESOME", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {

			}

		});

		txtTitle.setText(title);
		builder.setView(view);
		AlertDialog dialog = builder.show();
		dialog.setCanceledOnTouchOutside(false);
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
