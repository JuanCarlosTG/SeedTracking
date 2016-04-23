package com.kreativeco.sysbioscience.farmers;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.Sales;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kreativeco on 22/02/16.
 */
public class FarmerElementAdapter extends RecyclerView.Adapter<FarmerElementAdapter.FarmerViewHolder> {

    private JSONArray farmers;
    Activity activity;
    private static Context context;

    public static class FarmerViewHolder extends RecyclerView.ViewHolder {
        public ImageView farmerImage;
        public TextView farmerName;
        public TextView farmerSite;
        public Button farmerActions;

        public FarmerViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMenuActions(v);
                }
            });
            farmerImage = (ImageView) itemView.findViewById(R.id.iv_farmer);
            farmerName = (TextView) itemView.findViewById(R.id.tv_name);
            farmerSite = (TextView) itemView.findViewById(R.id.tv_site);
            farmerActions = (Button) itemView.findViewById(R.id.btn_farmer_actions);
            farmerActions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "Texto", Toast.LENGTH_SHORT).show();
                    showMenuActions(v);
                }
            });
        }

        public void showMenuActions(View v){

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View inflatedView = layoutInflater.inflate(R.layout.farmer_actions, null,false);

            final PopupWindow popWindow = new PopupWindow(context);
            popWindow.setContentView(inflatedView);

            popWindow.setBackgroundDrawable(new BitmapDrawable());
            popWindow.setFocusable(true);
            popWindow.setOutsideTouchable(true);

            // Getting a reference to Close button, and close the popup when clicked.
            Button close = (Button) inflatedView.findViewById(R.id.btn_close);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popWindow.dismiss();
                }
            });

            Button btBuy = (Button) inflatedView.findViewById(R.id.btn_buy);
            btBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    runSales();
                }
            });

            Button btnData = (Button) inflatedView.findViewById(R.id.btn_data);
            btnData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    runSales();
                }
            });

            Button btnLicence = (Button) inflatedView.findViewById(R.id.btn_licence);
            btnLicence.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    runSales();
                }
            });

            Button btnProperties = (Button) inflatedView.findViewById(R.id.btn_properties);
            btnProperties.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    runSales();
                }
            });



            popWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            popWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            popWindow.showAtLocation(v, Gravity.CENTER, v.getLeft(),v.getBottom());
        }

    }

    public FarmerElementAdapter(JSONArray farmers, Activity activity) {
        this.farmers = farmers;
        this.activity = activity;
        this.context = activity.getBaseContext();
    }

    public static void runSales() {
        Intent sales = new Intent(context, Sales.class);
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

        } catch (JSONException jsonE) {

        }

    }

    @Override
    public int getItemCount() {
        return farmers.length();
    }

    private void showMenuActions(){
    }

}
