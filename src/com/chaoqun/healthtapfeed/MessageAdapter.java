package com.chaoqun.healthtapfeed;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;



//customed adapter useful
public class MessageAdapter extends ArrayAdapter<OneFeed> {
	
	protected static final String TAG = MessageAdapter.class.getSimpleName();
	protected Context mContext;
	protected List<OneFeed> mFeeds;
	
	public MessageAdapter(Context context, List<OneFeed> feeds) {
		super(context, R.layout.message_item, feeds);
		mContext = context;
		mFeeds = feeds;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.message_item, null);
			holder = new ViewHolder();
			holder.iconImageView = (ImageView)convertView.findViewById(R.id.imageIcon);
			holder.Lastname = (TextView)convertView.findViewById(R.id.DoctorName);
			holder.Question = (TextView)convertView.findViewById(R.id.question);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder)convertView.getTag();
		}
		
		OneFeed feed = mFeeds.get(position);
		Picasso.with( mContext).load(feed.getImagePath()).into(holder.iconImageView);
		
		holder.Lastname.setText("Dr. " + feed.getName() +" answerd:");
		holder.Question.setText(feed.getQuestion());
		//Log.d(TAG, date.toString());
		//Log.d(TAG, message.getCreatedAt().toString());
		//Log.d(TAG, getTimeDiffString(after));
		
		return convertView;
	}
	

	private static class ViewHolder {
		ImageView iconImageView;
		TextView Lastname;
		TextView Question;
	}
	
	public void refill(List<OneFeed> feeds) {
			mFeeds = feeds;
			notifyDataSetChanged();// tells the ListView that the data has been modified; 
								//and to show the new data, the ListView must be redrawn
		
	}
}






