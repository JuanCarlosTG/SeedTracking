package com.kreativeco.sysbioscience.utils;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.SectionActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JuanC on 25/04/2016.
 */
public class ListStatesRequest extends SectionActivity implements WebBridge.WebBridgeListener{

    private RecyclerView recyclerList;
    private static final int RESULT_OK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        overridePendingTransition(R.anim.slide_up, R.anim.slide_up);
        setStatusBarColor(SectionActivity.STATUS_BAR_COLOR);

        ImageButton headerBackButton = (ImageButton) findViewById(R.id.i_btn_header);
        headerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_down, R.anim.slide_down);
            }
        });

        recyclerList = (RecyclerView) findViewById(R.id.recycler_list);
        recyclerList.setHasFixedSize(false);

        RecyclerView.LayoutManager rvLayoutManager = new LinearLayoutManager(getBaseContext());
        recyclerList.setLayoutManager(rvLayoutManager);

        /*Intent i = new Intent();
        i.putExtra("list_name_user", tv_name_user.getText().toString());
        i.putExtra("register_id", id);
        setResult(RESULT_OK, i);
        finish();
        Log.e("Presionaste a: ", tv_name_user.getText().toString());*/

        HashMap<String, Object> params = new HashMap<>();
        params.put("metodo", "consultarEstadosSolicitud");
        WebBridge.send("Catalogos.ashx?states", params, "Obteniendo estados", this, this);

    }

    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {
        try {
            boolean status = json.getInt("ResponseCode") == 200;
            if (status) {
                JSONArray states = json.getJSONArray("Object");
                RecyclerView.Adapter rvAdapter = new StatesElementAdapter(states,this);
                recyclerList.setAdapter(rvAdapter);
            } else if (json.getInt("ResponseCode") == 500) {

                JSONArray errors = json.getJSONArray("Errors");
                ArrayList<String> errorArray = new ArrayList<String>();

                for (int i = 0; i < errors.length(); i++) {

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

        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }
    }

    @Override
    public void onWebBridgeFailure(String url, String response) {

    }



    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                result_listUser = data.getStringExtra("list_name_user");
                register_id = data.getStringExtra("register_id");

                et_name_full = (CustomEditTextRegular) v.findViewById(R.id.et_name_full);
                et_name_full.setKeyListener(null);
                et_name_full.setText(result_listUser);

            }
        }
    }*/
}
