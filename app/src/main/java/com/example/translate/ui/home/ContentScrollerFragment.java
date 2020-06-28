package com.example.translate.ui.home;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
		mTxtContentScrollerTitle.setText(learningType);

		myDb = new DatabaseHelper(getActivity());
		final Cursor res = getAllContent(learningType);
		res.moveToFirst();
		setAdapter(view, res);

		mFabContentDone.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//add new row
				//move cursor
				mAdapter.increaseCount();
			}
		});

	}

	private Cursor getAllContent(String learningType) {
		return myDb.getContentCategory(learningType);
	}

	private void setAdapter(View view, Cursor res) {
		RecyclerView mRecyclerView = view.findViewById(R.id.rvContent);
		RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
		mRecyclerView.setLayoutManager(mLayoutManager);
		mAdapter = new ContentAdapter(getContext(), res);
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setNestedScrollingEnabled(false);
	}

	// on button click:
	// add new row to recyclerview

	//
}
