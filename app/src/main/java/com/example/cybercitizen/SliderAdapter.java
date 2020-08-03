package com.example.cybercitizen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

	Context context;
	LayoutInflater layoutInflater;

	public SliderAdapter(Context context) {
		this.context = context;
	}

	public int[] slide_images = {
			R.drawable.onboarding_cyber_security,
			R.drawable.onboarding_globe,
			R.drawable.onboarding_book,
			R.drawable.onboarding_vr,
			R.drawable.onboarding_voucher,
			R.drawable.onboarding_medal,
			R.drawable.onboarding_career,
			R.drawable.onboarding_journey
	};

	public String[] slide_headings = {
			"text1",
			"text2",
			"text3",
			"text4",
			"text5",
			"text6",
			"text7",
			"text8",
	};

	public String[] slide_descs = {
			"Lorem ipsum",
			"Lorem ipsum",
			"Lorem ipsum",
			"Lorem ipsum",
			"Lorem ipsum",
			"Lorem ipsum",
			"Lorem ipsum",
			"Lorem ipsum"
	};


	@Override
	public int getCount() {
		return slide_headings.length;
	}

	@Override
	public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
		return view == object;
	}

	@NonNull
	@Override
	public Object instantiateItem(@NonNull ViewGroup container, int position) {
		layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.onboarding_slide_layout, container, false);

		ImageView slideImageView = (ImageView) view.findViewById(R.id.ivOnboardingSlide);
		TextView slideHeading = (TextView) view.findViewById(R.id.txtOnboardingHeading);
		TextView slideDescription = (TextView) view.findViewById(R.id.txtOnboardingDescription);

		slideImageView.setImageResource(slide_images[position]);
		slideHeading.setText(slide_headings[position]);
		slideDescription.setText(slide_descs[position]);

		container.addView(view);

		return view;
	}

	@Override
	public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
		container.removeView((ConstraintLayout) object);
	}
}
