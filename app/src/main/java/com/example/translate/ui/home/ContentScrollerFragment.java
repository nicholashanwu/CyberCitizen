package com.example.translate.ui.home;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
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
	private ContentAdapter mAdapter;
	private DatabaseHelper myDb;
	private FloatingActionButton mFabContentDone;
	private ProgressBar mProgressBarContent;
	private ProgressBar mProgressBarContentPage;
	private CardView mCvContent;
	private RecyclerView mRecyclerView;
	private int pageNumber = 1;
	private int progress = 0;

	public ContentScrollerFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_content_scroller, container, false);

		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		learningType = getArguments().getString("learningType");
		System.out.println(learningType);
		mTxtContentScrollerTitle = view.findViewById(R.id.txtContentScrollerTitle);
		mFabContentDone = view.findViewById(R.id.fabContentDone);
		mProgressBarContent = view.findViewById(R.id.progressBarContent);
		mProgressBarContentPage = view.findViewById(R.id.progressBarContentPage);
		mCvContent = view.findViewById(R.id.cvContent);
		mRecyclerView = view.findViewById(R.id.rvContent);


		mTxtContentScrollerTitle.setText(learningType);

		myDb = new DatabaseHelper(getActivity());
		final Cursor res = getAllContent(learningType, pageNumber);
		res.moveToFirst();
		setAdapter(res);

		progress = 0;
		mProgressBarContent.setProgress(0, true);
		mProgressBarContentPage.setProgress(1);
		mProgressBarContentPage.setMax(2);


		mFabContentDone.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//add new row
				//move cursor
				progress++;
				int percentage = 100 * progress/res.getCount();

				if(percentage == 100){
					moveToNextPage();
				} else {
					mProgressBarContent.setProgress(percentage, true);
					mAdapter.increaseCount();

				}





			}
		});

	}

	private Cursor getAllContent(String learningType, int pageNumber) {
		return myDb.getContentCategory(learningType, pageNumber);
	}

	private void setAdapter(Cursor res) {
		RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
		mRecyclerView.setLayoutManager(mLayoutManager);
		mAdapter = new ContentAdapter(getContext(), res);
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setNestedScrollingEnabled(false);
	}

	private void moveToNextPage(){
		mProgressBarContentPage.setProgress(mProgressBarContent.getProgress() + 1, true);
		YoYo.with(Techniques.SlideOutLeft).duration(200).playOn(mCvContent);
		YoYo.with(Techniques.SlideInRight).duration(200).delay(300).playOn(mCvContent);


		pageNumber++;

		setAdapter(getAllContent(learningType, pageNumber));

		progress = 0;
		mProgressBarContent.setProgress(0, true);


		//animate recyclerview moving away
		//replace recyclerview
	}

}
