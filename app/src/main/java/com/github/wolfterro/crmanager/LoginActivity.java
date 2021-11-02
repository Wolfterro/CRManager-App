package com.github.wolfterro.crmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.wolfterro.crmanager.login.Login;
import com.github.wolfterro.crmanager.utils.Utils;

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

                startLoginProcess(email_string, password_string);
            }
        });
    }

    public void startLoginProcess(String email, String password) {
        Login login = new Login(email, password);
        try {
            Utils.API_KEY = login.login();
        } catch(NullPointerException e) {
            Toast.makeText(getBaseContext(), getString(R.string.couldNotLogin), Toast.LENGTH_SHORT).show();
        }

        if(Utils.isLogged()) {
            Intent intent = new Intent(
                    LoginActivity.this, MainActivity.class
            );
            startActivity(intent);
            finish();
        }
    }
}