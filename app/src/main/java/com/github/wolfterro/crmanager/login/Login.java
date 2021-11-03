package com.github.wolfterro.crmanager.login;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.github.wolfterro.crmanager.MainActivity;
import com.github.wolfterro.crmanager.R;
import com.github.wolfterro.crmanager.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
        body = getLoginResponse();

        if(this.body != null) {
            setApiKeyAndUserProfileId();
            loginSuccessful.sendEmptyMessage(0);
        } else {
            loginFailure.sendEmptyMessage(0);
        }
    }

    /* Private Methods */
    private String getLoginResponse() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(Utils.BASE_URL + "login/")
                .post(getRequestBody())
                .build();

        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            if(response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ERROR", e.toString());
        }

        return null;
    }

    private RequestBody getRequestBody() {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        JSONObject bodyJson = new JSONObject();
        try {
            bodyJson.put("email", this.email);
            bodyJson.put("password", this.password);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return RequestBody.create(JSON, bodyJson.toString());
    }

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
