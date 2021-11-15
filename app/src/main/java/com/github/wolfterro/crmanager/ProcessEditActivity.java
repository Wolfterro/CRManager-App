package com.github.wolfterro.crmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.github.wolfterro.crmanager.process.ServiceType;
import com.github.wolfterro.crmanager.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class ProcessEditActivity extends AppCompatActivity {

    public JSONObject processDetail;

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

    public Button editProcess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_edit);

        getProcessDetail();
        instantiateElements();
        setElementValues();

        editProcess = (Button) findViewById(R.id.editProcessButton);
        editProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
    }

    // Private Methods
    private void getProcessDetail() {
        Intent i = getIntent();
        String processDetailString = i.getStringExtra("PROCESS_JSON");

        try {
            processDetail = new JSONObject(processDetailString);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR", e.toString());
        }
    }

    private void instantiateElements() {
        editProcess = (Button) findViewById(R.id.editProcessButton);
        gruStatus = (Spinner) findViewById(R.id.processGRUStatusSpinnerEdit);
        processStatus = (Spinner) findViewById(R.id.processStatusSpinnerEdit);
        serviceType = (Spinner) findViewById(R.id.serviceTypeSpinnerEdit);
        pceType = (Spinner) findViewById(R.id.processPCETypeEdit);

        protocol = (EditText) findViewById(R.id.editTextProtocolNumberEdit);
        entryDate = (EditText) findViewById(R.id.editTextEntryDateEdit);
        gruEntryDate = (EditText) findViewById(R.id.editTextGRUEntryDateEdit);
        OM = (EditText) findViewById(R.id.editTextOMEdit);
        reason = (EditText) findViewById(R.id.editTextReasonEdit);

        name = (EditText) findViewById(R.id.editTextPCENameEdit);
        manufacturer = (EditText) findViewById(R.id.editTextPCEManufacturerEdit);
        quantity = (EditText) findViewById(R.id.editTextPCEQuantityEdit);

        setGruStatusSpinner();
        setProcessStatusSpinner();
        setPCETypeSpinner();
        setServiceTypeSpinner();
    }

    private void setGruStatusSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.gru_status_array,
                R.layout.custom_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gruStatus.setAdapter(adapter);

        try {
            int valuePos = adapter.getPosition(processDetail.getString("gru_status_label"));
            gruStatus.setSelection(valuePos);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setProcessStatusSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.process_status_array,
                R.layout.custom_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        processStatus.setAdapter(adapter);

        try {
            int valuePos = adapter.getPosition(processDetail.getString("status_label"));
            processStatus.setSelection(valuePos);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setPCETypeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.pce_type_array,
                R.layout.custom_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pceType.setAdapter(adapter);

        if(!processDetail.isNull("pce")) {
            try {
                JSONObject pce = processDetail.getJSONObject("pce");

                int valuePos = adapter.getPosition(pce.getString("pce_type_label"));
                pceType.setSelection(valuePos);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setServiceTypeSpinner() {
        ServiceType serviceTypeThread = new ServiceType(this, this.serviceType, processDetail, true);
        serviceTypeThread.start();
    }

    private void setElementValues() {
        try {
            protocol.setText(processDetail.getString("protocol"));

            entryDate.setText(Utils.formatDateReverse(processDetail.getString("entry_date")));
            gruEntryDate.setText(Utils.formatDateReverse(processDetail.getString("gru_compensation_date")));
            OM.setText(processDetail.getString("om"));

            if(!processDetail.isNull("reason")) {
                reason.setText(processDetail.getString("reason"));
            }

            if(!processDetail.isNull("pce")) {
                JSONObject pce = processDetail.getJSONObject("pce");

                name.setText(pce.getString("name"));
                manufacturer.setText(pce.getString("manufacturer"));
                quantity.setText(String.valueOf(pce.getInt("quantity")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR", e.toString());
        }
    }
}