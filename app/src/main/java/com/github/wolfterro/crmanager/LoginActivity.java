package com.github.wolfterro.crmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
    public Button signUpButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* Setting up elements */
        email = (EditText) findViewById(R.id.loginEmail);
        password = (EditText) findViewById(R.id.loginPassword);
        loginButton = (Button) findViewById(R.id.buttonLogin);
        signUpButton = (Button) findViewById(R.id.buttonSignup);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email_string = email.getText().toString();
                String password_string = password.getText().toString();

                startLoginProcess(email_string, password_string);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
    }

    public void startLoginProcess(String email, String password) {
        ProgressDialog pd = new ProgressDialog(LoginActivity.this);

        pd.setTitle(getString(R.string.startLogin));
        pd.setMessage(getString(R.string.pleaseStandBy));

        pd.setCancelable(false);
        pd.show();

        Login login = new Login(email, password, pd, getBaseContext());
        login.start();
    }
}