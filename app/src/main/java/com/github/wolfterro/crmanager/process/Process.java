package com.github.wolfterro.crmanager.process;

import android.app.Activity;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.wolfterro.crmanager.R;
import com.github.wolfterro.crmanager.adapters.ProcessListAdapter;
import com.github.wolfterro.crmanager.utils.API;

import org.json.JSONArray;

public class Process extends Thread {
    public Activity activity;
    public JSONArray processList;
    public RecyclerView processListView;

    public Process(Activity activity, RecyclerView processListView) {
        this.activity = activity;
        this.processListView = processListView;
    }

    @Override
    public void run() {
        JSONArray processList = API.getProcessList();
        this.processList = processList;

        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                if(processList != null) {
                    setProcessListOnActivity();
                } else {
                    Toast.makeText(
                            activity,
                            activity.getString(R.string.couldNotRetrieveProcessList),
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });
    }

    public void setProcessListOnActivity() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.processListView.setLayoutManager(linearLayoutManager);

        ProcessListAdapter processListAdapter = new ProcessListAdapter(this.processList, this.activity);
        this.processListView.setAdapter(processListAdapter);
    }
}
