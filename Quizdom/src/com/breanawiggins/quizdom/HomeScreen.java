package com.breanawiggins.quizdom;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class HomeScreen extends ActionBarActivity {

    ImageButton btnLogin, btnSignUp;
    TextView tvCurrentUser;
    public static UserSessionManager session;
    Button requestButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_home);
        session = new UserSessionManager(getApplicationContext());
        tvCurrentUser = (TextView)findViewById(R.id.tvCurrentUser);
        setCurrentUserText();
        final Animation animScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
        requestButton = (Button) findViewById(R.id.btnRequest);
        requestButton.startAnimation(animScale);
        
    }
    
    private void setCurrentUserText(){
        HashMap<String, String> user = new HashMap<String, String>();
        user = session.getUserDetails();
        String strUser = user.get("name");
    	tvCurrentUser.setText("Logged in as: "+strUser);
    }
    
    public void onClick(View v){
    	switch(v.getId()){
    	case R.id.imFriends:
           	Intent i = new Intent(HomeScreen.this, FriendsListActivity.class);
           	finish();
			startActivity(i);
			break;
    	case R.id.btnSignOut:
    		session.logoutUser();
    		finish();
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
