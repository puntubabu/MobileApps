package com.breanawiggins.quizdom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class EndGame extends Activity implements OnClickListener {

    public static GameSessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.endofgame);
        session = new GameSessionManager(this.getApplicationContext());
        int score = session.getScore();
        String userscore = Integer.toString(score);
        TextView sc = (TextView) this.findViewById(R.id.scoretxt);
        sc.setText("Score = " + userscore);

        // handle button actions
        Button finishBtn = (Button) this.findViewById(R.id.playagain);
        finishBtn.setOnClickListener(this);
        Button answerBtn = (Button) this.findViewById(R.id.gohome);
        answerBtn.setOnClickListener(this);

    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
     * 
     * This method is to override the back button on the phone to prevent users
     * from navigating back in to the quiz
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.playagain) {
            this.startActivity(new Intent(this, Quiz.class));
            ;

        } else if (v.getId() == R.id.gohome) {
            this.startActivity(new Intent(this, HomeScreen.class));
        }
    }

}