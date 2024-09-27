package com.gps.tools.speedometer.area.calculator.AddsManager;


import static com.gps.tools.speedometer.area.calculator.AddsManager.AdmobInterstitialAd.admob_splash_app_open_ad_id;

import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.gps.tools.speedometer.area.calculator.Activities.MainActivity;
import com.gps.tools.speedometer.area.calculator.PermissionScreen.PrivacyPolicyScreenActivity;

public class AdmobSplashAppOpenAd {
    private static AppOpenAd mappOpenAd;
    private static TinyDB db;

    public static void preloadAd(Activity activity, AdCallBack adCallBack) {
        db = new TinyDB(activity);
        if (!db.isAppPurchased()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            AppOpenAd.load(activity, admob_splash_app_open_ad_id, adRequest, new AppOpenAd.AppOpenAdLoadCallback() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    mappOpenAd = null;
                    adCallBack.onFailedToLoadAd(loadAdError);
                    Log.d("bjhgjhjb", "onAdFailedToLoad : " + loadAdError.getMessage());
                    Log.d("bjhgjhjb", "onAdFailedToLoad : " + loadAdError.getCode());
                }

                @Override
                public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                    super.onAdLoaded(appOpenAd);
                    mappOpenAd = appOpenAd;
                    adCallBack.onAdLoaded();
                    Log.d("bjhgjhjb", "onAdLoaded : ");
                }
            });
        }

    }

    public static void showSplashAppOpenAd(Activity activity) {
        if (!db.isAppPurchased()) {
            if (mappOpenAd != null) {
                mappOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                    }
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        navigateToNextScreen(activity);
                        mappOpenAd = null;
                    }
                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        navigateToNextScreen(activity);
                        mappOpenAd = null;
                    }
                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                    }
                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                    }
                });
                mappOpenAd.show(activity);


            } else {
                navigateToNextScreen(activity);
            }
        } else {
            navigateToNextScreen(activity);
        }


    }

    private static boolean checkForPrivacyScreen(Activity activity) {
        return PreferenceManager.getDefaultSharedPreferences(activity).getBoolean("my_pref", false);
    }

    private static void navigateToNextScreen(Activity activity) {
        if (checkForPrivacyScreen(activity)) {
            //...........intent to home Activity
            Intent homeIntent = new Intent(activity, MainActivity.class);
            activity.startActivity(homeIntent);
            activity.finish();

        } else {
            //.... move to privacy
            Intent privacyIntent = new Intent(activity, PrivacyPolicyScreenActivity.class);
            activity.startActivity(privacyIntent);
            activity.finish();
        }
    }
}
