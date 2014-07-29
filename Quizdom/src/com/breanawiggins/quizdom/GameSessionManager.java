package com.breanawiggins.quizdom;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class GameSessionManager {

    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREFER_NAME = "AndroidExamplePref";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";
    public static final String CATEGORY_CHOSEN = "category";
    public static final String USER_SCORE = "score";
    public static final String CHALLENGER_NAME = "ChallengerName";
    public static final String IS_ACCEPTED = "ChallengeAccepted";

    // Constructor
    public GameSessionManager(Context context) {
        this._context = context;
        this.pref = this._context.getSharedPreferences(PREFER_NAME,
                this.PRIVATE_MODE);
        this.editor = this.pref.edit();
        this.editor.commit();
    }

    // Create GAME session
    public void setQuizCategory(String category) {
        // Storing category in pref
        this.editor.putString(CATEGORY_CHOSEN, category);

        // commit changes
        this.editor.commit();
    }

    public void setFinalScore(int score) {
        this.editor.putInt(USER_SCORE, score);
        this.editor.commit();
    }

    public int getScore() {
        return this.pref.getInt(USER_SCORE, 0);
    }

    public HashMap<String, String> getUserDetails() {

        // Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        // user name
        user.put(KEY_NAME, this.pref.getString(KEY_NAME, null));

        // return user
        return user;
    }

    public void createChallenge(String challenger) {
        // Storing login value as TRUE
        this.editor.putBoolean(IS_ACCEPTED, false);
        // this.editor.putBoolean("")

        // Storing name in pref
        this.editor.putString(CHALLENGER_NAME, challenger);

        // commit changes
        this.editor.commit();
    }

    public String getChallenger() {
        return this.pref.getString(CHALLENGER_NAME, "");
    }

    public boolean isChallenged(boolean challenge) {
        return this.pref.getBoolean(IS_ACCEPTED, false);
    }

    public boolean challengeAccepted() {
        return this.pref.getBoolean(IS_ACCEPTED, false);
    }

}
