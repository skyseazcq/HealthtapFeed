package com.chaoqun.healthtapfeed;


import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class FeedAnswer extends Activity {
	
	private OneFeed oneFeed;
	
	private TextView questionTextView;
	private TextView snapshoTextView;
	private TextView fullnameTextView;
	private ImageView avatarImageView;
	private TextView ansTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedanswer);
		
		questionTextView = (TextView)findViewById(R.id.questionField);
		snapshoTextView = (TextView)findViewById(R.id.snapshot);
		fullnameTextView = (TextView)findViewById(R.id.docName);
		avatarImageView = (ImageView)findViewById(R.id.Avatar);
		ansTextView = (TextView)findViewById(R.id.answerField);
		
		Intent intent = getIntent();
		oneFeed =(OneFeed) intent.getSerializableExtra("oneFeed");
		
		questionTextView.setText(oneFeed.getQuestion());
		snapshoTextView.setText(oneFeed.getSnapshot());
		fullnameTextView.setText(oneFeed.getFullName());
		Picasso.with(this).load(oneFeed.getAvatar()).into(avatarImageView);
		ansTextView.setText(oneFeed.getAnswer());
		
	}

}
