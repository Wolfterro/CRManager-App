package com.github.wolfterro.crmanager.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Utils {
    public static boolean isDebug = true;
    public static String BASE_URL = getBaseURL();

    public static String USER_PROFILE_ID = null;
    public static String API_KEY = null;

//    public static String USER_PROFILE_ID = "8";
//    public static String API_KEY = "0802048490c5e5f6e56581360484c8eb9c41de4e";

    public static ArrayList<String> serviceTypeValuesList = new ArrayList<>();

    /* Public Methods */
    public static boolean isLogged() {
        if(API_KEY == null) {
            API_KEY = getApiKeyFromStorage();
            USER_PROFILE_ID = getUserProfileIdFromStorage();
        }

        if(API_KEY == null) {
            return false;
        }

        return true;
    }

    public static String getFormattedAddressFromJson(JSONObject addressJson) {
        try {
            String address = addressJson.getString("address");
            String number = addressJson.getString("number");
            String complement = null;
            if(!addressJson.isNull("complement")) {
               complement = addressJson.getString("complement");
            } else {
                complement = "";
            }

            String zipCode = addressJson.getString("zip_code");
            String city = addressJson.getString("city");
            String neighborhood = addressJson.getString("neighborhood");
            String uf = addressJson.getString("uf");

            String templateString = "%s, %s%s CEP: %s - %s - %s, %s";
            if(!complement.equals("")) {
                return String.format(
                        templateString,
                        address,
                        number,
                        String.format(" (%s)", complement),
                        zipCode,
                        neighborhood,
                        city,
                        uf
                );
            } else {
                return String.format(
                        templateString,
                        address,
                        number,
                        "",
                        zipCode,
                        neighborhood,
                        city,
                        uf
                );
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR", e.toString());
        }

        return "-";
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatDate(String date) {
        SimpleDateFormat fromUser = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

        String newFormat = date;
        try {
            newFormat = myFormat.format(fromUser.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newFormat;
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatDateReverse(String date) {
        if(date == null || date.equals("-")) {
            return date;
        }

        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");

        String newFormat = date;
        try {
            newFormat = myFormat.format(fromUser.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newFormat;
    }

    /* Private Methods */
    private static String getApiKeyFromStorage() {
        /* TODO: Create storage to store ApiKey from logged user */
        return null;
    }

    private static String getUserProfileIdFromStorage() {
        /* TODO: Create storage to store User Profile ID from logged user */
        return null;
    }

    private static String getBaseURL() {
        if(isDebug) {
            return "https://backend-crmanager.herokuapp.com/"; // "http://192.168.0.219:8000/";
        } else {
            return "";
        }
    }
}
