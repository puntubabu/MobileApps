package com.breanawiggins.quizdom;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class HomeScreen extends ActionBarActivity {

    private ImageButton btnLogin, btnSignUp;
    private TextView tvCurrentUser;
    public static UserSessionManager session;
    private Button requestButton;
    private ArrayList<HashMap<String, String>> friendRequests;
    
    private String strUser;
    
    //List Adapter
    private ListAdapter adapter;

    //Tags
    private static final String TAG_FRIEND = "friend_name";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_FRIENDS = "friends";
    private static final String TAG = "HomeScreen";

    //URL's
    private static final String GET_REQUESTS_URL = "http://pradeepkeshary.com/webservice/getrequests.php";
    
    //JSON Objects
    private JSONParser jsonParser = new JSONParser();
    private JSONArray jarrayFriends = new JSONArray();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_home);
        session = new UserSessionManager(getApplicationContext());
        tvCurrentUser = (TextView)findViewById(R.id.tvCurrentUser);
        setCurrentUserText();
        friendRequests = new ArrayList<HashMap<String, String>>();
        
        //Get Requests
        new GetRequests().execute();
    }
    
    private void setCurrentUserText(){
        HashMap<String, String> user = new HashMap<String, String>();
        user = session.getUserDetails();
        String strUser = user.get("name");
    	tvCurrentUser.setText("Logged in as: "+strUser);
    }
    
    private void getRequests(){
		int success = 0;

    	friendRequests = new ArrayList<HashMap<String, String>>();
		
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        HashMap<String, String> user = new HashMap<String, String>();
        user = session.getUserDetails();
        strUser = user.get("name");
        params.add(new BasicNameValuePair("username", strUser));
        
        
		jsonParser = new JSONParser();
		JSONObject json = jsonParser.makeHttpRequest(GET_REQUESTS_URL, "POST", params);

		// when parsing JSON stuff, we should probably
		// try to catch any exceptions:
		try {

			jarrayFriends = json.getJSONArray(TAG_FRIENDS);
			// looping through all posts according to the json object returned
			for (int i = 0; i < jarrayFriends.length(); i++) {
				JSONObject c = jarrayFriends.getJSONObject(i);

				// gets the content of each tag
				String friendName = c.getString(TAG_FRIEND);
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();

				map.put(TAG_FRIEND, friendName);

				// adding HashList to ArrayList
				friendRequests.add(map);

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
    }
    
    private void animateAlertButton(){
        //If requests exist, animate Alert button
        if (friendRequests.size() > 0){
            final Animation animScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
            requestButton = (Button) findViewById(R.id.btnRequest);
            requestButton.startAnimation(animScale);
        }
    }
    
    class GetRequests extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... arg0) {
			getRequests();
			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			animateAlertButton();
		}
    	
    }
    
    private void openRequestsDialog(){
    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
    		LayoutInflater inflater = getLayoutInflater();
			// set title
			alertDialogBuilder.setTitle("Alerts!");
 
			//set inflater
			View requestLayout = inflater.inflate(R.layout.request, null);
			
			ListView lvFriendsRequest = (ListView) requestLayout.findViewById(R.id.lvFriends);

			adapter = new SimpleAdapter(this, friendRequests, R.layout.single_request, 
					new String[] { TAG_FRIEND }, new int[] {R.id.tvFriendRequest});
			
			lvFriendsRequest.setAdapter(adapter);
			
			alertDialogBuilder.setView(requestLayout);
			
			// set dialog message
			alertDialogBuilder
				.setCancelable(false)
				.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
    }
    
    public void onClick(View v){
    	switch(v.getId()){
    	case R.id.imFriends:
           	Intent i = new Intent(HomeScreen.this, FriendsListActivity.class);
			startActivity(i);
			break;
    	case R.id.btnSignOut:
    		session.logoutUser();
    		finish();
    		break;
    	case R.id.btnRequest:
    		openRequestsDialog();
    		break;
    	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        this.getMenuInflater().inflate(R.menu.main, menu);
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

}
