package com.example.cybercitizen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class OnboardingFragment extends Fragment {

	private ViewPager mVpOnboarding;
	private LinearLayout mLlDots;
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
	}
}