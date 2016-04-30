package com.kreativeco.sysbioscience.farmer.datafarmer;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.farmer.currentdatas.CurrentDataFarmer;
import com.kreativeco.sysbioscience.farmer.FarmerActivity;
import com.kreativeco.sysbioscience.utils.ListIds;
import com.kreativeco.sysbioscience.utils.ListMunicipality;
import com.kreativeco.sysbioscience.utils.ListStates;
import com.kreativeco.sysbioscience.utils.User;
import com.kreativeco.sysbioscience.utils.WebBridge;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kreativeco on 01/02/16.
 */
public class DataFragment extends Fragment implements WebBridge.WebBridgeListener {

    private EditText etName, etLastNameA, etLastNameB, etEmail;
    private EditText etCell, etCompany, etRFC, etID, etAddress, etZip;
    private ImageView addImageFarmer, addImageContract;
    private ImageButton iBtnBackArrow;
    private Button btnUpdateFarmer;
    Button btnState, btnMunicipality;

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
        addImageFarmer = (ImageView) v.findViewById(R.id.add_iv_farmer);
        addImageContract = (ImageView) v.findViewById(R.id.add_iv_contract);
        btnUpdateFarmer = (Button) v.findViewById(R.id.btn_update_farmer);

        btnUpdateFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFarmer(v);
            }
        });

        addImageFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage(0);
            }
        });

        addImageContract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage(1);
            }
        });

        btnState = (Button) v.findViewById(R.id.btn_state);
        btnMunicipality = (Button) v.findViewById(R.id.btn_municipality);

        btnState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMunicipality.setText("");
                ListIds.setIdLocality(-1);
                ListIds.setIdVariety(-1);
                selectState();
            }
        });

        btnMunicipality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ListIds.getIdState() == -1) return;
                ListIds.setIdVariety(-1);
                selectMunicipality();
            }
        });

        ListIds.setIdState(CurrentDataFarmer.getFarmerIdState());
        ListIds.setIdLocality(CurrentDataFarmer.getFarmerIdMunicipality());
        ListIds.setStringLocality(CurrentDataFarmer.getFarmerNameMunicipality());
        ListIds.setStringState(CurrentDataFarmer.getFarmerNameState());

        return v;

    }

    private void selectState() {
        Intent listStates = new Intent(getActivity(), ListStates.class);
        startActivityForResult(listStates, 1);
    }

    private void selectMunicipality() {
        Intent listLocalities = new Intent(getActivity(), ListMunicipality.class);
        startActivityForResult(listLocalities, 2);
    }

    public void getImage(int imageSelector) {
        ((FarmerActivity) getActivity()).clickCamera(imageSelector);
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
        btnMunicipality.setText(ListIds.getStringLocality());
        btnState.setText(ListIds.getStringState());
        //addIvFarmer
    }

    public void setImageFarmer() {


        if (Build.VERSION.SDK_INT >= 23) {
            Log.e("FILE LOCATION", CurrentDataFarmer.getStrFileFarmer());
            Glide.with(getActivity()).load(CurrentDataFarmer.getStrFileFarmer()).into(addImageFarmer);
        } else {
            Bitmap photo = BitmapFactory.decodeFile(CurrentDataFarmer.getStrFileFarmer());
            addImageFarmer.setImageURI(Uri.parse(CurrentDataFarmer.getStrFileFarmer()));
        }
    }

    public void setImageContract() {

        if (Build.VERSION.SDK_INT >= 23){
                Glide.with(this).load(CurrentDataFarmer.getStrFileContract()).into(addImageContract);
            }else{
                Bitmap photo = BitmapFactory.decodeFile(CurrentDataFarmer.getStrFileContract());
                addImageFarmer.setImageURI(Uri.parse(CurrentDataFarmer.getStrFileContract()));
            }
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
        if (CurrentDataFarmer.getStrFileContract().equals("")) errors.add(getString(R.string.txt_error_photo));
        if (ListIds.getIdLocality() == -1) errors.add(getString(R.string.txt_error_region));

        if (errors.size() != 0) {
            String msg = "";
            for (String s : errors) {
                msg += "- " + s + "\n";
            }
            new AlertDialog.Builder(getActivity()).setTitle(R.string.txt_error).setMessage(msg.trim()).setNeutralButton(R.string.bt_close, null).show();
            return;
        }

        HashMap<String, Object> params = new HashMap<>();


        params.put("metodo", "actualizar");
        params.put("token", User.getToken(getActivity()));
        //params.put("idUsuarioSubdistribuidor", User.get("IdTipoUsuario", this));
        params.put("mail", etEmail.getText().toString());
        params.put("password", "123456");
        params.put("nombre", etName.getText().toString());
        params.put("apellidoP", etLastNameA.getText().toString());
        params.put("apellidoM", etLastNameB.getText().toString());
        params.put("razonSocial", etCompany.getText().toString());
        params.put("rfc", etRFC.getText().toString());
        params.put("telefono", etCell.getText().toString());
        params.put("idMunicipio", ListIds.getIdLocality());
        params.put("direccion", etAddress.getText().toString());
        params.put("cp", etZip.getText().toString());
        params.put("credencial", etID.getText().toString());
        params.put("activo", true);
        params.put("notifSubdistribuidor", true);
        params.put("idUsuario", CurrentDataFarmer.getFarmerId());

        Log.e("idUSEUARIO", CurrentDataFarmer.getFarmerId() + "");

        if (!CurrentDataFarmer.getFarmerContract().equals("")) {
            File fileFarmer = new File(CurrentDataFarmer.getFarmerContract());
            params.put("archivoContrato", fileFarmer);
        }

        if (!CurrentDataFarmer.getStrFileFarmer().equals("")) {
            File photoFile = new File(CurrentDataFarmer.getStrFileFarmer());
            params.put("archivoFoto", photoFile);
        }else params.put("archivoFoto", "image.jpg");

        WebBridge.send("Agricultor.ashx?update", params, getActivity().getString(R.string.txt_sending), getActivity(), this);

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

    @Override
    public void onResume() {
        super.onResume();
        setImageFarmer();
        setImageContract();
        setDataFarmer();
    }
}