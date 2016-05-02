package com.kreativeco.sysbioscience.utils;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.SectionActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by JuanC on 25/04/2016.
 */
public class ListSeedType extends SectionActivity{

    private RecyclerView recyclerStates;
    private static final int RESULT_OK = 4;

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


        recyclerStates = (RecyclerView) findViewById(R.id.recycler_list);
        recyclerStates.setHasFixedSize(false);

        RecyclerView.LayoutManager rvLayoutManager = new LinearLayoutManager(getBaseContext());
        recyclerStates.setLayoutManager(rvLayoutManager);

        JSONObject data1 = new JSONObject();
        JSONObject data2 = new JSONObject();
        JSONObject data3 = new JSONObject();
        JSONObject data4 = new JSONObject();
        try {
            data1.put("id", 1);
            data1.put("name", "Siembra");
            data2.put("id", 2);
            data2.put("name", "Resiembra 1");
            data3.put("id", 3);
            data3.put("name", "Resiembra 2");
            data4.put("id", 4);
            data4.put("name", "Resiembra 3");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray seedType = new JSONArray();
        seedType.put(data1);
        seedType.put(data2);
        seedType.put(data3);
        seedType.put(data4);

        RecyclerView.Adapter rvAdapter = new SeedTypeElementAdapter(seedType, this);
        recyclerStates.setAdapter(rvAdapter);

    }

}
