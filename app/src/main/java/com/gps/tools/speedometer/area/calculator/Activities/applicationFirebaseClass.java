package com.gps.tools.speedometer.area.calculator.Activities;

import android.app.Application;

import android.content.Context;
import androidx.multidex.MultiDex;

import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.gps.tools.speedometer.area.calculator.AddsManager.AppOpenResumeAd;
import com.gps.tools.speedometer.area.calculator.AddsManager.TinyDB;
import com.gps.tools.speedometer.area.calculator.BillingMethod.GPSToolsBillingHelper;

import java.util.Arrays;
import java.util.List;

public class applicationFirebaseClass extends Application {

    private AppOpenResumeAd appOpenAdX;

    private static applicationFirebaseClass GPSTools;

    public static String getStringFromResource(int id) {
        return GPSTools.getString(id);
    }
   private TinyDB db;
    @Override
    public void onCreate() {
        db = new TinyDB(this);
        super.onCreate();
        GPSTools = this;

        try {
            GPSToolsBillingHelper billingHelper = new GPSToolsBillingHelper(this);
            billingHelper.fetchGpsToolsPurchasedSubscription();
        }catch (Exception e){

        }

        if(!db.isAppPurchased()){

            appOpenAdX = new AppOpenResumeAd(this);
        }

        //   Fabric.with(this, new Crashlytics());
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }
}

