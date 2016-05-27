package com.kreativeco.sysbioscience;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.kreativeco.sysbioscience.farmers.Farmers;
import com.kreativeco.sysbioscience.login.profile.Profile;
import com.kreativeco.sysbioscience.utils.WebBridge;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements WebBridge.WebBridgeListener {

    private DrawerLayout mDrawer;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view

        setupDrawerContent(nvDrawer);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.txt_farmer_1, R.string.txt_farmer_1) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setHomeAsUpIndicator(R.drawable.profile_ic_menu);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        //mDrawerToggle.setDrawerIndicatorEnabled(false);
        //mDrawerToggle.syncState();
        mDrawer.setDrawerListener(mDrawerToggle);

        Fragment fragment = null;

        Class fragmentClass;
        fragmentClass = News.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        setTitle("Noticias");
        /*HashMap<String, Object> params = new HashMap<>();
        params.put("tipoCatalogo", "estado");
        params.put("idpais", 135);
        WebBridge.send("getCatalog.ashx", params, getString(R.string.txt_sending), this, this);*/

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;

        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.news:
                fragmentClass = News.class;
                break;
            case R.id.farmers:
                fragmentClass = Farmers.class;
                break;
            case R.id.profile:
                fragmentClass = Profile.class;
                break;
            default:
                fragmentClass = News.class;
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item, update the title, and close the drawer
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Make sure this is the method with just `Bundle` as the signature

    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {

        //String strJSON = json.toString();
        //Log.e("JSON SUCCESS", strJSON);
    }

    @Override
    public void onWebBridgeFailure(String url, String response) {
        Log.e("JSON FAILURE", response);
    }
}


