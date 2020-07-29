package com.example.translate.ui.achievements;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.PorterDuff;
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

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.translate.DatabaseHelper;
import com.example.translate.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class AchievementFragment extends Fragment {

    private DatabaseHelper myDb;
    private RoundCornerProgressBar mPbAchievement;

    private ExtendedFloatingActionButton mBtnStartSaved;
    private ExtendedFloatingActionButton mBtnStartMastered;
    private ExtendedFloatingActionButton mFabTokens;

    private CardView mCvCoupons;
    private CardView mCvBadges;

    private TextView mTxtCouponProgress;
    private TextView mTxtBadgeProgress;

    private ProgressBar mPbCoupons;
    private ProgressBar mPbBadges;

    private View.OnClickListener couponsButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            couponsButtonClicked();
        }
    };

    private View.OnClickListener badgesButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            badgesButtonClicked();
        }
    };

    public AchievementFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ExtendedFloatingActionButton mBtnAchievements = view.findViewById(R.id.btnAchievements);

        mPbAchievement = view.findViewById(R.id.pbAchievement);

        mBtnStartSaved = view.findViewById(R.id.btnStartSaved);
        mBtnStartMastered = view.findViewById(R.id.btnStartMastered);
        mFabTokens = view.findViewById(R.id.fabTokens);

        mCvCoupons = view.findViewById(R.id.cvCoupons);
        mCvBadges = view.findViewById(R.id.cvBadges);

        mPbBadges = view.findViewById(R.id.progressBarBadges);
        mPbCoupons = view.findViewById(R.id.progressBarCoupons);

        mTxtCouponProgress = view.findViewById(R.id.txtCouponProgress);
        mTxtBadgeProgress = view.findViewById(R.id.txtBadgeProgress);

        mCvCoupons.setOnClickListener(couponsButtonClickListener);
        mCvBadges.setOnClickListener(badgesButtonClickListener);

        myDb = DatabaseHelper.getInstance(getActivity().getApplicationContext());


        mBtnAchievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_navigation_dashboard_to_navigation_achievement);
            }
        });



        mFabTokens.setText(String.valueOf(myDb.getTokens()));


        mPbCoupons.setProgress(myDb.getCouponProgress());
        mTxtCouponProgress.setText(myDb.getCouponProgress() + "/6");

        mPbBadges.setProgress(myDb.getBadgeProgress());
        mTxtBadgeProgress.setText(myDb.getBadgeProgress() + "/8");







        mBtnStartSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDb.getSaved();
                if (res.getCount() == 0) {
                    showMessage("No Saved Words...", "No saved!");
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("learningType", "saved");
                    Navigation.findNavController(getView()).navigate(R.id.action_navigation_dashboard_to_navigation_learning, bundle);

                    if (myDb.progressAchievement("Dedicated")) {
                        showAchievement("Dedicated");
                    }

                    for (int i = 0; i < res.getCount(); i++) {
                        if (myDb.progressAchievement("Smart Saver")) {
                            showAchievement("Smart Saver");
                        }

                        if (myDb.progressAchievement("Sophisticated Saver")) {
                            showAchievement("Sophisticated Saver");
                        }
                    }


                }
                res.close();
            }
        });

        mBtnStartMastered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDb.getLearned();
                if (res.getCount() == 0) {
                    showMessage("No Data", "No learned!");
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("learningType", "learned");
                    Navigation.findNavController(getView()).navigate(R.id.action_navigation_dashboard_to_navigation_learning, bundle);
                    if (myDb.progressAchievement("Pursuing Perfection")) {
                        showAchievement("Pursuing Perfection");
                    }
                }
                res.close();
            }
        });

    }


//    public int getAchievements() {
//
//        Cursor cur = null;
//        int count = 0;
//
//        try {
//            cur = myDb.getAchieved();
//            mPbAchievement.setMax(32);
//            mPbAchievement.setProgress(cur.getCount());
//            count = cur.getCount();
////            mTxtAchievements.setText(cur.getCount() + "/32");
//
//        } catch (Exception e) {
//            // exception handling
//        } finally {
//            if (cur != null) {
//                cur.close();
//            }
//        }
//
//        return count;
//    }


    private void showAchievement(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.Yellow));
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_alert_dialog_achievement, null);
        TextView txtTitle = view.findViewById(R.id.title);
        ImageButton imageButton = view.findViewById(R.id.image);
        TextView mTxtAchievementDescription = view.findViewById(R.id.txtAchievementDescription);

        mTxtAchievementDescription.setText(myDb.returnAchievementDescription(title));

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

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_alert_dialog_profile, null);

        TextView txtTitle = view.findViewById(R.id.title);
        ImageButton imageButton = view.findViewById(R.id.image);

        imageButton.setImageResource(R.mipmap.over_40);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //closes dialog
            }
        });

        txtTitle.setText(title);
        builder.setView(view);
        AlertDialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    private void couponsButtonClicked() {
        //navigate to coupon page
        Navigation.findNavController(getView()).navigate(R.id.action_navigation_dashboard_to_couponFragment);

    }

    private void badgesButtonClicked() {
        //navigate to badges page
        Navigation.findNavController(getView()).navigate(R.id.action_navigation_dashboard_to_badgeFragment);
    }



}
