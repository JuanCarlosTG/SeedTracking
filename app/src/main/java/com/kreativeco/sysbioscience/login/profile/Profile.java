package com.kreativeco.sysbioscience.login.profile;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.utils.ListIds;
import com.kreativeco.sysbioscience.utils.User;
import com.kreativeco.sysbioscience.utils.WebBridge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kreativeco on 01/02/16.
 */
public class Profile extends Fragment implements WebBridge.WebBridgeListener {

    private EditText txtName, txtLastNameA, txtLastNameB, txtEmail, txtPass, txtConfirmPass;
    Button btnUpdateProfile;

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.profile, null);

        txtName = (EditText) v.findViewById(R.id.et_name);
        txtLastNameA = (EditText) v.findViewById(R.id.et_last_name_a);
        txtLastNameB = (EditText) v.findViewById(R.id.et_last_name_b);
        txtEmail = (EditText) v.findViewById(R.id.et_e_mail);
        txtPass = (EditText) v.findViewById(R.id.et_pass);
        txtConfirmPass = (EditText) v.findViewById(R.id.et_confirm_pass);
        btnUpdateProfile = (Button) v.findViewById(R.id.btn_update_profile);

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

        String userName = User.get("Nombre", getActivity());
        String apellidoA = User.get("ApellidoP", getActivity());
        String apellidoB = User.get("ApellidoM", getActivity());
        String mail = User.get("Mail", getActivity());

        txtName.setText(userName);
        txtLastNameA.setText(apellidoA);
        txtLastNameB.setText(apellidoB);
        txtEmail.setText(mail);


        HashMap<String, Object> params = new HashMap<>();
        params.put("token", User.getToken(getActivity()));

        //WebBridge.send("M_SubdistribuidorProfileGet.ashx", params, getString(R.string.txt_sending), getActivity(), this);


        return v;

    }

    public void updateProfile() {
        ArrayList<String> errors = new ArrayList<String>();
        if (txtName.getText().length() < 1) errors.add(getString(R.string.txt_error_name));
        if (txtLastNameA.getText().length() < 1)
            errors.add(getString(R.string.txt_error_last_name_a));
        if (txtLastNameA.getText().length() < 1)
            errors.add(getString(R.string.txt_error_last_name_b));
        if (txtEmail.getText().length() < 1) errors.add(getString(R.string.txt_error_mail));
        if (!txtPass.getText().toString().equals(txtConfirmPass.getText().toString()))
            errors.add(getString(R.string.txt_error_confirm_pass));

        if (errors.size() != 0) {
            String msg = "";
            for (String s : errors) {
                msg += "- " + s + "\n";
            }
            new AlertDialog.Builder(getActivity()).setTitle(R.string.txt_error).setMessage(msg.trim()).setNeutralButton(R.string.bt_close, null).show();
            return;
        }

        HashMap<String, Object> params = new HashMap<>();

        params.put("metodo",        "editarPerfil");
        params.put("token", User.getToken(getActivity()));
        params.put("mail",          txtEmail.getText().toString());
        params.put("password",      txtPass.getText().toString());
        params.put("nombre",        txtName.getText().toString());
        params.put("apellidoP",     txtLastNameA.getText().toString());
        params.put("apellidoM",     txtLastNameB.getText().toString());

        WebBridge.send("Login.ashx?update", params, getString(R.string.txt_sending), getActivity(), this);

    }


    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {
        Log.e("JSON SUCCESS", json.toString());
        try {
            if (json.getInt("ResponseCode") == 200) {

                JSONArray jsonArrayNews = json.getJSONArray("Object");

            } else if (json.getInt("ResponseCode") == 500) {

                JSONArray errors = json.getJSONArray("Errors");
                ArrayList<String> errorArray = new ArrayList<String>();

                for (int i = 0; i < errors.length(); i++) {

                    errorArray.add(errors.getJSONObject(i).getString("Message"));

                }

                if (errorArray.size() != 0) {
                    String msg = "";
                    for (String s : errorArray) {
                        msg += "- " + s + "\n";
                    }
                    new AlertDialog.Builder(getActivity()).setTitle(R.string.txt_error).setMessage(msg.trim()).setNeutralButton(R.string.bt_close, null).show();

                }
            }

        } catch (JSONException jsonE) {
            Log.e("JSON EXCEPTION", jsonE.toString());
        }

    }

    @Override
    public void onWebBridgeFailure(String url, String response) {
        Log.e("JSON FAILURE", response);
    }
}
