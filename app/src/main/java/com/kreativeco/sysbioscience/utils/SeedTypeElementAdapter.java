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
public class SeedTypeElementAdapter extends RecyclerView.Adapter<SeedTypeElementAdapter.SeedTypesViewHolder> {

    private JSONArray seedTypes;
    Activity activity;
    private static Activity seedTypesActivity;
    private static Context context;
    private static final int RESULT_OK = 8;

    public static class SeedTypesViewHolder extends RecyclerView.ViewHolder {

        public TextView txtSate;
        public int idSeedType;
        public String nameSeedType;

        public SeedTypesViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ListIds.setIdSeedType(idSeedType);
                    ListIds.setNameSeedType(nameSeedType);

                    Intent i = new Intent();

                    seedTypesActivity.setResult(RESULT_OK, i);
                    seedTypesActivity.finish();
                    seedTypesActivity.overridePendingTransition(R.anim.slide_down, R.anim.slide_down);
                }
            });
            txtSate       = (TextView) itemView.findViewById(R.id.txt_seed_types);
        }

    }

    public SeedTypeElementAdapter(JSONArray seedTypes, Activity activity) {
        this.seedTypes = seedTypes;
        this.activity = activity;
        seedTypesActivity = this.activity;
        this.context = activity.getBaseContext();
    }

    @Override
    public SeedTypesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View seedTypesView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_seed_types, parent, false);
        return new SeedTypesViewHolder(seedTypesView);
    }

    @Override
    public void onBindViewHolder(final SeedTypesViewHolder holder, int position) {

        try {
            JSONObject seedType = seedTypes.getJSONObject(position);

            String seedTypeName = seedType.getString("name");
            int seedTypeId  = seedType.getInt("id");

            holder.txtSate.setText(seedTypeName);
            holder.idSeedType = seedTypeId;
            holder.nameSeedType = seedTypeName;

        } catch (JSONException jsonE) {

        }

    }

    @Override
    public int getItemCount() {
        return seedTypes.length();
    }



}
