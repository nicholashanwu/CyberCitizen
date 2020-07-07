package com.example.translate.ui.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.translate.DatabaseHelper;
import com.example.translate.R;
import com.example.translate.ui.dashboard.AchievementAdapter;
import com.makeramen.roundedimageview.RoundedImageView;

public class CompassFragment extends Fragment {

    private AchievementAdapter mAdapter;
    private DatabaseHelper myDb;
    private CardView mCvAdditionalResources;
    private CardView mCvGoogle;

    private View.OnClickListener additionalResourcesClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            additionalResourcesButtonClicked();
        }
    };

    private View.OnClickListener googleClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            googleButtonClicked();
        }
    };


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_compass, container, false);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mCvAdditionalResources = view.findViewById(R.id.cvAdditionalResources);
        mCvGoogle = view.findViewById(R.id.cvGoogle);


        mCvAdditionalResources.setOnClickListener(additionalResourcesClickListener);
        mCvGoogle.setOnClickListener(googleClickListener);

        myDb = new DatabaseHelper(getActivity());

    }

    private void additionalResourcesButtonClicked() {
        Navigation.findNavController(getView()).navigate(R.id.action_navigation_compass_to_navigation_resource);
    }

    private void googleButtonClicked() {
        Navigation.findNavController(getView()).navigate(R.id.action_navigation_compass_to_navigation_google);
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
        builder.show();
    }

    private void showAchievement(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.Yellow));
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_alert_dialog_achievement, null);
        TextView txtTitle = view.findViewById(R.id.title);
        ImageButton imageButton = view.findViewById(R.id.image);

        imageButton.setImageResource(R.mipmap.over_95);

        builder.setPositiveButton("AWESOME", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }

        });

        txtTitle.setText("You got the " + title + " achievement!");
        builder.setView(view);
        builder.show();
    }





}
