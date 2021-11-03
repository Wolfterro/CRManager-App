package com.github.wolfterro.crmanager.login;

import android.util.Log;

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

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /* Public Methods */
    @Override
    public void run() {
        body = getLoginResponse();
        if(this.body != null) {
            setApiKeyAndUserProfileId();
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
}
