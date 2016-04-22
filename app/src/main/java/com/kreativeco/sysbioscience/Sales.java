package com.kreativeco.sysbioscience;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Sales extends SectionActivity {

    TabLayout tabLayout;
    RelativeLayout rlHeader;
    LinearLayout llTabHeader;
    RadioGroup radioGroup;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        setStatusBarColor(SectionActivity.STATUS_BAR_COLOR);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        final FragmentAdapterSales fragmentAdapterSales = new FragmentAdapterSales
                (getFragmentManager(), 4);
        viewPager.setAdapter(fragmentAdapterSales);


        rlHeader =(RelativeLayout) findViewById(R.id.rl_header);
        llTabHeader = (LinearLayout) findViewById(R.id.ll_tab_header);

        ViewTreeObserver vto = rlHeader.getViewTreeObserver();
        if(vto.isAlive()){
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    create();
                    if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        rlHeader.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        rlHeader.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            });
        }

        radioGroup = (RadioGroup) findViewById(R.id.rg_tab);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });


    }

    private void create() {
        rlHeader.getHeight();
        height   = rlHeader.getHeight();
        llTabHeader.getLayoutParams().height = height + (height/2);
        Log.e("sizeHeader", height + "");

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e("sizeHeader", height + "");
    }

}
