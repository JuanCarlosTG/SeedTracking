package com.kreativeco.sysbioscience.login.profile;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.utils.User;
import com.kreativeco.sysbioscience.utils.WebBridge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by kreativeco on 01/02/16.
 */
public class Profile extends Fragment implements WebBridge.WebBridgeListener{

    private EditText txtName, txtLastNameA, txtLastNameB, txtEmail, txtConfirmEmail, txtPhone;
    private EditText txtCell, txtCompany, txtRFC, txtID, txtAddress, txtZip;

    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.profile, null);

        txtName          = (EditText) v.findViewById(R.id.txt_name);
        txtLastNameA     = (EditText) v.findViewById(R.id.txt_last_name_a);
        txtLastNameB     = (EditText) v.findViewById(R.id.txt_last_name_b);
        txtEmail         = (EditText) v.findViewById(R.id.txt_mail);
        txtPhone         = (EditText) v.findViewById(R.id.txt_phone);
        txtCell          = (EditText) v.findViewById(R.id.txt_cell);
        txtCompany       = (EditText) v.findViewById(R.id.txt_company);
        txtRFC           = (EditText) v.findViewById(R.id.txt_rfc);
        txtID            = (EditText) v.findViewById(R.id.txt_id);
        txtAddress       = (EditText) v.findViewById(R.id.txt_address);
        txtZip           = (EditText) v.findViewById(R.id.txt_zip);

        /*ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.width = R.dimen.sp_100;
        layoutParams.height = R.dimen.sp_200;

        txtAddress.setLayoutParams(layoutParams);*/

        HashMap<String, Object> params = new HashMap<>();
        params.put("token", User.getToken(getActivity()));

        //WebBridge.send("M_SubdistribuidorProfileGet.ashx", params, getString(R.string.txt_sending), getActivity(), this);


        return v;

    }

    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {
        Log.e("JSON SUCCESS", json.toString());
        try {
            if(json.getInt("error_code") == 200){
                JSONArray   data        = json.getJSONArray("data");
                JSONObject  jsonObject  = data.getJSONObject(0);

                txtName.setText(jsonObject.getString("nombre"));
                txtLastNameA.setText(jsonObject.getString("appaterno"));
                txtLastNameB.setText(jsonObject.getString("apmaterno"));
                txtEmail.setText(jsonObject.getString("email"));
                txtCompany.setText(jsonObject.getString("razon_social_subdistribuidor"));
                txtRFC.setText(jsonObject.getString("rfc_subdistribuidor"));
                txtAddress.setText(jsonObject.getString("direccion_subdistribuidor"));
                txtZip.setText(jsonObject.getString("cp_subdistribuidor"));
                txtPhone.setText(jsonObject.getString("tel_subdistribuidor"));

            }else {
                String errorMessage = json.getString("error_message");
                new AlertDialog.Builder(getActivity().getBaseContext()).setTitle(R.string.txt_error).setMessage(errorMessage).setNeutralButton(R.string.bt_close, null).show();
            }

        }catch (JSONException jsonE){
            Log.e("JSON EXCEPTION", jsonE.toString());
        }

    }

    @Override
    public void onWebBridgeFailure(String url, String response) {
        Log.e("JSON FAILURE", response);
    }
}
