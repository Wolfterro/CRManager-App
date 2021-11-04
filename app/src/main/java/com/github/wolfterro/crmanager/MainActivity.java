package com.github.wolfterro.crmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.github.wolfterro.crmanager.login.UserProfile;

public class MainActivity extends AppCompatActivity {

    public TextView fullName;
    public TextView cpf;
    public TextView cr;
    public TextView mainAddress;
    public TextView secondAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fullName = (TextView) findViewById(R.id.userProfileFullName);
        cpf = (TextView) findViewById(R.id.userProfileCPF);
        cr = (TextView) findViewById(R.id.userProfileCR);
        mainAddress = (TextView) findViewById(R.id.userProfileMainAddress);
        secondAddress = (TextView) findViewById(R.id.userProfileSecondAddress);

        /* Retrieve UserProfile information */
        UserProfile userProfile = new UserProfile(this);
        userProfile.start();
    }
}