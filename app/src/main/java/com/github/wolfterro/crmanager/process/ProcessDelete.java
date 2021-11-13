package com.github.wolfterro.crmanager.process;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.widget.Toast;

import com.github.wolfterro.crmanager.R;
import com.github.wolfterro.crmanager.utils.API;

public class ProcessDelete extends Thread {

    public Activity activity;
    public Integer processId;

    public ProcessDelete(Activity activity, Integer processId) {
        this.activity = activity;
        this.processId = processId;
    }

    @Override
    public void run() {
        int status = API.deleteProcess(processId);
        showToast(status);
    }

    @SuppressLint("DefaultLocale")
    private void showToast(Integer status) {
        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                if(status == 204) {
                    Toast.makeText(
                            activity.getBaseContext(),
                            activity.getString(R.string.processDeletedMessage),
                            Toast.LENGTH_LONG
                    ).show();
                    activity.onBackPressed();
                } else {
                    String message = String.format(
                            "%s - Erro: %d",
                            activity.getString(R.string.couldNotDeleteProcessMessage),
                            status
                    );

                    Toast.makeText(
                            activity.getBaseContext(),
                            message,
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });
    }
}
