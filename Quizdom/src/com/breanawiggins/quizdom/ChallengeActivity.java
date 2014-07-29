package com.breanawiggins.quizdom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class ChallengeActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_challenge);

        View btnFriend = this.findViewById(R.id.challenge_friend);
        btnFriend.setOnClickListener(this);
        View btnChallenge = this.findViewById(R.id.challenge_random);
        btnChallenge.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        // Intent i;
        if (v.getId() == R.id.challenge_friend) {
            this.startActivity(new Intent(this, ChallengeFriend.class));
            // this.startActivity(new Intent(this, idk.class));
        }

    }

    /**
     * A placeholder fragment containing a simple view.
     */

}