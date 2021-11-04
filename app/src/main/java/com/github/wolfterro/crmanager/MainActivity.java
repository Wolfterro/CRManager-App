package com.github.wolfterro.crmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.github.wolfterro.crmanager.login.UserProfile;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    public TextView fullName;
    public TextView cpfAndCR;
    public TextView mainAddress;
    public TextView secondAddress;
    public CircleImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fullName = (TextView) findViewById(R.id.userProfileFullName);
        cpfAndCR = (TextView) findViewById(R.id.userProfileCPFAndCR);
        mainAddress = (TextView) findViewById(R.id.userProfileMainAddress);
        secondAddress = (TextView) findViewById(R.id.userProfileSecondAddress);
        photo = (CircleImageView) findViewById(R.id.userProfilePhoto);

        /* Retrieve UserProfile information */
        UserProfile userProfile = new UserProfile(this);
        userProfile.start();
    }
}