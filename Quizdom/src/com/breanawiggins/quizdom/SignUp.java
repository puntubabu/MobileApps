package com.breanawiggins.quizdom;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends Activity implements OnClickListener {
    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirm;
    private DatabaseHelper dh;

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

    private void CreateAccount() {
        // this.output = (TextView) this.findViewById(R.id.out_text);
        String username = this.etUsername.getText().toString();
        String password = this.etPassword.getText().toString();
        String confirm = this.etConfirm.getText().toString();
        if ((password.equals(confirm)) && (!username.equals(""))
                && (!password.equals("")) && (!confirm.equals(""))) {
            this.dh = new DatabaseHelper(this);
            this.dh.insert(username, password);
            // this.labResult.setText("Added");
            Toast.makeText(SignUp.this, "new record inserted",
                    Toast.LENGTH_SHORT).show();
            this.finish();
            // Bring up the Home screen
            // this.startActivity(new Intent(this, HomeScreen.class));
        } else if ((username.equals("")) || (password.equals(""))
                || (confirm.equals(""))) {
            Toast.makeText(SignUp.this, "Missing entry", Toast.LENGTH_SHORT)
                    .show();
        } else if (!password.equals(confirm)) {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("passwords do not match")
                    .setNeutralButton("Try Again",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                        int which) {
                                }
                            })

                    .show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonCreate) {
            this.CreateAccount();
            this.finish();
        }
    }
}