package com.kreativeco.sysbioscience;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

public class AddProperty extends AppCompatActivity{

    CoordinatorLayout rootLayout;
    private ImageView imgToolIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        imgToolIcon = (ImageView) toolbar.findViewById(R.id.imgToolIcon);
        imgToolIcon.setImageResource(R.drawable.profile_logo);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rootLayout = (CoordinatorLayout) findViewById(R.id.root_layout);

    }

}
