package com.kreativeco.sysbioscience;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kreativeco.sysbioscience.utils.WebBridge;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kreativeco on 01/02/16.
 */
public class FastBuyActivity extends Fragment implements WebBridge.WebBridgeListener {

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.activity_add_buy, null);

        return v;

    }

    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {
        Log.e("json", json.toString());
        try {
            if (json.getInt("ResponseCode") == 200) {

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

        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }
    }


    @Override
    public void onWebBridgeFailure(String url, String response) {
        Log.e("JSON", response);
    }

}
