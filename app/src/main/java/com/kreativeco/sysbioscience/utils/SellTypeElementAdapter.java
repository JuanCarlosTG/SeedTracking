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
public class SellTypeElementAdapter extends RecyclerView.Adapter<SellTypeElementAdapter.SellTypesViewHolder> {

    private JSONArray sellTypes;
    Activity activity;
    private static Activity sellTypesActivity;
    private static Context context;
    private static final int RESULT_OK = 4;

    public static class SellTypesViewHolder extends RecyclerView.ViewHolder {

        public TextView txtSate;
        public int idSellType;
        public String nameSellType;

        public SellTypesViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.putExtra("id", idSellType);
                    i.putExtra("name", nameSellType);
                    sellTypesActivity.setResult(RESULT_OK, i);
                    sellTypesActivity.finish();
                    sellTypesActivity.overridePendingTransition(R.anim.slide_down, R.anim.slide_down);
                }
            });
            txtSate       = (TextView) itemView.findViewById(R.id.txt_sell_types);
        }

    }

    public SellTypeElementAdapter(JSONArray sellTypes, Activity activity) {
        this.sellTypes = sellTypes;
        this.activity = activity;
        sellTypesActivity = this.activity;
        this.context = activity.getBaseContext();
    }

    @Override
    public SellTypesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View sellTypesView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_sell_types, parent, false);
        return new SellTypesViewHolder(sellTypesView);
    }

    @Override
    public void onBindViewHolder(final SellTypesViewHolder holder, int position) {

        try {
            JSONObject sellType = sellTypes.getJSONObject(position);

            String sellTypeName = sellType.getString("name");
            int sellTypeId  = sellType.getInt("id");

            holder.txtSate.setText(sellTypeName);
            holder.idSellType = sellTypeId;
            holder.nameSellType = sellTypeName;

        } catch (JSONException jsonE) {

        }

    }

    @Override
    public int getItemCount() {
        return sellTypes.length();
    }



}
