package com.kreativeco.sysbioscience.home;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.kreativeco.sysbioscience.News;
import com.kreativeco.sysbioscience.farmers.Farmers;
import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.SectionActivity;
import com.kreativeco.sysbioscience.login.profile.Profile;
import com.kreativeco.sysbioscience.utils.User;

public class HomeActivity extends SectionActivity {

    public ImageButton btnMenu;

    private DrawerLayout mDrawer;
    private ListView mDrawerOptions;
    private String[] adapter = {"Noticias", "Agricultores ", "Perfil", "Parcelas"};
    ArrayAdapter<String> adapterArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        overridePendingTransition(R.anim.fade_in, R.anim.static_motion);
        setStatusBarColor(SectionActivity.STATUS_BAR_COLOR);

        adapterArray = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, adapter);

        mDrawerOptions = (ListView) findViewById(R.id.left_drawer);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        View header = getLayoutInflater().inflate(R.layout.header_list, null);
        View footer = getLayoutInflater().inflate(R.layout.footer_list, null);
        View statusBarList = header.findViewById(R.id.status_bar_list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (statusBarList == null) return;
            statusBarList.setVisibility(View.GONE);
        }else{
            statusBarList.getLayoutParams().height = getStatusBarHeight();
        }

        mDrawerOptions.addHeaderView(header);
        mDrawerOptions.addFooterView(footer);
        mDrawerOptions.setAdapter(adapterArray);



        btnMenu = (ImageButton) findViewById(R.id.i_btn_header);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer(mDrawerOptions);
            }
        });

        final Farmers farmers = new Farmers();
        getFragmentManager().beginTransaction().add(R.id.flContent, farmers).commit();

        setTitle("Noticias");

        mDrawerOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                if (position == 1) {
                    News news = new News();
                    getFragmentManager().beginTransaction().replace(R.id.flContent, news).commit();
                } else if (position == 2) {
                    getFragmentManager().beginTransaction().replace(R.id.flContent, farmers).commit();
                } else if (position == 3) {
                    Profile profile = new Profile();
                    getFragmentManager().beginTransaction().replace(R.id.flContent, profile).commit();
                } else if (position == 4) {

                }

                mDrawer.closeDrawers();

            }
        });

        Log.e("IdTipoUsuario", User.get("IdTipoUsuario", this));

    }

    public void closeDrawer(View view){
        mDrawer.closeDrawers();
    }

}
