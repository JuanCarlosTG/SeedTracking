package com.kreativeco.sysbioscience;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by noisedan on 4/6/15.
 */
public class SectionActivity extends Activity {


	/*------------*/
    /* PROPERTIES */

    static public int STATUS_BAR_COLOR = Color.parseColor("#009FDD");
    static public int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

	/*----------------*/
	/* CUSTOM METHODS */

    public void setStatusBarColor(int color) {

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {

            View statusBar = (View) findViewById(R.id.status_bar);
            if (statusBar == null) return;
            statusBar.setVisibility(View.GONE);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            View statusBar = (View) findViewById(R.id.status_bar);
            if (statusBar == null) return;

            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int statusBarHeight = getStatusBarHeight();
            statusBar.getLayoutParams().height = statusBarHeight;
            statusBar.setBackgroundColor(color);

        }


    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void setTitle(String title) {
        TextView txtTitle = (TextView) findViewById(R.id.txt_title);
        if (txtTitle != null) {
            txtTitle.setText(title);
        }
    }



	/*--------------*/
	/* CLICK EVENTS */

    public void clickBack(View v) {
        finish();
        overridePendingTransition(R.anim.slide_right_from, R.anim.slide_right);
    }

    @Override
    public void onBackPressed() {
        clickBack(null);
    }

}
