package com.example.translate.ui.compass.resources;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.translate.R;

public class ResourceYoutubeFragment extends Fragment {

	private CardView mCvYoutubeFullCourse;
	private CardView mCvYoutubeCareers;
	private CardView mCvYoutubeCrashCourse;
	private CardView mCvYoutubeFiveLaws;

	public ResourceYoutubeFragment() {
		// Required empty public constructor
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_resource_youtube, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mCvYoutubeFullCourse = view.findViewById(R.id.cvYoutubeFullCourse);
		mCvYoutubeCareers = view.findViewById(R.id.cvYoutubeCareers);
		mCvYoutubeCrashCourse = view.findViewById(R.id.cvYoutubeCrashCourse);
		mCvYoutubeFiveLaws = view.findViewById(R.id.cvYoutubeFiveLaws);

		mCvYoutubeFullCourse.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("https://www.youtube.com/watch?v=U_P23SqJaDc"));
				startActivity(browserIntent);
			}
		});

		mCvYoutubeCareers.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("https://www.youtube.com/watch?v=-AkuKKJ8dN0"));
				startActivity(browserIntent);
			}
		});

		mCvYoutubeCrashCourse.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("https://www.youtube.com/watch?v=bPVaOlJ6ln0"));
				startActivity(browserIntent);
			}
		});

		mCvYoutubeFiveLaws.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("https://www.youtube.com/watch?v=_nVq7f26-Uo"));
				startActivity(browserIntent);
			}
		});
	}
}