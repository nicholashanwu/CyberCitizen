package com.example.cybercitizen;

import android.content.Context;
import android.graphics.Color;
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

	private Context context;
	private LayoutInflater layoutInflater;

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

	public String[] slide_descs = {
			"Welcome to Cyber Citizen",
			"Equipping you with the information and tools to become a responsible citizen who is aware of the dangers within the cyber space.",
			"Ditch the books - we have the information you'll actually want to read.",
			"Play a part in real-world cybersecurity simulations. Every choice you make affects the outcome!",
			"Earn CyberCoins by completing modules and redeem coupons to use at your favourite places!",
			"We've partnered with industry leaders to deliver you real experience. ",
			"Earn LinkedIn badges to show off your cybersecurity accreditations. ",
			"Taking you on a rewarding journey to becoming a Cyber Citizen in a Cyber World"
	};



	@Override
	public int getCount() {
		return slide_descs.length;
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
		TextView slideDescription = (TextView) view.findViewById(R.id.txtOnboardingDescription);

		slideImageView.setImageResource(slide_images[position]);
		slideDescription.setText(slide_descs[position]);

		container.addView(view);

		return view;
	}

	@Override
	public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
		container.removeView((ConstraintLayout) object);
	}
}
