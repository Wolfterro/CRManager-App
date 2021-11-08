package com.github.wolfterro.crmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.wolfterro.crmanager.process.ServiceType;

public class ProcessAddActivity extends AppCompatActivity {

    public Button addProcess;
    public Spinner gruStatus;
    public Spinner processStatus;
    public Spinner serviceType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_add);

        addProcess = (Button) findViewById(R.id.addProcessButton);
        gruStatus = (Spinner) findViewById(R.id.processGRUStatusSpinner);
        processStatus = (Spinner) findViewById(R.id.processStatusSpinner);
        serviceType = (Spinner) findViewById(R.id.serviceTypeSpinner);

        setGruStatusSpinner();
        setProcessStatusSpinner();
        setServiceTypeSpinner();

        addProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Create ProcessAdd class to validate data send POST request
                Toast.makeText(getBaseContext(), getString(R.string.processAdded), Toast.LENGTH_LONG).show();   // TODO: Move it to ProcessAdd class
                onBackPressed();
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