package com.github.wolfterro.crmanager.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Utils {
    public static boolean isDebug = true;
    public static String BASE_URL = getBaseURL();

    public static String USER_PROFILE_ID = null;
    public static String API_KEY = null;

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
            String complement = addressJson.getString("complement");
            String zipCode = addressJson.getString("zip_code");
            String city = addressJson.getString("city");
            String neighborhood = addressJson.getString("neighborhood");
            String uf = addressJson.getString("uf");

            String templateString = "%s, %s%s CEP: %s - %s - %s, %s";
            if(complement != null || !complement.equals("")) {
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
            return "http://192.168.0.220:8000/";
        } else {
            return "";
        }
    }
}
