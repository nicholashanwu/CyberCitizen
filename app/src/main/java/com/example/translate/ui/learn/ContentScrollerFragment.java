package com.example.translate.ui.learn;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.translate.DatabaseHelper;
import com.example.translate.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ContentScrollerFragment extends Fragment {

	private String learningType;
	private TextView mTxtContentScrollerTitle;
	private TextView mTxtProgressPageNumbers;
	private TextView mTxtContentScrollerPageTitle;
	private ContentAdapter mAdapter;
	private DatabaseHelper myDb;
	private FloatingActionButton mFabContentDone;
	private FloatingActionButton mFabBackContent;
	private RoundCornerProgressBar mProgressBarContentPage;
	private CardView mCvContent;
	private RecyclerView mRecyclerView;
	private NestedScrollView mSvContent;
	private int pageNumber = 1;
	private int totalPageCount;
	private int progress = 1;
	private Cursor res;

	public ContentScrollerFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_content_scroller, container, false);

		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		learningType = getArguments().getString("learningType");
		mTxtContentScrollerTitle = view.findViewById(R.id.txtContentScrollerTitle);
		mTxtContentScrollerPageTitle = view.findViewById(R.id.txtContentScrollerPageTitle);
		mFabContentDone = view.findViewById(R.id.fabContentDone);
		mProgressBarContentPage = view.findViewById(R.id.progressBarContentPage);
		mCvContent = view.findViewById(R.id.cvContent);
		mRecyclerView = view.findViewById(R.id.rvContent);
		mTxtProgressPageNumbers = view.findViewById(R.id.txtProgressPageNumbers);
		mFabBackContent = view.findViewById(R.id.fabBackContent);
		mSvContent = view.findViewById(R.id.svContent);


		myDb = DatabaseHelper.getInstance(getActivity().getApplicationContext());
		res = getAllContent(learningType, pageNumber);

		mTxtContentScrollerTitle.setText(learningType);
		mTxtProgressPageNumbers.setText("Page " + pageNumber + " of " + totalPageCount);

		setAdapter(res);

		progress = 1;
		mProgressBarContentPage.setProgress(pageNumber);
		mProgressBarContentPage.setMax(totalPageCount);

		System.out.println(res.getCount());

		mFabContentDone.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(progress == res.getCount()){
					moveToNextPage();
				} else {
					mAdapter.increaseCount();
					progress++;

					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
							//mSvContent.fullScroll(ScrollView.FOCUS_DOWN);
						}
					}, 100);
				}
			}
		});

		mFabBackContent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if(progress == 1) {
					moveToPreviousPage();
				} else {
					mAdapter.decreaseCount();
					progress--;
				}
			}
		});

	}

	private Cursor getAllContent(String learningType, int pageNumber) {
		totalPageCount = myDb.getContentCategoryPageCount(learningType, pageNumber);
		return myDb.getContentCategory(learningType, pageNumber);
	}

	private void setAdapter(Cursor res) {
		res.moveToFirst();
		mTxtContentScrollerPageTitle.setText(res.getString(3));
		RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
		mRecyclerView.setLayoutManager(mLayoutManager);
		mAdapter = new ContentAdapter(getContext(), res);
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setNestedScrollingEnabled(false);
	}

	private void moveToNextPage(){

		if(pageNumber >= totalPageCount) {
			checkAchievement();
			showMessage("Nice Work!");
		} else {
			mProgressBarContentPage.setProgress(mProgressBarContentPage.getProgress() + 1);
			YoYo.with(Techniques.SlideOutLeft).duration(200).playOn(mCvContent);
			YoYo.with(Techniques.SlideInRight).duration(200).delay(300).playOn(mCvContent);

			pageNumber++;

			res = getAllContent(learningType, pageNumber);
			setAdapter(res);
			mTxtProgressPageNumbers.setText("Page " + pageNumber + " of " + totalPageCount);


			progress = 1;
		}

	}

	private void moveToPreviousPage(){

		if(pageNumber == 1) {
			//do nothing
		} else {
			mProgressBarContentPage.setProgress(mProgressBarContentPage.getProgress() - 1);
			YoYo.with(Techniques.SlideOutRight).duration(200).playOn(mCvContent);
			YoYo.with(Techniques.SlideInLeft).duration(200).delay(300).playOn(mCvContent);

			pageNumber--;

			res = getAllContent(learningType, pageNumber);
			setAdapter(res);
			mTxtProgressPageNumbers.setText("Page " + pageNumber + " of " + totalPageCount);

			progress = 1;
		}

	}

	private void showMessage(String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.Green));
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_alert_dialog_learning, null);
		TextView txtTitle = view.findViewById(R.id.title);
		ImageButton imageButton = view.findViewById(R.id.image);

		imageButton.setImageResource(R.mipmap.over_75);

		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				res.close();
				Navigation.findNavController(getView()).popBackStack();
			}
		});


		txtTitle.setText(title);
		builder.setView(view);


		AlertDialog dialog = builder.show();
		dialog.setCanceledOnTouchOutside(false);
	}

	public void checkAchievement() {
		if (learningType.equals("What is Cyber?")) {
			if (myDb.progressAchievement("Cyber Novice I")) {
				showAchievement("Cyber Novice I");
				if (myDb.progressAchievement("Certified Cyber Novice")){
					showAchievement("Certified Cyber Novice");
				}
			}
		} else if (learningType.equals("Cyber 101")) {
			if (myDb.progressAchievement("Cyber Skilled I")) {
				showAchievement("Cyber Skilled I");
				if (myDb.progressAchievement("Certified Cyber Skilled")){
					showAchievement("Certified Cyber Skilled");
				}
			}
		} else if (learningType.equals("Social Engineering")) {
			if (myDb.progressAchievement("Anti-Social Engineer I")) {
				showAchievement("Anti-Social Engineer I");
				if (myDb.progressAchievement("Certified Anti-Social Engineer")){
					showAchievement("Certified Anti-Social Engineer");
				}
			}
		} else if (learningType.equals("Protecting Yourself")) {
			if (myDb.progressAchievement("Cyber Defender I")) {
				showAchievement("Cyber Defender I");
				if (myDb.progressAchievement("Certified Cyber Defender")){
					showAchievement("Certified Cyber Defender");
				}
			}
		}


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
