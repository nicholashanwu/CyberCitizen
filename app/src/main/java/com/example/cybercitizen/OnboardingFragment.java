package com.example.cybercitizen;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class OnboardingFragment extends Fragment {

	private ViewPager mVpOnboarding;
	private LinearLayout mLlDots;
	private TextView[] mDots;
	private ViewPager mViewPager;
	private SliderAdapter sliderAdapter;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_onboarding, container, false);
		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mVpOnboarding = view.findViewById(R.id.vpOnboarding);
		mLlDots = view.findViewById(R.id.llDots);
		mViewPager = view.findViewById(R.id.vpOnboarding);


		sliderAdapter = new SliderAdapter(getActivity());
		mViewPager.setAdapter(sliderAdapter);

		addDotsIndicator(0);

		mViewPager.addOnPageChangeListener(viewListener);
	}

	public void addDotsIndicator(int position){
		mDots = new TextView[8];
		mLlDots.removeAllViews();

		for(int i = 0; i < mDots.length; i++) {
			mDots[i] = new TextView(getActivity());
			mDots[i].setText(Html.fromHtml("&#8226;"));
			mDots[i].setTextSize(36);
			mDots[i].setTextColor(getResources().getColor(R.color.colorYellow));
			mLlDots.addView(mDots[i]);
		}

		if(mDots.length > 0) {
			mDots[position].setTextColor(getResources().getColor(R.color.colorBlue));
		}
	}

	ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

		}

		@Override
		public void onPageSelected(int i) {
			addDotsIndicator(i);
		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}
	};

}