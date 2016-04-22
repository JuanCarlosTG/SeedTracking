package com.kreativeco.sysbioscience.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.SectionActivity;
import com.kreativeco.sysbioscience.utils.WebBridge;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by omar on 4/21/16.
 */
public class RecoverPassActivity extends SectionActivity implements WebBridge.WebBridgeListener{

    private EditText txtRecoverMail;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recover_pass);
        setStatusBarColor(SectionActivity.STATUS_BAR_COLOR);
        setTitle(getString(R.string.title_activity_recover));

        txtRecoverMail = (EditText) findViewById(R.id.txt_recover_mail);
    }

    public void recoverPass(View view){
        HashMap<String, Object> params = new HashMap<>();
        params.put("metodo", "recuperaPwd");
        params.put("mail", txtRecoverMail.getText().toString());
        WebBridge.send("Login", params, "realizando petición", this, this);
    }

    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {

    }

    @Override
    public void onWebBridgeFailure(String url, String response) {

    }
}
