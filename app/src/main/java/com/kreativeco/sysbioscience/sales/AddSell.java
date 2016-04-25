package com.kreativeco.sysbioscience.sales;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.SectionActivity;
import com.kreativeco.sysbioscience.utils.ListMunicipality;
import com.kreativeco.sysbioscience.utils.ListStates;
import com.kreativeco.sysbioscience.utils.ListIds;
import com.kreativeco.sysbioscience.utils.ListVarieties;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by JuanC on 24/04/2016.
 */
public class AddSell extends SectionActivity {

    JSONObject jsonObjectData;
    EditText txtBill, txtCantity;
    Button btnState, btnMunicipality, btnVariety, btnSellType;
    int idState, idMunicipality, idvariety;
    String stateName, municipalityName, varietyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sell);

        txtBill = (EditText) findViewById(R.id.txt_bill);
        txtCantity = (EditText) findViewById(R.id.txt_cantity);
        btnState = (Button) findViewById(R.id.btn_state);
        btnMunicipality = (Button) findViewById(R.id.btn_municipality);
        btnVariety = (Button) findViewById(R.id.btn_variety);
        btnSellType = (Button) findViewById(R.id.btn_sell_type);

        btnState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectState();
            }
        });

        btnMunicipality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMunicipality();
            }
        });

        btnVariety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectVariety();
            }
        });

        Intent intent = getIntent();
        if(intent != null){

            int option = intent.getIntExtra("option", 0);
            if(option == 0)
                return;
            if (option == 1){
                String json = intent.getStringExtra("jsonData");
                handleJSON(json);
            }
        }
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

    public void handleJSON(String json){
        if(!json.isEmpty()){
            try {
                jsonObjectData = new JSONObject(json);

                CurrentDataSales.setSalesCantity(jsonObjectData.getString("Cantidad"));
                CurrentDataSales.setSaleDate(jsonObjectData.getString("Fecha"));
                CurrentDataSales.setSaleId(jsonObjectData.getString("Id"));
                CurrentDataSales.setSaleIdFarmer(jsonObjectData.getString("IdAgricultor"));
                CurrentDataSales.setSaleIdState(jsonObjectData.getString("IdEstado"));
                CurrentDataSales.setSaleIdMunicipality(jsonObjectData.getString("IdMunicipio"));
                CurrentDataSales.setSaleRequest(jsonObjectData.getString("IdSolicitud"));
                CurrentDataSales.setSaleIdTypeSell(jsonObjectData.getString("IdTipoCompra"));
                CurrentDataSales.setSaleUserSell(jsonObjectData.getString("IdUsuarioCompra"));
                CurrentDataSales.setSaleIdVariety(jsonObjectData.getString("IdVariedad"));
                CurrentDataSales.setSaleNumberAgreement(jsonObjectData.getString("NoConvenio"));
                CurrentDataSales.setSaleNameState(jsonObjectData.getString("NombreEstado"));
                CurrentDataSales.setSaleNameMunicipality(jsonObjectData.getString("NombreMunicipio"));
                CurrentDataSales.setSaleKG(jsonObjectData.getString("RemanenteKG"));
                CurrentDataSales.setSaleSeed(jsonObjectData.getString("Semilla"));
                CurrentDataSales.setSaleTypeSell(jsonObjectData.getString("TipoCompra"));
                CurrentDataSales.setSaleVariety(jsonObjectData.getString("Variedad"));

                txtBill.setText(CurrentDataSales.getSaleNumberAgreement());
                txtCantity.setText(CurrentDataSales.getSalesCantity());

                btnState.setText(CurrentDataSales.getSaleNameState());
                btnMunicipality.setText(CurrentDataSales.getSaleNameMunicipality());
                btnSellType.setText(CurrentDataSales.getSaleTypeSell());
                btnVariety.setText(CurrentDataSales.getSaleVariety());

                Log.e("Jsondata", jsonObjectData.toString());
            }catch (JSONException jsonException){
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

                idvariety = data.getIntExtra("idVariety", 0);
                varietyName = data.getStringExtra("nameVariety");
                btnVariety.setText(varietyName);
            }
        }
    }
}
