package com.chaoqun.healthtapfeed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	public static final String TAG = MainActivity.class.getSimpleName();
    
	protected HttpClient mhttpclient = new DefaultHttpClient();
	
	protected ArrayList<OneFeed> mFeeds = new ArrayList<OneFeed>();
	protected String postResponse;
	protected JSONArray mJsonArray;
	protected JSONObject mBlogData;
	//protected ProgressBar mProgressBar;
	protected SwipeRefreshLayout swipeLayout;
	protected int pageNum = 1;
	protected int RefreshCnt = 0;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Log.d(TAG, "TO EXECUTE1");
        //mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            	swipeLayout.setRefreshing(true);
            	FeedGetTask getFeedTask = new FeedGetTask();
    			getFeedTask.execute();
            	
            }
        });
        
        if (isNetworkAvailable()) {
        	//mProgressBar.setVisibility(View.VISIBLE);
        	swipeLayout.setRefreshing(true);
        	//Log.d(TAG, "TO EXECUTE");
        	LoginPostTask LoginTask = new LoginPostTask();
        	LoginTask.execute();
        }
        else {
        	Toast.makeText(this, "Network is unavailable!", Toast.LENGTH_LONG).show();
        }
    }


	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onStop() {
		//mhttpclient.getConnectionManager().shutdown();
		super.onStop();
	};
	
	/*
	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	super.onListItemClick(l, v, position, id);
    	
    }
	*/
	private void logException(Exception e) {
    	Log.e(TAG, "Exception caught!", e);
	}
	
    private boolean isNetworkAvailable() {
		ConnectivityManager manager = (ConnectivityManager) 
				getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		
		boolean isAvailable = false;
		if (networkInfo != null && networkInfo.isConnected()) {
			isAvailable = true;
		}
		
		return isAvailable;
	}
    
    
    public void handleFeedResponse() {
		//mProgressBar.setVisibility(View.INVISIBLE);
		swipeLayout.setRefreshing(false);
		
		if (mJsonArray == null) {
			Toast.makeText(this, "No data available", Toast.LENGTH_LONG).show();
		}
		else {
			try {
				
				for (int i = 0; i < mJsonArray.length(); i++) {
					JSONObject post = mJsonArray.getJSONObject(i);
					JSONObject user_answer = post.getJSONObject("user_answer");
					String question = user_answer.getString("question");
					
					JSONObject actor = user_answer.getJSONObject("actor");
					String last_name = actor.getString("last_name");
					String imagePath = actor.getString("photo");
					
					mFeeds.add(0, new OneFeed(last_name, question, imagePath));
					Log.d("question", question);
					Log.d("last_name", last_name);
					
				}
				
				Log.d(TAG, " " + RefreshCnt);
				if(getListAdapter() == null) {
					Log.d(TAG, "Feeds numbers: " + mFeeds.size());
					MessageAdapter adapter = new MessageAdapter(this, mFeeds);
					setListAdapter(adapter);
				}
				else if(mJsonArray.length() > 0) {
					//to maintain your scroll position when you refresh
					Log.d(TAG, "Feeds numbers: " + mFeeds.size());
					((MessageAdapter)getListAdapter()).refill(mFeeds);
				}
				
			} 
			catch (JSONException e) {
				logException(e);
			}
		}
		++RefreshCnt;
		/*while(++RefreshCnt < 2) {
			FeedGetTask getFeedTask = new FeedGetTask();
			getFeedTask.execute();
		}*/
	}
    
    private class LoginPostTask extends AsyncTask<Object, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Object... args) {
			
			//HttpClient httpclient = new DefaultHttpClient();
	        
	        HttpPost httppost = new HttpPost("http://www.healthtap.com/people/sign_in");
	 
	        try {
	        	
	        	String email = "Your Email"; //"chaoqunzhu@yahoo.com";
	        	String password = "Your password";//
	          
	            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
	            nameValuePairs.add(new BasicNameValuePair("person[email]", email));
	            nameValuePairs.add(new BasicNameValuePair("person[password]", password));
	            nameValuePairs.add(new BasicNameValuePair("login_from_hash", ""));
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	            //Log.d(TAG, httppost.toString());
	            // Execute HTTP Post Request
	            HttpResponse response = mhttpclient.execute(httppost); // 
	            if(response.getEntity() == null) {
	            	Log.d(TAG, "Login Failed");
	            	return false;
	            }
	            else {
	            	Log.d(TAG, "SUCCESSFUL");
	            }
	            
	 
	        } catch (ClientProtocolException e) {
	         e.printStackTrace();
	        } catch (IOException e) {
	         e.printStackTrace();
	        }
	        
			return true;
		}
    
		@Override
		protected void onPostExecute(Boolean bool) {
			//postResponse = result;
			//Log.d(TAG, postResponse);
			//Log.d(TAG, "TO EXECUTE");
			if(bool == true) {
				FeedGetTask getFeedTask = new FeedGetTask();
				getFeedTask.execute();
			}
			else {
				Toast.makeText(MainActivity.this, "Login failed!", Toast.LENGTH_LONG).show();
			}
			//handleBlogResponse();
		}
    }
    
    private class FeedGetTask extends AsyncTask<Object, Void, JSONArray> {

		@Override
		protected JSONArray doInBackground(Object... args) {
			
			//StringBuilder stringBuilder = new StringBuilder();
			JSONArray jsonArray = null;
			//HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet("https://www.healthtap.com/polymorphic_activities.json?v2=1&page="+pageNum+"&per_page=8");
			//&auth_token=false&generate_token=true&authenticity_token=VusibgQYTOo2sZPI3NM67wKcCpw2IZVNRxh6m0RlwQE%3D");
			pageNum++;
			try {
				HttpResponse response = mhttpclient.execute(request);

				String json_string = EntityUtils.toString(response.getEntity());
				//Log.d(TAG, json_string);
				//Log.d(TAG, "LENGTH: " + json_string.length());
				jsonArray = new JSONArray(json_string);
				
			} catch (IOException e) {
		         e.printStackTrace();
		    } catch (JSONException e) {
				e.printStackTrace();
			}
			
			return jsonArray;
			
		}
    
		@Override
		protected void onPostExecute(JSONArray result) {
			mJsonArray = result;
			handleFeedResponse();
			//Log.d(TAG, postResponse);
			//handleBlogResponse();
		}
    }
   
}