package com.kreativeco.sysbioscience.farmer.purchases;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.farmer.currentdatas.CurrentDataFarmer;
import com.kreativeco.sysbioscience.farmer.properties.PropertiesElementAdapter;
import com.kreativeco.sysbioscience.utils.User;
import com.kreativeco.sysbioscience.utils.WebBridge;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kreativeco on 01/02/16.
 */
public class PurchasesFragment extends Fragment implements WebBridge.WebBridgeListener {

    View v;
    private RecyclerView rvSales;
    TextView txtNoItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.sales_fragment, null);


        rvSales = (RecyclerView) v.findViewById(R.id.rv_sales);
        rvSales.setHasFixedSize(false);

        RecyclerView.LayoutManager rvLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        rvSales.setLayoutManager(rvLayoutManager);
        ImageButton btnAddSales = (ImageButton) v.findViewById(R.id.btn_add_sales);
        btnAddSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddPurchase.class);
                intent.putExtra("jsonData", "");
                intent.putExtra("option", 0);
                getActivity().startActivity(intent);
            }
        });

        txtNoItems = (TextView) v.findViewById(R.id.txt_no_items);
        txtNoItems.setVisibility(View.GONE);

        return v;

    }

    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {
        try {
            boolean status = json.getInt("ResponseCode") == 200;
            if (status) {
                JSONArray jsonArraySales = json.getJSONArray("Object");
                if(jsonArraySales.length() == 0){
                    txtNoItems.setVisibility(View.VISIBLE);
                    return;
                }else {
                    txtNoItems.setVisibility(View.GONE);
                    RecyclerView.Adapter rvAdapter = new PurchasesElementAdapter(jsonArraySales, getActivity());
                    rvSales.setAdapter(rvAdapter);
                }

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

    @Override
    public void onResume() {
        super.onResume();

        HashMap<String, Object> params = new HashMap<>();
        params.put("token", User.getToken(getActivity()));
        params.put("metodo", "consultarPorAgricultor");
        params.put("idAgricultor", CurrentDataFarmer.getFarmerId());
        WebBridge.send("Compras.ashx", params, "Obteniedo datos", getActivity(), this);

    }
}
