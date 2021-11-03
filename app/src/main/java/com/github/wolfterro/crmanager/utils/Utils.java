package com.github.wolfterro.crmanager.utils;

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
