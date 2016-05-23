package com.kreativeco.sysbioscience.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.SectionActivity;
import com.kreativeco.sysbioscience.farmers.HomeFarmer;
import com.kreativeco.sysbioscience.home.HomeActivity;
import com.kreativeco.sysbioscience.utils.User;
import com.kreativeco.sysbioscience.utils.WebBridge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends SectionActivity implements WebBridge.WebBridgeListener{

    String services[] = new String[]{"Login.ashx", ""};

    private EditText tvUser, tvPass;
    private TextView txtTitleNews;
    private TextView txtContent;
    private TextView txtDate;
    private ImageView imageNews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setStatusBarColor(SectionActivity.STATUS_BAR_COLOR);
        setTitle(getString(R.string.title_activity_login));

        tvUser = (EditText) findViewById(R.id.tv_user);
        tvPass = (EditText) findViewById(R.id.tv_pass);

        //tvUser.setText("jgarza@sistema.com");
        tvUser.setText("mig16.tar.gz@gmail.com");
        tvPass.setText("123456");

        txtTitleNews    = (TextView) findViewById(R.id.txt_title_news);
        txtContent      = (TextView) findViewById(R.id.txt_content);
        txtDate         = (TextView) findViewById(R.id.txt_date);
        imageNews       = (ImageView) findViewById(R.id.image_news);

        HashMap<String, Object> consultar = new HashMap<>();
        consultar.put("metodo", "consultar");
        WebBridge.send("Noticias.ashx", consultar, this, this);

    }

    public void runHome(View view) {

        ArrayList<String> errors = new ArrayList<String>();
        if (tvUser.getText().length() < 6) errors.add("El usuario no puede ser menor a 6 caracteres");
        if (tvPass.getText().length() < 6) errors.add("La contraseña debe tener al menos 6 caracteres");

        if (errors.size() != 0) {
            String msg = "";
            for (String s : errors) {
                msg += "- " + s + "\n";
            }
            new AlertDialog.Builder(this).setTitle(R.string.txt_error).setMessage(msg.trim()).setNeutralButton(R.string.bt_close, null).show();
            return;
        }

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("mail", tvUser.getText().toString());
        params.put("password", tvPass.getText().toString());
        params.put("metodo", "login");
        WebBridge.send(services[0], params, "Enviando", this, this);
    }

    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {

        Log.e("JSON SUCCESS", json.toString());

        try {
            boolean status = json.getInt("ResponseCode") == 200;
            if (status) {

                if(url.contains("Noticias")){

                    JSONArray jsonArrayNews = json.getJSONArray("Object");
                    printFirsNews(jsonArrayNews);
                    if(User.logged(this)){
                        HashMap<String, Object> params = new HashMap<String, Object>();
                        params.put("token", User.getToken(this));
                        params.put("metodo", "autoLogin");
                        WebBridge.send("Login.ashx", params, "Verificndo\nUsuario", this, this);
                    }

                }else{

                    User.logged(true, this, json.getJSONObject("Object").getString("Token"));

                    User.set("IdTipoUsuario", json.getJSONObject("Object").getString("IdTipoUsuario"), this);
                    User.set("NombreCompleto", json.getJSONObject("Object").getString("NombreCompleto"), this);
                    User.set("ApellidoP", json.getJSONObject("Object").getString("ApellidoP"), this);
                    User.set("ApellidoM", json.getJSONObject("Object").getString("ApellidoM"), this);
                    User.set("Nombre", json.getJSONObject("Object").getString("Nombre"), this);
                    User.set("Mail", json.getJSONObject("Object").getString("Mail"), this);
                    User.set("TipoUsuario", json.getJSONObject("Object").getString("TipoUsuario"), this);
                    User.set("Id", json.getJSONObject("Object").getString("Id"), this);

                    if(User.get("TipoUsuario", this).equals("Subdistribuidor")){
                        //Log.e("TipoUsuario", User.get("TipoUsuario", this));
                        Intent home = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(home);
                        finish();
                    }else if (User.get("TipoUsuario", this).equals("Agricultor"))
                    {
                        Intent home = new Intent(LoginActivity.this, HomeFarmer.class);
                        startActivity(home);
                        finish();
                    }


                }
            }else if(json.getInt("ResponseCode") == 500){
                if (url.contains("Login.ashx")){
                    User.clear(this);

                    JSONArray errors = json.getJSONArray("Errors");
                    ArrayList<String> errorArray = new ArrayList<String>();

                    for (int i = 0; i <errors.length(); i++){

                        errorArray.add(errors.getJSONObject(i).getString("Message"));

                    }

                    if (errorArray.size() != 0) {
                        String msg = "";
                        for (String s : errorArray) {
                            msg += "- " + s + "\n";
                        }
                        new AlertDialog.Builder(this).setTitle(R.string.txt_error).setMessage(msg.trim()).setNeutralButton(R.string.bt_close, null).show();

                    }

                }
            } else {
                String error = json.getJSONObject("Errors").getString("600");
                new AlertDialog.Builder(this).setTitle(R.string.txt_error).setMessage(error).setNeutralButton(R.string.bt_close, null).show();
            }

        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

    }

    private void printFirsNews(JSONArray jsonArrayNews) {
        try {
            JSONObject jsonObjectNews = jsonArrayNews.getJSONObject(0);

            String strTitle = jsonObjectNews.getString("Titulo");
            String strImage = jsonObjectNews.getString("UrlImagen");
            String strContent = jsonObjectNews.getString("Contenido");
            String strDate = jsonObjectNews.getString("FechaPublicacion");

            txtTitleNews.setText(strTitle);
            txtContent.setText(strTitle);
            txtDate.setText(strDate);

            Glide.with(this).load(strImage).into(imageNews);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void recoverPass(View view){
        Intent recoverPass = new Intent(LoginActivity.this, RecoverPassActivity.class);
        startActivity(recoverPass);
    }

    @Override
    public void onWebBridgeFailure(String url, String response) {
        Log.e("JSON FAILURE", response.toString());
    }


}
