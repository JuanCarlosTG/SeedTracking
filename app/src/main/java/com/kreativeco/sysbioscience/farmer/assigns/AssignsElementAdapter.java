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
public class AssignsElementAdapter extends RecyclerView.Adapter<AssignsElementAdapter.AssignsViewHolder> {

    private JSONArray assigns;
    Activity activity;
    private static Activity assignsActivity;
    private static Context context;

    public static class AssignsViewHolder extends RecyclerView.ViewHolder {

        public TextView assignsProperty;
        public TextView assignsIdAssign;
        public TextView assignsVariety;
        public Button assignsActions;
        public JSONObject assignsData;


        public AssignsViewHolder(final View itemView) {
            super(itemView);

            assignsProperty       = (TextView) itemView.findViewById(R.id.txt_property);
            assignsIdAssign       = (TextView) itemView.findViewById(R.id.txt_id_assign);
            assignsVariety   = (TextView) itemView.findViewById(R.id.txt_variety);
            assignsActions = (Button) itemView.findViewById(R.id.btn_assigns_actions);
            assignsActions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMenuActions(v);
                }
            });
        }

        public void showMenuActions(View view){

            final Dialog dialog = new Dialog(assignsActivity);
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
                    Intent intent = new Intent(assignsActivity, AddAssign.class);
                    intent.putExtra("jsonData", assignsData.toString());
                    intent.putExtra("option", 1);
                    assignsActivity.startActivity(intent);
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

    public AssignsElementAdapter(JSONArray assigns, Activity activity) {
        this.assigns = assigns;
        this.activity = activity;
        assignsActivity = this.activity;
        this.context = activity.getBaseContext();
    }

    @Override
    public AssignsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View assignsView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_assigns, parent, false);
        return new AssignsViewHolder(assignsView);
    }

    @Override
    public void onBindViewHolder(final AssignsViewHolder holder, int position) {

        try {
            JSONObject assign = assigns.getJSONObject(position);

            String type = assign.getInt("IdCompra") == 1 ? "compra)": "donación)";
            String assignProperty = assign.getString("Predio") +" ("+ type;
            String assignVariety = assign.getString("Variedad")
                    + " - " + assign.getString("CantidadKG") + " Kg - "
                    + assign.getString("SuperficieSiembra") + " Km2";
            String assignIdUserAssign = assign.getString("IdUsuarioAsignacion");


            holder.assignsVariety.setText(assignVariety);
            holder.assignsProperty.setText(assignProperty);
            holder.assignsIdAssign.setText(assignIdUserAssign);
            holder.assignsData = assign;

            if (assign.getInt("IdCompra") != 1){
                holder.assignsActions.setEnabled(false);
                holder.assignsActions.setAlpha(0.5f);
            }

        } catch (JSONException jsonE) {

        }

    }

    @Override
    public int getItemCount() {
        return assigns.length();
    }



}
