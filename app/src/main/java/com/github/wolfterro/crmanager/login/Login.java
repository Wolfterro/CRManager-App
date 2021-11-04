package com.github.wolfterro.crmanager.login;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.github.wolfterro.crmanager.MainActivity;
import com.github.wolfterro.crmanager.R;
import com.github.wolfterro.crmanager.utils.API;
import com.github.wolfterro.crmanager.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends Thread {
    private String email;
    private String password;
    private String body;

    public ProgressDialog pd;
    public Context c;

    public Login(String email, String password, ProgressDialog pd, Context c) {
        this.email = email;
        this.password = password;

        this.pd = pd;
        this.c = c;
    }

    /* Public Methods */
    @Override
    public void run() {
        body = API.getLoginResponse(this.email, this.password);

        if(this.body != null) {
            setApiKeyAndUserProfileId();
            loginSuccessful.sendEmptyMessage(0);
        } else {
            loginFailure.sendEmptyMessage(0);
        }
    }

    /* Private Methods */
    private void setApiKeyAndUserProfileId() {
        try {
            JSONObject json = new JSONObject(this.body);

            Utils.API_KEY = json.get("token").toString();
            Utils.USER_PROFILE_ID = json.get("user_profile_id").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler loginSuccessful = new Handler() {
        @Override
        public void handleMessage(Message m) {
            pd.dismiss();

            Intent i = new Intent(c, MainActivity.class);
            c.startActivity(i);
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler loginFailure = new Handler() {
        @Override
        public void handleMessage(Message m) {
            pd.dismiss();

            String err = c.getString(R.string.couldNotLogin);
            Toast.makeText(c, err, Toast.LENGTH_LONG).show();
        }
    };
}
