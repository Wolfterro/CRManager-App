package com.github.wolfterro.crmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class UserProfileDetailActivity extends AppCompatActivity {

    public JSONObject userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_detail);

        getUserProfile();

        // TODO: Set UserProfileDetailActivity layout and set values
    }

    private void getUserProfile() {
        Intent i = getIntent();
        String userProfileString = i.getStringExtra("USERPROFILE");

        try {
            userProfile = new JSONObject(userProfileString);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR", e.toString());
        }
    }
}