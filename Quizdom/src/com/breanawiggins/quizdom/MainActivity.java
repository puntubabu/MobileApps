package com.breanawiggins.quizdom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class MainActivity extends Activity implements OnClickListener {

    ImageButton btnLogin, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_mains);

        /*
         * if (savedInstanceState == null) {
         * this.getSupportFragmentManager().beginTransaction()
         * .add(R.id.container, new PlaceholderFragment()).commit(); }
         */
        View btnLogin = this.findViewById(R.id.buttonSignIn);
        btnLogin.setOnClickListener(this);
        View btnSignUp = this.findViewById(R.id.buttonSignUp);
        btnSignUp.setOnClickListener(this);
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
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container,
                    false);
            return rootView;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonSignIn) {
            this.startActivity(new Intent(this, Login.class));

        } else if (v.getId() == R.id.buttonSignUp) {
            this.startActivity(new Intent(this, SignUp.class));

        }

    }

}
