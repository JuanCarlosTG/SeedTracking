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
public class PeriodsElementAdapter extends RecyclerView.Adapter<PeriodsElementAdapter.PeriodsViewHolder> {

    private JSONArray periods;
    Activity activity;
    private static Activity periodsActivity;
    private static Context context;
    private static final int RESULT_OK = 7;

    public static class PeriodsViewHolder extends RecyclerView.ViewHolder {

        public TextView txtSate;
        public int idPeriod;
        public String namePeriod;

        public PeriodsViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ListIds.setIdPeriod(idPeriod);
                    ListIds.setNamePeriod(namePeriod);

                    Intent i = new Intent();
                    i.putExtra("id", idPeriod);
                    i.putExtra("name", namePeriod);
                    periodsActivity.setResult(RESULT_OK, i);
                    periodsActivity.finish();
                    periodsActivity.overridePendingTransition(R.anim.slide_down, R.anim.slide_down);
                }
            });
            txtSate       = (TextView) itemView.findViewById(R.id.txt_sell_types);
        }

    }

    public PeriodsElementAdapter(JSONArray periods, Activity activity) {
        this.periods = periods;
        this.activity = activity;
        periodsActivity = this.activity;
        this.context = activity.getBaseContext();
    }

    @Override
    public PeriodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View periodsView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_sell_types, parent, false);
        return new PeriodsViewHolder(periodsView);
    }

    @Override
    public void onBindViewHolder(final PeriodsViewHolder holder, int position) {

        try {
            JSONObject period = periods.getJSONObject(position);

            String periodName = period.getString("name");
            int periodId  = period.getInt("id");

            holder.txtSate.setText(periodName);
            holder.idPeriod = periodId;
            holder.namePeriod = periodName;

        } catch (JSONException jsonE) {

        }

    }

    @Override
    public int getItemCount() {
        return periods.length();
    }



}
