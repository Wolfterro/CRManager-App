package com.github.wolfterro.crmanager.signUp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.github.wolfterro.crmanager.MainActivity;
import com.github.wolfterro.crmanager.R;
import com.github.wolfterro.crmanager.utils.API;
import com.github.wolfterro.crmanager.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SignUp extends Thread {

    public Activity activity;
    public ProgressDialog pd;

    // SignUpActivity elements

    // UserProfile
    public EditText fullName;
    public EditText email;
    public EditText password;
    public EditText passwordConfirm;
    public EditText birthday;
    public EditText cpf;
    public EditText rg;
    public EditText photo;

    // Main Address
    public EditText mainAddress;
    public EditText mainNumber;
    public EditText mainComplement;
    public EditText mainZipCode;
    public EditText mainNeighborhood;
    public EditText mainCity;
    public EditText mainUF;

    // Second Address
    public EditText secondAddress;
    public EditText secondNumber;
    public EditText secondComplement;
    public EditText secondZipCode;
    public EditText secondNeighborhood;
    public EditText secondCity;
    public EditText secondUF;

    // CR
    public EditText crNumber;
    public EditText crExpirationDate;
    public EditText crRM;
    public CheckBox crIsHunter;
    public CheckBox crIsShooter;
    public CheckBox crIsCollector;

    public SignUp(Activity activity, ProgressDialog pd) {
        this.activity = activity;
        this.pd = pd;
    }

    @Override
    public void run() {
        instantiateFields();

        Boolean isValid = checkSignUpFields();
        Boolean isValidPassword = samePassword();

        isValid = isValid && isValidPassword;
        if(isValid) {
            sendSignUpRequest();
        } else if(!isValidPassword) {
            showToast(activity.getString(R.string.checkPassword));
        } else {
            showToast(activity.getString(R.string.addRequiredFieldsWarning));
        }

        this.pd.dismiss();
    }

    // Private Methods
    // ---------------
    private void instantiateFields() {
        fullName = (EditText) this.activity.findViewById(R.id.editTextFullNameRegister);
        email = (EditText) this.activity.findViewById(R.id.editTextEmailRegister);
        password = (EditText) this.activity.findViewById(R.id.editTextPasswordRegister);
        passwordConfirm = (EditText) this.activity.findViewById(R.id.editTextConfirmPasswordRegister);
        birthday = (EditText) this.activity.findViewById(R.id.editTextBirthdayRegister);
        cpf = (EditText) this.activity.findViewById(R.id.editTextCPFRegister);
        rg = (EditText) this.activity.findViewById(R.id.editTextRGRegister);
        photo = (EditText) this.activity.findViewById(R.id.editTextPhotoRegister);

        // Main Address
        mainAddress = (EditText) this.activity.findViewById(R.id.editTextMainAddressRegister);
        mainNumber = (EditText) this.activity.findViewById(R.id.editTextMainNumberRegister);
        mainComplement = (EditText) this.activity.findViewById(R.id.editTextMainComplementRegister);
        mainZipCode = (EditText) this.activity.findViewById(R.id.editTextMainZipCodeRegister);
        mainNeighborhood = (EditText) this.activity.findViewById(R.id.editTextMainNeighborhoodRegister);
        mainCity = (EditText) this.activity.findViewById(R.id.editTextMainCityRegister);
        mainUF = (EditText) this.activity.findViewById(R.id.editTextMainUFRegister);

        // Second Address
        secondAddress = (EditText) this.activity.findViewById(R.id.editTextSecondAddressRegister);
        secondNumber = (EditText) this.activity.findViewById(R.id.editTextSecondNumberRegister);
        secondComplement = (EditText) this.activity.findViewById(R.id.editTextSecondComplementRegister);
        secondZipCode = (EditText) this.activity.findViewById(R.id.editTextSecondZipCodeRegister);
        secondNeighborhood = (EditText) this.activity.findViewById(R.id.editTextSecondNeighborhoodRegister);
        secondCity = (EditText) this.activity.findViewById(R.id.editTextSecondCityRegister);
        secondUF = (EditText) this.activity.findViewById(R.id.editTextSecondUFRegister);

        // CR
        crNumber = (EditText) this.activity.findViewById(R.id.editTextCRNumberRegister);
        crExpirationDate = (EditText) this.activity.findViewById(R.id.editTextCRExpirationDateRegister);
        crRM = (EditText) this.activity.findViewById(R.id.editTextCRRMRegister);
        crIsHunter = (CheckBox) this.activity.findViewById(R.id.checkBoxHunt);
        crIsShooter = (CheckBox) this.activity.findViewById(R.id.checkBoxSportShooting);
        crIsCollector = (CheckBox) this.activity.findViewById(R.id.checkBoxCollection);
    }

    private Boolean checkSignUpFields() {
        // UserProfile checks
        if(this.fullName.getText().toString().equals("")) {
            return false;
        }
        if(this.email.getText().toString().equals("")) {
            return false;
        }
        if(this.password.getText().toString().equals("")) {
            return false;
        }
        if(this.passwordConfirm.getText().toString().equals("")) {
            return false;
        }
        if(this.birthday.getText().toString().equals("")) {
            return false;
        }
        if(this.cpf.getText().toString().equals("")) {
            return false;
        }
        if(this.rg.getText().toString().equals("")) {
            return false;
        }

        // MainAddress checks
        if(this.mainAddress.getText().toString().equals("")) {
            return false;
        }
        if(this.mainNumber.getText().toString().equals("")) {
            return false;
        }
        if(this.mainZipCode.getText().toString().equals("")) {
            return false;
        }
        if(this.mainNeighborhood.getText().toString().equals("")) {
            return false;
        }
        if(this.mainCity.getText().toString().equals("")) {
            return false;
        }
        if(this.mainUF.getText().toString().equals("")) {
            return false;
        }

        return true;
    }

    private Boolean samePassword() {
        return this.password.getText().toString().equals(this.passwordConfirm.getText().toString());
    }

    private JSONObject getRequestBody() {
        HashMap<String, Object> bodyHashMap = new HashMap<>();

        bodyHashMap.put("full_name", this.fullName.getText().toString());
        bodyHashMap.put("email", this.email.getText().toString());
        bodyHashMap.put("birthday", Utils.formatDate(this.birthday.getText().toString()));
        bodyHashMap.put("cpf", this.cpf.getText().toString());
        bodyHashMap.put("rg", this.rg.getText().toString());

        if(this.photo.getText().toString().equals("")) {
            bodyHashMap.put("photo", null);
        } else {
            bodyHashMap.put("photo", this.photo.getText().toString());
        }

        HashMap<String, Object> userHashMap = getUserHashMap();
        bodyHashMap.put("user", userHashMap);

        HashMap<String, Object> addressHashMap = getAddressHashMap(true);
        HashMap<String, Object> secondAddressHashMap = getAddressHashMap(false);
        HashMap<String, Object> crHashMap = getCRHashMap();

        bodyHashMap.put("address", addressHashMap);
        if(secondAddressHashMap != null) {
            bodyHashMap.put("second_address", secondAddressHashMap);
        }

        if(crHashMap != null) {
            bodyHashMap.put("cr", crHashMap);
        }

        return new JSONObject(bodyHashMap);
    }

    private HashMap<String, Object> getUserHashMap() {
        HashMap<String, Object> userHashMap = new HashMap<>();

        userHashMap.put("email", this.email.getText().toString());
        userHashMap.put("password", this.email.getText().toString());

        String fullName = this.fullName.getText().toString();
        String[] parts = fullName.split(" ");

        String lastName = "";
        if(parts.length > 1) {
            lastName = parts[parts.length - 1];
        }

        userHashMap.put("first_name", parts[0]);
        userHashMap.put("last_name", lastName);

        return userHashMap;
    }

    private HashMap<String, Object> getAddressHashMap(Boolean isMainAddress) {
        HashMap<String, Object> addressHashMap = new HashMap<>();

        if(isMainAddress) {
            if(this.mainAddress.getText().toString().equals("")) {
                return null;
            }

            addressHashMap.put("address", this.mainAddress.getText().toString());
            addressHashMap.put("number", this.mainNumber.getText().toString());

            if(this.mainComplement.getText().toString().equals("")) {
                addressHashMap.put("complement", null);
            } else {
                addressHashMap.put("complement", this.mainComplement.getText().toString());
            }

            addressHashMap.put("zip_code", this.mainZipCode.getText().toString());
            addressHashMap.put("neighborhood", this.mainNeighborhood.getText().toString());
            addressHashMap.put("city", this.mainCity.getText().toString());
            addressHashMap.put("uf", this.mainUF.getText().toString());
        } else {
            addressHashMap.put("address", this.secondAddress.getText().toString());
            addressHashMap.put("number", this.secondNumber.getText().toString());

            if(this.secondComplement.getText().toString().equals("")) {
                addressHashMap.put("complement", null);
            } else {
                addressHashMap.put("complement", this.secondComplement.getText().toString());
            }

            addressHashMap.put("zip_code", this.secondZipCode.getText().toString());
            addressHashMap.put("neighborhood", this.secondNeighborhood.getText().toString());
            addressHashMap.put("city", this.secondCity.getText().toString());
            addressHashMap.put("uf", this.secondUF.getText().toString());
        }

        return addressHashMap;
    }

    private HashMap<String, Object> getCRHashMap() {
        HashMap<String, Object> crHashMap = new HashMap<>();
        ArrayList<String> activitiesList = new ArrayList<>();

        if(this.crNumber.getText().toString().equals("")) {
            return null;
        }

        crHashMap.put("number", this.crNumber.getText().toString());
        crHashMap.put("expiration_date", Utils.formatDate(this.crExpirationDate.getText().toString()));
        crHashMap.put("rm", this.crRM.getText().toString());

        if(this.crIsHunter.isChecked()) {
            activitiesList.add(this.activity.getString(R.string.hunt));
        }
        if(this.crIsShooter.isChecked()) {
            activitiesList.add(this.activity.getString(R.string.sportShooting));
        }
        if(this.crIsCollector.isChecked()) {
            activitiesList.add(this.activity.getString(R.string.collection));
        }

        crHashMap.put("activities", activitiesList);

        return crHashMap;
    }

    private void sendSignUpRequest() {
        JSONObject body = getRequestBody();
        String response = API.signUp(body);

        if(response != null) {
            try {
                JSONObject responseJson = new JSONObject(response);
                setUserAndStartMainActivity(responseJson);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("ERROR", e.toString());
            }
        } else {
            showToast(activity.getString(R.string.couldNotCreateUser));
        }
    }

    private void setUserAndStartMainActivity(JSONObject response) {
        try {
            int userProfileId = response.getInt("id");
            String token = response.getString("token");
            String[] splitToken = token.split(" ");

            // Set Created USER_PROFILE_ID and API_KEY
            Utils.USER_PROFILE_ID = String.valueOf(userProfileId);
            Utils.API_KEY = splitToken[1];

            // Start MainActivity
            Intent i = new Intent(this.activity, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.activity.startActivity(i);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR", e.toString());
        }
    }

    private void showToast(String message) {
        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(
                        activity.getBaseContext(),
                        message,
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
}
