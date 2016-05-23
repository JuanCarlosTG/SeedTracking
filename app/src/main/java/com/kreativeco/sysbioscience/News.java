package com.kreativeco.sysbioscience;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kreativeco.sysbioscience.utils.User;
import com.kreativeco.sysbioscience.utils.WebBridge;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kreativeco on 01/02/16.
 */
public class News extends Fragment implements WebBridge.WebBridgeListener {

    View v;
    private RecyclerView recyclerViewBlog;
    TextView txtNoItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.activity_blog, null);

        recyclerViewBlog = (RecyclerView) v.findViewById(R.id.recycler_view_blog);

        txtNoItems = (TextView) v.findViewById(R.id.txt_no_items);
        txtNoItems.setVisibility(View.GONE);

        recyclerViewBlog.setHasFixedSize(false);
        RecyclerView.LayoutManager rvLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewBlog.setLayoutManager(rvLayoutManager);

        HashMap<String, Object> params = new HashMap<>();
        params.put("metodo", "consultar");
        WebBridge.send("Noticias.ashx", params, "Cargando", getActivity(), this);

        return v;

    }

    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {
        Log.e("json", json.toString());
        try {
            if (json.getInt("ResponseCode") == 200) {
                JSONArray jsonArrayBlog = json.getJSONArray("Object");
                if (jsonArrayBlog.length() == 0) {
                    txtNoItems.setVisibility(View.VISIBLE);
                    return;
                }
                RecyclerView.Adapter rvAdapter = new BlogElementAdapter(jsonArrayBlog, getActivity());
                recyclerViewBlog.setAdapter(rvAdapter);
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
                    new AlertDialog.Builder(getActivity()).setTitle(R.string.txt_error).setMessage(msg.trim()).setNeutralButton(R.string.bt_close, null).show();

                }
            }

        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }
    }


    @Override
    public void onWebBridgeFailure(String url, String response) {
        Log.e("JSON", response);
    }

}
