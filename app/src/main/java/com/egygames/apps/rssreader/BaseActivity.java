package com.egygames.apps.rssreader;
/**
 * Parent of all activities on the application.
 */

import android.content.Context;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BaseActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //overriding the animation for activities transitions
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_translate);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        //Inject CalligraphyContextWrapper into Context
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onResume() {
        super.onResume();
        setDefaultLocale();// set arabic as default locale
    }

    /**
     * Setting Arabic as the default local
     */
    public void setDefaultLocale() {
        Locale locale = new Locale("ar");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }



}
