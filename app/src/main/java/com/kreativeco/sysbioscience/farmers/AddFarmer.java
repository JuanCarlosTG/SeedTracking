package com.kreativeco.sysbioscience.farmers;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.SectionActivity;
import com.kreativeco.sysbioscience.farmer.currentdatas.CurrentDataFarmer;
import com.kreativeco.sysbioscience.utils.ListIds;
import com.kreativeco.sysbioscience.utils.ListMunicipality;
import com.kreativeco.sysbioscience.utils.ListStates;
import com.kreativeco.sysbioscience.utils.User;
import com.kreativeco.sysbioscience.utils.WebBridge;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AddFarmer extends SectionActivity implements WebBridge.WebBridgeListener {

    private EditText etName, etLastNameA, etLastNameB, etEmail;
    private EditText etCell, etCompany, etRFC, etID, etAddress, etZip;
    private ImageView addImageFarmer, addImageContract;
    Button btnState, btnMunicipality;

    private static final int START_CAMERA = 0;
    private static final int REQUEST_EXTERNAL_STORAGE_RESULT = 0;
    String strFileLocation = null;
    int imageSelector = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_farmer);
        overridePendingTransition(R.anim.slide_left_from, R.anim.slide_left);
        setStatusBarColor(SectionActivity.STATUS_BAR_COLOR);

        ImageButton headerBackButton = (ImageButton) findViewById(R.id.i_btn_header);
        headerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_right_from, R.anim.slide_right);
            }
        });

        etName = (EditText) findViewById(R.id.et_name);
        etLastNameA = (EditText) findViewById(R.id.et_last_name_a);
        etLastNameB = (EditText) findViewById(R.id.et_last_name_b);
        etEmail = (EditText) findViewById(R.id.et_e_mail);
        etCell = (EditText) findViewById(R.id.et_cell);
        etCompany = (EditText) findViewById(R.id.et_company);
        etRFC = (EditText) findViewById(R.id.et_rfc);
        etID = (EditText) findViewById(R.id.et_id);
        etAddress = (EditText) findViewById(R.id.et_address);
        etZip = (EditText) findViewById(R.id.et_zip);
        addImageFarmer = (ImageView) findViewById(R.id.add_iv_farmer);
        addImageContract = (ImageView) findViewById(R.id.add_iv_contract);

        addImageFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelector = 1;
                clickCamera(v);
            }
        });

        addImageContract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelector = 2;
                clickCamera(v);
            }
        });

        btnState = (Button) findViewById(R.id.btn_state);
        btnMunicipality = (Button) findViewById(R.id.btn_municipality);

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


    }

    private void selectState() {
        Intent listStates = new Intent(AddFarmer.this, ListStates.class);
        startActivityForResult(listStates, 1);
    }

    private void selectMunicipality() {
        Intent listLocalities = new Intent(AddFarmer.this, ListMunicipality.class);
        startActivityForResult(listLocalities, 2);
    }

    public void saveFarmer(View view) {
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
        if (strFileLocation == null) errors.add(getString(R.string.txt_error_photo));
        if (ListIds.getIdLocality() == -1) errors.add(getString(R.string.txt_error_region));

        if (errors.size() != 0) {
            String msg = "";
            for (String s : errors) {
                msg += "- " + s + "\n";
            }
            new AlertDialog.Builder(this).setTitle(R.string.txt_error).setMessage(msg.trim()).setNeutralButton(R.string.bt_close, null).show();
            return;
        }

        HashMap<String, Object> params = new HashMap<>();

        params.put("metodo",        "insertar");
        params.put("token", User.getToken(this));
        params.put("mail",          etEmail.getText().toString());
        params.put("password",      "123456");
        params.put("nombre",        etName.getText().toString());
        params.put("apellidoP",     etLastNameA.getText().toString());
        params.put("apellidoM",     etLastNameB.getText().toString());
        params.put("razonSocial",   etCompany.getText().toString());
        params.put("rfc",           etRFC.getText().toString());
        params.put("telefono",      etCell.getText().toString());
        params.put("idMunicipio",   ListIds.getIdLocality());
        params.put("direccion",     etAddress.getText().toString());
        params.put("cp",            etZip.getText().toString());
        params.put("credencial",    etID.getText().toString());
        params.put("activo",        true);
        params.put("notifSubdistribuidor",true);

        if (strFileLocation != null) {
            File file = new File(strFileLocation);
            params.put("archivoContrato", file);
            params.put("archivoFoto",     file);
        }

        WebBridge.send("Agricultor.ashx?insert", params, getString(R.string.txt_sending), this, this);

    }

    public void clickCamera(View v) {
        takePhoto();
    }

    private void takePhoto() {

        if (ContextCompat.checkSelfPermission(AddFarmer.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            callCameraApp();
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(AddFarmer.this, "Sysbioscience necesita acceso a la memoria del teléfono para usar la cámara", Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE_RESULT);
        }
    }

    private void callCameraApp() {

        Intent callCameraIntent = new Intent();
        if (Build.VERSION.SDK_INT >= 23)
            callCameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
        else callCameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = null;

        try {
            photoFile = createImageFile();
        } catch (Exception e) {
            Log.e("EXCEPTION", e.toString());
        }

        callCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        //hasPermissionInManifest(getBaseContext(), "android.permission.CAMERA");
        startActivityForResult(callCameraIntent, START_CAMERA);

    }

    File createImageFile() throws IOException {
        String strDate = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nameImage = "image_" + strDate + "_";

        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(nameImage, ".jpg", storageDir);
        strFileLocation = image.getAbsolutePath();
        Log.e("DIRECORY", storageDir.toString());
        Log.e("STR FILE LOCATION", strFileLocation);
        Log.e("IMAGE", image.toString());
        return image;
    }

    public void setImageFarmer() {


        if (Build.VERSION.SDK_INT >= 23) {
            Log.e("FILE LOCATION", CurrentDataFarmer.getStrFileFarmer());
            Glide.with(this).load(CurrentDataFarmer.getStrFileFarmer()).into(addImageFarmer);
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
            addImageContract.setImageURI(Uri.parse(CurrentDataFarmer.getStrFileContract()));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        if (requestCode == REQUEST_EXTERNAL_STORAGE_RESULT) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callCameraApp();
            } else {
                Toast.makeText(AddFarmer.this, "Sysbioscience necesita acceso a la memoria para usar la cámara", Toast.LENGTH_SHORT).show();
            }
        }else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == START_CAMERA && resultCode == RESULT_OK) {

            if(imageSelector == 1){
                CurrentDataFarmer.setStrFileFarmer(strFileLocation);
                setImageFarmer();

            }else if (imageSelector == 2){
                CurrentDataFarmer.setStrFileContract(strFileLocation);
                setImageContract();
            }else
                return;

        }

        if (requestCode == 1) {
            if (resultCode == 1) {
                ListIds.setIdState(data.getIntExtra("idState", 0));
                btnState.setText(data.getStringExtra("nameState"));

            }
        }

        if (requestCode == 2) {
            if (resultCode == 2) {
                ListIds.setIdLocality(data.getIntExtra("idMunicipality", 0));
                btnMunicipality.setText(data.getStringExtra("municipalityName"));
            }
        }

    }

    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {
        Log.e("JSON SUCCESS", json.toString());

        try {
            boolean status = json.getInt("ResponseCode") == 200;
            if (status) {
                Toast.makeText(AddFarmer.this, getString(R.string.txt_success_farmer), Toast.LENGTH_SHORT).show();
                ListIds.clear();
                finish();
            } else {
                String error = json.getString("Errors");
                new AlertDialog.Builder(this).setTitle(R.string.txt_error).setMessage(error).setNeutralButton(R.string.bt_close, null).show();
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
