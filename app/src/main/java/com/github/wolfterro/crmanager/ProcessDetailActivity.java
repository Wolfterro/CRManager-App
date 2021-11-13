package com.github.wolfterro.crmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.wolfterro.crmanager.process.ProcessDelete;
import com.github.wolfterro.crmanager.utils.API;

import org.json.JSONException;
import org.json.JSONObject;

public class ProcessDetailActivity extends AppCompatActivity {

    public Integer processId = null;

    public TextView protocol;
    public TextView entryDate;
    public TextView status;
    public TextView service;
    public TextView OM;
    public TextView gruStatus;
    public TextView gruCompensationDate;
    public TextView pceName;
    public TextView pceManufacturer;
    public TextView pceType;
    public TextView pceQuantity;

    public Button deleteButton;

    public JSONObject processDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_detail);

        protocol = (TextView) findViewById(R.id.textViewProtocolDetail);
        entryDate = (TextView) findViewById(R.id.textViewEntryDateDetail);
        service = (TextView) findViewById(R.id.textViewServiceTypeDetail);
        status = (TextView) findViewById(R.id.textViewStatusDetail);
        OM = (TextView) findViewById(R.id.textViewOMDetail);
        gruStatus = (TextView) findViewById(R.id.textViewGruStatusDetail);
        gruCompensationDate = (TextView) findViewById(R.id.textViewGruEntryDateDetail);
        pceName = (TextView) findViewById(R.id.textViewNameDetail);
        pceManufacturer = (TextView) findViewById(R.id.textViewManufacturerDetail);
        pceType = (TextView) findViewById(R.id.textViewPceTypeDetail);
        pceQuantity = (TextView) findViewById(R.id.textViewQuantityDetail);
        deleteButton = (Button) findViewById(R.id.deleteProcessButton);

        getProcessDetail();
        setValues();

        AlertDialog deletedialog = setDeleteConfirmDialog();

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(processId != null) {
                    deletedialog.show();
                }
            }
        });
    }

    // Private Methods
    @SuppressLint("DefaultLocale")
    private AlertDialog setDeleteConfirmDialog() {
        Activity activity = this;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.confirmDeleteDialog)
                .setTitle(R.string.confirmDeleteTitle)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ProcessDelete processDelete = new ProcessDelete(activity, processId);
                        processDelete.start();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        return;
                    }
                });

        return builder.create();
    }

    private void getProcessDetail() {
        Intent intent = getIntent();
        String processDetailString = intent.getStringExtra("PROCESS_JSON");

        try {
            processDetail = new JSONObject(processDetailString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setValues() {
        if(this.processDetail == null) {
            return;
        }

        try {
            processId = this.processDetail.getInt("id");

            String protocolValue = this.processDetail.getString("protocol");
            protocol.setText(String.format("%s %s", getString(R.string.protocolNumberDetail), protocolValue));

            entryDate.setText(valueOrDefaultString(this.processDetail,"entry_date", "-"));
            service.setText(valueOrDefaultString(this.processDetail,"service_label", "-"));
            status.setText(valueOrDefaultString(this.processDetail,"status_label", "-"));
            OM.setText(valueOrDefaultString(this.processDetail,"om", "-"));
            gruStatus.setText(valueOrDefaultString(this.processDetail,"gru_status_label", "-"));
            gruCompensationDate.setText(valueOrDefaultString(this.processDetail,"gru_compensation_date", "-"));

            if(!this.processDetail.isNull("pce")) {
                JSONObject pceJson = this.processDetail.getJSONObject("pce");

                pceName.setText(valueOrDefaultString(pceJson,"name", "-"));
                pceManufacturer.setText(valueOrDefaultString(pceJson,"manufacturer", "-"));
                pceType.setText(valueOrDefaultString(pceJson,"pce_type_label", "-"));
                pceQuantity.setText(valueOrDefaultInt(pceJson,"quantity", "-"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String valueOrDefaultString(JSONObject json, String field, String defaultValue) {
        try {
            if(json.isNull(field) || json.getString(field).equals("")) {
                return defaultValue;
            }

            return json.getString(field);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return defaultValue;
    }

    private String valueOrDefaultInt(JSONObject json, String field, String defaultValue) {
        try {
            if(json.isNull(field)) {
                return defaultValue;
            }

            return String.valueOf(json.getInt(field));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return defaultValue;
    }
}