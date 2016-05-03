package com.kreativeco.sysbioscience;

import android.app.Application;
import android.support.multidex.MultiDex;

/**
 * Created by JuanC on 03/05/2016.
 */
public class SeedTrackingHelper extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(getBaseContext());

    }

}
