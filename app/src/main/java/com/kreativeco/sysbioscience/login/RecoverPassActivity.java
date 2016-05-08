package com.kreativeco.sysbioscience.login;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.SectionActivity;
import com.kreativeco.sysbioscience.utils.WebBridge;

import org.json.JSONObject;

import java.util.ArrayList;
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
        overridePendingTransition(R.anim.slide_left_from, R.anim.slide_left);


        ImageButton headerBackButton = (ImageButton) findViewById(R.id.i_btn_header);
        headerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_right_from, R.anim.slide_right);
            }
        });

        txtRecoverMail = (EditText) findViewById(R.id.txt_recover_mail);
    }

    public void recoverPass(View view){

        ArrayList<String> errors = new ArrayList<String>();
        if (txtRecoverMail.getText().length() < 1) errors.add("Mail no válido");

        if (errors.size() != 0) {
            String msg = "";
            for (String s : errors) {
                msg += "- " + s + "\n";
            }
            new AlertDialog.Builder(this).setTitle(R.string.txt_error).setMessage(msg.trim()).setNeutralButton(R.string.bt_close, null).show();
            return;
        }
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
