package com.kreativeco.sysbioscience.farmers;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.kreativeco.sysbioscience.FarmerElementAdapter;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.farmers, null);

        rvFarmer = (RecyclerView) v.findViewById(R.id.rv_farmers);
        rvFarmer.setHasFixedSize(false);

        RecyclerView.LayoutManager rvLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        rvFarmer.setLayoutManager(rvLayoutManager);
        ImageButton iBtnAddFarmer = (ImageButton) v.findViewById(R.id.i_btn_add_farmer);

        iBtnAddFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFarmer();
            }
        });

        HashMap<String, Object> params = new HashMap<>();
        params.put("idUsuarioSubdistribuidor", User.get("IdTipoUsuario", getActivity()));
        params.put("metodo", "consultarPorSubdistribuidor");
        WebBridge.send("Agricultor.ashx", params, getActivity(), this);

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
}
