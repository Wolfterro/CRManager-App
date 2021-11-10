package com.github.wolfterro.crmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.github.wolfterro.crmanager.process.ProcessAdd;
import com.github.wolfterro.crmanager.process.ServiceType;

public class ProcessAddActivity extends AppCompatActivity {

    public Button addProcess;
    public Spinner gruStatus;
    public Spinner processStatus;
    public Spinner serviceType;
    public Spinner pceType;

    public Activity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_add);

        this.instance = this;

        addProcess = (Button) findViewById(R.id.addProcessButton);
        gruStatus = (Spinner) findViewById(R.id.processGRUStatusSpinner);
        processStatus = (Spinner) findViewById(R.id.processStatusSpinner);
        serviceType = (Spinner) findViewById(R.id.serviceTypeSpinner);
        pceType = (Spinner) findViewById(R.id.processPCEType);

        setGruStatusSpinner();
        setProcessStatusSpinner();
        setPCETypeSpinner();
        setServiceTypeSpinner();

        addProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProcessAdd processAdd = new ProcessAdd(instance);
                processAdd.start();
            }
        });
    }

    private void setGruStatusSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.gru_status_array,
                R.layout.custom_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gruStatus.setAdapter(adapter);
    }

    private void setProcessStatusSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.process_status_array,
                R.layout.custom_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        processStatus.setAdapter(adapter);
    }

    private void setPCETypeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.pce_type_array,
                R.layout.custom_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pceType.setAdapter(adapter);
    }

    private void setServiceTypeSpinner() {
        ServiceType serviceTypeThread = new ServiceType(this, this.serviceType);
        serviceTypeThread.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}