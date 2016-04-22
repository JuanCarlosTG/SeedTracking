package com.kreativeco.sysbioscience;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by kreativeco on 01/02/16.
 */
public class Section2 extends Fragment {

    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.section_2, null);

        Spinner spinnerState            = (Spinner) v.findViewById(R.id.state_spinner);
        Spinner spinnerMunicipality     = (Spinner) v.findViewById(R.id.municipality_spinner);
        Spinner spinnerRegion           = (Spinner) v.findViewById(R.id.region_spinner);
        Spinner spinnerRepresentative   = (Spinner) v.findViewById(R.id.representative_spinner);

        ArrayAdapter<CharSequence> adapterState         = ArrayAdapter.createFromResource(getContext(), R.array.str_array_farmer_state, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterMunicipality  = ArrayAdapter.createFromResource(getContext(), R.array.str_array_farmer_municipality, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterRegion        = ArrayAdapter.createFromResource(getContext(), R.array.str_array_farmer_region, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterRepresentative= ArrayAdapter.createFromResource(getContext(), R.array.str_array_farmer_representative, android.R.layout.simple_spinner_item);

        adapterState.setDropDownViewResource(android.R.layout.simple_spinner_item);
        adapterMunicipality.setDropDownViewResource(android.R.layout.simple_spinner_item);
        adapterRegion.setDropDownViewResource(android.R.layout.simple_spinner_item);
        adapterRepresentative.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinnerState.setAdapter(adapterState);
        spinnerMunicipality.setAdapter(adapterMunicipality);
        spinnerRegion.setAdapter(adapterRegion);
        spinnerRepresentative.setAdapter(adapterRepresentative);
        
        return v;

    }

}
