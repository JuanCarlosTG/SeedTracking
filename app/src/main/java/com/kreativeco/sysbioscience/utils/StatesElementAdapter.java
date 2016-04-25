package com.kreativeco.sysbioscience.utils;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.sales.AddSell;
import com.kreativeco.sysbioscience.sales.Sales;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kreativeco on 22/02/16.
 */
public class StatesElementAdapter extends RecyclerView.Adapter<StatesElementAdapter.StatesViewHolder> {

    private JSONArray states;
    Activity activity;
    private static Activity statesActivity;
    private static Context context;
    private static final int RESULT_OK = 1;

    public static class StatesViewHolder extends RecyclerView.ViewHolder {

        public TextView txtSate;
        public int idState;
        public int idCountry;
        public String nameState;

        public StatesViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.putExtra("idState", idState);
                    i.putExtra("nameState", nameState);
                    statesActivity.setResult(RESULT_OK, i);
                    statesActivity.finish();
                }
            });
            txtSate       = (TextView) itemView.findViewById(R.id.txt_states);
        }

    }

    public StatesElementAdapter(JSONArray states, Activity activity) {
        this.states = states;
        this.activity = activity;
        statesActivity = this.activity;
        this.context = activity.getBaseContext();
    }

    @Override
    public StatesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View statesView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_states, parent, false);
        return new StatesViewHolder(statesView);
    }

    @Override
    public void onBindViewHolder(final StatesViewHolder holder, int position) {

        try {
            JSONObject state = states.getJSONObject(position);

            String stateName = state.getString("NombreEstado");
            String stateCountry = state.getString("NombrePais");
            int stateIdCountry = state.getInt("IdPais");
            int stateId  = state.getInt("Id");

            holder.txtSate.setText(stateName);
            holder.idCountry = stateIdCountry;
            holder.idState = stateId;
            holder.nameState = stateName;

        } catch (JSONException jsonE) {

        }

    }

    @Override
    public int getItemCount() {
        return states.length();
    }



}
