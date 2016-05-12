package com.kreativeco.sysbioscience.farmers;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kreativeco.sysbioscience.News;
import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.SectionActivity;
import com.kreativeco.sysbioscience.home.AboutFragment;
import com.kreativeco.sysbioscience.home.CustomMenuAdapter;
import com.kreativeco.sysbioscience.home.ItemsMenu;
import com.kreativeco.sysbioscience.login.LoginActivity;
import com.kreativeco.sysbioscience.login.profile.Profile;
import com.kreativeco.sysbioscience.utils.User;

import java.net.FileNameMap;

/**
 * Created by omar on 5/7/16.
 */
public class HomeFarmer extends SectionActivity {

    RelativeLayout rlHeader;
    LinearLayout llTabHeader;
    TextView txtTitle;
    RadioGroup radioGroup;

    public ImageButton btnMenu;

    private DrawerLayout mDrawer;
    private ListView mDrawerOptions;
    CustomMenuAdapter adapterActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_farmer);
        setStatusBarColor(SectionActivity.STATUS_BAR_COLOR);

        rlHeader = (RelativeLayout) findViewById(R.id.rl_header);
        txtTitle = (TextView) findViewById(R.id.txt_title);

        ViewTreeObserver vto = rlHeader.getViewTreeObserver();
        if (vto.isAlive()) {
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    create();
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        rlHeader.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        rlHeader.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            });
        }

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

        final FragmentHomeFarmer farmerHome = new FragmentHomeFarmer();
        final News news = new News();
        final Profile profile = new Profile();
        final AboutFragment aboutFragment = new AboutFragment();
        getFragmentManager().beginTransaction().replace(R.id.flContent, farmerHome).commit();

        setTitle("Agricultores");

        mDrawerOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                if (position == 1) {

                    getFragmentManager().beginTransaction().replace(R.id.flContent, news).commit();
                    setTitle("Noticias");
                } else if (position == 2) {
                    getFragmentManager().beginTransaction().replace(R.id.flContent, farmerHome).commit();
                    setTitle("Agricultor");
                } else if (position == 3) {
                    setTitle("Perfil");
                    getFragmentManager().beginTransaction().replace(R.id.flContent, profile).commit();
                } else if (position == 4) {
                    setTitle("Acerca De");
                    getFragmentManager().beginTransaction().replace(R.id.flContent, aboutFragment).commit();
                }else if(position == 5){
                    askForLogout();
                }
                mDrawer.closeDrawers();

            }
        });
    }


    private void create() {
        rlHeader.getHeight();
        height = rlHeader.getHeight();
//        llTabHeader.getLayoutParams().height = height + (height / 2);
        Log.e("sizeHeader", height + "");
    }

    public void closeDrawer(View view){
        mDrawer.closeDrawers();
    }

    public void askForLogout(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.alert_dialog_logout);
        dialog.setTitle("¿Está seguro que quiere cerrar la sesión?");
        dialog.show();

        Button btnClose = (Button) dialog.findViewById(R.id.btn_cancel);
        Button btnEdit = (Button) dialog.findViewById(R.id.btn_logout);


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                User.clear(HomeFarmer.this);
                Intent login = new Intent(HomeFarmer.this, LoginActivity.class);
                startActivity(login);
                finish();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }
}


