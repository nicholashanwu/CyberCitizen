package com.example.cybercitizen.ui.learn;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class TestFragment extends Fragment {

    private TextView mTxtDefinition;
    private TextView mTxtLevelTitle;
    private TextView mTxtMessage;
    private TextView mTxtProgress;
    private TextView mTxtScore;
    private TextView mTxtTimer;
    private TextView mTxtViewCountDown;
    private TextView mTxtEarnedCoinsTest;
    private ImageView mIvReaction;
    private RadioGroup mRbGroup;
    private RadioButton mRbAnswerOne;
    private RadioButton mRbAnswerTwo;
    private RadioButton mRbAnswerThree;
    private ProgressBar mProgressBar;
    private FloatingActionButton mFabSubmit;
    private CardView mCvDefinition;

    private View view;

    private DatabaseHelper myDb;
    private Cursor res;

    private int percentage;
    private int score = 0;
    private int wrongStreak = 0;
    private int rightStreak = 0;

    public boolean answered;
    private int answerIndex;
    private long mTimeLeftInMillis = 11000;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private String testingType;

    private ArrayList<String> answerList = new ArrayList<>();

    public TestFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                if(res != null) {
                    res.close();
                }
                pauseTimer();
                Navigation.findNavController(getView()).popBackStack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_test, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myDb = DatabaseHelper.getInstance(getContext().getApplicationContext());

        mProgressBar = view.findViewById(R.id.pbTest);
        mTxtProgress = view.findViewById(R.id.txtProgress);
        mTxtDefinition = view.findViewById(R.id.txtPhrase);
        mTxtLevelTitle = view.findViewById(R.id.txtLevelTitle);
        mTxtMessage = view.findViewById(R.id.txtMessage);
        mRbGroup = view.findViewById(R.id.rbGroup);
        mRbAnswerOne = view.findViewById(R.id.rbAnswerOne);
        mRbAnswerTwo = view.findViewById(R.id.rbAnswerTwo);
        mRbAnswerThree = view.findViewById(R.id.rbAnswerThree);
        mFabSubmit = view.findViewById(R.id.fabSubmitAnswer);
        mTxtScore = view.findViewById(R.id.txtScore);
        mIvReaction = view.findViewById(R.id.ivReaction);
        mTxtTimer = view.findViewById(R.id.txtTimer);
        mCvDefinition = view.findViewById(R.id.cvDefinition);
        mTxtViewCountDown = view.findViewById(R.id.txtTimer);
        mTxtEarnedCoinsTest = view.findViewById(R.id.txtEarnedCoinsTest);


        testingType = getArguments().getString("testingType");
        res = getData(testingType);

        setParameters(res);
        setTitle(testingType);

        showNextQuestion(res);


        updateCountDownText();


        mFabSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!answered) {
                    if (mTimerRunning && (mRbAnswerOne.isChecked() || mRbAnswerTwo.isChecked() || mRbAnswerThree.isChecked())) {

                        checkAnswer(answerIndex, res);
                        pauseTimer();
                    } else if (mTimerRunning) {
                        mTxtMessage.setText("Please choose an answer");
                        YoYo.with(Techniques.FadeInDown).duration(300).playOn(mTxtMessage);
                        YoYo.with(Techniques.FadeOutUp).duration(300).delay(700).playOn(mTxtMessage);
                    } else {
                        resetTimer();
                        showNextQuestion(res);
                        for (int i = 0; i < mRbGroup.getChildCount(); i++) {
                            mRbGroup.getChildAt(i).setEnabled(true);
                        }
                    }
                } else {
                    resetTimer();
                    showNextQuestion(res);
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

        if (res.getPosition() < res.getCount()) {

            mTxtDefinition.setText(res.getString(2));

            double progressDouble = (double) 100 * res.getPosition() / res.getCount();
            int progressInt = (int) progressDouble;
            mTxtProgress.setText((res.getPosition() + 1) + "/" + res.getCount());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mProgressBar.setProgress(progressInt, true);
            }

            answerList = (ArrayList<String>) getAnswerList(res.getPosition(), res).clone();

            if (mTimerRunning) {
                pauseTimer();
            } else {
                startTimer(answerIndex, res);
            }

            mRbAnswerOne.setText(answerList.get(0));
            mRbAnswerTwo.setText(answerList.get(1));
            mRbAnswerThree.setText(answerList.get(2));
            mRbAnswerOne.setTextColor(Color.parseColor("#444444"));
            mRbAnswerTwo.setTextColor(Color.parseColor("#444444"));
            mRbAnswerThree.setTextColor(Color.parseColor("#444444"));

            answered = false;

        } else {
            finishTest(res);
        }

    }

    private void checkAnswer(int answerIndex, Cursor res) {
        answered = true;
        RadioButton rbSelected = getView().findViewById(mRbGroup.getCheckedRadioButtonId());
        int answerNum = mRbGroup.indexOfChild(rbSelected) + 1;

        if (answerNum == answerIndex) {

            showReaction(true);
            rightStreak++;
            wrongStreak = 0;
            score++;
            mTxtScore.setText("Score: " + score);


            if (res.getString(5).equals("1")) {
                myDb.updateLearned(res.getString(1), true);
            } else {
                myDb.updateLearned(res.getString(1), true);
            }

            if (rightStreak == 3) {
                if (myDb.progressAchievement("Oh Baby a Triple!")) {
                    showAchievement("Oh Baby a Triple!");
                }
            } else if (rightStreak == 5) {
                if (myDb.progressAchievement("Pentakill!")) {
                    showAchievement("Pentakill!");
                }
            }

            if (mTxtScore.getText().equals("9") || mTxtScore.getText().equals("10")) {
                if (myDb.progressAchievement("Instant Noodles!")) {
                    showAchievement("Instant Noodles!");
                }
            } else if (mTxtScore.getText().equals("8")) {
                if (myDb.progressAchievement("Slick Speedster!")) {
                    showAchievement("Slick Speedster!");
                }
            }


        } else {

            wrongStreak++;
            rightStreak = 0;
            showReaction(false);

            if (res.getPosition() == 0) {
                if (myDb.progressAchievement("Off to a Great Start")) {
                    showAchievement("Off to a Great Start");
                }
            }
            if (res.getString(5).equals("1")) {
                myDb.updateLearned(res.getString(1), false);
            }

            if (wrongStreak == 3) {
                if (myDb.progressAchievement("Abort Mission?")) {
                    showAchievement("Abort Mission?");
                }
            } else if (wrongStreak == 5) {
                if (myDb.progressAchievement("Abandon Ship!")) {
                    showAchievement("Abandon Ship!");
                }
            }

        }
        showSolution(answerIndex, res);
    }

    private void showSolution(int answerIndex, Cursor res) {
        mRbAnswerOne.setTextColor(Color.parseColor("#FF5252"));
        mRbAnswerTwo.setTextColor(Color.parseColor("#FF5252"));
        mRbAnswerThree.setTextColor(Color.parseColor("#FF5252"));

        switch (answerIndex) {
            case 1:
                mRbAnswerOne.setTextColor(Color.parseColor("#D4E157"));
                mRbAnswerTwo.setTextColor(Color.parseColor("#FF5252"));
                mRbAnswerThree.setTextColor(Color.parseColor("#FF5252"));
                break;
            case 2:
                mRbAnswerTwo.setTextColor(Color.parseColor("#D4E157"));
                mRbAnswerOne.setTextColor(Color.parseColor("#FF5252"));
                mRbAnswerThree.setTextColor(Color.parseColor("#FF5252"));
                break;
            case 3:
                mRbAnswerThree.setTextColor(Color.parseColor("#D4E157"));
                mRbAnswerOne.setTextColor(Color.parseColor("#FF5252"));
                mRbAnswerTwo.setTextColor(Color.parseColor("#FF5252"));
                break;
        }
        res.move(1);
    }

    private void finishTest(Cursor res) {
        mTxtProgress.setText("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mProgressBar.setProgress(99, true);
        }
        percentage = 100 * score / res.getCount();

        if (testingType.equals("What is Cyber?")) {
            if (myDb.progressAchievement("Cyber Novice III")) {
                showAchievement("Cyber Novice III");
                if (myDb.progressAchievement("Cyber Specialist")) {
                    showAchievement("Cyber Specialist");
                }
                if (myDb.progressAchievement("Certified Cyber Novice")){
                    showAchievement("Certified Cyber Novice");
                }
            }
        } else if (testingType.equals("Cyber 101")) {
            if (myDb.progressAchievement("Cyber Skilled III")) {
                showAchievement("Cyber Skilled III");
                if (myDb.progressAchievement("Cyber Specialist")) {
                    showAchievement("Cyber Specialist");
                }
                if (myDb.progressAchievement("Certified Cyber Skilled")){
                    showAchievement("Certified Cyber Skilled");
                }
            }
        } else if (testingType.equals("Social Engineering")) {
            if (myDb.progressAchievement("Anti-Social Engineer III")) {
                showAchievement("Anti-Social Engineer III");
                if (myDb.progressAchievement("Cyber Specialist")) {
                    showAchievement("Cyber Specialist");
                }
                if (myDb.progressAchievement("Certified Anti-Social Engineer")){
                    showAchievement("Certified Anti-Social Engineer");
                }
            }
        } else if (testingType.equals("Protecting Yourself")) {
            if (myDb.progressAchievement("Cyber Defender III")) {
                showAchievement("Cyber Defender III");
                if (myDb.progressAchievement("Cyber Specialist")) {
                    showAchievement("Cyber Specialist");
                }
                if (myDb.progressAchievement("Certified Cyber Defender")){
                    showAchievement("Certified Cyber Defender");
                }
            }
        } else {
        }

        if (percentage == 100) {
            if (myDb.progressAchievement("Cyber Savvy")) {
                showAchievement("Cyber Savvy");
            }
        } else if (percentage >= 90 && percentage < 100) {
            if (myDb.progressAchievement("Nice Nine")) {
                showAchievement("Nice Nine");
            }
        } else if (percentage >= 80 && percentage < 90) {
            if (myDb.progressAchievement("Excellent Eight")) {
                showAchievement("Excellent Eight");
            }
        } else if (percentage >= 70 && percentage < 80) {
            if (myDb.progressAchievement("Super Seven")) {
                showAchievement("Super Seven");
            }
        } else if (percentage >= 60 && percentage < 70) {
            if (myDb.progressAchievement("Sexy Six")) {
                showAchievement("Sexy Six");
            }
        } else if (percentage < 30) {
            if (myDb.progressAchievement("Did you even try?")) {
                showAchievement("Did you even try?");
            }
        } else {

        }

        showMessage("You're Finished!");
        if (res != null) {
            res.close();
        }

    }

    private ArrayList<String> getAnswerList(int currentCardNumber, Cursor res) {
        answerList.clear();
        answerList.add(res.getString(1));
        boolean unique = false;
        int numOne = (int) (Math.random() * res.getCount());
        int numTwo = (int) (Math.random() * res.getCount());

        while (!unique) {
            numOne = (int) (Math.random() * res.getCount());
            numTwo = (int) (Math.random() * res.getCount());
            if (currentCardNumber == numOne) {
                numOne = (int) (Math.random() * res.getCount());
            } else if (currentCardNumber == numTwo) {
                numTwo = (int) (Math.random() * res.getCount());
            } else if (numOne == numTwo) {
                numTwo = (int) (Math.random() * res.getCount());
            } else {
                unique = true;
            }

        }
        currentCardNumber = res.getPosition();

        res.moveToPosition(numOne);
        answerList.add(res.getString(1));
        res.moveToPosition(numTwo);
        answerList.add(res.getString(1));
        res.moveToPosition(currentCardNumber);

        Collections.shuffle(answerList);

        answerIndex = answerList.indexOf(res.getString(1)) + 1;
        return answerList;
    }

    public Cursor getData(String testingType) {
        Cursor res;
        if (testingType.equals("saved")) {
            res = myDb.getSaved();
        } else if (testingType.equals("learned")) {
            res = myDb.getLearned();
        } else {
            res = myDb.getCategory(testingType);
        }

        return res;

    }

    private void showMessage(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.Red));
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_alert_dialog, null);
        TextView txtTitle = view.findViewById(R.id.title);
        ImageButton imageButton = view.findViewById(R.id.image);
        TextView mTxtMessage = view.findViewById(R.id.message);

        if (percentage > 85) {
            myDb.updateScore("HD");
            imageButton.setImageResource(R.mipmap.over_95);
        } else if (percentage > 75) {
            myDb.updateScore("D");
            imageButton.setImageResource(R.mipmap.over_75);
        } else if (percentage > 65) {
            myDb.updateScore("C");
            imageButton.setImageResource(R.mipmap.over_65);
        } else if (percentage > 50) {
            myDb.updateScore("P");
            imageButton.setImageResource(R.mipmap.over_50);
        } else if (percentage > 40) {
            myDb.updateScore("F");
            imageButton.setImageResource(R.mipmap.over_40);
        } else if (percentage > 30) {
            myDb.updateScore("F");
            imageButton.setImageResource(R.mipmap.over_30);
        } else {
            myDb.updateScore("F");
            imageButton.setImageResource(R.mipmap.under_30);
        }

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Navigation.findNavController(getView()).popBackStack();
            }
        });

        txtTitle.setText(title);
        mTxtMessage.setText(percentage + "%");

        builder.setView(view);
        AlertDialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    public void setTitle(String testingType) {
        mTxtLevelTitle.setText(testingType);

    }

    public void setParameters(Cursor res) {
        mTxtMessage.setText("");
        mProgressBar.setProgress(1);
        mTxtProgress.setText("1/" + res.getCount());
        res.moveToFirst();
        mIvReaction.setVisibility(View.GONE);

        mTxtDefinition.setText(res.getString(1));
    }

    public void showReaction(boolean correct) {

        ArrayList<Integer> goodList = new ArrayList<>();
        goodList.add(R.mipmap.over_95);
        goodList.add(R.mipmap.over_75);
        goodList.add(R.mipmap.over_65);
        ArrayList<Integer> badList = new ArrayList<>();
        badList.add(R.mipmap.over_30);
        badList.add(R.mipmap.over_40);
        badList.add(R.mipmap.under_30);
        Collections.shuffle(goodList);
        Collections.shuffle(badList);

        if (correct) {
            mIvReaction.setImageResource(goodList.get(0));
            mIvReaction.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.FadeInUp).duration(300).playOn(mIvReaction);
            YoYo.with(Techniques.FadeOutUp).duration(300).delay(700).playOn(mIvReaction);
            addCoins();
        } else {
            mIvReaction.setImageResource(badList.get(0));
            mIvReaction.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.FadeInUp).duration(300).playOn(mIvReaction);
            YoYo.with(Techniques.FadeOutUp).duration(300).delay(700).playOn(mIvReaction);
        }

    }

    private void startTimer(final int answerIndex, final Cursor res) {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;

                for (int i = 0; i < mRbGroup.getChildCount(); i++) {
                    mRbGroup.getChildAt(i).setEnabled(false);
                }

                showReaction(false);

                showSolution(answerIndex, res);

            }
        }.start();

        mTimerRunning = true;

    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;

    }

    private void resetTimer() {
        mTimeLeftInMillis = 11000;
        updateCountDownText();

    }

    private void updateCountDownText() {
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d", seconds);

        mTxtViewCountDown.setText(timeLeftFormatted);
    }

    private void addCoins() {
        myDb.addTokens(10);
        mTxtEarnedCoinsTest.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.BounceInUp).duration(200).playOn(mTxtEarnedCoinsTest);
        YoYo.with(Techniques.FadeOutUp).delay(400).duration(200).playOn(mTxtEarnedCoinsTest);
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
