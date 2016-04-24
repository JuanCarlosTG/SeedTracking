package com.kreativeco.sysbioscience.sales;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kreativeco.sysbioscience.AddAssign;
import com.kreativeco.sysbioscience.R;

/**
 * Created by kreativeco on 01/02/16.
 */
public class LicenseFragment extends Fragment {

    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.license_fragment, null);

        return v;

    }

    public void addBuy(){
        Intent addBuy = new Intent(getActivity(), AddAssign.class);
        startActivity(addBuy);
        //Toast.makeText(getActivity(), "ADD FARMER", Toast.LENGTH_LONG).show();
    }

}
