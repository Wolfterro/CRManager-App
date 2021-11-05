package com.github.wolfterro.crmanager.process;

import android.app.Activity;
import android.widget.Toast;

import com.github.wolfterro.crmanager.R;
import com.github.wolfterro.crmanager.utils.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Process extends Thread {
    public Activity activity;
    public JSONArray processList;

    public Process(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {
        JSONArray processList = API.getProcessList();

        if(processList != null) {
            this.processList = processList;
            setProcessListOnActivity(processList);
        } else {
            showToast(this.activity, this.activity.getString(R.string.couldNotRetrieveProcessList));
        }
    }

    public void setProcessListOnActivity(JSONArray processList) {
        /* TODO: Set process informations on MainActivity */
        for (int i = 0; i < processList.length(); i++) {
            try {
                JSONObject process = processList.getJSONObject(i);
                // TODO: Add process on processList element
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void showToast(Activity activity, String message) {
        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
