package com.github.wolfterro.crmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.github.wolfterro.crmanager.utils.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileDetailActivity extends AppCompatActivity {

    public JSONObject userProfile;

    // Activity Elements
    public CircleImageView photo;
    public TextView fullName;

    public TextView email;
    public TextView birthday;
    public TextView cpf;
    public TextView rg;

    public TextView mainAddress;
    public TextView mainNumber;
    public TextView mainComplement;
    public TextView mainZipCode;
    public TextView mainNeighborhood;
    public TextView mainCity;
    public TextView mainUF;

    public TextView secondAddress;
    public TextView secondNumber;
    public TextView secondComplement;
    public TextView secondZipCode;
    public TextView secondNeighborhood;
    public TextView secondCity;
    public TextView secondUF;

    public TextView crNumber;
    public TextView crExpirationDate;
    public TextView crRM;
    public CheckBox isHunter;
    public CheckBox isShooter;
    public CheckBox isCollector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_detail);

        getUserProfile();

        instantiateElements();
        setElementValues();
    }

    private void getUserProfile() {
        Intent i = getIntent();
        String userProfileString = i.getStringExtra("USERPROFILE");

        try {
            userProfile = new JSONObject(userProfileString);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR", e.toString());
        }
    }

    private void instantiateElements() {
        photo = (CircleImageView) findViewById(R.id.userProfilePhotoDetail);
        fullName = (TextView) findViewById(R.id.userProfileFullNameDetail);

        email = (TextView) findViewById(R.id.textViewUserProfileEmailDetail);
        birthday = (TextView) findViewById(R.id.textViewUserProfileBirthdayDetail);
        cpf = (TextView) findViewById(R.id.textViewUserProfileCPFDetail);
        rg = (TextView) findViewById(R.id.textViewUserProfileRGDetail);

        mainAddress = (TextView) findViewById(R.id.textViewUserProfileMainAddressDetail);
        mainNumber = (TextView) findViewById(R.id.textViewUserProfileMainNumberDetail);
        mainComplement = (TextView) findViewById(R.id.textViewUserProfileMainComplementDetail);
        mainZipCode = (TextView) findViewById(R.id.textViewUserProfileMainZipCodeDetail);
        mainNeighborhood = (TextView) findViewById(R.id.textViewUserProfileMainNeighborhoodDetail);
        mainCity = (TextView) findViewById(R.id.textViewUserProfileMainCityDetail);
        mainUF = (TextView) findViewById(R.id.textViewUserProfileMainUFDetail);

        secondAddress = (TextView) findViewById(R.id.textViewUserProfileSecondAddressDetail);
        secondNumber = (TextView) findViewById(R.id.textViewUserProfileSecondNumberDetail);
        secondComplement = (TextView) findViewById(R.id.textViewUserProfileSecondComplementDetail);
        secondZipCode = (TextView) findViewById(R.id.textViewUserProfileSecondZipCodeDetail);
        secondNeighborhood = (TextView) findViewById(R.id.textViewUserProfileSecondNeighborhoodDetail);
        secondCity = (TextView) findViewById(R.id.textViewUserProfileSecondCityDetail);
        secondUF = (TextView) findViewById(R.id.textViewUserProfileSecondUFDetail);

        crNumber = (TextView) findViewById(R.id.textViewUserProfileCRNumberDetail);
        crExpirationDate = (TextView) findViewById(R.id.textViewUserProfileCRExpirationDateDetail);
        crRM = (TextView) findViewById(R.id.textViewUserProfileCRRMDetail);
        isHunter = (CheckBox) findViewById(R.id.checkBoxHuntDetail);
        isShooter = (CheckBox) findViewById(R.id.checkBoxSportShootingDetail);
        isCollector = (CheckBox) findViewById(R.id.checkBoxCollectionDetail);
    }

    private void setElementValues() {
        try {
            Picasso.get()
                    .load(userProfile.getString("photo"))
                    .placeholder(R.mipmap.default_avatar)
                    .noFade()
                    .into(photo);

            fullName.setText(userProfile.getString("full_name"));
            email.setText(userProfile.getString("email"));
            birthday.setText(Utils.formatDateReverse(userProfile.getString("birthday")));
            cpf.setText(userProfile.getString("cpf"));
            rg.setText(userProfile.getString("rg"));

            JSONObject mainAddressJson = userProfile.getJSONObject("address");

            mainAddress.setText(mainAddressJson.getString("address"));
            mainNumber.setText(mainAddressJson.getString("number"));
            if(!mainAddressJson.isNull("complement")) {
                mainComplement.setText(mainAddressJson.getString("complement"));
            }
            mainNeighborhood.setText(mainAddressJson.getString("neighborhood"));
            mainZipCode.setText(mainAddressJson.getString("zip_code"));
            mainCity.setText(mainAddressJson.getString("city"));
            mainUF.setText(mainAddressJson.getString("uf"));

            if(!userProfile.isNull("second_address")) {
                JSONObject secondAddressJson = userProfile.getJSONObject("second_address");

                secondAddress.setText(secondAddressJson.getString("address"));
                secondNumber.setText(secondAddressJson.getString("number"));
                if(!secondAddressJson.isNull("complement")) {
                    secondComplement.setText(secondAddressJson.getString("complement"));
                }
                secondNeighborhood.setText(secondAddressJson.getString("neighborhood"));
                secondZipCode.setText(secondAddressJson.getString("zip_code"));
                secondCity.setText(secondAddressJson.getString("city"));
                secondUF.setText(mainAddressJson.getString("uf"));
            }

            if(!userProfile.isNull("cr")) {
                JSONObject crJson = userProfile.getJSONObject("cr");

                crNumber.setText(crJson.getString("number"));
                crExpirationDate.setText(Utils.formatDateReverse(crJson.getString("expiration_date")));
                crRM.setText(String.format("%sª RM", crJson.getString("rm")));

                JSONArray activities = crJson.getJSONArray("activities");
                for(int i = 0; i < activities.length(); i++) {
                    if(activities.get(i).toString().equals(getString(R.string.hunt))) {
                        isHunter.setChecked(true);
                    }
                    else if(activities.get(i).toString().equals(getString(R.string.sportShooting))) {
                        isShooter.setChecked(true);
                    }
                    else if(activities.get(i).toString().equals(getString(R.string.collection))) {
                        isCollector.setChecked(true);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR", e.toString());

            Toast.makeText(
                    getBaseContext(),
                    getString(R.string.couldNotRetrieveUserProfileInfo),
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}