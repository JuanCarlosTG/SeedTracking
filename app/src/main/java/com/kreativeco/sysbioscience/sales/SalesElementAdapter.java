package com.kreativeco.sysbioscience.sales;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.kreativeco.sysbioscience.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kreativeco on 22/02/16.
 */
public class SalesElementAdapter extends RecyclerView.Adapter<SalesElementAdapter.SalesViewHolder> {

    private JSONArray sales;
    Activity activity;
    private static Activity salesActivity;
    private static Context context;

    public static class SalesViewHolder extends RecyclerView.ViewHolder {

        public TextView salesBill;
        public TextView salesDate;
        public TextView salesAssigned;
        public Button salesActions;
        public JSONObject salesData;


        public SalesViewHolder(final View itemView) {
            super(itemView);

            salesBill       = (TextView) itemView.findViewById(R.id.tv_bill);
            salesDate       = (TextView) itemView.findViewById(R.id.tv_date);
            salesAssigned   = (TextView) itemView.findViewById(R.id.tv_assigned);
            salesActions = (Button) itemView.findViewById(R.id.btn_sales_actions);
            salesActions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMenuActions(v);
                }
            });
        }

        public void showMenuActions(View view){

            final Dialog dialog = new Dialog(salesActivity);
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

        public void runSales(int option, JSONObject jsonObjectdata){
            Intent intent = new Intent(salesActivity, Sales.class);
            intent.putExtra("option", option);
            intent.putExtra("jsonData", jsonObjectdata.toString());
            salesActivity.startActivity(intent);
        }

    }

    public SalesElementAdapter(JSONArray sales, Activity activity) {
        this.sales = sales;
        this.activity = activity;
        salesActivity = this.activity;
        this.context = activity.getBaseContext();
    }

    @Override
    public SalesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View salesView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_sales, parent, false);
        return new SalesViewHolder(salesView);
    }

    @Override
    public void onBindViewHolder(final SalesViewHolder holder, int position) {

        try {
            JSONObject sale = sales.getJSONObject(position);

            String saleDate = sale.getString("Fecha");
            String saleVariety = sale.getString("NoConvenio") + " (" + sale.getString("Variedad") + ")";
            String saleSeed = sale.getString("Semilla");

            holder.salesBill.setText(saleVariety);
            holder.salesDate.setText(saleDate);
            holder.salesAssigned.setText(saleSeed);
            holder.salesData = sale;


        } catch (JSONException jsonE) {

        }

    }

    @Override
    public int getItemCount() {
        return sales.length();
    }



}
