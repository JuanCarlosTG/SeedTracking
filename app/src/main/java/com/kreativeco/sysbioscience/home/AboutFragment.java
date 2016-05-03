package com.kreativeco.sysbioscience.home;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.utils.User;
import com.kreativeco.sysbioscience.utils.WebBridge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kreativeco on 01/02/16.
 */
public class AboutFragment extends Fragment{

    private EditText txtName, txtLastNameA, txtLastNameB, txtEmail, txtPass, txtConfirmPass;
    Button btnUpdateProfile;

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_about, null);

        return v;

    }

}
