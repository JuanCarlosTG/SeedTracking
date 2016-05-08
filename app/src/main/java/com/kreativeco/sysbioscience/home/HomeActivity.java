package com.kreativeco.sysbioscience.home;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kreativeco.sysbioscience.News;
import com.kreativeco.sysbioscience.farmers.Farmers;
import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.SectionActivity;
import com.kreativeco.sysbioscience.login.profile.Profile;
import com.kreativeco.sysbioscience.utils.User;

public class HomeActivity extends SectionActivity{

    public ImageButton btnMenu;

    private DrawerLayout mDrawer;
    private ListView mDrawerOptions;
    CustomMenuAdapter adapterActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        overridePendingTransition(R.anim.fade_in, R.anim.static_motion);
        setStatusBarColor(SectionActivity.STATUS_BAR_COLOR);

        adapterActivity = new CustomMenuAdapter(this, R.layout.custom_menu_item, ItemsMenu.getCustomListView());

        mDrawerOptions = (ListView) findViewById(R.id.left_drawer);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        View header = getLayoutInflater().inflate(R.layout.header_list, null);
        View footer = getLayoutInflater().inflate(R.layout.footer_list, null);
        View statusBarList = header.findViewById(R.id.status_bar_list);

        TextView txtHeaderList = (TextView) header.findViewById(R.id.txt_username);
        String userName = User.get("Nombre", this) + " " + User.get("ApellidoP", this);
        txtHeaderList.setText(userName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (statusBarList == null) return;
            statusBarList.setVisibility(View.GONE);
        }else{
            statusBarList.getLayoutParams().height = getStatusBarHeight();
        }

        final int marginTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 200, this.getResources().getDisplayMetrics());
        final int marginBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, this.getResources().getDisplayMetrics());

        //final int marginLeft = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 60, homeActivity.getResources().getDisplayMetrics());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        RelativeLayout rlParent = (RelativeLayout) footer.findViewById(R.id.rl_parent);
        params.setMargins(0, marginTop, 0, marginBottom);
        rlParent.setLayoutParams(params);

        mDrawerOptions.addHeaderView(header);
        mDrawerOptions.addFooterView(footer);
        mDrawerOptions.setAdapter(adapterActivity);


        btnMenu = (ImageButton) findViewById(R.id.i_btn_header);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer(mDrawerOptions);
            }
        });

        final Farmers farmers = new Farmers();
        getFragmentManager().beginTransaction().add(R.id.flContent, farmers).commit();

        setTitle("Agricultores");

        mDrawerOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                if (position == 1) {
                    News news = new News();
                    getFragmentManager().beginTransaction().replace(R.id.flContent, news).commit();
                    setTitle("Noticias");
                } else if (position == 2) {
                    getFragmentManager().beginTransaction().replace(R.id.flContent, farmers).commit();
                    setTitle("Agricultores");
                } else if (position == 3) {
                    Profile profile = new Profile();
                    setTitle("Perfil");
                    getFragmentManager().beginTransaction().replace(R.id.flContent, profile).commit();
                } else if (position == 4) {
                    setTitle("Acerca De");
                    AboutFragment about = new AboutFragment();
                    getFragmentManager().beginTransaction().replace(R.id.flContent, about).commit();
                }
                mDrawer.closeDrawers();

            }
        });

    }

    public void closeDrawer(View view){
        mDrawer.closeDrawers();
    }

}
