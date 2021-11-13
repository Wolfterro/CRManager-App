package com.github.wolfterro.crmanager.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class API {
    /* Public Methods */
    public static JSONObject getUserProfileInfo() {
        String url = String.format(
                "%s%s/%s",
                Utils.BASE_URL,
                "user_profile",
                Utils.USER_PROFILE_ID
        );
        return getResponse(url);
    }

    public static JSONArray getProcessList() {
        String url = String.format("%s%s", Utils.BASE_URL, "process/");
        return getResponseList(url);
    }

    public static JSONArray getServiceTypeList() {
        String url = String.format("%s%s", Utils.BASE_URL, "service_type/");
        return getResponseList(url);
    }

    public static String getLoginResponse(String email, String password) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(Utils.BASE_URL + "login/")
                .post(getRequestBody(email, password))
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

    public static int createProcess(JSONObject body) {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        Request request = new Request.Builder()
                .url(Utils.BASE_URL + "process/")
                .addHeader("Authorization", String.format("Token %s", Utils.API_KEY))
                .post(RequestBody.create(JSON, body.toString()))
                .build();

        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            return response.code();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ERROR", e.toString());
            return 0;
        }
    }

    /* Private Methods */
    private static JSONObject getResponse(String url) {
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

    private static JSONArray getResponseList(String url) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", String.format("Token %s", Utils.API_KEY))
                .build();

        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            if(response.isSuccessful()) {
                JSONArray jsonArray = new JSONArray(response.body().string());
                return jsonArray;
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

    private static RequestBody getRequestBody(String email, String password) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        JSONObject bodyJson = new JSONObject();
        try {
            bodyJson.put("email", email);
            bodyJson.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return RequestBody.create(JSON, bodyJson.toString());
    }
}
