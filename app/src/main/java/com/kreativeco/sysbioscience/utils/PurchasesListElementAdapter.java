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
public class PurchasesListElementAdapter extends RecyclerView.Adapter<PurchasesListElementAdapter.PurchasesViewHolder> {

    private JSONArray purchases;
    Activity activity;
    private static Activity purchasesActivity;
    private static Context context;
    private static final int RESULT_OK = 5;

    public static class PurchasesViewHolder extends RecyclerView.ViewHolder {

        public TextView txtSate;
        public int idPurchase;
        public int idPurchaseVariety;
        public String numberPurchase;
        public String varietyPurchase;

        public PurchasesViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();

                    i.putExtra("NoConvenio", numberPurchase);
                    i.putExtra("Variedad", varietyPurchase);
                    i.putExtra("Id", idPurchase);
                    i.putExtra("IdVariedad", idPurchaseVariety);

                    ListIds.setNamePurchase(numberPurchase);
                    ListIds.setVarietyPurchase(varietyPurchase);
                    ListIds.setIdPurchase(idPurchase);
                    ListIds.setIdPurchaseVariety(idPurchaseVariety);

                    purchasesActivity.setResult(RESULT_OK, i);
                    purchasesActivity.finish();
                    purchasesActivity.overridePendingTransition(R.anim.slide_down, R.anim.slide_down);

                }
            });
            txtSate = (TextView) itemView.findViewById(R.id.txt_purchases);
        }

    }

    public PurchasesListElementAdapter(JSONArray purchases, Activity activity) {
        this.purchases = purchases;
        this.activity = activity;
        purchasesActivity = this.activity;
        this.context = activity.getBaseContext();
    }

    @Override
    public PurchasesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View purchasesView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_purchases, parent, false);
        return new PurchasesViewHolder(purchasesView);
    }

    @Override
    public void onBindViewHolder(final PurchasesViewHolder holder, int position) {

        try {
            JSONObject purchase = purchases.getJSONObject(position);

            String purchaseNumber = purchase.getString("NoConvenio");
            String purchaseVariety = purchase.getString("Variedad");
            int purchaseId  = purchase.getInt("Id");
            int purchaseVarietyId = purchase.getInt("IdVariedad");

            holder.txtSate.setText(purchaseNumber);
            holder.varietyPurchase = purchaseVariety;
            holder.numberPurchase = purchaseNumber;
            holder.idPurchase = purchaseId;
            holder.idPurchaseVariety = purchaseVarietyId;

        } catch (JSONException jsonE) {

        }

    }

    @Override
    public int getItemCount() {
        return purchases.length();
    }



}
