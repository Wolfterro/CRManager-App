package com.github.wolfterro.crmanager.login;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.github.wolfterro.crmanager.R;
import com.github.wolfterro.crmanager.utils.API;
import com.github.wolfterro.crmanager.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class UserProfile extends Thread {
    public Activity activity;

    public UserProfile(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {
        JSONObject userProfile = API.getUserProfileInfo();

        if(userProfile != null) {
            setUserProfileValuesOnActivity(userProfile);
        } else {
            Toast.makeText(
                    this.activity.getBaseContext(),
                    this.activity.getString(R.string.couldNotRetrieveUserProfileInfo),
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    public void setUserProfileValuesOnActivity(JSONObject userProfile) {
        TextView fullName = (TextView) this.activity.findViewById(R.id.userProfileFullName);
        TextView cpf = (TextView) this.activity.findViewById(R.id.userProfileCPF);
        TextView cr = (TextView) this.activity.findViewById(R.id.userProfileCR);
        TextView mainAddress = (TextView) this.activity.findViewById(R.id.userProfileMainAddress);
        TextView secondAddress = (TextView) this.activity.findViewById(R.id.userProfileSecondAddress);

        try {
            JSONObject crJson = null;
            if(!userProfile.isNull("cr")) {
                crJson = userProfile.getJSONObject("cr");
            }

            JSONObject mainAddressJson = null;
            if(!userProfile.isNull("address")) {
                mainAddressJson = userProfile.getJSONObject("address");
            }

            JSONObject secondAddressJson = null;
            if(!userProfile.isNull("second_address")) {
                secondAddressJson = userProfile.getJSONObject("second_address");
            }

            fullName.setText(userProfile.getString("full_name"));
            cpf.setText(String.format("CPF: %s", userProfile.getString("cpf")));

            if(crJson != null) {
                cr.setText(String.format("CR: %s", crJson.getString("number")));
            }
            if(mainAddressJson != null) {
                mainAddress.setText(Utils.getFormattedAddressFromJson(mainAddressJson));
            }
            if(secondAddressJson != null) {
                secondAddress.setText(Utils.getFormattedAddressFromJson(secondAddressJson));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR", e.toString());
        }
    }
}
