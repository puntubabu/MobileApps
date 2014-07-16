package com.breanawiggins.quizdom;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class FriendsListActivity extends ListActivity {

	//Edit Textbox
	private EditText etFriend;
	
	//Friends List
	private ListView lvFriendsList;
 
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    
    //testing from a real server:
    private static final String ADD_FRIEND_URL = "http://pradeepkeshary.com/webservice/addfriend.php";
    private static final String GET_FRIENDS_LIST_URL = "http://pradeepkeshary.com/webservice/getfriendslist.php";
    
    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_FRIEND_NAME = "friend_name";
    private String username;
    
	// An array of all of our friends
	private JSONArray mFriends = null;
	// manages all of our friends in a list.
	private ArrayList<String> mFriendsList;
    
	private ArrayAdapter<String> listAdapter ;  
	
	SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(FriendsListActivity.this);

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friends_list);
		username = sp.getString("username", "ERROR");
		etFriend = (EditText)findViewById(R.id.etFriend);
		lvFriendsList = (ListView)findViewById(R.id.lvFriendsList);  

	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// loading the comments via AsyncTask
		new LoadFriends().execute();
	}
	
	/**
	 * Inserts the parsed data into the listview.
	 */
	private void updateList() {
		mFriendsList.add("test1");
		mFriendsList.add("test2");
		mFriendsList.add("test3");
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        
	    listAdapter = new ArrayAdapter<String>(this, R.layout.friendrow, mFriendsList);  


	    lvFriendsList.setAdapter(listAdapter);
	    
		// when the user clicks a list item we 
		//could do something.  However, we will choose
		//to do nothing...
		ListView lv = getListView();	
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// This method is triggered if an item is click within our
				// list. For our example we won't be using this, but
				// it is useful to know in real life applications.

			}
		});
	}
	
	public class LoadFriends extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			updateJSONdata();
			return null;

		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			updateList();
		}
	}
	
	class AddFriend extends AsyncTask<String, String, String> {
		
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
		
		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			 // Check for success tag
            int success;
        	SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(FriendsListActivity.this);

            String user = sp.getString("username", "");
            String friend = etFriend.getText().toString();
            
            try {
                // Building Parameters
                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", user));
                params.add(new BasicNameValuePair("friendname", friend));
 
                Log.d("request!", "starting");
                
                //Posting user data to script 
                JSONObject json = jsonParser.makeHttpRequest(
                		ADD_FRIEND_URL, "POST", params);
 
                // full json response
                Log.d("Add Friend attempt", json.toString());
 
                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                	Log.d("Friend Added!", json.toString());    
                	finish();
                	return json.getString(TAG_MESSAGE);
                }else{
                	Log.d("Friend Doesn't Exist!", json.getString(TAG_MESSAGE));
                	return json.getString(TAG_MESSAGE);
                	
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
			
		}
		
        protected void onPostExecute(String file_url) {
            if (file_url != null){
            	Toast.makeText(FriendsListActivity.this, file_url, Toast.LENGTH_LONG).show();
            }
 
        }
		
	}
	
	public void onClick(View v){
		switch (v.getId()){
		case R.id.btnAddFriend:
			new AddFriend().execute();
			break;
		}
	}

	/**
	 * Retrieves recent post data from the server.
	 */
	public void updateJSONdata() {

		// Instantiate the arraylist to contain all the JSON data.
		// we are going to use a bunch of key-value pairs, referring
		// to the json element name, and the content, for example,
		// message it the tag, and "I'm awesome" as the content..

		mFriendsList = new ArrayList<String>();

		// Bro, it's time to power up the J parser
		JSONParser jParser = new JSONParser();
		// Feed the beast our comments url, and it spits us
		// back a JSON object.
		JSONObject json = jParser.getJSONFromUrl(GET_FRIENDS_LIST_URL);

		// when parsing JSON stuff, we should probably
		// try to catch any exceptions:
		try {

			// I know I said we would check if "Posts were Avail." (success==1)
			// before we tried to read the individual posts, but I lied...
			// mFriends will tell us how many "posts" or comments are
			// available
			mFriends = json.getJSONArray(TAG_FRIEND_NAME);

			// looping through all posts according to the json object returned
			for (int i = 0; i < mFriends.length(); i++) {
				JSONObject c = mFriends.getJSONObject(i);

				// gets each friend
				String friendname = c.getString(TAG_FRIEND_NAME);

//				// creating new HashMap
//				HashMap<String, String> map = new HashMap<String, String>();

				//map.put(TAG_FRIEND_NAME, friendname);

				// adding HashList to ArrayList
				mFriendsList.add(friendname);

				// annndddd, our JSON data is up to date same with our array
				// list
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.friends_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_friends_list,
					container, false);
			return rootView;
		}
	}

}
