package com.kreativeco.sysbioscience.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kreativeco.sysbioscience.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kreativeco on 22/02/16.
 */
public class VarietiesElementAdapter extends RecyclerView.Adapter<VarietiesElementAdapter.VarietiesViewHolder> {

    private JSONArray varieties;
    Activity activity;
    private static Activity varietiesActivity;
    private static Context context;
    private static final int RESULT_OK = 3;

    public static class VarietiesViewHolder extends RecyclerView.ViewHolder {

        public TextView txtVarieties;
        public int idVariety;
        public int idCountry;
        public String nameVariety;

        public VarietiesViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.putExtra("idVariety", idVariety);
                    i.putExtra("nameVariety", nameVariety);
                    varietiesActivity.setResult(RESULT_OK, i);
                    varietiesActivity.finish();
                }
            });
            txtVarieties = (TextView) itemView.findViewById(R.id.txt_varieties);
        }

    }

    public VarietiesElementAdapter(JSONArray varieties, Activity activity) {
        this.varieties = varieties;
        this.activity = activity;
        varietiesActivity = this.activity;
        this.context = activity.getBaseContext();
    }

    @Override
    public VarietiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View varietiesView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_varieties, parent, false);
        return new VarietiesViewHolder(varietiesView);
    }

    @Override
    public void onBindViewHolder(final VarietiesViewHolder holder, int position) {

        try {
            JSONObject variety = varieties.getJSONObject(position);

            String varietyName = variety.getString("VariedadDesc");
            int varietyId  = variety.getInt("Id");
          
            holder.txtVarieties.setText(varietyName);
            holder.nameVariety = varietyName;
            holder.idVariety = varietyId;

        } catch (JSONException jsonE) {

        }

    }

    @Override
    public int getItemCount() {
        return varieties.length();
    }



}
