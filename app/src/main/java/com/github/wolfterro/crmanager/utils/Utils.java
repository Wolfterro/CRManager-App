package com.github.wolfterro.crmanager.utils;

public class Utils {
    public static String API_KEY = null;

    /* Public Methods */
    public static boolean isLogged() {
        if(API_KEY == null) {
            API_KEY = getApiKeyFromStorage();
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
}
