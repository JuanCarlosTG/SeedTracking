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
public class PossessionElementAdapter extends RecyclerView.Adapter<PossessionElementAdapter.PossessionsViewHolder> {

    private JSONArray possessions;
    Activity activity;
    private static Activity possessionsActivity;
    private static Context context;
    private static final int RESULT_OK = 9;

    public static class PossessionsViewHolder extends RecyclerView.ViewHolder {

        public TextView txtSate;
        public int idPossession;
        public String namePossession;

        public PossessionsViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ListIds.setIdPossession(idPossession);
                    ListIds.setNamePossession(namePossession);

                    Intent i = new Intent();
                    possessionsActivity.setResult(RESULT_OK, i);
                    possessionsActivity.finish();
                    possessionsActivity.overridePendingTransition(R.anim.slide_down, R.anim.slide_down);
                }
            });
            txtSate       = (TextView) itemView.findViewById(R.id.txt_possessions);
        }

    }

    public PossessionElementAdapter(JSONArray possessions, Activity activity) {
        this.possessions = possessions;
        this.activity = activity;
        possessionsActivity = this.activity;
        this.context = activity.getBaseContext();
    }

    @Override
    public PossessionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View possessionsView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_possessions, parent, false);
        return new PossessionsViewHolder(possessionsView);
    }

    @Override
    public void onBindViewHolder(final PossessionsViewHolder holder, int position) {

        try {
            JSONObject possession = possessions.getJSONObject(position);

            String possessionName = possession.getString("name");
            int possessionId  = possession.getInt("id");

            holder.txtSate.setText(possessionName);
            holder.idPossession = possessionId;
            holder.namePossession = possessionName;

        } catch (JSONException jsonE) {

        }

    }

    @Override
    public int getItemCount() {
        return possessions.length();
    }



}
