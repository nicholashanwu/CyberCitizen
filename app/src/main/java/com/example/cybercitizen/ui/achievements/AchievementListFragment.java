package com.example.cybercitizen.ui.achievements;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.cybercitizen.DatabaseHelper;
import com.example.cybercitizen.R;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AchievementListFragment extends Fragment {

    private AchievementAdapter mAdapter;
    private DatabaseHelper myDb;
    private Cursor res;
    private RoundCornerProgressBar mRpbAchievements;
    private TextView mTxtAchievementProgress;

    public AchievementListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                res.close();
                Navigation.findNavController(getView()).popBackStack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_achievement, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mRpbAchievements = view.findViewById(R.id.rpbAchievements);
        mTxtAchievementProgress = view.findViewById(R.id.txtAchievementProgress);

        myDb = DatabaseHelper.getInstance(getActivity().getApplicationContext());
        res = getAllAchievements();
        setAdapter(view, res);

        int max = myDb.getCountAchievements();
        int completed = myDb.getAchieved();

        mRpbAchievements.setMax(max);
        mRpbAchievements.setProgress(completed);
        mTxtAchievementProgress.setText(completed + " achievements of " + max + " completed");

    }

    private Cursor getAllAchievements() {
        return myDb.getAchievements();
    }

    private void setAdapter(View view, Cursor res) {
        RecyclerView mRecyclerView = view.findViewById(R.id.rvAchievement);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AchievementAdapter(getContext(), res);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
    }
}
