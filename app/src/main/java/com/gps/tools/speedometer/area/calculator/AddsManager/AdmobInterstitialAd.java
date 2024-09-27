package com.gps.tools.speedometer.area.calculator.AddsManager;

import static com.gps.tools.speedometer.area.calculator.AddsManager.AppOpenResumeAd.isAppOpenAdShow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.gps.tools.speedometer.area.calculator.Activities.MainActivity;

import com.gps.tools.speedometer.area.calculator.Activities.applicationFirebaseClass;
import com.gps.tools.speedometer.area.calculator.PermissionScreen.PrivacyPolicyScreenActivity;
import com.gps.tools.speedometer.area.calculator.R;


public class AdmobInterstitialAd {
    static TinyDB db;
    public static Boolean isFirstCLickEnabled = false;
    AdView adview;
    public static InterstitialAd mInterstitialAd;
    public static String admob_interstitial_ad_id = applicationFirebaseClass.getStringFromResource(R.string.admob_interstitial_ad_id);
    public static String admob_banner_id = applicationFirebaseClass.getStringFromResource(R.string.admob_banner_id);
    public static String admob_medium_rectangle_banner_id = applicationFirebaseClass.getStringFromResource(R.string.admob_medium_rectangle_banner_id);

    public static String admob_app_resume_add = applicationFirebaseClass.getStringFromResource(R.string.admob_app_resume_add);

    static String admob_splash_app_open_ad_id = applicationFirebaseClass.getStringFromResource(R.string.admob_splash_app_open_ad_id);
    private String TAG = "ladkfjaskljflkaslk";
    public static Integer divider = 0;

    public static void preloadInterstitialAd(Activity activity) {
        db = new TinyDB(activity);
        if (!db.isAppPurchased()) {

            Log.d("adbhsghdbjs", "preloadInterstitialAd mInterstitialAd : " + mInterstitialAd);

            if (mInterstitialAd == null) {
                AdRequest adRequest = new AdRequest.Builder().build();
                InterstitialAd.load(activity, admob_interstitial_ad_id, adRequest, new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                        Log.d("nknknnknknk", "Ad loaded interstitial : ");
                    }
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                        mInterstitialAd = null;
                        Log.d("nknknnknknk", "Ad failed to load interstitial : " + adError.getMessage());
                        Log.d("nknknnknknk", "Ad failed to load interstitial : " + adError.getCode());
                    }
                });
            }
        }
    }



    private boolean checkForPrivacyScreen(Activity activity) {
        return PreferenceManager.getDefaultSharedPreferences(activity).getBoolean("my_pref", false);
    }

    private void navigateToNextScreen(Activity activity) {
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

    public static void gpsToolsBannerAdMob(ConstraintLayout constraintLayout, final Context context) {
        TinyDB db = new TinyDB(context);
        if (!db.isAppPurchased()) {
            AdView mAdView = new AdView(context);
            AdSize adSize = getBannerAdSize((Activity) context);
            mAdView.setAdSize(adSize);
            mAdView.setAdUnitId(admob_banner_id);

            try {
                mAdView.loadAd(new AdRequest.Builder().build());
            } catch (Exception e) {
                e.printStackTrace();
            }

            mAdView.setAdListener(new com.google.android.gms.ads.AdListener() {
                @Override
                public void onAdLoaded() {
                    Log.d("ConstantAdsLoadAdsB", "Admob banner loaded");
                    super.onAdLoaded();
                    try {
                        constraintLayout.addView(mAdView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    mAdView.destroy();
                    constraintLayout.setVisibility(View.GONE);
                    Log.d("ConstantAdsLoadAdsB", "Admob banner Failed" + loadAdError.getMessage());
                }
            });
        } else {
            constraintLayout.setVisibility(View.GONE);
        }
    }

    public static void gpsToolsAdMobMeduimBanner(final FrameLayout constraintLayout, final Context context) {
        TinyDB db = new TinyDB(context);
        if (!db.isAppPurchased()) {
            AdView mAdView = new AdView(context);
            mAdView.setAdSize(AdSize.MEDIUM_RECTANGLE);
            mAdView.setAdUnitId(admob_medium_rectangle_banner_id);

            try {
                mAdView.loadAd(new AdRequest.Builder().build());
            } catch (Exception e) {
                e.printStackTrace();
            }

            mAdView.setAdListener(new com.google.android.gms.ads.AdListener() {
                @Override
                public void onAdLoaded() {
                    Log.d("ConstantAdsLoadAdsB", "Admob banner loaded");
                    super.onAdLoaded();
                    try {
                        constraintLayout.addView(mAdView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    mAdView.destroy();
                    constraintLayout.setVisibility(View.GONE);
                    Log.d("ConstantAdsLoadAdsB", "Admob banner Failed" + loadAdError.getMessage());
                }
            });
        } else {
            constraintLayout.setVisibility(View.GONE);
        }


    }

    private static AdSize getBannerAdSize(Activity activity) {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
    }


}
