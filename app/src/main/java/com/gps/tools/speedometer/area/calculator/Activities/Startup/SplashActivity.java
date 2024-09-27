package com.gps.tools.speedometer.area.calculator.Activities.Startup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.gps.tools.speedometer.area.calculator.Activities.MainActivity;
import com.gps.tools.speedometer.area.calculator.AddsManager.AdCallBack;
import com.gps.tools.speedometer.area.calculator.AddsManager.AdmobInterstitialAd;
import com.gps.tools.speedometer.area.calculator.AddsManager.AdmobSplashAppOpenAd;
import com.gps.tools.speedometer.area.calculator.AddsManager.TinyDB;
import com.gps.tools.speedometer.area.calculator.Model.firebasedatamodel;
import com.gps.tools.speedometer.area.calculator.PermissionScreen.PrivacyPolicyScreenActivity;

import com.gps.tools.speedometer.area.calculator.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SplashActivity extends AppCompatActivity implements AdCallBack {

    private static final String TAG = "SplashActivityInof";
    private List<firebasedatamodel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        MobileAds.initialize(this);
        AudienceNetworkAds.initialize(this);

        Log.d(TAG, "onCreate: Splash");
        if (new TinyDB(this).isAppPurchased()){
            showSplashScreen();
        }else {
            AdmobSplashAppOpenAd.preloadAd(SplashActivity.this , this);
            AdmobInterstitialAd.preloadInterstitialAd(SplashActivity.this);
        }

        //TODO add test device id
        List<String> testDeviceIds = Arrays.asList("2EB7EA63EE74542BE69A254D6C140537");
        RequestConfiguration configuration = new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
       MobileAds.setRequestConfiguration(configuration);

    }

    private void showSplashScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToNextScreen();
            }
        }, 3000);
    }

    private boolean checkForPrivacyScreen() {
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean("my_pref", false);
    }

    @Override
    public void onAdLoaded() {
        AdmobSplashAppOpenAd.showSplashAppOpenAd(SplashActivity.this);
    }

    @Override
    public void onFailedToLoadAd(@NonNull LoadAdError loadAdError) {
        navigateToNextScreen();
    }

    private void navigateToNextScreen(){
        if (checkForPrivacyScreen()) {
            //...........intent to home Activity
            Intent homeIntent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(homeIntent);
            finish();

        } else {
            //.... move to privacy
            Intent privacyIntent = new Intent(SplashActivity.this, PrivacyPolicyScreenActivity.class);
            startActivity(privacyIntent);
            finish();
        }
    }

}

