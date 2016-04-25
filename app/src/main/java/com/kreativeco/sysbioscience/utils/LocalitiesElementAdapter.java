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
public class LocalitiesElementAdapter extends RecyclerView.Adapter<LocalitiesElementAdapter.LocalitiesViewHolder> {

    private JSONArray localities;
    Activity activity;
    private static Activity localitiesActivity;
    private static Context context;
    private static final int RESULT_OK = 2;

    public static class LocalitiesViewHolder extends RecyclerView.ViewHolder {

        public TextView txtSate;
        public int idLocality;
        public int idCountry;
        public String nameLocality;

        public LocalitiesViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.putExtra("idMunicipality", idLocality);
                    i.putExtra("municipalityName", nameLocality);

                    ListIds.setStringLocality(nameLocality);
                    ListIds.setIdLocality(idLocality);

                    localitiesActivity.setResult(RESULT_OK, i);
                    localitiesActivity.finish();
                    localitiesActivity.overridePendingTransition(R.anim.slide_down, R.anim.slide_down);

                }
            });
            txtSate = (TextView) itemView.findViewById(R.id.txt_localities);
        }

    }

    public LocalitiesElementAdapter(JSONArray localities, Activity activity) {
        this.localities = localities;
        this.activity = activity;
        localitiesActivity = this.activity;
        this.context = activity.getBaseContext();
    }

    @Override
    public LocalitiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View localitiesView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_localities, parent, false);
        return new LocalitiesViewHolder(localitiesView);
    }

    @Override
    public void onBindViewHolder(final LocalitiesViewHolder holder, int position) {

        try {
            JSONObject locality = localities.getJSONObject(position);

            String localityName = locality.getString("NombreMunicipio");
            int localityId  = locality.getInt("Id");
          
            holder.txtSate.setText(localityName);
            holder.nameLocality = localityName;
            holder.idLocality = localityId;

        } catch (JSONException jsonE) {

        }

    }

    @Override
    public int getItemCount() {
        return localities.length();
    }



}
