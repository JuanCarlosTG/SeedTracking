package com.kreativeco.sysbioscience.farmers;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.utils.User;
import com.kreativeco.sysbioscience.utils.WebBridge;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by JC on 03/02/16.
 */
public class Farmers extends Fragment implements WebBridge.WebBridgeListener{

    View v;

    private RecyclerView rvFarmer;
    private ImageButton btnSearch, btnCloseSearch;
    private EditText txtSearch;


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
            }
        });

        btnCloseSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFarmers();
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
            } else {
                String error = json.getString("Errors");
                new AlertDialog.Builder(getActivity().getBaseContext()).setTitle(R.string.txt_error).setMessage(error).setNeutralButton(R.string.bt_close, null).show();
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
