package com.example.translate.ui.achievements;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.translate.R;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    public AchievementAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

//    public interface RecyclerViewClickListener {
//        void onClick(View view, int position);
//    }

    public static class AchievementViewHolder extends RecyclerView.ViewHolder {
        public TextView mName, mIsAchieved, mDescription;
        public ProgressBar mProgressBar;

        public AchievementViewHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.txtAchievementName);
            mProgressBar = itemView.findViewById(R.id.pbAchievement);
            mDescription = itemView.findViewById(R.id.txtAchievementDescription);
            mIsAchieved = itemView.findViewById(R.id.txtIsAchieved);
        }
    }

    @Override
    public AchievementAdapter.AchievementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.row_achievement, parent, false);
        return new AchievementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AchievementViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String name = mCursor.getString(1);
        String description = mCursor.getString(2);
        String currentProgress = mCursor.getString(3);
        String totalProgress = mCursor.getString(4);
        String complete = mCursor.getString(5);

        int progressInt = calculateProgress(currentProgress, totalProgress);
        holder.mProgressBar.setProgress(progressInt, true);
        holder.mName.setText(name);
        holder.mDescription.setText(description);

        if (complete.equals("1")) {
            holder.mIsAchieved.setTypeface(Typeface.DEFAULT_BOLD);
            holder.mIsAchieved.setTextColor(Color.parseColor("#FFD54F"));
        } else {
            holder.mIsAchieved.setTypeface(Typeface.DEFAULT);
            holder.mIsAchieved.setTextColor(Color.parseColor("#666666"));
        }
        holder.mIsAchieved.setText(Integer.valueOf(currentProgress) + "/" + Integer.valueOf(totalProgress));
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public int calculateProgress(String currentProgress, String totalProgress) {
        double progressDouble = 100 * (Double.valueOf(currentProgress) / Double.valueOf(totalProgress));
        int progressInt = (int) progressDouble;

        return progressInt;
    }

}
