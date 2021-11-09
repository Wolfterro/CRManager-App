package com.github.wolfterro.crmanager.process;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.wolfterro.crmanager.R;
import com.github.wolfterro.crmanager.utils.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceType extends Thread {

    public Activity activity;
    public Spinner serviceTypeSpinner;

    public JSONArray serviceType;

    public ServiceType(Activity activity, Spinner serviceTypeSpinner) {
        this.activity = activity;
        this.serviceTypeSpinner = serviceTypeSpinner;
    }

    @Override
    public void run() {
        this.serviceType = API.getServiceTypeList();

        if(this.serviceType != null) {
            ArrayList<String> serviceTypeList = getServiceTypeList();
            setServiceTypeOnSpinner(serviceTypeList);
        }
    }

    private ArrayList<String> getServiceTypeList() {
        ArrayList<String> serviceTypeList = new ArrayList<>();

        for(int i = 0; i < this.serviceType.length(); i++) {
            try {
                JSONObject service = this.serviceType.getJSONObject(i);
                serviceTypeList.add(service.getString("label"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return serviceTypeList;
    }

    private void setServiceTypeOnSpinner(ArrayList<String> serviceTypeList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.activity,
                R.layout.custom_spinner_item,
                serviceTypeList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceTypeSpinner.setAdapter(adapter);
    }

}