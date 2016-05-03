package com.kreativeco.sysbioscience.farmer.properties;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.SectionActivity;
import com.kreativeco.sysbioscience.utils.ListIds;
import com.kreativeco.sysbioscience.utils.ListMunicipality;
import com.kreativeco.sysbioscience.utils.ListMunicipalityRequest;
import com.kreativeco.sysbioscience.utils.ListPossession;
import com.kreativeco.sysbioscience.utils.ListStates;
import com.kreativeco.sysbioscience.utils.ListStatesRequest;
import com.kreativeco.sysbioscience.utils.WebBridge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.Inet4Address;

/**
 * Created by JuanC on 24/04/2016.
 */
public class AddProperty extends SectionActivity implements WebBridge.WebBridgeListener{

    JSONObject jsonObjectData;

    Button btnPossession, btnState, btnLocality, btnCoordinates, btnAddProperty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);
        overridePendingTransition(R.anim.slide_left_from, R.anim.slide_left);
        setStatusBarColor(SectionActivity.STATUS_BAR_COLOR);

        btnPossession   = (Button) findViewById(R.id.btn_possession_type);
        btnState        = (Button) findViewById(R.id.btn_state);
        btnLocality     = (Button) findViewById(R.id.btn_locality);
        btnCoordinates  = (Button) findViewById(R.id.btn_coordinates);
        btnAddProperty  = (Button) findViewById(R.id.btn_add_property);

        btnCoordinates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coordinates = new Intent(AddProperty.this, CoordinatesActivity.class);
                startActivity(coordinates);
            }
        });


        btnState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLocality.setText("");
                ListIds.setIdLocality(-1);
                ListIds.setIdVariety(-1);
                selectState(v);
            }
        });

        btnLocality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ListIds.getIdState() == -1) return;
                ListIds.setIdVariety(-1);
                selectLocality(v);
            }
        });

        ImageButton headerBackButton = (ImageButton) findViewById(R.id.i_btn_header);
        headerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_right_from, R.anim.slide_right);
            }
        });

        Intent intent = getIntent();
        if (intent != null) {

            int option = intent.getIntExtra("option", 0);
            if (option == 0) {
                btnAddProperty.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //saveAssign();

                    }
                });
                return;
            }

            if (option == 1) {
                String json = intent.getStringExtra("jsonData");
                //handleJSON(json);
                btnAddProperty.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //updateAssign();
                    }
                });
            }
        }


    }

    /*private void updateAssign() {

        ArrayList<String> errors = new ArrayList<String>();
        if (txtCantity.getText().length() < 1 || Integer.parseInt(txtCantity.getText().toString()) <= 0)
            errors.add(getString(R.string.txt_error_cantity));
        if (txtArea.getText().length() < 1) errors.add(getString(R.string.txt_error_area));

        *//*if (ListIds.getIdVariety() == -1) errors.add(getString(R.string.txt_error_variety));
        if (ListIds.getIdSellType() == -1) errors.add(getString(R.string.txt_error_selltype));
        if (ListIds.getIdLocality() == -1) errors.add(getString(R.string.txt_error_state));
        if (ListIds.getIdState() == -1) errors.add(getString(R.string.txt_error_region));
*//*

        if (errors.size() != 0) {
            String msg = "";
            for (String s : errors) {
                msg += "- " + s + "\n";
            }
            new AlertDialog.Builder(this).setTitle(R.string.txt_error).setMessage(msg.trim()).setNeutralButton(R.string.bt_close, null).show();
            return;
        }

        HashMap<String, Object> params = new HashMap<>();

        params.put("idAgricultor", CurrentDataFarmer.getFarmerId());

        params.put("idCompra", ListIds.getIdSellType());
        params.put("idCiclo", ListIds.getIdPeriod());
        params.put("cantidad", txtCantity.getText().toString());
        params.put("idPredio", ListIds.getIdProperty());
        params.put("idTipoSiembra", ListIds.getIdSeedType());
        params.put("loteSemilla", txtSeedLote.getText().toString());
        params.put("superficie", txtArea.getText().toString());
        params.put("fechaSiembra", "2016-05-31");
        params.put("IdStatusAsignacion", 1);
        params.put("Token", User.getToken(this));


        WebBridge.send("Asignaciones.ashx?update", params, "Guardando", this, this);
        params.put("idAsignacion", CurrentDataPurchases.getSaleId());

    }

    private void saveAssign() {

        ArrayList<String> errors = new ArrayList<String>();
        if (txtCantity.getText().length() < 1 || Integer.parseInt(txtCantity.getText().toString()) <= 0)
            errors.add(getString(R.string.txt_error_cantity));
        if (txtArea.getText().length() < 1) errors.add(getString(R.string.txt_error_area));

        *//*if (ListIds.getIdVariety() == -1) errors.add(getString(R.string.txt_error_variety));
        if (ListIds.getIdSellType() == -1) errors.add(getString(R.string.txt_error_selltype));
        if (ListIds.getIdLocality() == -1) errors.add(getString(R.string.txt_error_state));
        if (ListIds.getIdState() == -1) errors.add(getString(R.string.txt_error_region));
*//*

        if (errors.size() != 0) {
            String msg = "";
            for (String s : errors) {
                msg += "- " + s + "\n";
            }
            new AlertDialog.Builder(this).setTitle(R.string.txt_error).setMessage(msg.trim()).setNeutralButton(R.string.bt_close, null).show();
            return;
        }

        HashMap<String, Object> params = new HashMap<>();

        params.put("idAgricultor", CurrentDataFarmer.getFarmerId());

        params.put("idCompra", ListIds.getIdSellType());
        params.put("idCiclo", ListIds.getIdPeriod());
        params.put("cantidad", txtCantity.getText().toString());
        params.put("idPredio", ListIds.getIdProperty());
        params.put("idTipoSiembra", ListIds.getIdSeedType());
        params.put("loteSemilla", txtSeedLote.getText().toString());
        params.put("superficie", txtArea.getText().toString());
        params.put("fechaSiembra", "2016-05-31");
        params.put("IdStatusAsignacion", 1);
        params.put("Token", User.getToken(this));


        params.put("metodo", "insertar");
        WebBridge.send("Asignaciones.ashx?insert", params, "Guardando", this, this);
    }*/

    public void selectPossession(View view) {
        Intent listPossession = new Intent(AddProperty.this, ListPossession.class);
        startActivityForResult(listPossession, 9);
    }

    public void selectState(View view) {
        Intent listStates = new Intent(AddProperty.this, ListStatesRequest.class);
        startActivityForResult(listStates, 1);
    }

    public void selectLocality(View view) {
        Intent listLocality = new Intent(AddProperty.this, ListMunicipalityRequest.class);
        startActivityForResult(listLocality, 2);
    }

    /*

    public void selectSeedType(View view) {
        Intent listSeedType = new Intent(AddProperty.this, ListSeedType.class);
        startActivityForResult(listSeedType, 8);
    }

    public void handleJSON(String json) {
        if (!json.isEmpty()) {
            try {
                jsonObjectData = new JSONObject(json);

                CurrentDataAssigns.setAssignId(jsonObjectData.getInt("Id"));
                CurrentDataAssigns.setAssignIdPurchase(jsonObjectData.getInt("IdCompra"));
                CurrentDataAssigns.setAssignIdFarmer(jsonObjectData.getInt("IdAgricultor"));
                CurrentDataAssigns.setAssignIdVariety(jsonObjectData.getInt("IdVariedad"));
                CurrentDataAssigns.setAssignIdUserAssign(jsonObjectData.getInt("IdUsuarioAsignacion"));
                CurrentDataAssigns.setAssignIdPeriod(jsonObjectData.getInt("IdCiclo"));
                CurrentDataAssigns.setAssignCantity(jsonObjectData.getInt("CantidadKG"));
                CurrentDataAssigns.setAssignIdProperty(jsonObjectData.getInt("IdPredio"));
                CurrentDataAssigns.setAssignIdSeedType(jsonObjectData.getInt("IdTipoSiembra"));
                CurrentDataAssigns.setAssignArea(jsonObjectData.getInt("SuperficieSiembra"));
                CurrentDataAssigns.setAssignIdAssignStatus(jsonObjectData.getInt("IdStatusAsignacion"));

                CurrentDataAssigns.setAssignVariety(jsonObjectData.getString("Variedad"));
                CurrentDataAssigns.setAssignPeriod(jsonObjectData.getString("Ciclo"));
                CurrentDataAssigns.setAssignProperty(jsonObjectData.getString("Predio"));
                CurrentDataAssigns.setAssignSeedType(jsonObjectData.getString("TipoSiembra"));
                CurrentDataAssigns.setAssignStatus(jsonObjectData.getString("StatusAsignacion"));
                CurrentDataAssigns.setAssignDateSeed(jsonObjectData.getString("FechaSiembra"));
                CurrentDataAssigns.setAssignStages(jsonObjectData.getString("Etapas"));
                CurrentDataAssigns.setAssignBeaconObj(jsonObjectData.getString("BeaconObj"));


                HashMap<String, Object> params = new HashMap<>();

                params.put("metodo", "consultarPorAgricultor");
                params.put("idAgricultor", CurrentDataFarmer.getFarmerId());
                WebBridge.send("Compras.ashx", params, "Obteniendo compras", this, this);

                *//*ListIds.setIdState(CurrentDataPurchases.getSaleIdState());
                ListIds.setIdSellType(CurrentDataPurchases.getSaleIdTypeSell());
                ListIds.setIdVariety(CurrentDataPurchases.getSaleIdVariety());
                ListIds.setIdLocality(CurrentDataPurchases.getSaleIdMunicipality());*//*

                txtVariety.setText(CurrentDataAssigns.getAssignVariety());
                txtCantity.setText(Integer.toString(CurrentDataAssigns.getAssignCantity()));

                btnProperty.setText(CurrentDataAssigns.getAssignProperty());
                btnSellType.setText(CurrentDataAssigns.getAssignSeedType());
                btnDateSeed.setText(CurrentDataAssigns.getAssignDateSeed());
                btnPeriod.setText(CurrentDataAssigns.getAssignPeriod());
                txtArea.setText(Integer.toString(CurrentDataAssigns.getAssignArea()));

                Log.e("Jsondata", jsonObjectData.toString());
            } catch (JSONException jsonException) {
                Log.e("JSON", jsonException.toString());
            }
        }
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("requestCode", requestCode + "");
        Log.e("resultCode", resultCode + "");

        if (requestCode == 9) {
            if (resultCode == 9) {
                btnPossession.setText(ListIds.getNamePossession());
            }
        }

        if (requestCode == 1) {
            if (resultCode == 1) {
                btnState.setText(ListIds.getStringState());
            }
        }

        if (requestCode == 2) {
            if (resultCode == 2) {
                btnLocality.setText(ListIds.getStringLocality());
            }
        }

        if (requestCode == 8) {
            if (resultCode == 8) {
                btnCoordinates.setText(ListIds.getNameSeedType());
            }
        }
    }

    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {
        try {
            if(json.getInt("ResponseCode") == 200){
                if(url.contains("Asignaciones.ashx")){
                    ListIds.clear();
                }else if(url.contains("Compras.ashx")){
                    JSONArray purchases = json.getJSONArray("Object");
                    //getPurchase(purchases);
                }

            }
        } catch (JSONException e) {
            Log.e("ERROR", e.toString());
        }
    }

    /*private void getPurchase(JSONArray purchases) {

        for(int i = 0; i < purchases.length(); i ++) {

            try {

                JSONObject purchase = purchases.getJSONObject(i);
                Log.e("Purchase", purchase.toString());
                if(purchase.getInt("Id") == CurrentDataAssigns.getAssignIdPurchase()){
                    btnPurchases.setText(purchase.getString("NoConvenio"));
                    return;
                }

            } catch (JSONException e) {
                Log.e("ERROR", e.toString());
            }

        }
    }*/

    @Override
    public void onWebBridgeFailure(String url, String response) {

    }
}
