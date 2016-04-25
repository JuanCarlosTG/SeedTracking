package com.kreativeco.sysbioscience.utils;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.SectionActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by JuanC on 25/04/2016.
 */
public class ListVarieties extends SectionActivity implements WebBridge.WebBridgeListener{

    private RecyclerView recyclerStates;
    private static final int RESULT_OK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerStates = (RecyclerView) findViewById(R.id.recycler_list);
        recyclerStates.setHasFixedSize(false);

        RecyclerView.LayoutManager rvLayoutManager = new LinearLayoutManager(getBaseContext());
        recyclerStates.setLayoutManager(rvLayoutManager);


        HashMap<String, Object> params = new HashMap<>();
        params.put("metodo", "consultarVariedadesMunicipio");
        params.put("idMunicipio", ListIds.getIdLocality());
        WebBridge.send("Catalogos.ashx?varieties", params, "Obteniendo variedades", this, this);

    }

    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {
        try {
            boolean status = json.getInt("ResponseCode") == 200;
            if (status) {
                JSONArray varieties = json.getJSONArray("Object");
                RecyclerView.Adapter rvAdapter = new VarietiesElementAdapter(varieties, this);
                recyclerStates.setAdapter(rvAdapter);
            } else {
                String error = json.getString("Errors");
                new AlertDialog.Builder(getBaseContext()).setTitle(R.string.txt_error).setMessage(error).setNeutralButton(R.string.bt_close, null).show();
            }

        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }
    }

    @Override
    public void onWebBridgeFailure(String url, String response) {

    }



    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                result_listUser = data.getStringExtra("list_name_user");
                register_id = data.getStringExtra("register_id");

                et_name_full = (CustomEditTextRegular) v.findViewById(R.id.et_name_full);
                et_name_full.setKeyListener(null);
                et_name_full.setText(result_listUser);

            }
        }
    }*/
}
