package com.example.cybercitizen;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OnboardingFragment extends Fragment {

	private ViewPager mVpOnboarding;
	private RelativeLayout mRlOnboarding;
	private LinearLayout mLlDots;
	private TextView[] mDots;
	private ViewPager mViewPager;
	private SliderAdapter sliderAdapter;
	private Button mBtnPrev;
	private Button mBtnNext;
	private int mCurrentPage;
	private BottomNavigationView bottomBar;


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
		mBtnNext = view.findViewById(R.id.btnNext);
		mBtnPrev = view.findViewById(R.id.btnPrevious);
		mRlOnboarding = view.findViewById(R.id.rlOnboarding);
		bottomBar = getActivity().findViewById(R.id.nav_view);


		sliderAdapter = new SliderAdapter(getActivity());
		mViewPager.setAdapter(sliderAdapter);

		addDotsIndicator(0);

		mViewPager.addOnPageChangeListener(viewListener);

		mBtnNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mCurrentPage == mDots.length - 1) {
					bottomBar.setVisibility(View.VISIBLE);

					Navigation.findNavController(getView()).navigate(R.id.action_onboardingFragment_to_navigation_home);
				}

				mViewPager.setCurrentItem(mCurrentPage + 1);
			}
		});

		mBtnPrev.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mViewPager.setCurrentItem(mCurrentPage - 1);
			}
		});

	}

	public void addDotsIndicator(int position) {
		mDots = new TextView[8];
		mLlDots.removeAllViews();

		for (int i = 0; i < mDots.length; i++) {
			mDots[i] = new TextView(getActivity());
			mDots[i].setText(Html.fromHtml("&#8226;"));
			mDots[i].setTextSize(36);
			mDots[i].setTextColor(Color.parseColor("#DDDDDD"));
			mLlDots.addView(mDots[i]);
		}

		if (mDots.length > 0) {
			mDots[position].setTextColor(Color.parseColor("#AAAAAA"));
		}
	}

	ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(int i) {
			addDotsIndicator(i);
			if (i == 0) {
				mBtnNext.setText("NEXT");
				mBtnPrev.setVisibility(View.INVISIBLE);
			} else if (i == mDots.length - 1) {
				mBtnNext.setText("FINISH");
				mBtnPrev.setText("BACK");
			} else {
				mBtnPrev.setVisibility(View.VISIBLE);
				mBtnNext.setText("NEXT");
				mBtnPrev.setText("BACK");
			}
			mCurrentPage = i;
			mRlOnboarding.setBackgroundColor(slide_colors[i]);


		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}
	};

	public int[] slide_colors = {
			Color.parseColor("#5bc1eb"),
			Color.parseColor("#3090fc"),
			Color.parseColor("#e64c3c"),
			Color.parseColor("#00bcd4"),
			Color.parseColor("#f6e266"),
			Color.parseColor("#fad207"),
			Color.parseColor("#6e4b53"),
			Color.parseColor("#d43a2f"),
	};

}