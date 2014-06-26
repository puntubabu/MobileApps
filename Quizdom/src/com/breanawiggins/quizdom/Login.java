package com.breanawiggins.quizdom;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class Login extends Activity implements OnClickListener {
    private DatabaseHelper dh;
    private EditText userNameEditableField;
    private EditText passwordEditableField;
    private final static String OPT_NAME = "name";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);

        this.userNameEditableField = (EditText) this
                .findViewById(R.id.editTextUserNameToLogin);
        this.passwordEditableField = (EditText) this
                .findViewById(R.id.editTextPasswordToLogin);
        View btnLogin = this.findViewById(R.id.loginbutton);
        btnLogin.setOnClickListener(this);
    }

    private void checkLogin() {
        String username = this.userNameEditableField.getText().toString();
        String password = this.passwordEditableField.getText().toString();
        this.dh = new DatabaseHelper(this);
        List<String> names = this.dh.selectAll(username, password);
        if (names.size() > 0) { // Login successful
            // Save username as the name of the player
            SharedPreferences settings = PreferenceManager
                    .getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(OPT_NAME, username);
            editor.commit();

            // Bring up the Home screen
            this.startActivity(new Intent(this, HomeScreen.class));
            // startActivity(new Intent(this, DummyActivity.class));
            this.finish();
        } else {
            // Try again?
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Login failed")
                    .setNeutralButton("Try Again",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                        int which) {
                                }
                            }).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.loginbutton) {
            this.checkLogin();
        }
    }
}