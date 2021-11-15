package com.github.wolfterro.crmanager.process;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.wolfterro.crmanager.R;
import com.github.wolfterro.crmanager.utils.API;
import com.github.wolfterro.crmanager.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceType extends Thread {

    public Activity activity;
    public Spinner serviceTypeSpinner;

    public JSONArray serviceType;
    public JSONObject processDetail;
    public Boolean isEdit = false;

    public ServiceType(Activity activity, Spinner serviceTypeSpinner, JSONObject processDetail, Boolean isEdit) {
        this.activity = activity;
        this.serviceTypeSpinner = serviceTypeSpinner;

        this.processDetail = processDetail;
        this.isEdit = isEdit;
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
                Utils.serviceTypeValuesList.add(service.getString("value"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return serviceTypeList;
    }

    private void setServiceTypeOnSpinner(ArrayList<String> serviceTypeList) {

        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        activity,
                        R.layout.custom_spinner_item,
                        serviceTypeList
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                serviceTypeSpinner.setAdapter(adapter);

                if(isEdit) {
                    try {
                        int valuePos = adapter.getPosition(processDetail.getString("service_label"));
                        serviceTypeSpinner.setSelection(valuePos);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}
