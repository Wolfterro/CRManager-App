package com.github.wolfterro.crmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.wolfterro.crmanager.signUp.SignUp;

public class SignUpActivity extends AppCompatActivity {

    public Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpButton = (Button) findViewById(R.id.buttonSignup);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignUpProcess();
            }
        });
    }

    public void startSignUpProcess() {
        ProgressDialog pd = new ProgressDialog(SignUpActivity.this);

        pd.setTitle(getString(R.string.addingProcess));
        pd.setMessage(getString(R.string.pleaseStandBy));

        pd.setCancelable(false);
        pd.show();

        SignUp signUp = new SignUp(SignUpActivity.this, pd);
        signUp.start();
    }
}