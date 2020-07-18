package com.example.translate.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
	private ProgressBar mProgressBarContent;
	private ProgressBar mProgressBarContentPage;
	private CardView mCvContent;
	private RecyclerView mRecyclerView;
	private int pageNumber = 1;
	private int totalPageCount;
	private int progress = 0;
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


		myDb = new DatabaseHelper(getActivity());
		res = getAllContent(learningType, pageNumber);

		mTxtContentScrollerTitle.setText(learningType);
		mTxtProgressPageNumbers.setText(pageNumber + "/" + totalPageCount);

		setAdapter(res);

		progress = 0;
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

				}
			}
		});

		mFabBackContent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if(progress == 0) {
					moveToPreviousPage();
				} else {
					progress--;

					mAdapter.decreaseCount();
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
			showMessage("Congratulations!");
		} else {
			mProgressBarContentPage.setProgress(mProgressBarContentPage.getProgress() + 1, true);
			YoYo.with(Techniques.SlideOutLeft).duration(200).playOn(mCvContent);
			YoYo.with(Techniques.SlideInRight).duration(200).delay(300).playOn(mCvContent);

			pageNumber++;

			setAdapter(getAllContent(learningType, pageNumber));
			mTxtProgressPageNumbers.setText(pageNumber + "/" + totalPageCount);

			progress = 1;
		}

	}

	private void moveToPreviousPage(){

		if(pageNumber == 1) {
			//do nothing
		} else {
			mProgressBarContentPage.setProgress(mProgressBarContentPage.getProgress() - 1, true);
			YoYo.with(Techniques.SlideOutRight).duration(200).playOn(mCvContent);
			YoYo.with(Techniques.SlideInLeft).duration(200).delay(300).playOn(mCvContent);

			pageNumber--;

			setAdapter(getAllContent(learningType, pageNumber));
			mTxtProgressPageNumbers.setText(pageNumber + "/" + totalPageCount);

			progress = 0;
		}

	}

	private void showMessage(String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.Green));
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_alert_dialog_learning, null);
		TextView txtTitle = view.findViewById(R.id.title);
		ImageButton imageButton = view.findViewById(R.id.image);

		imageButton.setImageResource(R.mipmap.over_75);

		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				res.close();
				myDb.close();
				Navigation.findNavController(getView()).popBackStack();
			}
		});

		txtTitle.setText(title);
		builder.setView(view);


		builder.show();
	}

}
