package com.example.cybercitizen.ui.learn;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cybercitizen.R;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {
	private Context mContext;
	private Cursor mCursor;
	private int count = 1;

	public ContentAdapter(Context context, Cursor cursor) {
		mContext = context;
		mCursor = cursor;
	}

	public static class ContentViewHolder extends RecyclerView.ViewHolder {
		public TextView mName;

		public ContentViewHolder(View itemView) {
			super(itemView);
			mName = itemView.findViewById(R.id.txtContent);
		}
	}

	@Override
	public ContentAdapter.ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(R.layout.content_row, parent, false);
		return new ContentViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ContentViewHolder holder, int position) {
		if (!mCursor.moveToPosition(position)) {
			return;
		}
		String name = mCursor.getString(4);
		holder.mName.setText(name);
	}

	@Override
	public int getItemCount() {
		return count;
	}

	public void increaseCount() {
		if(count < mCursor.getCount()){
			count++;
		}
		notifyDataSetChanged();
	}

	public void decreaseCount() {
		if(count > 0){
			count--;
		}
		notifyDataSetChanged();
	}

}
