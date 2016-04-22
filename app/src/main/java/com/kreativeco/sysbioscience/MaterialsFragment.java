package com.kreativeco.sysbioscience;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kreativeco on 01/02/16.
 */
public class MaterialsFragment extends Fragment {

    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.sales_fragment, null);

        return v;

    }

    public void addBuy(){
        Intent addBuy = new Intent(getActivity(), AddBuy.class);
        startActivity(addBuy);
        //Toast.makeText(getActivity(), "ADD FARMER", Toast.LENGTH_LONG).show();
    }
}
