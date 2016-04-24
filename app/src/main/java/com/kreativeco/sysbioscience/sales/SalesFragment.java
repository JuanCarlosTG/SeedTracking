package com.kreativeco.sysbioscience.sales;

import android.app.AlertDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.utils.User;
import com.kreativeco.sysbioscience.utils.WebBridge;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by kreativeco on 01/02/16.
 */
public class SalesFragment extends Fragment implements WebBridge.WebBridgeListener {

    View v;
    private RecyclerView rvSales;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.sales_fragment, null);


        rvSales = (RecyclerView) v.findViewById(R.id.rv_sales);
        rvSales.setHasFixedSize(false);

        RecyclerView.LayoutManager rvLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        rvSales.setLayoutManager(rvLayoutManager);
        //ImageButton iBtnAddFarmer = (ImageButton) v.findViewById(R.id.i_btn_add_farmer);

        HashMap<String, Object> params = new HashMap<>();
        params.put("token", User.getToken(getActivity()));
        params.put("metodo", "consultarPorAgricultor");
        params.put("idAgricultor", CurrentDataFarmer.getFarmerId());

        WebBridge.send("Compras.ashx", params, "Obteniedo datos", getActivity(), this);

        return v;

    }

    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {
        try {
            boolean status = json.getInt("ResponseCode") == 200;
            if (status) {
                JSONArray jsonArraySales = json.getJSONArray("Object");
                RecyclerView.Adapter rvAdapter = new SalesElementAdapter(jsonArraySales, getActivity());
                rvSales.setAdapter(rvAdapter);
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
