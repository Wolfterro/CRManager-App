package com.github.wolfterro.crmanager.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class API {
    public static JSONObject getUserProfileInfo() {
        String url = String.format(
                "%s%s/%s",
                Utils.BASE_URL,
                "user_profile",
                Utils.USER_PROFILE_ID
        );
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", String.format("Token %s", Utils.API_KEY))
                .build();

        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            if(response.isSuccessful()) {
                JSONObject jsonObject = new JSONObject(response.body().string());
                return jsonObject;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ERROR", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR", e.toString());
        }

        return null;
    }
}
