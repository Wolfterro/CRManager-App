package com.github.wolfterro.crmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.wolfterro.crmanager.login.UserProfile;
import com.github.wolfterro.crmanager.process.Process;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    public TextView fullName;
    public TextView cpfAndCR;
    public TextView mainAddress;
    public TextView secondAddress;
    public CardView userProfileCard;
    public CircleImageView photo;
    public RecyclerView recyclerView;
    public FloatingActionButton actionButton;
    public UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fullName = (TextView) findViewById(R.id.userProfileFullName);
        cpfAndCR = (TextView) findViewById(R.id.userProfileCPFAndCR);
        mainAddress = (TextView) findViewById(R.id.userProfileMainAddress);
        secondAddress = (TextView) findViewById(R.id.userProfileSecondAddress);
        photo = (CircleImageView) findViewById(R.id.userProfilePhoto);
        recyclerView = (RecyclerView) findViewById(R.id.processListRecyclerView);
        actionButton = (FloatingActionButton) findViewById(R.id.addProcessActionButton);
        userProfileCard = (CardView) findViewById(R.id.userProfileCardView);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ProcessAddActivity.class);
                startActivity(i);
            }
        });

        userProfileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userProfile.getUserProfile() != null) {
                    Intent i = new Intent(MainActivity.this, UserProfileDetailActivity.class);
                    i.putExtra("USERPROFILE", userProfile.getUserProfile().toString());

                    startActivity(i);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        /* Retrieve UserProfile information */
        userProfile = new UserProfile(this);
        userProfile.start();

        /* Retrieve Process Information */
        Process process = new Process(this, recyclerView);
        process.start();
    }
}