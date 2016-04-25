package com.kreativeco.sysbioscience.sales;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.SectionActivity;
import com.kreativeco.sysbioscience.utils.ListMunicipality;
import com.kreativeco.sysbioscience.utils.ListSellType;
import com.kreativeco.sysbioscience.utils.ListStates;
import com.kreativeco.sysbioscience.utils.ListIds;
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
public class AddSell extends SectionActivity implements WebBridge.WebBridgeListener{

    JSONObject jsonObjectData;
    EditText txtBill, txtCantity;
    Button btnState, btnMunicipality, btnVariety, btnSellType, btnAddSell;
    int idState, idMunicipality, idVariety, idSellType;
    String stateName, municipalityName, varietyName, sellTypeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sell);
        overridePendingTransition(R.anim.slide_left_from, R.anim.slide_left);
        setStatusBarColor(SectionActivity.STATUS_BAR_COLOR);

        txtBill = (EditText) findViewById(R.id.txt_bill);
        txtCantity = (EditText) findViewById(R.id.txt_cantity);
        btnState = (Button) findViewById(R.id.btn_state);
        btnMunicipality = (Button) findViewById(R.id.btn_municipality);
        btnVariety = (Button) findViewById(R.id.btn_variety);
        btnSellType = (Button) findViewById(R.id.btn_sell_type);
        btnAddSell = (Button) findViewById(R.id.btn_add_sell);

        ImageButton headerBackButton = (ImageButton) findViewById(R.id.i_btn_header);
        headerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_right_from, R.anim.slide_right);
            }
        });

        btnState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMunicipality.setText("");
                btnVariety.setText("");
                ListIds.setIdLocality(-1);
                ListIds.setIdVariety(-1);
                selectState();
            }
        });

        btnMunicipality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ListIds.getIdState() == -1) return;
                btnVariety.setText("");
                ListIds.setIdVariety(-1);
                selectMunicipality();
            }
        });

        btnVariety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ListIds.getIdState() == -1 || ListIds.getIdLocality() == -1) return;
                selectVariety();
            }
        });

        btnSellType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSellType();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {

            int option = intent.getIntExtra("option", 0);
            if (option == 0) {
                btnAddSell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveSell();
                        //finish();
                    }
                });
                return;
            }

            if (option == 1) {
                String json = intent.getStringExtra("jsonData");
                handleJSON(json);
                btnAddSell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateSell();
                    }
                });
            }
        }
    }

    private void updateSell() {

        ArrayList<String> errors = new ArrayList<String>();
        if (txtBill.getText().length() < 1) errors.add(getString(R.string.txt_error_contract));
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

        params.put("idAgricultor", CurrentDataSales.getSaleIdFarmer());
        params.put("noConvenio", txtBill.getText().toString());
        params.put("idVariedad", ListIds.getIdVariety());
        params.put("cantidad", txtCantity.getText().toString());
        params.put("idTipoCompra", ListIds.getIdSellType());
        params.put("idMunicipio", ListIds.getIdLocality());
        params.put("Token", User.getToken(this));


        params.put("metodo", "actualizar");
        params.put("idCompra", CurrentDataSales.getSaleId());
        WebBridge.send("Compras.ashx?update", params, "Guardando", this, this);

    }

    private void saveSell() {

        ArrayList<String> errors = new ArrayList<String>();
        if (txtBill.getText().length() < 1) errors.add(getString(R.string.txt_error_contract));
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

        params.put("idAgricultor", CurrentDataSales.getSaleIdFarmer());
        params.put("noConvenio", txtBill.getText().toString());
        params.put("idVariedad", ListIds.getIdVariety());
        params.put("cantidad", txtCantity.getText().toString());
        params.put("idTipoCompra", ListIds.getIdSellType());
        params.put("idMunicipio", ListIds.getIdLocality());
        params.put("Token", User.getToken(this));

        params.put("metodo", "insertar");
        WebBridge.send("Compras.ashx?insert", params, "Guardando", this, this);
    }

    private void selectState() {
        Intent listStates = new Intent(AddSell.this, ListStates.class);
        startActivityForResult(listStates, 1);
    }

    private void selectMunicipality() {
        Intent listLocalities = new Intent(AddSell.this, ListMunicipality.class);
        startActivityForResult(listLocalities, 2);
    }

    private void selectVariety() {
        Intent listVarieties = new Intent(AddSell.this, ListVarieties.class);
        startActivityForResult(listVarieties, 3);
    }

    private void selectSellType() {
        Intent listSellType = new Intent(AddSell.this, ListSellType.class);
        startActivityForResult(listSellType, 4);
    }

    public void handleJSON(String json) {
        if (!json.isEmpty()) {
            try {
                jsonObjectData = new JSONObject(json);

                CurrentDataSales.setSalesCantity(jsonObjectData.getString("Cantidad"));
                CurrentDataSales.setSaleDate(jsonObjectData.getString("Fecha"));
                CurrentDataSales.setSaleId(jsonObjectData.getString("Id"));
                CurrentDataSales.setSaleUserSell(jsonObjectData.getString("IdUsuarioCompra"));
                CurrentDataSales.setSaleIdVariety(jsonObjectData.getInt("IdVariedad"));
                CurrentDataSales.setSaleNameState(jsonObjectData.getString("NombreEstado"));
                CurrentDataSales.setSaleNameMunicipality(jsonObjectData.getString("NombreMunicipio"));
                CurrentDataSales.setSaleKG(jsonObjectData.getString("RemanenteKG"));
                CurrentDataSales.setSaleSeed(jsonObjectData.getString("Semilla"));
                CurrentDataSales.setSaleTypeSell(jsonObjectData.getString("TipoCompra"));
                CurrentDataSales.setSaleVariety(jsonObjectData.getString("Variedad"));
                CurrentDataSales.setSaleIdTypeSell(jsonObjectData.getInt("IdTipoCompra"));
                CurrentDataSales.setSaleIdFarmer(jsonObjectData.getInt("IdAgricultor"));
                CurrentDataSales.setSaleIdState(jsonObjectData.getInt("IdEstado"));
                CurrentDataSales.setSaleIdMunicipality(jsonObjectData.getInt("IdMunicipio"));
                CurrentDataSales.setSaleRequest(jsonObjectData.getString("IdSolicitud"));
                CurrentDataSales.setSaleNumberAgreement(jsonObjectData.getString("NoConvenio"));

                ListIds.setIdState(CurrentDataSales.getSaleIdState());
                ListIds.setIdSellType(CurrentDataSales.getSaleIdTypeSell());
                ListIds.setIdVariety(CurrentDataSales.getSaleIdVariety());
                ListIds.setIdLocality(CurrentDataSales.getSaleIdMunicipality());

                txtBill.setText(CurrentDataSales.getSaleNumberAgreement());
                txtCantity.setText(CurrentDataSales.getSalesCantity());

                btnState.setText(CurrentDataSales.getSaleNameState());
                btnMunicipality.setText(CurrentDataSales.getSaleNameMunicipality());
                btnSellType.setText(CurrentDataSales.getSaleTypeSell());
                btnVariety.setText(CurrentDataSales.getSaleVariety());

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
                idState = data.getIntExtra("idState", 0);
                ListIds.setIdState(idState);
                stateName = data.getStringExtra("nameState");
                btnState.setText(stateName);

            }
        }

        if (requestCode == 2) {
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
        }
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
