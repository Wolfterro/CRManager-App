package com.github.wolfterro.crmanager.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.wolfterro.crmanager.R;

public class ProcessViewHolder extends RecyclerView.ViewHolder {

    public TextView protocolNumber;
    public TextView processType;
    public TextView processStatus;
    public TextView processDates;
    public TextView processPCE;

    public ProcessViewHolder(View itemView){
        super(itemView);

        protocolNumber = (TextView) itemView.findViewById(R.id.processInstanceProtocolNumber);
        processType = (TextView) itemView.findViewById(R.id.processInstanceProcessType);
        processStatus = (TextView) itemView.findViewById(R.id.processInstanceProcessStatus);
        processDates = (TextView) itemView.findViewById(R.id.processInstanceProcessDates);
        processPCE = (TextView) itemView.findViewById(R.id.processInstanceProcessPCE);
    }
}