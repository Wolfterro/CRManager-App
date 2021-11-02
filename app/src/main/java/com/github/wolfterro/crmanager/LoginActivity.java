package com.github.wolfterro.crmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    public EditText email = null;
    public EditText password = null;
    public Button loginButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* Setting up elements */
        email = (EditText) findViewById(R.id.loginEmail);
        password = (EditText) findViewById(R.id.loginPassword);
        loginButton = (Button) findViewById(R.id.buttonLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email_string = email.getText().toString();
                String password_string = password.getText().toString();
            }
        });
    }
}