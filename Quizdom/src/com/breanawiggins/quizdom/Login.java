package com.breanawiggins.quizdom;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;


public class Login extends Activity implements OnClickListener {
    private EditText userNameEditableField;
    private EditText passwordEditableField;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    
    //testing from a real server:
    private static final String LOGIN_URL = "http://pradeepkeshary.com/webservice/login.php";
    
    //JSON element ids from response of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    //User session
    public static UserSessionManager session;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);
        
        //Grab session, if user is already logged in, then redirect to
        //home screen activity
        session = new UserSessionManager(getApplicationContext());
        if (session.isUserLoggedIn()){
           	Intent i = new Intent(Login.this, HomeScreen.class);
				startActivity(i);
				finish();
        }
        
        
        this.userNameEditableField = (EditText) this
                .findViewById(R.id.editTextUserNameToLogin);
        this.passwordEditableField = (EditText) this
                .findViewById(R.id.editTextPasswordToLogin);
        View btnLogin = this.findViewById(R.id.loginbutton);
        btnLogin.setOnClickListener(this);
    }

    class AttemptLogin extends AsyncTask<String, String, String> {

		 /**
        * Before starting background thread Show Progress Dialog
        * */
		boolean failure = false;
		
		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			 // Check for success tag
           int success;
           String username = userNameEditableField.getText().toString();
           String password = passwordEditableField.getText().toString();
           try {
               // Building Parameters
               ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
               params.add(new BasicNameValuePair("username", username));
               params.add(new BasicNameValuePair("password", password));

               Log.d("request!", "starting");
               // getting product details by making HTTP request
               JSONObject json = jsonParser.makeHttpRequest(
                      LOGIN_URL, "POST", params);

               // check your log for json response
               Log.d("Login attempt", json.toString());

               // json success tag
               success = json.getInt(TAG_SUCCESS);
               if (success == 1) {
               	session.createUserLoginSession(username);
               	Log.d("Login Successful!", json.toString());
               	Intent i = new Intent(Login.this, HomeScreen.class);
   				startActivity(i);
   				finish();
               	return json.getString(TAG_MESSAGE);
               }else{
               	Log.d("Login Failure!", json.getString(TAG_MESSAGE));
               	return json.getString(TAG_MESSAGE);

               }
           } catch (JSONException e) {
               e.printStackTrace();
           }

           return null;

		}
		/**
        * After completing background task Dismiss the progress dialog
        * **/
       protected void onPostExecute(String file_url) {
           if (file_url != null){
           	Toast.makeText(Login.this, file_url, Toast.LENGTH_LONG).show();
           }
       }
    }
    

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.loginbutton) {
        	new AttemptLogin().execute();
        }
    }
}