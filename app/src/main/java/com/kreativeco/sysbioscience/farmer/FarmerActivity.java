package com.kreativeco.sysbioscience.farmer;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.SectionActivity;
import com.kreativeco.sysbioscience.farmer.currentdatas.CurrentDataFarmer;
import com.kreativeco.sysbioscience.farmer.datafarmer.DataFragment;
import com.kreativeco.sysbioscience.utils.ListIds;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FarmerActivity extends SectionActivity {

    RelativeLayout rlHeader;
    LinearLayout llTabHeader;
    RadioGroup radioGroup;
    private JSONObject jsonObjectData;
    TextView txtTitle;

    private static final int START_CAMERA = 0;
    private static final int REQUEST_EXTERNAL_STORAGE_RESULT = 0;
    private int imageSelector;
    String strFileLocation = null;
    public ViewPager viewPager;
    public FragmentAdapterFarmer fragmentAdapterFarmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        setStatusBarColor(SectionActivity.STATUS_BAR_COLOR);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        fragmentAdapterFarmer = new FragmentAdapterFarmer
                (getFragmentManager(), 4);
        viewPager.setAdapter(fragmentAdapterFarmer);
        viewPager.setOffscreenPageLimit(1);

        rlHeader =(RelativeLayout) findViewById(R.id.rl_header);
        llTabHeader = (LinearLayout) findViewById(R.id.ll_tab_header);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        ImageButton headerBackButton = (ImageButton) findViewById(R.id.i_btn_header);
        headerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_right_from, R.anim.slide_right);
            }
        });

        ViewTreeObserver vto = rlHeader.getViewTreeObserver();
        if(vto.isAlive()){
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    create();
                    if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        rlHeader.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        rlHeader.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            });
        }

        radioGroup = (RadioGroup) findViewById(R.id.rg_tab);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButtonSelect = (RadioButton) findViewById(checkedId);
                String strTag = radioButtonSelect.getTag().toString();

                switch (strTag){
                    case "0":
                        viewPager.setCurrentItem(0);
                        break;
                    case "1":
                        viewPager.setCurrentItem(1);
                        break;
                    case "2":
                        viewPager.setCurrentItem(2);
                        break;
                    case "3":
                        viewPager.setCurrentItem(3);
                        break;
                    default:
                        break;
                }
            }
        });


        Intent intent = getIntent();
        if(intent != null){
            String json = intent.getStringExtra("jsonData");
            handleJSON(json);
            try {
                jsonObjectData = new JSONObject(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            int option = intent.getIntExtra("option", 0);

            switch (option){

                case 0:
                    viewPager.setCurrentItem(0);
                    radioGroup.check(R.id.radio_button_0);
                    break;
                case 1:
                    viewPager.setCurrentItem(1);
                    radioGroup.check(R.id.radio_button_1);
                    break;
                case 2:
                    viewPager.setCurrentItem(2);
                    radioGroup.check(R.id.radio_button_2);
                    break;
                case 3:
                    viewPager.setCurrentItem(3);
                    radioGroup.check(R.id.radio_button_3);
                    break;
                default:
                    break;
            }

        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){

                    case 0:
                        radioGroup.check(R.id.radio_button_0);
                        break;
                    case 1:
                        radioGroup.check(R.id.radio_button_1);
                        break;
                    case 2:
                        radioGroup.check(R.id.radio_button_2);
                        break;
                    case 3:
                        radioGroup.check(R.id.radio_button_3);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void create() {
        rlHeader.getHeight();
        height   = rlHeader.getHeight();
        llTabHeader.getLayoutParams().height = height + (height/2);
        Log.e("sizeHeader", height + "");

    }

    public void handleJSON(String json){
        if(!json.isEmpty()){
            try {
                jsonObjectData = new JSONObject(json);

                CurrentDataFarmer.setFarmerName(jsonObjectData.getString("Nombre"));
                CurrentDataFarmer.setFarmerLastNameA(jsonObjectData.getString("ApellidoP"));
                CurrentDataFarmer.setFarmerLastNameB(jsonObjectData.getString("ApellidoM"));
                CurrentDataFarmer.setFarmerFullName(jsonObjectData.getString("NombreCompleto"));
                CurrentDataFarmer.setFarmerCompany(jsonObjectData.getString("RazonSocial"));
                CurrentDataFarmer.setFarmerRFC(jsonObjectData.getString("RFC"));
                CurrentDataFarmer.setFarmerPhone(jsonObjectData.getString("Telefono"));
                CurrentDataFarmer.setFarmerIdMunicipality(jsonObjectData.getInt("IdMunicipio"));
                CurrentDataFarmer.setFarmerNameMunicipality(jsonObjectData.getString("NombreMunicipio"));
                CurrentDataFarmer.setFarmerIdState(jsonObjectData.getInt("IdEstado"));
                CurrentDataFarmer.setFarmerNameState(jsonObjectData.getString("NombreEstado"));
                CurrentDataFarmer.setFarmerAddress(jsonObjectData.getString("Direccion"));
                CurrentDataFarmer.setFarmerZip(jsonObjectData.getString("CP"));
                CurrentDataFarmer.setFarmerIdCard(jsonObjectData.getString("Credencial"));
                CurrentDataFarmer.setFarmerPhotoFile(jsonObjectData.getString("ArchivoFoto"));
                CurrentDataFarmer.setFarmerContract(jsonObjectData.getString("ArchivoContrato"));
                CurrentDataFarmer.setFarmerAssignTypeSeeds(jsonObjectData.getBoolean("SemillaPorAsignar"));
                CurrentDataFarmer.setFarmerNotify(jsonObjectData.getBoolean("NotifSubdistribuidor"));
                CurrentDataFarmer.setFarmerAssigns(jsonObjectData.getString("Asignaciones"));
                CurrentDataFarmer.setFarmerApplications(jsonObjectData.getString("Aplicaciones"));
                CurrentDataFarmer.setFarmerId(jsonObjectData.getInt("Id"));
                CurrentDataFarmer.setFarmerIdUserType(jsonObjectData.getInt("IdTipoUsuario"));
                CurrentDataFarmer.setFarmerUserType(jsonObjectData.getString("TipoUsuario"));
                CurrentDataFarmer.setFarmerMail(jsonObjectData.getString("Mail"));
                CurrentDataFarmer.setFarmerPass(jsonObjectData.getString("Password"));
                CurrentDataFarmer.setFarmerToken(jsonObjectData.getString("Token"));
                CurrentDataFarmer.setFarmerCanLogin(jsonObjectData.getBoolean("CanLogin"));
                CurrentDataFarmer.setFarmerActive(jsonObjectData.getBoolean("Activo"));

                txtTitle.setText(jsonObjectData.getString("NombreCompleto"));
                Log.e("Jsondata", jsonObjectData.toString());
            }catch (JSONException jsonException){
                Log.e("JSON", jsonException.toString());
            }
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e("sizeHeader", height + "");
    }

    public void clickCamera(int imageSelector) {
        this.imageSelector = imageSelector;
        takePhoto();
    }

    private void takePhoto() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            callCameraApp();
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Sysbioscience necesita acceso a la memoria del teléfono para usar la cámara", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "Sysbioscience necesita acceso a la memoria para usar la cámara", Toast.LENGTH_SHORT).show();
            }
        }else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == START_CAMERA && resultCode == RESULT_OK) {

            FragmentManager fm = getFragmentManager();
            DataFragment fragment = (DataFragment) fragmentAdapterFarmer.getItem(1);

            if(imageSelector == 0){
                CurrentDataFarmer.setStrFileFarmer(strFileLocation);
                //fragment.setImageFarmer();

            }else if (imageSelector == 1){
                CurrentDataFarmer.setStrFileContract(strFileLocation);
                //fragment.setImageContract();
            }else
                return;

        }

        if (requestCode == 1) {
            if (resultCode == 1) {
                ListIds.setIdState(data.getIntExtra("idState", 0));
                ListIds.setStringState(data.getStringExtra("nameState"));

            }
        }

        if (requestCode == 2) {
            if (resultCode == 2) {
                ListIds.setIdLocality(data.getIntExtra("idMunicipality", 0));
                ListIds.setStringLocality(data.getStringExtra("municipalityName"));
            }
        }

    }

}
