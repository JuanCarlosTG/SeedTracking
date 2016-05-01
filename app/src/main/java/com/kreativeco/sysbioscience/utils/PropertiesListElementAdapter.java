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
public class PropertiesListElementAdapter extends RecyclerView.Adapter<PropertiesListElementAdapter.PropertiesViewHolder> {

    private JSONArray properties;
    Activity activity;
    private static Activity propertiesActivity;
    private static Context context;
    private static final int RESULT_OK = 6;

    public static class PropertiesViewHolder extends RecyclerView.ViewHolder {

        public TextView txtProperty;
        public int idProperty;
        public String nameProperty;

        public PropertiesViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();

                    ListIds.setNameProperty(nameProperty);
                    ListIds.setIdProperty(idProperty);

                    propertiesActivity.setResult(RESULT_OK, i);
                    propertiesActivity.finish();
                    propertiesActivity.overridePendingTransition(R.anim.slide_down, R.anim.slide_down);

                }
            });
            txtProperty = (TextView) itemView.findViewById(R.id.txt_properties);
        }

    }

    public PropertiesListElementAdapter(JSONArray properties, Activity activity) {
        this.properties = properties;
        this.activity = activity;
        propertiesActivity = this.activity;
        this.context = activity.getBaseContext();
    }

    @Override
    public PropertiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View propertiesView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_properties, parent, false);
        return new PropertiesViewHolder(propertiesView);
    }

    @Override
    public void onBindViewHolder(final PropertiesViewHolder holder, int position) {

        try {
            JSONObject property = properties.getJSONObject(position);

            String propertyName = property.getString("Nombre");
            int propertyId  = property.getInt("Id");

            holder.txtProperty.setText(propertyName);
            holder.nameProperty = propertyName;
            holder.idProperty = propertyId;

        } catch (JSONException jsonE) {

        }

    }

    @Override
    public int getItemCount() {
        return properties.length();
    }

}
