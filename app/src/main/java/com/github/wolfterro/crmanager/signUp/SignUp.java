package com.github.wolfterro.crmanager.signUp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import com.github.wolfterro.crmanager.MainActivity;
import com.github.wolfterro.crmanager.utils.Utils;

public class SignUp extends Thread {

    public Activity activity;
    public ProgressDialog pd;

    public SignUp(Activity activity, ProgressDialog pd) {
        this.activity = activity;
        this.pd = pd;
    }

    @Override
    public void run() {
        // TODO: Check Fields; Build request body; Send POST to create UserProfile

        // Set Created USER_PROFILE_ID and API_KEY (MOCKED FOR NOW)
        Utils.USER_PROFILE_ID = "8";
        Utils.API_KEY = "0802048490c5e5f6e56581360484c8eb9c41de4e";

        // Start MainActivity
        Intent i = new Intent(this.activity, MainActivity.class);
        this.activity.startActivity(i);

        this.pd.cancel();
    }
}
