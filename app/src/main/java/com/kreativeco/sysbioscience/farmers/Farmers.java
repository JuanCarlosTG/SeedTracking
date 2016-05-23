package com.kreativeco.sysbioscience.farmers;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.utils.User;
import com.kreativeco.sysbioscience.utils.WebBridge;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JC on 03/02/16.
 */
public class Farmers extends Fragment implements WebBridge.WebBridgeListener{

    View v;

    private RecyclerView rvFarmer;
    private ImageButton btnSearch, btnCloseSearch;
    private EditText txtSearch;
    private int method = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.farmers, null);

        rvFarmer = (RecyclerView) v.findViewById(R.id.rv_farmers);
        rvFarmer.setHasFixedSize(false);

        RecyclerView.LayoutManager rvLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        rvFarmer.setLayoutManager(rvLayoutManager);
        ImageButton iBtnAddFarmer = (ImageButton) v.findViewById(R.id.i_btn_add_farmer);
        btnSearch = (ImageButton) v.findViewById(R.id.search_farmer);
        txtSearch = (EditText) v.findViewById(R.id.txt_search);
        btnCloseSearch = (ImageButton) v.findViewById(R.id.btn_close_search);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFarmer(v);
                method = 1;
            }
        });


        txtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchFarmer(txtSearch);
                    return true;
                }
                return false;
            }
        });

        btnCloseSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSearch.setText("");
                method = 0;
            }
        });

        iBtnAddFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFarmer();
            }
        });

        loadFarmers();
        return v;

    }

    public void addFarmer(){
        Intent addFarmer = new Intent(getActivity(), AddFarmer.class);
        startActivity(addFarmer);
    }

    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {
        Log.e("JSON FARMERS", json.toString());

        try {
            boolean status = json.getInt("ResponseCode") == 200;
            if (status) {
                JSONArray jsonArrayFarmers = json.getJSONArray("Object");
                RecyclerView.Adapter rvAdapter = new FarmerElementAdapter(jsonArrayFarmers, getActivity());
                rvFarmer.setAdapter(rvAdapter);

                if(jsonArrayFarmers.length() == 0 && method == 1)
                    loadFarmers();

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

    }

    public void searchFarmer(View view){

        HashMap<String, Object> params = new HashMap<>();
        params.put("token", User.getToken(getActivity()));
        params.put("rfc", txtSearch.getText().toString());
        params.put("metodo", "consultarPorRFC");
        WebBridge.send("Agricultor.ashx?List", params, getActivity(), this);
    }


    public void loadFarmers(){
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", User.getToken(getActivity()));
        params.put("metodo", "consultarPorSubdistribuidor");
        WebBridge.send("Agricultor.ashx?List", params, getActivity(), this);

    }
}
