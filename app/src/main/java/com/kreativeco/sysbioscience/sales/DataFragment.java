package com.kreativeco.sysbioscience.sales;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kreativeco.sysbioscience.AddProperty;
import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.utils.User;
import com.kreativeco.sysbioscience.utils.WebBridge;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by kreativeco on 01/02/16.
 */
public class DataFragment extends Fragment implements WebBridge.WebBridgeListener{

    private EditText etName, etLastNameA, etLastNameB, etEmail;
    private EditText etCell, etCompany, etRFC, etID, etAddress, etZip;
    private ImageView addIvFarmer;
    private ImageButton iBtnBackArrow;
    
    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.data_fragment, null);

        etName = (EditText) v.findViewById(R.id.et_name);
        etLastNameA = (EditText) v.findViewById(R.id.et_last_name_a);
        etLastNameB = (EditText) v.findViewById(R.id.et_last_name_b);
        etEmail = (EditText) v.findViewById(R.id.et_e_mail);
        etCell = (EditText) v.findViewById(R.id.et_cell);
        etCompany = (EditText) v.findViewById(R.id.et_company);
        etRFC = (EditText) v.findViewById(R.id.et_rfc);
        etID = (EditText) v.findViewById(R.id.et_id);
        etAddress = (EditText) v.findViewById(R.id.et_address);
        etZip = (EditText) v.findViewById(R.id.et_zip);
        addIvFarmer = (ImageView) v.findViewById(R.id.add_iv_farmer);

        setDataFarmer();
        return v;

    }

    private void setDataFarmer() {

        etName.setText(CurrentDataFarmer.getFarmerName());
        etLastNameA.setText(CurrentDataFarmer.getFarmerLastNameA());
        etLastNameB.setText(CurrentDataFarmer.getFarmerLastNameB());
        etEmail.setText(CurrentDataFarmer.getFarmerMail());
        etCell.setText(CurrentDataFarmer.getFarmerPhone());
        etCompany.setText(CurrentDataFarmer.getFarmerCompany());
        etRFC.setText(CurrentDataFarmer.getFarmerRFC());
        etID.setText(CurrentDataFarmer.getFarmerIdCard());
        etAddress.setText(CurrentDataFarmer.getFarmerAddress());
        etZip.setText(CurrentDataFarmer.getFarmerZip());
        //addIvFarmer
    }

    public void updateFarmer(View view) {
        ArrayList<String> errors = new ArrayList<String>();
        if (etName.getText().length() < 1) errors.add(getString(R.string.txt_error_name));
        if (etLastNameA.getText().length() < 1)
            errors.add(getString(R.string.txt_error_last_name_a));
        if (etLastNameB.getText().length() < 1)
            errors.add(getString(R.string.txt_error_last_name_b));
        if (etEmail.getText().length() < 1) errors.add(getString(R.string.txt_error_mail));
        if (etCell.getText().length() < 1) errors.add(getString(R.string.txt_error_cell));
        if (etCompany.getText().length() < 1) errors.add(getString(R.string.txt_error_company));
        if (etRFC.getText().length() < 13) errors.add(getString(R.string.txt_error_rfc));
        if (etID.getText().length() < 1) errors.add(getString(R.string.txt_error_id));
        if (etAddress.getText().length() < 1) errors.add(getString(R.string.txt_error_address));
        if (etZip.getText().length() < 5) errors.add(getString(R.string.txt_error_zip));
//        if (strFileLocation == null) errors.add(getString(R.string.txt_error_photo));

        if (errors.size() != 0) {
            String msg = "";
            for (String s : errors) {
                msg += "- " + s + "\n";
            }
            new AlertDialog.Builder(getActivity()).setTitle(R.string.txt_error).setMessage(msg.trim()).setNeutralButton(R.string.bt_close, null).show();
            return;
        }

        HashMap<String, Object> params = new HashMap<>();

        /*BitmapDrawable bitmapDrawable = ((BitmapDrawable) addIvFarmer.getDrawable());
        Bitmap bitmap = bitmapDrawable .getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] imageInByte = stream.toByteArray();
        ByteArrayInputStream bis = new ByteArrayInputStream(imageInByte);
        */
        params.put("metodo",        "insertar");
        params.put("token", User.getToken(getActivity()));
        //params.put("idUsuarioSubdistribuidor", User.get("IdTipoUsuario", this));
        params.put("mail",          etEmail.getText().toString());
        params.put("password",      "123456");
        params.put("nombre",        etName.getText().toString());
        params.put("apellidoP",     etLastNameA.getText().toString());
        params.put("apellidoM",     etLastNameB.getText().toString());
        params.put("razonSocial",   etCompany.getText().toString());
        params.put("rfc",           etRFC.getText().toString());
        params.put("telefono",      etCell.getText().toString());
        params.put("idMunicipio",   1);
        params.put("direccion",     etAddress.getText().toString());
        params.put("cp",            etZip.getText().toString());
        params.put("credencial",    etID.getText().toString());
        params.put("activo",        true);
        params.put("notifSubdistribuidor",true);



        /*if (strFileLocation != null) {
            File file = new File(strFileLocation);
            params.put("archivoContrato", file);
            params.put("archivoFoto",     file);
        }*/

        WebBridge.send("Agricultor.ashx?insert", params,getActivity().getString(R.string.txt_sending), getActivity(), this);

    }

    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {
        Log.e("JSON SUCCESS", json.toString());

        try {
            boolean status = json.getInt("ResponseCode") == 200;
            if (status) {
                Toast.makeText(getActivity(), getString(R.string.txt_success_farmer), Toast.LENGTH_SHORT).show();
                //finish();
            } else {
                String error = json.getString("Errors");
                new AlertDialog.Builder(getActivity()).setTitle(R.string.txt_error).setMessage(error).setNeutralButton(R.string.bt_close, null).show();
            }

        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }
    }

    @Override
    public void onWebBridgeFailure(String url, String response) {
        Log.e("JSON FAILURE", response);
    }


}
