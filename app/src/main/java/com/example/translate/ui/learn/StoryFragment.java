package com.example.translate.ui.learn;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.translate.DatabaseHelper;
import com.example.translate.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

public class StoryFragment extends Fragment {

	private TextView mTxtContent;
	private TextView mTxtLevelTitle;
	private TextView mTxtMessage;
	private TextView mTxtEarnedCoins;
	private RadioGroup mRbGroup;
	private RadioButton mRbAnswerOne;
	private RadioButton mRbAnswerTwo;
	private RadioButton mRbAnswerThree;
	private RadioButton mRbAnswerFour;
	private FloatingActionButton mFabSubmit;

	private DatabaseHelper myDb;
	private Cursor res;

	private boolean answered;
	private boolean endReached = false;
	private boolean pass = false;

	private int storyId;
	private int nextPageId = 0;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_story, container, false);
		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		myDb = DatabaseHelper.getInstance(getContext().getApplicationContext());

		mTxtContent = view.findViewById(R.id.txtStoryContent);
		mTxtLevelTitle = view.findViewById(R.id.txtLevelTitle);
		mTxtMessage = view.findViewById(R.id.txtMessage);
		mTxtEarnedCoins = view.findViewById(R.id.txtEarnedCoins);
		mRbGroup = view.findViewById(R.id.rbGroup);
		mRbAnswerOne = view.findViewById(R.id.rbAnswerOne);
		mRbAnswerTwo = view.findViewById(R.id.rbAnswerTwo);
		mRbAnswerThree = view.findViewById(R.id.rbAnswerThree);
		mRbAnswerFour = view.findViewById(R.id.rbAnswerFour);
		mFabSubmit = view.findViewById(R.id.fabSubmitAnswer);

		storyId = getArguments().getInt("storyId");

		res = getData(storyId);
		setParameters(res);
		setTitle(storyId);

		showNextQuestion(res);

		mFabSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				if (!answered) {
					if (mRbAnswerOne.isChecked() || mRbAnswerTwo.isChecked() || mRbAnswerThree.isChecked() || mRbAnswerFour.isChecked()) {
						checkAnswer(res);
					} else if (endReached) {
						finishTest();
					} else if (pass) {
						checkAnswer(res);
//						showNextQuestion(res);
						pass = false;
					} else {
						mTxtMessage.setText("Please choose an answer");
						YoYo.with(Techniques.FadeInDown).duration(300).playOn(mTxtMessage);
						YoYo.with(Techniques.FadeOutUp).duration(300).delay(700).playOn(mTxtMessage);
					}
				} else {
					showNextQuestion(res);
					for (int i = 0; i < mRbGroup.getChildCount(); i++) {
						mRbGroup.getChildAt(i).setEnabled(true);
					}
				}
			}
		});


	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private void showNextQuestion(Cursor res) {

		mRbGroup.clearCheck();

		mRbAnswerOne.setVisibility(View.VISIBLE);
		mRbAnswerTwo.setVisibility(View.VISIBLE);
		mRbAnswerThree.setVisibility(View.VISIBLE);
		mRbAnswerFour.setVisibility(View.VISIBLE);

		if(res != null){
			mTxtContent.setText(res.getString(3));

			if (res.getString(4).equals("")) {
				mRbAnswerOne.setVisibility(View.GONE);
			}
			if (res.getString(5).equals("")) {
				mRbAnswerTwo.setVisibility(View.GONE);
			}
			if (res.getString(6).equals("")) {
				mRbAnswerThree.setVisibility(View.GONE);
			}
			if (res.getString(7).equals("")) {
				mRbAnswerFour.setVisibility(View.GONE);
			}
		}


		mRbAnswerOne.setText(res.getString(4));
		mRbAnswerTwo.setText(res.getString(5));
		mRbAnswerThree.setText(res.getString(6));
		mRbAnswerFour.setText(res.getString(7));
		mRbAnswerOne.setTextColor(Color.parseColor("#444444"));
		mRbAnswerTwo.setTextColor(Color.parseColor("#444444"));
		mRbAnswerThree.setTextColor(Color.parseColor("#444444"));
		mRbAnswerFour.setTextColor(Color.parseColor("#444444"));

		answered = false;

	}

	private void checkAnswer(Cursor res) {
		answered = true;
		RadioButton rbSelected = getView().findViewById(mRbGroup.getCheckedRadioButtonId());
		int answerNum = mRbGroup.indexOfChild(rbSelected) + 1;

		if (storyId == 0) {                // ------------------------ STORY ONE------------------------
			if (nextPageId == 0) {
				if (answerNum == 1) {            // yes
					nextPageId = 2;
				} else if (answerNum == 2) {    // no
					nextPageId = 1;
				}
			} else if (nextPageId == 1) {
				if (answerNum == 2) {
					nextPageId = 3;
				} else {
					nextPageId = 4;
				}
			} else if (nextPageId == 2) {
				if (answerNum == 1) {
					nextPageId = 5;
				} else {
					nextPageId = 1;
				}
			} else if (nextPageId == 3) {
				if (answerNum == 1) {
					nextPageId = 7;
					endReached = true;
				} else {
					nextPageId = 6;
					endReached = true;
				}
			} else if (nextPageId == 4) {
				if (answerNum == 1) {
					nextPageId = 7;
					endReached = true;
				} else if (answerNum == 2) {
					nextPageId = 5;
				} else {
					nextPageId = 6;
					endReached = true;
				}
			} else if (nextPageId == 5) {
				if (answerNum == 1) {
					nextPageId = 8;
					endReached = true;
				} else {
					nextPageId = 9;
					endReached = true;
				}
			} else {
				// do nothing
			}

		} else if (storyId == 1) {
			System.out.println("HIHFIUSIUFISUFH");
			if (nextPageId == 0) {
				if (answerNum == 1 || answerNum == 4) {
					nextPageId = 1;
					pass = true;
					addCoins();
				} else {
					nextPageId = 2;
					pass = true;
				}
			} else if (nextPageId == 1 || nextPageId == 2) {
				nextPageId = 3;
			} else if (nextPageId == 3) {
				if (answerNum == 1) {
					nextPageId = 5;
					addCoins();
					pass = true;
				} else {
					nextPageId = 4;
					pass = true;
				}
			} else if (nextPageId == 4 || nextPageId == 5) {
				nextPageId = 6;

			} else if (nextPageId == 6) {
				if (answerNum == 1) {
					nextPageId = 7;
					pass = true;
				} else {
					nextPageId = 8;
					pass = true;
					addCoins();
				}
			} else if (nextPageId == 7 || nextPageId == 8) {
				nextPageId = 9;
			} else if (nextPageId == 9) {
				if (answerNum == 1) {
					nextPageId = 10;
					endReached = true;
					addCoins();
				} else {
					nextPageId = 11;
					endReached = true;
				}
			} else {

			}
		}


		res.moveToFirst();
		while (res.moveToNext()) {
			if (res.getInt(1) == storyId && res.getInt(2) == nextPageId) {
				showNextQuestion(res);
			}

		}

	}

	private void addCoins() {
		myDb.addTokens(50);
		mTxtEarnedCoins.setVisibility(View.VISIBLE);
		YoYo.with(Techniques.BounceInUp).duration(200).playOn(mTxtEarnedCoins);
		YoYo.with(Techniques.FadeOutUp).delay(400).duration(200).playOn(mTxtEarnedCoins);
	}

	private void finishTest() {

		if (storyId == 0) {
			if (myDb.progressAchievement("Cyber Skilled IV")) {
				showAchievement("Cyber Skilled IV");
			}
		} else if (storyId == 1) {
			if (myDb.progressAchievement("Anti-Social Engineer IV")) {
				showAchievement("Anti-Social Engineer IV");
			}
		} else if (storyId == 2) {
			if (myDb.progressAchievement("Cyber Defender IV")) {
				showAchievement("Cyber Defender IV");
			}
		} else {

		}

		showMessage("You completed the story!");
		if (res != null) {
			res.close();
		}

	}

	public Cursor getData(int storyId) {
		Cursor res;
		if (storyId == 0) {
			res = myDb.getStory(0);
		} else if (storyId == 1) {
			// load story 1
			res = myDb.getStory(1);

		} else if (storyId == 2) {
			// load story 2
			res = myDb.getStory(2);

		} else {
			// load story 3
			res = myDb.getStory(3);

		}

		return res;

	}

	private void showMessage(String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.Red));
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_alert_dialog_test, null);
		TextView txtTitle = view.findViewById(R.id.title);
		ImageButton imageButton = view.findViewById(R.id.image);
		TextView mTxtMessage = view.findViewById(R.id.txtMessage);

		if (storyId == 0) {
			if (nextPageId == 6) {
				imageButton.setImageResource(R.mipmap.over_75);
				mTxtMessage.setText("You did well - but you could do even better!");
			} else if (nextPageId == 7) {
				imageButton.setImageResource(R.mipmap.over_95);
				mTxtMessage.setText("Excellent work!");
			} else if (nextPageId == 8) {
				imageButton.setImageResource(R.mipmap.over_50);
				mTxtMessage.setText("That was close. You almost leaked company data - give it another go!");
			} else {
				imageButton.setImageResource(R.mipmap.under_30);
				mTxtMessage.setText("Oh no.");
			}
		} else if (storyId == 1) {
			imageButton.setImageResource(R.mipmap.over_75);
			mTxtMessage.setText("Nice work! \nP.S. Did you get all the coins?");
		}

		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Navigation.findNavController(getView()).popBackStack();
			}
		});

		txtTitle.setText(title);

		builder.setView(view);
		AlertDialog dialog = builder.show();
		dialog.setCanceledOnTouchOutside(false);
	}

	public void setTitle(int storyId) {
		mTxtLevelTitle.setText("Story " + (storyId + 1));

	}

	public void setParameters(Cursor res) {
		mTxtMessage.setText("");
		res.moveToFirst();

		mTxtContent.setText(res.getString(3));
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


}
