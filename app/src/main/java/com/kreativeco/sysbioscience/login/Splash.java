package com.kreativeco.sysbioscience.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.SectionActivity;
import com.kreativeco.sysbioscience.home.HomeActivity;
import com.kreativeco.sysbioscience.utils.User;

public class Splash extends SectionActivity {

    public Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        activity = this;
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(1500);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{

                    if (User.logged(activity)) {
                        Intent intent = new Intent(Splash.this, HomeActivity.class);
                        startActivityForResult(intent, 1);
                    } else {
                        Intent i = new Intent(Splash.this, LoginActivity.class);
                        startActivity(i);
                    }

                }
            }
        };
        timerThread.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
