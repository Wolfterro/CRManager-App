package com.github.wolfterro.crmanager.process;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.wolfterro.crmanager.MainActivity;
import com.github.wolfterro.crmanager.R;
import com.github.wolfterro.crmanager.utils.API;
import com.github.wolfterro.crmanager.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ProcessEdit extends Thread {

    public Activity activity;
    public ProgressDialog pd;
    public int id;

    // ProcessEditActivity Elements
    public EditText protocol;
    public EditText entryDate;
    public EditText gruEntryDate;
    public EditText OM;
    public EditText reason;

    public EditText name;
    public EditText manufacturer;
    public EditText quantity;

    public Spinner gruStatus;
    public Spinner processStatus;
    public Spinner serviceType;
    public Spinner pceType;

    public ProcessEdit(Activity activity, ProgressDialog pd, int id) {
        this.activity = activity;
        this.pd = pd;
        this.id = id;
    }

    @Override
    public void run() {
        instantiateElements();
        JSONObject body = getBody();
        Boolean isValid = validateFields(body);

        if(isValid) {
            int status = API.editProcess(body, this.id);
            showToast(status);
        }

        this.pd.dismiss();
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

        if(!this.reason.getText().toString().equals("")) {
            bodyHash.put("reason", this.reason.getText().toString());
        } else {
            bodyHash.put("reason", null);
        }

        if(this.gruEntryDate.getText() != null) {
            bodyHash.put("gru_compensation_date", Utils.formatDate(this.gruEntryDate.getText().toString()));
        } else {
            bodyHash.put("gru_compensation_date", null);
        }

        if(!this.name.getText().toString().equals("") &&
                !this.manufacturer.getText().toString().equals("") &&
                !this.quantity.getText().toString().equals(""))
        {
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
        protocol = (EditText) this.activity.findViewById(R.id.editTextProtocolNumberEdit);
        entryDate = (EditText) this.activity.findViewById(R.id.editTextEntryDateEdit);
        gruEntryDate = (EditText) this.activity.findViewById(R.id.editTextGRUEntryDateEdit);
        OM = (EditText) this.activity.findViewById(R.id.editTextOMEdit);
        reason = (EditText) this.activity.findViewById(R.id.editTextReasonEdit);

        gruStatus = (Spinner) this.activity.findViewById(R.id.processGRUStatusSpinnerEdit);
        processStatus = (Spinner) this.activity.findViewById(R.id.processStatusSpinnerEdit);
        serviceType = (Spinner) this.activity.findViewById(R.id.serviceTypeSpinnerEdit);
        pceType = (Spinner) this.activity.findViewById(R.id.processPCETypeEdit);

        name = (EditText) this.activity.findViewById(R.id.editTextPCENameEdit);
        manufacturer = (EditText) this.activity.findViewById(R.id.editTextPCEManufacturerEdit);
        quantity = (EditText) this.activity.findViewById(R.id.editTextPCEQuantityEdit);
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

    private Boolean validateFields(JSONObject body) {
        try {
            if(body.get("protocol").equals("") || body.isNull("protocol")) {
                return false;
            }
            if(body.get("entry_date").equals("") || body.isNull("entry_date")) {
                return false;
            }
            if(body.get("service").equals("") || body.isNull("service")) {
                return false;
            }
            if(body.get("status").equals("") || body.isNull("status")) {
                return false;
            }
            if(body.get("om").equals("") || body.isNull("om")) {
                return false;
            }
            if(body.get("gru_status").equals("") || body.isNull("gru_status")) {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @SuppressLint("DefaultLocale")
    private void showToast(Integer status) {
        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                if(status == 202) {
                    Toast.makeText(
                            activity.getBaseContext(),
                            activity.getString(R.string.processEditedMessage),
                            Toast.LENGTH_LONG
                    ).show();

                    Intent i = new Intent(activity, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.startActivity(i);
                } else {
                    String message = String.format(
                            "%s - Erro: %d",
                            activity.getString(R.string.couldNotEditProcess),
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
