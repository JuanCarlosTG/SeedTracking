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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.farmer.assigns.AddAssign;
import com.kreativeco.sysbioscience.farmer.assigns.AssignsElementAdapter;
import com.kreativeco.sysbioscience.farmer.currentdatas.CurrentDataFarmer;
import com.kreativeco.sysbioscience.farmer.properties.AddProperty;
import com.kreativeco.sysbioscience.farmer.properties.PropertiesElementAdapter;
import com.kreativeco.sysbioscience.utils.User;
import com.kreativeco.sysbioscience.utils.WebBridge;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by omar on 5/7/16.
 */
public class FragmentHomeFarmer extends Fragment implements WebBridge.WebBridgeListener{

    LinearLayout llTabHeader;
    RadioGroup radioGroup;
    int webBridgeSelector = 1;

    View v;
    private RecyclerView rvProperties;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        v = inflater.inflate(R.layout.fragment_home_farmer, null);
        rvProperties = (RecyclerView) v.findViewById(R.id.rv_properties);
        rvProperties.setHasFixedSize(false);

        RecyclerView.LayoutManager rvLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        rvProperties.setLayoutManager(rvLayoutManager);

        ImageButton btnAddProperty = (ImageButton) v.findViewById(R.id.btn_add_properties);
        btnAddProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;

                if(webBridgeSelector == 2)
                    intent = new Intent(getActivity(), AddProperty.class);
                else intent = new Intent(getActivity(), AddAssign.class);

                intent.putExtra("jsonData", "");
                intent.putExtra("option", 0);
                getActivity().startActivity(intent);

            }
        });

        llTabHeader = (LinearLayout) v.findViewById(R.id.ll_tab_header);

        radioGroup = (RadioGroup) v.findViewById(R.id.rg_tab);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButtonSelect = (RadioButton) v.findViewById(checkedId);
                String strTag = radioButtonSelect.getTag().toString();

                switch (strTag) {
                    case "0":
                        getAssigns();
                        break;
                    case "1":
                        getProperties();
                        break;
                    default:
                        break;
                }
            }
        });

        getAssigns();

        return v;

    }


    public void getProperties(){
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", User.getToken(getActivity()));
        params.put("metodo", "consultarPorAgricultor");
        params.put("idAgricultor", CurrentDataFarmer.getFarmerId());
        WebBridge.send("Predios.ashx", params, "Obteniedo datos", getActivity(), this);
        webBridgeSelector = 2;
    }


    public void getAssigns(){
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", User.getToken(getActivity()));
        params.put("metodo", "consultarPorAgricultor");
        params.put("idAgricultor", CurrentDataFarmer.getFarmerId());
        WebBridge.send("Asignaciones.ashx", params, "Obteniedo datos", getActivity(), this);
        webBridgeSelector = 1;
    }

    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {
        try {
            boolean status = json.getInt("ResponseCode") == 200;
            if (status) {
                JSONArray jsonArray = json.getJSONArray("Object");
                if(jsonArray.length() == 0){
                    new AlertDialog.Builder(getActivity()).setTitle(R.string.txt_error).setMessage("No hay registros disponibles").setNeutralButton(R.string.bt_close, null).show();
                    return;
                }else {
                    if(webBridgeSelector == 1){
                        RecyclerView.Adapter rvAdapter = new AssignsElementAdapter(jsonArray, getActivity());
                        rvProperties.setAdapter(rvAdapter);
                    }else {
                        RecyclerView.Adapter rvAdapter = new PropertiesElementAdapter(jsonArray, getActivity());
                        rvProperties.setAdapter(rvAdapter);
                    }

                }

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


