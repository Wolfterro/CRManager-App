package com.github.wolfterro.crmanager.login;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.github.wolfterro.crmanager.R;
import com.github.wolfterro.crmanager.utils.API;
import com.github.wolfterro.crmanager.utils.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends Thread {
    public Activity activity;
    public JSONObject userProfile;

    public UserProfile(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {
        JSONObject userProfile = API.getUserProfileInfo();

        if(userProfile != null) {
            this.userProfile = userProfile;
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
        TextView cpfAndCR = (TextView) this.activity.findViewById(R.id.userProfileCPFAndCR);
        TextView mainAddress = (TextView) this.activity.findViewById(R.id.userProfileMainAddress);
        TextView secondAddress = (TextView) this.activity.findViewById(R.id.userProfileSecondAddress);
        CircleImageView photo = (CircleImageView) this.activity.findViewById(R.id.userProfilePhoto);

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

            String crNumber = "-";
            if(crJson != null) {
                crNumber = crJson.getString("number");
            }
            cpfAndCR.setText(String.format("CPF: %s | CR: %s", userProfile.getString("cpf"), crNumber));

            if(mainAddressJson != null) {
                mainAddress.setText(Utils.getFormattedAddressFromJson(mainAddressJson));
            }
            if(secondAddressJson != null) {
                secondAddress.setText(Utils.getFormattedAddressFromJson(secondAddressJson));
            }

            new Handler(Looper.getMainLooper()).post(() -> {
                try {
                    Picasso.get()
                            .load(userProfile.getString("photo"))
                            .placeholder(R.mipmap.default_avatar)
                            .noFade()
                            .into(photo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR", e.toString());
        }
    }

}
