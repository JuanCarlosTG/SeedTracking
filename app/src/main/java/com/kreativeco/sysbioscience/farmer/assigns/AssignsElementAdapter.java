package com.kreativeco.sysbioscience.farmer.assigns;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kreativeco on 22/02/16.
 */
public class AssignsElementAdapter extends RecyclerView.Adapter<AssignsElementAdapter.LicensesViewHolder> {

    private JSONArray licenses;
    Activity activity;
    private static Activity licensesActivity;
    private static Context context;

    public static class LicensesViewHolder extends RecyclerView.ViewHolder {

        public TextView licensesProperty;
        public TextView licensesIdAssign;
        public TextView licensesVariety;
        public Button licensesActions;
        public JSONObject licensesData;


        public LicensesViewHolder(final View itemView) {
            super(itemView);

            licensesProperty       = (TextView) itemView.findViewById(R.id.txt_property);
            licensesIdAssign       = (TextView) itemView.findViewById(R.id.txt_id_assign);
            licensesVariety   = (TextView) itemView.findViewById(R.id.txt_variety);
            licensesActions = (Button) itemView.findViewById(R.id.btn_licenses_actions);
            licensesActions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMenuActions(v);
                }
            });
        }

        public void showMenuActions(View view){

            final Dialog dialog = new Dialog(licensesActivity);
            dialog.setContentView(R.layout.alert_dialog_licenses);
            dialog.setTitle("Selecciona una opción");
            dialog.show();

            Button btnClose = (Button) dialog.findViewById(R.id.btn_cancel);
            Button btnEdit = (Button) dialog.findViewById(R.id.btn_edit);
            Button btnDelete = (Button) dialog.findViewById(R.id.btn_delete);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                    Intent intent = new Intent(licensesActivity, AddAssign.class);
                    intent.putExtra("jsonData", licensesData.toString());
                    intent.putExtra("option", 1);
                    licensesActivity.startActivity(intent);
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

    }

    public AssignsElementAdapter(JSONArray licenses, Activity activity) {
        this.licenses = licenses;
        this.activity = activity;
        licensesActivity = this.activity;
        this.context = activity.getBaseContext();
    }

    @Override
    public LicensesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View licensesView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_licenses, parent, false);
        return new LicensesViewHolder(licensesView);
    }

    @Override
    public void onBindViewHolder(final LicensesViewHolder holder, int position) {

        try {
            JSONObject license = licenses.getJSONObject(position);

            String type = license.getInt("IdCompra") == 1 ? "compra)": "donación)";
            String licenseProperty = license.getString("Predio") +" ("+ type;
            String licenseVariety = license.getString("Variedad")
                    + " - " + license.getString("CantidadKG") + " Kg - "
                    + license.getString("SuperficieSiembra") + " Km2";
            String licenseIdUserAssign = license.getString("IdUsuarioAsignacion");


            holder.licensesVariety.setText(licenseVariety);
            holder.licensesProperty.setText(licenseProperty);
            holder.licensesIdAssign.setText(licenseIdUserAssign);
            holder.licensesData = license;

            if (license.getInt("IdCompra") != 1){
                holder.licensesActions.setEnabled(false);
                holder.licensesActions.setAlpha(0.5f);
            }

        } catch (JSONException jsonE) {

        }

    }

    @Override
    public int getItemCount() {
        return licenses.length();
    }



}
