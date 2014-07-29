package com.breanawiggins.quizdom;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.breanawiggins.quizdom.FriendsListActivity.DeleteFriend;
import com.breanawiggins.quizdom.FriendsListActivity.GetFriends;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class SettingsActivity extends Activity {
	//TAGS
	private static final String TAG = "SettingsActivity";
	private static final String DELETE_ACCOUNT_URL = "http://www.pradeepkeshary.com/webservice/deleteaccount.php";
    private static final String TAG_SUCCESS = "success";

	UserSessionManager session;
	
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    JSONArray jarrayFriends = null;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		session = new UserSessionManager(getApplicationContext());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
	}
	
	public void onClick(View v){
		switch (v.getId()){
		case R.id.btnDeleteAccount:
			deleteAccountDialog();
			break;
		case R.id.btnChangePassword:
			break;
		case R.id.btnChangePicture:
			break;
		}
	}
	
	//Open delete Account Dialog
	private void deleteAccountDialog(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);
 
			// set title
			alertDialogBuilder.setTitle("Delete Account? :(");
 
			// set dialog message
			alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("Delete!",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						new DeleteAccount().execute();
					}
				  })
				.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					}
				});
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
	}
	
	//Runs Script to delete account
	private void deleteAccount(){
		int success;
		String strUser = session.getUserDetails().get("name");
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", strUser));
        try{
        JSONObject json = jsonParser.makeHttpRequest(
                DELETE_ACCOUNT_URL, "POST", params);
        
        //json success tag
        success = json.getInt(TAG_SUCCESS);
        
        if (success == 1){
        	session.logoutUser();
        }
        }
        catch (JSONException e){
        	e.printStackTrace();
        }
	}
	public class DeleteAccount extends AsyncTask<Void, Void, Boolean>{
		@Override
		protected Boolean doInBackground(Void... arg0) {
			deleteAccount();
			return null;
		}
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			session.logoutUser();
        	runOnUiThread(new Runnable() {
        		public void run() {
        			Toast.makeText(SettingsActivity.this, "We will miss you :(", Toast.LENGTH_LONG).show();
        		    }
        		});
			finish();
		}
	}
}
