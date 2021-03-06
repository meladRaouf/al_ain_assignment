package com.egygames.apps.rssreader;

/**
 * Extends Android application and used mainly to set the global used font.
 */

import android.app.Application;

import com.activeandroid.ActiveAndroid;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyApplication extends Application {

    /**
     * Setting global font for the application.
     * and Initializing the database.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("DroidKufi-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        ActiveAndroid.initialize(this);//Initializing the database.

    }

}