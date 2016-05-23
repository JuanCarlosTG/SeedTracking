package com.kreativeco.sysbioscience.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.utils.WebBridge;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class BlogActivity extends Activity implements WebBridge.WebBridgeListener{

    private RecyclerView recyclerViewBlog;
    private ImageButton btnBackHeader;
    private TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        overridePendingTransition(R.anim.slide_left_from, R.anim.slide_left);

        recyclerViewBlog = (RecyclerView) findViewById(R.id.recycler_view_blog);

        //btnBackHeader = (ImageButton) findViewById(R.id.btn_back_login);
        btnBackHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                BlogActivity.this.overridePendingTransition(R.anim.slide_right_from, R.anim.slide_right);
            }
        });

        txtTitle = (TextView) findViewById(R.id.txt_title);
        txtTitle.setText("Invitados");

        recyclerViewBlog.setHasFixedSize(false);
        RecyclerView.LayoutManager rvLayoutManager = new LinearLayoutManager(this);
        recyclerViewBlog.setLayoutManager(rvLayoutManager);

        WebBridge.send("/news", "Cargando", this, this);

    }

    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {
        Log.e("json", json.toString());
        try {
            if (json.getBoolean("success")) {
                JSONArray jsonArrayBlog = json.getJSONArray("data");
                //RecyclerView.Adapter rvAdapter = new BlogElementAdapter(jsonArrayBlog, this);
                //recyclerViewBlog.setAdapter(rvAdapter);
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

    public void clickBack(View v) {
        finish();
        overridePendingTransition(R.anim.slide_right_from, R.anim.slide_right);
    }

    @Override
    public void onBackPressed() {
        clickBack(null);
    }

    @Override
    public void onWebBridgeFailure(String url, String response) {
        Log.e("JSON", response);
    }
}
