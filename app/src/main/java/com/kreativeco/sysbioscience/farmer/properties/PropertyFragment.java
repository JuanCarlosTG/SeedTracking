package com.kreativeco.sysbioscience.farmer.properties;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.kreativeco.sysbioscience.R;

/**
 * Created by kreativeco on 01/02/16.
 */
public class PropertyFragment extends Fragment {

    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.property_fragment, null);

        return v;

    }

}
