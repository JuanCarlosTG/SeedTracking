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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.SectionActivity;
import com.kreativeco.sysbioscience.utils.User;
import com.kreativeco.sysbioscience.utils.WebBridge;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AddFarmer extends SectionActivity implements WebBridge.WebBridgeListener {

    private EditText etName, etLastNameA, etLastNameB, etEmail;
    private EditText etCell, etCompany, etRFC, etID, etAddress, etZip;
    private ImageView addIvFarmer;
    private ImageButton iBtnBackArrow;

    private static final int START_CAMERA = 0;
    private static final int REQUEST_EXTERNAL_STORAGE_RESULT = 0;
    String strFileLocation = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_farmer);
        overridePendingTransition(R.anim.slide_left_from, R.anim.slide_left);
        setStatusBarColor(SectionActivity.STATUS_BAR_COLOR);

        iBtnBackArrow = (ImageButton) findViewById(R.id.i_btn_header);
        iBtnBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBack(v);
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

        addIvFarmer = (ImageView) findViewById(R.id.add_iv_farmer);

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

        if (errors.size() != 0) {
            String msg = "";
            for (String s : errors) {
                msg += "- " + s + "\n";
            }
            new AlertDialog.Builder(this).setTitle(R.string.txt_error).setMessage(msg.trim()).setNeutralButton(R.string.bt_close, null).show();
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
        params.put("token", User.getToken(this));
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

            Bitmap bm = BitmapFactory.decodeFile(strFileLocation);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 1, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();

            String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

            params.put("archivoContrato", encodedImage);
            params.put("archivoFoto",     encodedImage);
        }*/
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

            if (Build.VERSION.SDK_INT >= 23){
                Glide.with(this).load(strFileLocation).into(addIvFarmer);
                Toast.makeText(AddFarmer.this, "FOTO EXITOSA", Toast.LENGTH_SHORT).show();
            }else{
                Bitmap photo = BitmapFactory.decodeFile(strFileLocation);
                addIvFarmer.setImageURI(Uri.parse(strFileLocation));
            }

            //icImage.setFilePathOriginal(strFileLocation);
            //PhotoReport.setImageString(strFileLocation);

        }

    }

    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {
        Log.e("JSON SUCCESS", json.toString());

        try {
            boolean status = json.getInt("ResponseCode") == 200;
            if (status) {
                Toast.makeText(AddFarmer.this, getString(R.string.txt_success_farmer), Toast.LENGTH_SHORT).show();
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
