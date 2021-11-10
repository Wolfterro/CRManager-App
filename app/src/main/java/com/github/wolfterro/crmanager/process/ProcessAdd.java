package com.github.wolfterro.crmanager.process;

import android.app.Activity;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.wolfterro.crmanager.R;
import com.github.wolfterro.crmanager.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProcessAdd extends Thread {
    public Activity activity;

    // ProcessAddActivity Elements
    public EditText protocol;
    public EditText entryDate;
    public EditText gruEntryDate;
    public EditText OM;

    public EditText name;
    public EditText manufacturer;
    public EditText quantity;

    public Spinner gruStatus;
    public Spinner processStatus;
    public Spinner serviceType;
    public Spinner pceType;

    public ProcessAdd(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {
        instantiateElements();
        JSONObject body = getBody();

        // TODO: Send POST request with body

        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(activity.getBaseContext(), activity.getString(R.string.processAdded), Toast.LENGTH_LONG).show();
                activity.onBackPressed();
            }
        });
    }

    // Private Methods
    // ---------------
    private JSONObject getBody() {
        HashMap<String, Object> bodyHash = new HashMap<>();
        HashMap<String, Object> pceHash = new HashMap<>();

        bodyHash.put("user", Integer.parseInt(Utils.USER_PROFILE_ID));
        bodyHash.put("protocol", this.protocol.getText().toString());

        if(this.entryDate.getText() != null) {
            bodyHash.put("entry_date", Utils.formatDate(this.entryDate.getText().toString()));
        } else {
            bodyHash.put("entry_date", null);
        }
        bodyHash.put("service", getServiceValue());
        bodyHash.put("status", getProcessStatusValue());
        bodyHash.put("om", this.OM.getText().toString());
        bodyHash.put("gru_status", getGRUStatusValue());

        if(this.entryDate.getText() != null) {
            bodyHash.put("gru_compensation_date", Utils.formatDate(this.entryDate.getText().toString()));
        } else {
            bodyHash.put("gru_compensation_date", null);
        }

        if(this.name.getText() != null && this.manufacturer.getText() != null && this.quantity.getText() != null) {
            pceHash.put("name", this.name.getText().toString());
            pceHash.put("manufacturer", this.manufacturer.getText().toString());
            pceHash.put("quantity", Integer.parseInt(this.quantity.getText().toString()));
            pceHash.put("pce_type", getPCETypeValue());
        } else {
            pceHash = null;
        }

        bodyHash.put("pce", pceHash);
        return new JSONObject(bodyHash);
    }

    private void instantiateElements() {
        protocol = (EditText) this.activity.findViewById(R.id.editTextProtocolNumber);
        entryDate = (EditText) this.activity.findViewById(R.id.editTextEntryDate);
        gruEntryDate = (EditText) this.activity.findViewById(R.id.editTextGRUEntryDate);
        OM = (EditText) this.activity.findViewById(R.id.editTextOM);

        gruStatus = (Spinner) this.activity.findViewById(R.id.processGRUStatusSpinner);
        processStatus = (Spinner) this.activity.findViewById(R.id.processStatusSpinner);
        serviceType = (Spinner) this.activity.findViewById(R.id.serviceTypeSpinner);
        pceType = (Spinner) this.activity.findViewById(R.id.processPCEType);

        name = (EditText) this.activity.findViewById(R.id.editTextPCEName);
        manufacturer = (EditText) this.activity.findViewById(R.id.editTextPCEManufacturer);
        quantity = (EditText) this.activity.findViewById(R.id.editTextPCEQuantity);
    }

    private String getServiceValue() {
        int index = serviceType.getSelectedItemPosition();
        return Utils.serviceTypeValuesList.get(index);
    }

    private String getProcessStatusValue() {
        int index = processStatus.getSelectedItemPosition();
        String[] processStatusArray = this.activity.getResources().getStringArray(R.array.process_status_array_value);

        return processStatusArray[index];
    }

    private String getPCETypeValue() {
        int index = pceType.getSelectedItemPosition();
        String[] pceTypeValueList = this.activity.getResources().getStringArray(R.array.pce_type_array_value);

        return pceTypeValueList[index];
    }

    private String getGRUStatusValue() {
        int index = gruStatus.getSelectedItemPosition();
        String[] gruStatusArray = this.activity.getResources().getStringArray(R.array.gru_status_array_value);

        return gruStatusArray[index];
    }
}
