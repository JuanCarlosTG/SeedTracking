package com.kreativeco.sysbioscience;

import android.app.Application;
import android.support.multidex.MultiDex;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by JuanC on 03/05/2016.
 */
public class SeedTrackingHelper extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        MultiDex.install(getBaseContext());

    }

}
