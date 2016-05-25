package com.kreativeco.sysbioscience.farmer.properties;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.SectionActivity;
import com.kreativeco.sysbioscience.farmer.currentdatas.CurrentDataProperties;
import com.kreativeco.sysbioscience.farmer.currentdatas.CurrentDataPurchases;
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

    private int option;
    JSONObject jsonObjectData;

    Button btnPossession, btnState, btnLocality, btnCoordinates, btnAddProperty;
    EditText txtNameProperty, txtReferences, txtArea;

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
        txtNameProperty = (EditText) findViewById(R.id.txt_name_possession);
        txtReferences   = (EditText) findViewById(R.id.txt_references);
        txtArea   = (EditText) findViewById(R.id.txt_area);

        setTitle("PREDIOS");

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

            option = intent.getIntExtra("option", 0);

            if (option == 0) {
                btnAddProperty.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //saveAssign();
                    }
                });

                btnCoordinates.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent coordinates = new Intent(AddProperty.this, CoordinatesActivity.class);
                        coordinates.putExtra("option", option);
                        startActivityForResult(coordinates, 10);
                    }
                });

                return;
            }

            if (option == 1) {
                final String json = intent.getStringExtra("jsonData");
                handleJSON(json);
                btnAddProperty.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //updateAssign();
                    }
                });

                btnCoordinates.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent coordinates = new Intent(AddProperty.this, CoordinatesActivity.class);
                        coordinates.putExtra("jsonData", json);
                        coordinates.putExtra("option", option);
                        startActivityForResult(coordinates, 10);
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
    }*/

    public void handleJSON(String json) {
        if (!json.isEmpty()) {
            try {
                jsonObjectData = new JSONObject(json);

                CurrentDataProperties.setPropertyId(jsonObjectData.getInt("Id"));
                CurrentDataProperties.setPropertyIdAgricultor(jsonObjectData.getInt("IdAgricultor"));
                CurrentDataProperties.setPropertyIdTipoPredio(jsonObjectData.getInt("IdTipoPredio"));
                CurrentDataProperties.setPropertyIdEstado(jsonObjectData.getInt("IdEstado"));
                CurrentDataProperties.setPropertyIdMunicipio(jsonObjectData.getInt("IdMunicipio"));
                CurrentDataProperties.setPropertySuperficie(jsonObjectData.getInt("Superficie"));
                CurrentDataProperties.setPropertyTipoPredio(jsonObjectData.getString("TipoPredio"));
                CurrentDataProperties.setPropertyNombre(jsonObjectData.getString("Nombre"));
                CurrentDataProperties.setPropertyReferencias(jsonObjectData.getString("Referencias"));
                CurrentDataProperties.setPropertyNombreEstado(jsonObjectData.getString("NombreEstado"));
                CurrentDataProperties.setPropertyNombreMunicipio(jsonObjectData.getString("NombreMunicipio"));
                CurrentDataProperties.setPropertyPoligono(jsonObjectData.getString("Poligono"));


                txtNameProperty.setText(CurrentDataProperties.getPropertyNombre());
                txtReferences.setText(CurrentDataProperties.getPropertyReferencias());
                btnPossession.setText(CurrentDataProperties.getPropertyTipoPredio());
                btnState.setText(CurrentDataProperties.getPropertyNombreEstado());
                btnLocality.setText(CurrentDataProperties.getPropertyNombreMunicipio());
                txtArea.setText(Integer.toString(CurrentDataProperties.getPropertySuperficie()));

                if(CurrentDataProperties.getPropertyPoligono().contains("POINT"))
                    btnCoordinates.setText("Coordenadas - 1 Punto");
                else {

                    String [] countPoints = CurrentDataProperties.getPropertyPoligono().split(",");
                    String coordinates = Integer.toString(countPoints.length);
                    btnCoordinates.setText("Coordenadas - " + coordinates + " Puntos");
                }

                Log.e("Jsondata PROPERTIES", jsonObjectData.toString());
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

        if (requestCode == 10) {
            if (resultCode == 10) {
                btnCoordinates.setText(ListIds.getStringCoordinatesCounter());
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
