package com.github.wolfterro.crmanager.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.github.wolfterro.crmanager.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProcessListAdapter extends RecyclerView.Adapter<ProcessViewHolder>{

    private JSONArray processList;
    Activity activity;

    public ProcessListAdapter(JSONArray processList, Activity activity){
        this.processList = processList;
        this.activity = activity;
    }

    public int getItemCount(){
        return this.processList.length();
    }

    public ProcessViewHolder onCreateViewHolder(ViewGroup viewGroup, int position){
        return new ProcessViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.process_list_view, viewGroup, false));
    }

    public void onBindViewHolder(ProcessViewHolder holder, int i){
        int pos = holder.getAdapterPosition();

        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                setProcessInformation(holder, pos);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @SuppressLint("DefaultLocale")
    public void setProcessInformation(ProcessViewHolder holder, int pos) {
        try {
            JSONObject processJson = this.processList.getJSONObject(pos);

            holder.protocolNumber.setText(
                    String.format(
                            "Protocolo Nº %s", processJson.getString("protocol")
                    )
            );
            holder.processType.setText(
                    String.format(
                            "Serviço: %s", processJson.getString("service_label")
                    )
            );
            holder.processStatus.setText(
                    String.format(
                            "Status: %s", processJson.getString("status_label")
                    )
            );
            holder.processDates.setText(
                    String.format(
                            "Prazo: %d dias (%d dias úteis)",
                            processJson.getInt("entry_date_days"),
                            processJson.getInt("entry_date_working_days")
                    )
            );

            if(!processJson.isNull("pce")) {
                JSONObject pce = processJson.getJSONObject("pce");

                holder.processPCE.setText(
                        String.format(
                                "%dx %s %s (%s)",
                                pce.getInt("quantity"),
                                pce.getString("manufacturer"),
                                pce.getString("name"),
                                pce.getString("pce_type_label")
                        )
                );
            } else {
                holder.processPCE.setText("-");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR", e.toString());
        }
    }
}