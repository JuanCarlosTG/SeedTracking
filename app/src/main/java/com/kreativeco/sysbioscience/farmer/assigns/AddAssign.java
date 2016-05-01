package com.kreativeco.sysbioscience.farmer.assigns;

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
import com.kreativeco.sysbioscience.farmer.currentdatas.CurrentDataPurchases;
import com.kreativeco.sysbioscience.utils.ListIds;
import com.kreativeco.sysbioscience.utils.ListMunicipality;
import com.kreativeco.sysbioscience.utils.ListPurchases;
import com.kreativeco.sysbioscience.utils.ListSellType;
import com.kreativeco.sysbioscience.utils.ListStates;
import com.kreativeco.sysbioscience.utils.ListVarieties;
import com.kreativeco.sysbioscience.utils.User;
import com.kreativeco.sysbioscience.utils.WebBridge;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JuanC on 24/04/2016.
 */
public class AddAssign extends SectionActivity implements WebBridge.WebBridgeListener{

    JSONObject jsonObjectData;
    EditText txtCantity;
    TextView txtVariety;
    Button btnPurchases, btnPeriod, btnProperty, btnSellType, btnDateSeed;
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

        btnPurchases = (Button) findViewById(R.id.btn_purchases);
        btnPeriod = (Button) findViewById(R.id.btn_period);
        btnProperty = (Button) findViewById(R.id.btn_property);
        btnSellType = (Button) findViewById(R.id.btn_sell_type);
        btnDateSeed = (Button) findViewById(R.id.btn_date);

        ImageButton headerBackButton = (ImageButton) findViewById(R.id.i_btn_header);
        headerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_right_from, R.anim.slide_right);
            }
        });


    }

    private void updateSell() {

        ArrayList<String> errors = new ArrayList<String>();
        if (txtVariety.getText().length() < 1) errors.add(getString(R.string.txt_error_contract));
        if (txtCantity.getText().length() < 1) errors.add(getString(R.string.txt_error_cantity));
        if (ListIds.getIdVariety() == -1) errors.add(getString(R.string.txt_error_variety));
        if (ListIds.getIdSellType() == -1) errors.add(getString(R.string.txt_error_selltype));
        if (ListIds.getIdLocality() == -1) errors.add(getString(R.string.txt_error_state));
        if (ListIds.getIdState() == -1) errors.add(getString(R.string.txt_error_region));


        if (errors.size() != 0) {
            String msg = "";
            for (String s : errors) {
                msg += "- " + s + "\n";
            }
            new AlertDialog.Builder(this).setTitle(R.string.txt_error).setMessage(msg.trim()).setNeutralButton(R.string.bt_close, null).show();
            return;
        }

        HashMap<String, Object> params = new HashMap<>();

        params.put("idAgricultor", CurrentDataPurchases.getSaleIdFarmer());
        params.put("noConvenio", txtVariety.getText().toString());
        params.put("idVariedad", ListIds.getIdVariety());
        params.put("cantidad", txtCantity.getText().toString());
        params.put("idTipoCompra", ListIds.getIdSellType());
        params.put("idMunicipio", ListIds.getIdLocality());
        params.put("Token", User.getToken(this));


        params.put("metodo", "actualizar");
        params.put("idCompra", CurrentDataPurchases.getSaleId());
        WebBridge.send("Compras.ashx?update", params, "Guardando", this, this);

    }

    private void saveSell() {

        ArrayList<String> errors = new ArrayList<String>();
        if (txtVariety.getText().length() < 1) errors.add(getString(R.string.txt_error_contract));
        if (txtCantity.getText().length() < 1 || Integer.parseInt(txtCantity.getText().toString()) <= 0)
            errors.add(getString(R.string.txt_error_cantity));
        if (ListIds.getIdVariety() == -1) errors.add(getString(R.string.txt_error_variety));
        if (ListIds.getIdSellType() == -1) errors.add(getString(R.string.txt_error_selltype));
        if (ListIds.getIdLocality() == -1) errors.add(getString(R.string.txt_error_state));
        if (ListIds.getIdState() == -1) errors.add(getString(R.string.txt_error_region));


        if (errors.size() != 0) {
            String msg = "";
            for (String s : errors) {
                msg += "- " + s + "\n";
            }
            new AlertDialog.Builder(this).setTitle(R.string.txt_error).setMessage(msg.trim()).setNeutralButton(R.string.bt_close, null).show();
            return;
        }

        HashMap<String, Object> params = new HashMap<>();

        params.put("idAgricultor", CurrentDataPurchases.getSaleIdFarmer());
        params.put("noConvenio", txtVariety.getText().toString());
        params.put("idVariedad", ListIds.getIdVariety());
        params.put("cantidad", txtCantity.getText().toString());
        params.put("idTipoCompra", ListIds.getIdSellType());
        params.put("idMunicipio", ListIds.getIdLocality());
        params.put("Token", User.getToken(this));

        params.put("metodo", "insertar");
        WebBridge.send("Compras.ashx?insert", params, "Guardando", this, this);
    }

    public void selectPurchases(View view) {
        Intent listPurchases = new Intent(AddAssign.this, ListPurchases.class);
        startActivityForResult(listPurchases, 1);
    }

    private void selectMunicipality() {
        Intent listLocalities = new Intent(AddAssign.this, ListMunicipality.class);
        startActivityForResult(listLocalities, 2);
    }

    private void selectVariety() {
        Intent listVarieties = new Intent(AddAssign.this, ListVarieties.class);
        startActivityForResult(listVarieties, 3);
    }

    private void selectSellType() {
        Intent listSellType = new Intent(AddAssign.this, ListSellType.class);
        startActivityForResult(listSellType, 4);
    }

    public void handleJSON(String json) {
        if (!json.isEmpty()) {
            try {
                jsonObjectData = new JSONObject(json);

                CurrentDataPurchases.setSalesCantity(jsonObjectData.getString("Cantidad"));
                CurrentDataPurchases.setSaleDate(jsonObjectData.getString("Fecha"));
                CurrentDataPurchases.setSaleId(jsonObjectData.getString("Id"));
                CurrentDataPurchases.setSaleUserSell(jsonObjectData.getString("IdUsuarioCompra"));
                CurrentDataPurchases.setSaleIdVariety(jsonObjectData.getInt("IdVariedad"));
                CurrentDataPurchases.setSaleNameState(jsonObjectData.getString("NombreEstado"));
                CurrentDataPurchases.setSaleNameMunicipality(jsonObjectData.getString("NombreMunicipio"));
                CurrentDataPurchases.setSaleKG(jsonObjectData.getString("RemanenteKG"));
                CurrentDataPurchases.setSaleSeed(jsonObjectData.getString("Semilla"));
                CurrentDataPurchases.setSaleTypeSell(jsonObjectData.getString("TipoCompra"));
                CurrentDataPurchases.setSaleVariety(jsonObjectData.getString("Variedad"));
                CurrentDataPurchases.setSaleIdTypeSell(jsonObjectData.getInt("IdTipoCompra"));
                CurrentDataPurchases.setSaleIdFarmer(jsonObjectData.getInt("IdAgricultor"));
                CurrentDataPurchases.setSaleIdState(jsonObjectData.getInt("IdEstado"));
                CurrentDataPurchases.setSaleIdMunicipality(jsonObjectData.getInt("IdMunicipio"));
                CurrentDataPurchases.setSaleRequest(jsonObjectData.getString("IdSolicitud"));
                CurrentDataPurchases.setSaleNumberAgreement(jsonObjectData.getString("NoConvenio"));

                ListIds.setIdState(CurrentDataPurchases.getSaleIdState());
                ListIds.setIdSellType(CurrentDataPurchases.getSaleIdTypeSell());
                ListIds.setIdVariety(CurrentDataPurchases.getSaleIdVariety());
                ListIds.setIdLocality(CurrentDataPurchases.getSaleIdMunicipality());

                txtVariety.setText(CurrentDataPurchases.getSaleNumberAgreement());
                txtCantity.setText(CurrentDataPurchases.getSalesCantity());

                btnPurchases.setText(CurrentDataPurchases.getSaleNameState());
                btnProperty.setText(CurrentDataPurchases.getSaleNameMunicipality());
                btnSellType.setText(CurrentDataPurchases.getSaleTypeSell());
                btnDateSeed.setText(CurrentDataPurchases.getSaleVariety());

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

        if (requestCode == 1) {
            if (resultCode == 1) {
                txtVariety.setText(ListIds.getVarietyPurchase());
                btnPurchases.setText(ListIds.getNamePurchase());
            }
        }

        /*if (requestCode == 2) {
            if (resultCode == 2) {
                idMunicipality = data.getIntExtra("idMunicipality", 0);
                ListIds.setIdLocality(idMunicipality);
                municipalityName = data.getStringExtra("municipalityName");
                btnMunicipality.setText(municipalityName);
            }
        }

        if (requestCode == 3) {
            if (resultCode == 3) {
                idVariety = data.getIntExtra("idVariety", 0);
                ListIds.setIdVariety(idVariety);
                varietyName = data.getStringExtra("nameVariety");
                btnVariety.setText(varietyName);
            }
        }

        if (requestCode == 4) {
            if (resultCode == 4) {

                idSellType = data.getIntExtra("id", 0);
                ListIds.setIdSellType(idSellType);
                sellTypeName = data.getStringExtra("name");
                btnSellType.setText(sellTypeName);
            }
        }*/
    }

    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {
        try {
            if(json.getInt("ResponseCode") == 200){
                ListIds.clear();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWebBridgeFailure(String url, String response) {

    }
}
