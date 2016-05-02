package com.kreativeco.sysbioscience.farmer.properties;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.SectionActivity;
import com.kreativeco.sysbioscience.farmer.currentdatas.CurrentDataAssigns;
import com.kreativeco.sysbioscience.farmer.currentdatas.CurrentDataFarmer;
import com.kreativeco.sysbioscience.farmer.currentdatas.CurrentDataPurchases;
import com.kreativeco.sysbioscience.utils.ListIds;
import com.kreativeco.sysbioscience.utils.ListPeriods;
import com.kreativeco.sysbioscience.utils.ListProperties;
import com.kreativeco.sysbioscience.utils.ListPurchases;
import com.kreativeco.sysbioscience.utils.ListSeedType;
import com.kreativeco.sysbioscience.utils.User;
import com.kreativeco.sysbioscience.utils.WebBridge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JuanC on 24/04/2016.
 */
public class AddProperty extends SectionActivity implements WebBridge.WebBridgeListener{

    JSONObject jsonObjectData;
    EditText txtCantity, txtSeedLote, txtArea;
    TextView txtVariety;
    Button btnPurchases, btnPeriod, btnProperty, btnSellType, btnDateSeed;
    Button btnAddSeed;
    int idState, idMunicipality, idVariety, idSellType;
    String stateName, municipalityName, varietyName, sellTypeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assign);
        overridePendingTransition(R.anim.slide_left_from, R.anim.slide_left);
        setStatusBarColor(SectionActivity.STATUS_BAR_COLOR);

        txtVariety = (TextView) findViewById(R.id.txt_variety);
        txtCantity = (EditText) findViewById(R.id.txt_cantity);
        txtSeedLote = (EditText) findViewById(R.id.txt_seed_lote);
        txtArea = (EditText) findViewById(R.id.txt_area);

        btnPurchases = (Button) findViewById(R.id.btn_purchases);
        btnPeriod = (Button) findViewById(R.id.btn_period);
        btnProperty = (Button) findViewById(R.id.btn_property);
        btnSellType = (Button) findViewById(R.id.btn_sell_type);
        btnDateSeed = (Button) findViewById(R.id.btn_date);

        btnAddSeed = (Button) findViewById(R.id.btn_add_seed);

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
                btnAddSeed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveAssign();
                        //finish();
                    }
                });
                return;
            }

            if (option == 1) {
                String json = intent.getStringExtra("jsonData");
                handleJSON(json);
                btnAddSeed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateAssign();
                    }
                });
            }
        }


    }

    private void updateAssign() {

        ArrayList<String> errors = new ArrayList<String>();
        if (txtCantity.getText().length() < 1 || Integer.parseInt(txtCantity.getText().toString()) <= 0)
            errors.add(getString(R.string.txt_error_cantity));
        if (txtArea.getText().length() < 1) errors.add(getString(R.string.txt_error_area));

        /*if (ListIds.getIdVariety() == -1) errors.add(getString(R.string.txt_error_variety));
        if (ListIds.getIdSellType() == -1) errors.add(getString(R.string.txt_error_selltype));
        if (ListIds.getIdLocality() == -1) errors.add(getString(R.string.txt_error_state));
        if (ListIds.getIdState() == -1) errors.add(getString(R.string.txt_error_region));
*/

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

        /*if (ListIds.getIdVariety() == -1) errors.add(getString(R.string.txt_error_variety));
        if (ListIds.getIdSellType() == -1) errors.add(getString(R.string.txt_error_selltype));
        if (ListIds.getIdLocality() == -1) errors.add(getString(R.string.txt_error_state));
        if (ListIds.getIdState() == -1) errors.add(getString(R.string.txt_error_region));
*/

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
    }

    public void selectPurchases(View view) {
        Intent listPurchases = new Intent(AddProperty.this, ListPurchases.class);
        startActivityForResult(listPurchases, 5);
    }

    public void selectProperty(View view) {
        Intent listProperties = new Intent(AddProperty.this, ListProperties.class);
        startActivityForResult(listProperties, 6);
    }

    public void selectPeriod(View view) {
        Intent listPeriods = new Intent(AddProperty.this, ListPeriods.class);
        startActivityForResult(listPeriods, 7);
    }

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

                /*ListIds.setIdState(CurrentDataPurchases.getSaleIdState());
                ListIds.setIdSellType(CurrentDataPurchases.getSaleIdTypeSell());
                ListIds.setIdVariety(CurrentDataPurchases.getSaleIdVariety());
                ListIds.setIdLocality(CurrentDataPurchases.getSaleIdMunicipality());*/

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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("requestCode", requestCode + "");
        Log.e("resultCode", resultCode + "");

        if (requestCode == 5) {
            if (resultCode == 5) {
                txtVariety.setText(ListIds.getVarietyPurchase());
                btnPurchases.setText(ListIds.getNamePurchase());
            }
        }

        if (requestCode == 6) {
            if (resultCode == 6) {
                btnProperty.setText(ListIds.getNameProperty());
            }
        }

        if (requestCode == 7) {
            if (resultCode == 7) {
                btnPeriod.setText(ListIds.getNamePeriod());
            }
        }

        if (requestCode == 8) {
            if (resultCode == 8) {
                btnSellType.setText(ListIds.getNameSeedType());
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
                    getPurchase(purchases);
                }

            }
        } catch (JSONException e) {
            Log.e("ERROR", e.toString());
        }
    }

    private void getPurchase(JSONArray purchases) {

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
    }

    @Override
    public void onWebBridgeFailure(String url, String response) {

    }
}
