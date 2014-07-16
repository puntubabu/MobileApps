package com.breanawiggins.quizdom;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends Activity implements OnClickListener {
    private EditText etUsername,etPassword,etConfirm;

	 // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    
  //testing from a real server:
    private static final String LOGIN_URL = "http://pradeepkeshary.com/webservice/register.php";

    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_signup);

        this.etUsername = (EditText) this.findViewById(R.id.editTextUserName);
        this.etPassword = (EditText) this.findViewById(R.id.editTextPassword);
        this.etConfirm = (EditText) this
                .findViewById(R.id.editTextConfirmPassword);
        View btnAdd = this.findViewById(R.id.buttonCreate);
        btnAdd.setOnClickListener(this);
    }

    	class CreateUser extends AsyncTask<String, String, String> {

   		 /**
            * Before starting background thread Show Progress Dialog
            * */
   		boolean failure = false;

   		@Override
   		protected String doInBackground(String... args) {
   			// TODO Auto-generated method stub
   			 // Check for success tag
               int success;
               String username = etUsername.getText().toString();
               String password = etPassword.getText().toString();
               String confirmPassword = etConfirm.getText().toString();
               try {
                   // Building Parameters
                   List<NameValuePair> params = new ArrayList<NameValuePair>();
                   params.add(new BasicNameValuePair("username", username));
                   params.add(new BasicNameValuePair("password", password));

                   Log.d("request!", "starting");

                   //Posting user data to script
                   JSONObject json = jsonParser.makeHttpRequest(
                          LOGIN_URL, "POST", params);

                   // full json response
                   //Log.d("Login attempt", json.toString());

                   // json success element
                   success = json.getInt(TAG_SUCCESS);
                   if (success == 1) {
                   	Log.d("User Created!", json.toString());
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
               // dismiss the dialog once product deleted
               //pDialog.dismiss();
               if (file_url != null){
               	Toast.makeText(SignUp.this, file_url, Toast.LENGTH_LONG).show();
               }

           }

   	}
    	
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonCreate) {
            new CreateUser().execute();
            //this.finish();
        }
    }
}