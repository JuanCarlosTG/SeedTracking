package com.kreativeco.sysbioscience.farmers;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.CardView;
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
import com.kreativeco.sysbioscience.farmer.FarmerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kreativeco on 22/02/16.
 */
public class FarmerElementAdapter extends RecyclerView.Adapter<FarmerElementAdapter.FarmerViewHolder> {

    private JSONArray farmers;
    Activity activity;
    private static Activity farmerActivity;
    private static Context context;

    public static class FarmerViewHolder extends RecyclerView.ViewHolder {
        public ImageView farmerImage;
        public TextView farmerName;
        public TextView farmerSite;
        public Button farmerActions;
        public JSONObject farmerData;


        public FarmerViewHolder(final View itemView) {
            super(itemView);

            CardView cardView = (CardView) itemView;
            cardView.setCardBackgroundColor(Color.argb(0, 255, 255, 255));

            farmerImage = (ImageView) itemView.findViewById(R.id.iv_farmer);
            farmerName = (TextView) itemView.findViewById(R.id.tv_name);
            farmerSite = (TextView) itemView.findViewById(R.id.tv_site);
            farmerActions = (Button) itemView.findViewById(R.id.btn_farmer_actions);
            farmerActions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMenuActions(v);
                }
            });
        }

        public void showMenuActions(View view){

            final Dialog dialog = new Dialog(farmerActivity);
            dialog.setContentView(R.layout.alert_dialog_farmer);
            dialog.setTitle("Selecciona una opción");
            dialog.show();

            Button btnClose = (Button) dialog.findViewById(R.id.btn_cancel);
            Button btnData = (Button) dialog.findViewById(R.id.btn_data);
            Button btnSales = (Button) dialog.findViewById(R.id.btn_sales);
            Button btnAssigned = (Button) dialog.findViewById(R.id.btn_assigned);
            Button btnProperties = (Button) dialog.findViewById(R.id.btn_properties);
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {dialog.cancel();}
            });

            btnData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    runSales(0, farmerData);
                    dialog.cancel();
                }
            });

            btnSales.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {runSales(1, farmerData);
                    dialog.cancel();}
            });

            btnAssigned.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {runSales(2, farmerData);
                    dialog.cancel();}
            });

            btnProperties.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {runSales(3, farmerData);
                    dialog.cancel();}
            });

        }

        public void runSales(int option, JSONObject jsonObjectdata){
            Intent intent = new Intent(farmerActivity, FarmerActivity.class);
            intent.putExtra("option", option);
            intent.putExtra("jsonData", jsonObjectdata.toString());
            farmerActivity.startActivity(intent);
        }

    }

    public FarmerElementAdapter(JSONArray farmers, Activity activity) {
        this.farmers = farmers;
        this.activity = activity;
        farmerActivity = this.activity;
        this.context = activity.getBaseContext();
    }

    public static void runSales() {
        Intent sales = new Intent(context, FarmerActivity.class);
        sales.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(sales);
    }

    @Override
    public FarmerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View farmerView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_farmer, parent, false);
        return new FarmerViewHolder(farmerView);
    }

    @Override
    public void onBindViewHolder(final FarmerViewHolder holder, int position) {

        try {
            JSONObject farmer = farmers.getJSONObject(position);

            String farmerPhoto = farmer.getString("ArchivoFoto");
            String farmerAddress = farmer.getString("NombreMunicipio") + ", " + farmer.getString("NombreEstado");
            String farmerName = farmer.getString("Nombre") + " "
                    + farmer.getString("ApellidoP") + " "
                    + farmer.getString("ApellidoM");

            farmerAddress = farmerAddress.equals("null") ? "Sin dirección asignada" : farmerAddress;

            Bitmap src = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar);
            RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(context.getResources(), src);
            dr.setCornerRadius(Math.max(src.getWidth(), src.getHeight()) / 2.0f);

            Drawable drawable = (Drawable) dr;
            //imageView.setImageDrawable(dr);


            Glide.with(activity).load(farmerPhoto).asBitmap().centerCrop().error(drawable).into(new BitmapImageViewTarget(holder.farmerImage) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder.farmerImage.setImageDrawable(circularBitmapDrawable);
                }
            });

            holder.farmerName.setText(farmerName);
            holder.farmerSite.setText(farmerAddress);
            holder.farmerData = farmer;

            Log.e("Farmer DATA", holder.farmerData.toString());

        } catch (JSONException jsonE) {

        }

    }

    @Override
    public int getItemCount() {
        return farmers.length();
    }



}
