package com.kreativeco.sysbioscience.farmer.properties;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.farmer.FarmerActivity;
import com.kreativeco.sysbioscience.farmer.purchases.AddPurchase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kreativeco on 22/02/16.
 */
public class PropertiesElementAdapter extends RecyclerView.Adapter<PropertiesElementAdapter.PropertiesViewHolder> {

    private JSONArray properties;
    Activity activity;
    private static Activity propertiesActivity;
    private static Context context;

    public static class PropertiesViewHolder extends RecyclerView.ViewHolder {

        public TextView propertiesName;
        public TextView propertiesLocalities;
        public Button propertiesActions;
        public JSONObject propertiesData;


        public PropertiesViewHolder(final View itemView) {
            super(itemView);

            propertiesName       = (TextView) itemView.findViewById(R.id.txt_property_name);
            propertiesLocalities = (TextView) itemView.findViewById(R.id.txt_localities);
            propertiesActions = (Button) itemView.findViewById(R.id.btn_properties_actions);
            propertiesActions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMenuActions(v);
                }
            });
        }

        public void showMenuActions(View view){

            final Dialog dialog = new Dialog(propertiesActivity);
            dialog.setContentView(R.layout.alert_dialog_sales);
            dialog.setTitle("Selecciona una opción");
            dialog.show();

            Button btnClose = (Button) dialog.findViewById(R.id.btn_cancel);
            Button btnEdit = (Button) dialog.findViewById(R.id.btn_edit);
            Button btnDelete = (Button) dialog.findViewById(R.id.btn_delete);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                    Intent intent = new Intent(propertiesActivity, AddPurchase.class);
                    intent.putExtra("jsonData", propertiesData.toString());
                    intent.putExtra("option", 1);
                    propertiesActivity.startActivity(intent);
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();}
            });

            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();}
            });

        }

        public void runProperties(int option, JSONObject jsonObjectdata){
            Intent intent = new Intent(propertiesActivity, FarmerActivity.class);
            intent.putExtra("option", option);
            intent.putExtra("jsonData", jsonObjectdata.toString());
            propertiesActivity.startActivity(intent);
        }

    }

    public PropertiesElementAdapter(JSONArray properties, Activity activity) {
        this.properties = properties;
        this.activity = activity;
        propertiesActivity = this.activity;
        this.context = activity.getBaseContext();
    }

    @Override
    public PropertiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View propertiesView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_property, parent, false);
        return new PropertiesViewHolder(propertiesView);
    }

    @Override
    public void onBindViewHolder(final PropertiesViewHolder holder, int position) {

        try {
            JSONObject property = properties.getJSONObject(position);

            String propertyDate = property.getString("Nombre");
            String propertyVariety = property.getString("NombreMunicipio") + ". " + property.getString("NombreEstado");

            holder.propertiesName.setText(propertyDate);
            holder.propertiesLocalities.setText(propertyVariety);

            holder.propertiesData = property;

        } catch (JSONException jsonE) {

        }

    }

    @Override
    public int getItemCount() {
        return properties.length();
    }



}
